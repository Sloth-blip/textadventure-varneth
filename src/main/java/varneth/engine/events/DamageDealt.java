package varneth.engine.events;

import varneth.systems.actors.Actor;
import varneth.systems.spells.Skill;

public record DamageDealt(
        Actor<?> attacker,
        Actor<?> target,
        int amount,
        Skill skill,        // null = Basic Attack
        int hpBefore,
        int hpAfter
) implements GameEvent {}
