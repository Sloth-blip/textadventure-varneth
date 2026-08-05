# Riddle prototypes

This document records observations from the first two placeholder riddle
prototypes. It is evidence for the upcoming content-model discussion, not a
finished scene, Notion, JSON, or `RiddlePhase` contract.

The initial authoring and Notion-to-repository handoff contract now lives in
[CONTENT-AUTHORING.md](CONTENT-AUTHORING.md). These prototype observations remain
the evidence used to refine its challenge sections after the first real scene.

## Prototype 1: learn a spell from a rune

Location: the unknown rune in the first development room.

Current console flow:

1. The POI presents placeholder rune context.
2. Free text temporarily stands in for drawing the rune.
3. A correct normalized answer sets
   `riddle.rune_book_pebbles.solved`.
4. The existing reward pipeline unlocks `pebbles` and grants the earth focus.
5. Repeating the interaction shows only its solved text.

The eventual graphical input may replace the spell-name text without moving
spell unlocking or reward rules into the UI.

## Prototype 2: knowledge and environmental casting

Locations: the displaced fire symbols in room 2 and the fire seal in room 3.

Current console flow:

1. Ordering the placeholder symbols sets
   `knowledge.fire_seal.sequence`.
2. Without that knowledge, the seal does not offer spell selection.
3. With the knowledge, the player chooses from currently available spells.
4. Casting always pays its normal resource or crystal cost, even when the spell
   is wrong for the seal.
5. Only `flamethrower` cast from a valid crystal opens the seal.
6. A valid partial crystal cast also opens it and destroys the exhausted
   crystal, preserving the established partial-cast rule.
7. Success sets `world.fire_seal.opened` and connects room 3 to the previously
   unreachable room 4 in both directions.

The future rune gesture belongs between spell selection and confirmed casting.
Cancelling before confirmation must remain free.

## Shared observations

Both prototypes need:

- stable content and result IDs
- prerequisite inspection before input
- explicit cancel, failure, success, and repeat behavior
- durable effects applied outside presentation code
- separate initial and repeat presentation
- a way to expose newly valid navigation or dialogue choices from state

Their inputs and effects differ:

- free text currently prototypes rune reconstruction and pattern ordering
- environmental casting selects a runtime `AvailableSpell` and pays its source
- effects currently touch story knowledge, learned spells, rewards, crystal or
  actor resources, POI state, and room connections

This supports a shared interaction lifecycle, but does not yet prove that every
mechanic belongs in one runtime phase or one definition class.

## Questions for the content-transfer discussion

Before defining the Notion and JSON shape, decide together:

- Which concepts are authored as chapter, scene, dialogue, POI, challenge, or
  transition?
- Which text variants are needed for intro, repeat, missing prerequisite,
  cancellation, wrong attempt, success, and later consequences?
- How are speakers, narration, dialogue choices, hints, and optional lore
  represented?
- How are prerequisites expressed: knowledge, item, spell, attribute, previous
  decision, location, or combinations?
- Which effects may content request, and which must remain purpose-built game
  rules?
- How should ordered patterns, missing lines, rune drawing, and spell selection
  describe their mechanic-specific input?
- Can failure be retried, is it permanent, and can it create an alternate story
  branch?
- How does Notion expose stable IDs and references without making story writing
  unpleasant?

## Decisions deliberately deferred

The prototypes do not yet settle:

- whether the shared coordinator is named `RiddlePhase`, `ChallengeScene`, or
  remains part of story/POI orchestration
- the permanent scene/dialog class hierarchy
- the Notion database/page structure
- the JSON schema and generic condition/effect representation
- rune recognition or drawing-quality rules
