package systems.actors.npc;

import systems.actors.Actor;
import systems.actors.ActorDefinition;
import systems.actors.ActorState;

public class NPC extends Actor<ActorDefinition> {

    protected NPC(ActorDefinition def, ActorState state) {
        super(def, state);
    }

}
