package engine.events;

import systems.actors.Actor;
import systems.spells.Skill;

public record DamageDealt(
        Actor<?> attacker,
        Actor<?> target,
        int amount,
        Skill skill,        // null = Basic Attack
        int hpBefore,
        int hpAfter
) implements GameEvent {}
