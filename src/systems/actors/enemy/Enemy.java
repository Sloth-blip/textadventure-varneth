package systems.actors.enemy;

import systems.reward.Reward;
import systems.actors.Actor;
import systems.actors.ActorState;

public class Enemy extends Actor<EnemyDefinition>  {



    public Enemy(EnemyDefinition def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

    public Reward getReward() {return def.getReward();}
}
