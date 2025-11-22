package systems.actors.enemy;

import systems.actors.Actor;
import systems.actors.ActorDefintiton;
import systems.actors.ActorState;

public class Enemy extends Actor  {

    public Enemy(ActorDefintiton def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

}
