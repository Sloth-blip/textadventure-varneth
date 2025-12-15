package systems.world;

import systems.actors.enemy.EnemyTemplates;
import systems.interactables.PointOfInterest;
import systems.interactables.PointOfInterestDefinition;
import systems.interactables.PointOfInterestState;
import systems.reward.Reward;
import systems.rooms.RoomStateTest;
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
        var restingPlace = new PointOfInterest(
                new PointOfInterestDefinition(
                        "resting Place",
                        "Rastplatz",
                        List.of(
                                List.of(
                                        "Ein Platz zum Rasten.",
                                        "Sleep tite booboo."
                                ),
                                List.of(
                                        "Ein Platz zum Rasten.",
                                        "Sleep tite booboo."
                                )
                        ),
                        new Reward(null,0,0)
                ),
                new PointOfInterestState(true)
        );
        /** Enemies **/

        var bat1 = EnemyTemplates.get("Fledermaus");
        var slime = EnemyTemplates.get("Schleim");
        var bat2 = EnemyTemplates.get("Fledermaus");

        /** Räume (Instanz - Interactable einsetzen - Gegner platzieren) **/

        var room1 = new RoomStateTest(
                1,
                "Beginn",
                "Der erste Raum",
                List.of("Spielintro.", "Blablabla", "Bla.", "Bla?", "Bla!"),
                new ArrayList<>(List.of(book1, cabinet1, restingPlace))
        );

        room1.setEnemies(new ArrayList<>(List.of()));

        var room2 = new RoomStateTest(
                2,
                "Weiter",
                "Der zweite Raum",
                List.of("Hier geht es weiter.", "Du musst erst die Gegner besiegen."),
                List.of()
        );

        room2.setEnemies(new ArrayList<>(List.of(bat1,slime)));

        var room3 = new RoomStateTest(
                3,
                "Noch Weiter",
                "Der dritte Raum",
                List.of(),
                new ArrayList<>(List.of(restingPlace))
        );

        room3.setEnemies(new ArrayList<>(List.of(bat2)));

        /** Räume verbinden **/

        room1.setConnectedRooms(new ArrayList<>(List.of(room2)));
        room2.setConnectedRooms(new ArrayList<>(List.of(room1, room3)));
        room3.setConnectedRooms(new ArrayList<>(List.of(room2)));

        return new WorldState(room1, List.of(room1,room2, room3));
    }



}
