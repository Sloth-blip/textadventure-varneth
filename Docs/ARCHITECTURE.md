# Core Architecture

The `GameLoop` acts as the central coordinator for the main gameplay phases:

- `GameStart`
- `ExplorationPhase` ↔ `DialogPhase`
- `CombatScene`

Game logic produces results and passes them back to the `GameLoop`.
The `GameLoop` can then forward them to the UI for presentation and decide how
the game should continue.

## Running game state

`GameStart` creates the player and world, then combines them in one
`GameState`. `GameLoop` receives that state instead of constructing a second
world or keeping the current room as independent mutable state.

`GameState` owns the player, canonical `WorldState`, stable current-room ID,
visited-room IDs, and story-flag IDs. `getCurrentRoom()` always resolves the ID
through `WorldState`. This preserves one mutable `Room` instance per ID and
provides the first explicit boundary for future save data.

Story flags record durable facts without putting presentation or provisional
scene structures into the state, for example `riddle.stone_door.solved`. Their
IDs are stable technical contracts; the first two placeholder riddles will help
determine the later scene, dialogue, decision, and effect content model.

### Riddle prototypes

The first prototype attaches a provisional `RiddleDefinition` to a
`RIDDLE` point of interest. `RiddleHandler` evaluates normalized console text
and changes only its durable story flag. `ExplorationPhase` coordinates the
prompt, existing reward pipeline, and POI-used state; the console menu contains
no solving or reward rules.

The second prototype separates acquired knowledge from environmental casting.
`SpellSealHandler` checks the knowledge flag, pays the selected
`AvailableSpell` through the existing player casting rules, validates spell ID
and source, sets the opened flag, and connects canonical rooms. A wrong spell is
still a real cast and therefore consumes its resource. The shared
`SpellConsoleMenu` presents the same available-spell options in combat and
exploration without deciding whether a spell solves the seal.

Text answers and immediate spell confirmation remain console stand-ins for
future mechanic-specific input and rune drawing. No general `RiddlePhase` or
permanent content schema is derived yet. The comparison and open transfer
questions are recorded in [RIDDLE-PROTOTYPES.md](RIDDLE-PROTOTYPES.md).

The loop's temporary `running` flag and presentation objects are control/UI
state and are deliberately not part of a persisted game state.

## Definition - State - Instance

### Definition
Stores immutable base data such as name, base stats, stats per level, and similar core values.

### State
Stores mutable runtime data such as level, current HP, and current calculated stats.

### Instance
Connects definition and state.

An instance should not introduce additional persistent fields beyond `definition` and `state`.
Its main responsibility is to provide the behavior and methods that operate on both.

The first inventory slice follows the same split:

- `PlayerState` extends actor runtime state with the player's item inventory.
- `MagicCrystalDefinition` contains stable identity, display text, magic type,
  and maximum charge.
- `MagicCrystalState` contains the remaining charge.
- `MagicCrystal` validates compatibility and changes its charge.
- `EquipmentDefinition` contains stable identity, display text, slot, and
  attribute modifiers.
- `Equipment` currently has no item-local mutable data, so it does not carry a
  fake empty state object. If durability or upgrades arrive, they belong in a
  dedicated `EquipmentState`.
- The mutable equipped relationship belongs to `PlayerState`, which references
  owned equipment by slot.

`AvailableSpell` is derived from learned spells, current player resource, and
usable inventory crystals. It is a transient combat option and is not persistent state.

## Inventory, equipment, and player-status boundary

`ExplorationAction.INVENTORY` displays the current inventory and can collect an
optional equipment selection. `InventoryConsoleRenderer` only renders generic
item identity and lore plus type-specific presentation details. It never changes
item or player state. `EquipmentConsoleMenu` also contains no equipment rules;
it returns the selected owned item to `GameLoop`.

`EquipmentHandler` owns the equip/unequip toggle, delegates slot mutation to
`Player`, and publishes an immutable `EquipmentChanged` result.
`PlayerState` is the single source of truth for the `EquipmentSlot -> Equipment`
relationship. Equipment has no duplicate `equipped` flag. Equipping another
item in an occupied slot replaces the previous item rather than stacking both.

`Player` adds modifiers from currently equipped items to its final Strength,
Intelligence, and Wisdom getters. Combat, spell damage, crystal efficiency, and
presentation therefore consume the same values. HP, resource, defense, and
durability modifiers are outside this first equipment slice.

`ExplorationAction.PLAYER_STATUS` remains read-only.
`PlayerStatusConsoleRenderer` displays progress, current and maximum resources,
final attributes, and all equipment slots; it does not repeat calculations in
the presentation layer.

A later graphical inventory and status screen replace these console components
while continuing to use the authoritative item instances and state from the
game core.

## Combat presentation boundary

`CombatScene` resolves game rules and publishes an immutable `CombatStateChanged`
snapshot at action boundaries: when combat begins, after a player action, and
after each enemy attack. The snapshot contains only the values a presentation
layer needs and does not expose mutable actors.

`CombatConsoleNarrator` renders those state changes for the console, while
`CombatConsoleMenu` remains responsible for player input. A later graphical UI
can subscribe to the same state event without moving combat rules into the UI.

## Reward pipeline

`Reward` is immutable content data containing lists of skills and items plus XP
and gold. Empty lists represent absent reward types; reward content does not use
`null` sentinels. Gold belongs to `PlayerState` alongside inventory and other
runtime state.

`RewardHandler` applies a complete reward to the player and publishes one
immutable `RewardGranted` event describing the values actually granted,
including aggregated level-ups. It contains no presentation code.
`RewardConsoleNarrator` turns that event into console text. Other presentation
layers can subscribe without duplicating reward rules.

## Skill progression

`xpPerCast` is stable spell content and is independent from mana or crystal
cost. A learned elemental spell keeps its `SkillState` and gains that XP only
after a cast has successfully paid its cost and resolved its effect. The cast
therefore uses the spell level it had before receiving XP.

`SkillProgressionHandler` ignores crystal-provided spells because those are
temporary derived options rather than learned persistent skills. For elemental
spells it updates the skill and publishes an immutable `SkillProgressed`
snapshot. `SkillProgressConsoleNarrator` renders the progress and level-up.

## Crystal casting efficiency

Wisdom normally changes crystal charge cost, not spell damage. The effective
cost is `max(1, ceil(baseCost * BASELINE_WISDOM / max(1, wisdom)))`, with a baseline
Wisdom of `10`. Wisdom below the baseline can make a spell cost more than its
base value; higher Wisdom lowers the integer charge cost.

The calculated value is stored in the derived `AvailableSpell`. The menu,
availability check, and payment therefore use the same cost. Elemental spells
continue to use their unchanged base cost from the player's personal resource.
A future Ur-magic inefficiency is a separate source modifier applied before
the Wisdom calculation.

A compatible crystal with positive charge can also offer a partial cast when
its remaining charge is below the effective cost. `AvailableSpell` freezes the
required cost, actual payment, selected crystal, and whether that crystal will
break. This keeps menu text, payment, and combat resolution on one calculation.

Partial damage is `round(fullDamage * payment / requiredCost)` and remains at
least one for a normally positive hit. The cast consumes all remaining charge
and removes the shattered crystal from inventory. Paying the exact required
amount is still a full cast: a crystal reduced exactly to zero remains in the
inventory so a later recharge system can reuse it.

When several crystals provide the same spell, selection prefers a crystal that
can pay the full cost. If all candidates are partial, it uses the crystal with
the highest remaining usable charge.
