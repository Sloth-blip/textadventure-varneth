package varneth.systems.actors.enemy;

import varneth.systems.actors.Actor;
import varneth.systems.actors.ActorState;
import varneth.systems.reward.Reward;

public class Enemy extends Actor<EnemyDefinition>  {



    public Enemy(EnemyDefinition def, ActorState state) {
        super(def, state);
    }

    public boolean isDead() {return getCurrentHp() == 0;}

    public Reward getReward() {return def.getReward();}
}
