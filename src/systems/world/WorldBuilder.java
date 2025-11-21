package systems.world;

import systems.actors.enemy.EnemyState;
import systems.actors.enemy.EnemyTemplates;
import systems.actors.interactables.PointofInterestState;
import systems.rooms.RoomState;

import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {

    public static WorldState buildTestWorld() {

        /** Interactables **/

        var book1 = new PointofInterestState(
                "Buch",
                List.of("Very book", "much knowledge", "wow"),
                true);
        var cabinet1 = new PointofInterestState(
                "Schrank",
                List.of("Du schwingst die Schranktüren mit Wucht auf", "Mit einem lauten Krachen bricht der Schrank in sich zusammen."),
                false
        );

        /** Enemies **/

        var bat1 = new EnemyState(EnemyTemplates.get("bat"));
        var slime = new EnemyState(EnemyTemplates.get("slime"));

        /** Räume (Instanz - Interactable einsetzen - Gegner platzieren) **/

        var room1 = new RoomState(
                1,
                "Beginn",
                "Der erste Raum",
                List.of("Spielintro.", "Blablabla", "Bla.", "Bla?", "Bla!"),
                new ArrayList<>(List.of(book1, cabinet1))
        );

        room1.setEnemies(new ArrayList<>(List.of(bat1, slime)));

        var room2 = new RoomState(
                2,
                "Weiter",
                "Der zweite Raum",
                List.of("Hier geht es weiter.", "Du kannst nur zurück."),
                List.of()
        );

        room2.setEnemies(new ArrayList<>(List.of()));

        /** Räume verbinden **/

        room1.setConnectedRooms(new ArrayList<>(List.of(room2)));
        room2.setConnectedRooms(new ArrayList<>(List.of(room1)));

        return new WorldState(room1, List.of(room1,room2));
    }



}
