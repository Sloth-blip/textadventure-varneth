package systems.actors.enemy;

import java.util.List;
import java.util.Map;

public class EnemyTemplates {

    private static final EnemyTemplate BAT = new EnemyTemplate(
            "bat",
            "Fledermaus",
            1,
            10,
            1,
            1,
            List.of(),
            100
    );

    private static final EnemyTemplate SLIME = new EnemyTemplate(
            "slime",
            "Schleim",
            1,
            15,
            1,
            1,
            List.of(),
            100
    );

    private static final Map<String, EnemyTemplate> BY_ID = Map.of(
            "bat", BAT,
            "slime", SLIME
    );

    public static EnemyTemplate get(String id){
        return BY_ID.get(id);
    }
}
