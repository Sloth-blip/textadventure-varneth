package systems.actors.player;

import systems.actors.Actor;
import systems.actors.ActorDefintiton;
import systems.actors.ActorState;

public class Player extends Actor {

    public Player(ActorDefintiton def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

}
