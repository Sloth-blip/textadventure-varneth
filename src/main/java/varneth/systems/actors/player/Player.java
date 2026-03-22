package varneth.systems.actors.player;

import varneth.systems.actors.Actor;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.ActorState;

public class Player extends Actor<ActorDefinition> {

    public Player(ActorDefinition def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

}
