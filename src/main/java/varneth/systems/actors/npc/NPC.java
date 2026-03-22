package varneth.systems.actors.npc;

import varneth.systems.actors.Actor;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.ActorState;

public class NPC extends Actor<ActorDefinition> {

    protected NPC(ActorDefinition def, ActorState state) {
        super(def, state);
    }

}
