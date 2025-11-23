package systems.actors.enemy;

import systems.reward.Reward;
import systems.actors.ActorState;
import systems.actors.MainAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnemyTemplates {

    private static final Enemy BAT = new Enemy(
            new EnemyDefinition(
                    "Fledermaus",
                    15,
                    5,
                    3,
                    2,
                    5,
                    2,
                    5,
                    2,
                    5,
                    2,
                    10,
                    10,
                    MainAttribute.STRENGTH,
                    new Reward(null,500,0)
            ),
            new ActorState(
                    20,
                    5,
                    1,
                    0,
                    new ArrayList<>(List.of())
            )
    );

    private static final Enemy SLIME = new Enemy(
            new EnemyDefinition(
            "Schleim",
            15,
            5,
            3,
            2,
            5,
            2,
            5,
            2,
            5,
            2,
            10,
            10,
            MainAttribute.STRENGTH,
                    new Reward(
                            null,
                            1000,
                            0
                    )
    ),
            new ActorState(
                    20,
                            5,
                            1,
                            0,
                            new ArrayList<>(List.of())
            )
    );

    private static final Map<String, Enemy> BY_ID = Map.of(
            "Fledermaus", BAT,
            "Schleim", SLIME
    );

    public static Enemy get(String id){
        return BY_ID.get(id);
    }
}
