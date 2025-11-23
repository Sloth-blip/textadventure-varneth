package systems.world;

import systems.actors.enemy.EnemyTemplates;
import systems.interactables.PointOfInterest;
import systems.interactables.PointOfInterestDefinition;
import systems.interactables.PointOfInterestState;
import systems.reward.Reward;
import systems.rooms.RoomState;
import systems.spells.SpellTemplates;

import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {

    public static WorldState buildTestWorld() {

        /** Interactables **/

        var book1 = new PointOfInterest(
                new PointOfInterestDefinition(
                        "book",
                        "Buch",
                        List.of(
                                List.of(
                                        "Very book",
                                        "much knowledge",
                                        "wow"
                                ),
                                List.of(
                                        "Still very booky"
                                )
                        ),
                        new Reward(SpellTemplates.get("pebbles"),0,0)

                ),
                new PointOfInterestState(true)
        );
        var cabinet1 = new PointOfInterest(
                new PointOfInterestDefinition(
                        "cabinet",
                        "Schrank",
                        List.of(
                                List.of(
                                        "Ikea Schrank",
                                        "Bricht beim ersten Anblick in sich zusammen."
                                ),
                                List.of()
                        ),
                        new Reward(null,100,0)

                ),
                new PointOfInterestState(false)
        );

        /** Enemies **/

        var bat1 = EnemyTemplates.get("Fledermaus");
        var slime = EnemyTemplates.get("Schleim");

        /** Räume (Instanz - Interactable einsetzen - Gegner platzieren) **/

        var room1 = new RoomState(
                1,
                "Beginn",
                "Der erste Raum",
                List.of("Spielintro.", "Blablabla", "Bla.", "Bla?", "Bla!"),
                new ArrayList<>(List.of(book1, cabinet1))
        );

        room1.setEnemies(new ArrayList<>(List.of()));

        var room2 = new RoomState(
                2,
                "Weiter",
                "Der zweite Raum",
                List.of("Hier geht es weiter.", "Du musst erst die Gegner besiegen."),
                List.of()
        );

        room2.setEnemies(new ArrayList<>(List.of(bat1,slime)));

        /** Räume verbinden **/

        room1.setConnectedRooms(new ArrayList<>(List.of(room2)));
        room2.setConnectedRooms(new ArrayList<>(List.of(room1)));

        return new WorldState(room1, List.of(room1,room2));
    }



}
