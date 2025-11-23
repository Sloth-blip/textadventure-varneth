package systems.actors.player;

import systems.actors.Actor;
import systems.actors.ActorDefinition;
import systems.actors.ActorState;

public class Player extends Actor {

    public Player(ActorDefinition def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

}
