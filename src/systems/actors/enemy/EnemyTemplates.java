package systems.actors.enemy;

import systems.reward.Reward;
import systems.actors.ActorState;
import systems.actors.MainAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnemyTemplates {

    private static final EnemyDefinition BAT =
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
                    new Reward(
                            null,
                            500,
                            0
                    )
            );

    private static final EnemyDefinition SLIME =
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
            );



    private static final Map<String, EnemyDefinition> BY_ID = Map.of(
            "Fledermaus", BAT,
            "Schleim", SLIME
    );

    public static Enemy get(String id){
        EnemyDefinition def = BY_ID.get(id);
        return new Enemy(
                def,
                new ActorState(
                        def.getBaseHp() + def.getHpPerLevel(),
                        def.getBaseResource() + def.getResourcePerLevel(),
                        1,
                        0,
                        new ArrayList<>(List.of())
                )
        );
    }
}
