package systems.actors.enemy;

import systems.actors.ActorDefintiton;
import systems.actors.ActorState;
import systems.actors.MainAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnemyTemplates {

    private static final Enemy BAT = new Enemy(
            new ActorDefintiton(
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
                    MainAttribute.STRENGTH
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
            new ActorDefintiton(
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
            MainAttribute.STRENGTH
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
