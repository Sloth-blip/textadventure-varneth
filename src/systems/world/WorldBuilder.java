package systems.world;

import systems.actors.enemy.EnemyTemplates;
import systems.interactables.PointOfInterest;
import systems.interactables.PointOfInterestDefinition;
import systems.interactables.PointOfInterestState;
import systems.interactables.PointOfInterestType;
import systems.reward.Reward;
import systems.rooms.Room;
import systems.rooms.RoomDefinition;
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
                        PointOfInterestType.STORY,
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
                        new Reward(SpellTemplates.get("pebbles"), 0, 0)

                ),
                new PointOfInterestState(true)
        );
        var cabinet1 = new PointOfInterest(
                new PointOfInterestDefinition(
                        "cabinet",
                        "Schrank",
                        PointOfInterestType.LOOT,
                        List.of(
                                List.of(
                                        "Ikea Schrank",
                                        "Bricht beim ersten Anblick in sich zusammen."
                                ),
                                List.of()
                        ),
                        new Reward(null, 100, 0)

                ),
                new PointOfInterestState(false)
        );
        var restingPlace = new PointOfInterest(
                new PointOfInterestDefinition(
                        "resting Place",
                        "Rastplatz",
                        PointOfInterestType.REST,
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
                        new Reward(null, 0, 0)
                ),
                new PointOfInterestState(true)
        );
        /** Enemies **/

        var bat1 = EnemyTemplates.get("Fledermaus");
        var slime = EnemyTemplates.get("Schleim");
        var bat2 = EnemyTemplates.get("Fledermaus");

        /** Räume (Instanz - Interactable einsetzen - Gegner platzieren) **/

        var room1 = new Room(
                new RoomDefinition(
                        "1",
                        "Beginn",
                        "Der erste Raum"
                ),
                new RoomState(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(List.of(book1, cabinet1, restingPlace)),
                        List.of(),
                        List.of("Spielintro.", "Blablabla", "Bla.", "Bla?", "Bla!")
                ));

        var room2 = new Room(
                new RoomDefinition(
                        "2",
                        "Weiter",
                        "Der zweite Raum"
                ),
                new RoomState(
                        new ArrayList<>(List.of(bat1, slime)),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of("Hier geht es weiter.", "Du musst erst die Gegner besiegen.")
                ));

        var room3 = new Room(
                new RoomDefinition(
                        "3",
                        "Noch Weiter",
                        "Der dritte Raum"
                ),
                new RoomState(
                        new ArrayList<>(List.of(bat2)),
                        List.of(),
                        new ArrayList<>(List.of(restingPlace)),
                        List.of(),
                        List.of("Henlo")
                ));

        /** Räume verbinden **/

        room1.setConnectedRooms(new ArrayList<>(List.of(room2)));
        room2.setConnectedRooms(new ArrayList<>(List.of(room1, room3)));
        room3.setConnectedRooms(new ArrayList<>(List.of(room2)));

        return new WorldState(room1, List.of(room1, room2, room3));
    }


}
