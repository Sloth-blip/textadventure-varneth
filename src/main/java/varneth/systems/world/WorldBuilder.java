package varneth.systems.world;

import java.util.ArrayList;
import java.util.List;

import varneth.systems.actors.enemy.EnemyTemplates;
import varneth.systems.items.EquipmentTemplates;
import varneth.systems.items.MagicCrystalTemplates;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.interactables.PointOfInterestDefinition;
import varneth.systems.interactables.PointOfInterestState;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.reward.Reward;
import varneth.systems.rooms.Room;
import varneth.systems.rooms.RoomDefinition;
import varneth.systems.rooms.RoomState;
import varneth.systems.spells.SpellTemplates;

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
                        new Reward(
                                List.of(SpellTemplates.get("pebbles")),
                                List.of(EquipmentTemplates.get("earth_focus")),
                                0,
                                0
                        )

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
                        new Reward(MagicCrystalTemplates.get("fire_crystal"))

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
                        new Reward(0, 0)
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
                        "Der erste Raum",
                        0,
                        0
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
                        "Der zweite Raum",
                        1,
                        0
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
                        "Der dritte Raum",
                        1,
                        1
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

    public static WorldState buildTestWorldTwo() {

    /** Interactables **/

    var noteEntrance = new PointOfInterest(
            new PointOfInterestDefinition(
                    "note_entrance",
                    "Eingangstafel",
                    PointOfInterestType.STORY,
                    List.of(
                            List.of(
                                    "Eine halb zerbrochene Tafel lehnt an der Wand.",
                                    "\"Außenposten K-17. Zugang nur für autorisierte Träger.\"",
                                    "Jemand hat darunter gekritzelt: \"Wenn du das lesen kannst, bist du eh schon zu weit drin.\""
                            ),
                            List.of(
                                    "Die Tafel ist noch genauso sarkastisch wie zuvor."
                            )
                    ),
                    new Reward(0, 0)
            ),
            new PointOfInterestState(true)
    );

    var oldSatchel = new PointOfInterest(
            new PointOfInterestDefinition(
                    "old_satchel",
                    "Alte Tasche",
                    PointOfInterestType.LOOT,
                    List.of(
                            List.of(
                                    "Eine staubige Tasche liegt zwischen Geröll und morschem Holz.",
                                    "Du öffnest sie vorsichtig.",
                                    "Der Inhalt ist überraschend nützlich."
                            ),
                            List.of()
                    ),
                    new Reward(40, 15)
            ),
            new PointOfInterestState(false)
    );

    var archiveBook = new PointOfInterest(
            new PointOfInterestDefinition(
                    "archive_book",
                    "Verkohltes Archivbuch",
                    PointOfInterestType.STORY,
                    List.of(
                            List.of(
                                    "Die Seiten kleben zusammen, aber ein paar Zeilen sind lesbar.",
                                    "\"Resonanzpartikel reagieren auf Stress, Hunger und Lärm.\"",
                                    "\"Notfallzauber Pebbles bleibt für Rekruten freigegeben.\""
                            ),
                            List.of(
                                    "Die lesbaren Stellen hast du dir bereits gemerkt."
                            )
                    ),
                    new Reward(SpellTemplates.get("pebbles"), 0, 0)
            ),
            new PointOfInterestState(true)
    );

    var collapsedCrate = new PointOfInterest(
            new PointOfInterestDefinition(
                    "collapsed_crate",
                    "Eingestürzte Kiste",
                    PointOfInterestType.LOOT,
                    List.of(
                            List.of(
                                    "Eine Kiste ist unter einem Steinbrocken eingeklemmt.",
                                    "Mit etwas Mühe bekommst du sie auf.",
                                    "Innen ist fast alles hinüber – fast."
                            ),
                            List.of()
                    ),
                    new Reward(70, 25)
            ),
            new PointOfInterestState(false)
    );

    var shrineRest = new PointOfInterest(
            new PointOfInterestDefinition(
                    "shrine_rest",
                    "Rastplatz",
                    PointOfInterestType.REST,
                    List.of(
                            List.of(
                                    "Zwischen alten Säulen glimmt eine ruhige, bernsteinfarbene Flamme.",
                                    "Die Luft ist warm. Für einen Moment wirkt alles weniger bedrohlich.",
                                    "Du sammelst dich."
                            ),
                            List.of(
                                    "Die Flamme brennt ruhig weiter.",
                                    "Ein kurzer Moment der Ruhe kann nicht schaden."
                            )
                    ),
                    new Reward(0, 0)
            ),
            new PointOfInterestState(true)
    );

    var wallScratchings = new PointOfInterest(
            new PointOfInterestDefinition(
                    "wall_scratchings",
                    "Kratzspuren an der Wand",
                    PointOfInterestType.STORY,
                    List.of(
                            List.of(
                                    "Die Kratzspuren verlaufen in unruhigen Kreisen.",
                                    "Zwischen ihnen erkennst du Worte: \"nicht die Geräusche verfolgen\"",
                                    "Jemand war hier lange vor dir ziemlich panisch."
                            ),
                            List.of(
                                    "Die Kratzspuren machen jetzt nicht mehr Sinn als zuvor."
                            )
                    ),
                    new Reward(20, 0)
            ),
            new PointOfInterestState(true)
    );

    var supplyCache = new PointOfInterest(
            new PointOfInterestDefinition(
                    "supply_cache",
                    "Versorgungskiste",
                    PointOfInterestType.LOOT,
                    List.of(
                            List.of(
                                    "Hinter einer losen Steinplatte ist eine versiegelte Kiste versteckt.",
                                    "Diesmal hält das Schloss dem ersten Ruck nicht stand.",
                                    "Jackpot – zumindest für Testwelt-Verhältnisse."
                            ),
                            List.of()
                    ),
                    new Reward(120, 50)
            ),
            new PointOfInterestState(false)
    );

    var finalJournal = new PointOfInterest(
            new PointOfInterestDefinition(
                    "final_journal",
                    "Letzter Einsatzbericht",
                    PointOfInterestType.STORY,
                    List.of(
                            List.of(
                                    "\"Trupp getrennt. Nachschub verloren. Geräusche im Nest verstärken sich.\"",
                                    "\"Wer diesen Bericht findet: Die vorderen Kammern sind verloren, aber der Vorratsraum hält noch.\"",
                                    "Die Schrift endet abrupt."
                            ),
                            List.of(
                                    "Mehr gibt der Bericht nicht her."
                            )
                    ),
                    new Reward(60, 0)
            ),
            new PointOfInterestState(true)
    );

    var finalRest = new PointOfInterest(
            new PointOfInterestDefinition(
                    "final_rest",
                    "Verlassene Feuerstelle",
                    PointOfInterestType.REST,
                    List.of(
                            List.of(
                                    "Alte Kohle, trockene Decken, ein halb eingerichtetes Lager.",
                                    "Hier hat einmal jemand versucht, einen sicheren Punkt zu schaffen.",
                                    "Für jetzt reicht es auch dir als Pause."
                            ),
                            List.of(
                                    "Die Feuerstelle ist kalt, aber der Ort bleibt ruhig."
                            )
                    ),
                    new Reward(0, 0)
            ),
            new PointOfInterestState(true)
    );

    /** Enemies **/

    var batEntrance = EnemyTemplates.get("Fledermaus");

    var slimeHall1 = EnemyTemplates.get("Schleim");
    var batHall1 = EnemyTemplates.get("Fledermaus");

    var slimeCrossing = EnemyTemplates.get("Schleim");

    var batNest1 = EnemyTemplates.get("Fledermaus");
    var batNest2 = EnemyTemplates.get("Fledermaus");
    var slimeNest = EnemyTemplates.get("Schleim");

    var batFinal = EnemyTemplates.get("Fledermaus");
    var slimeFinal = EnemyTemplates.get("Schleim");

    /** Räume **/

    var room1 = new Room(
            new RoomDefinition(
                    "1",
                    "Eingangshalle",
                    "Ein schmaler Vorraum mit eingerissenen Bögen und kaltem Staub. Der Rückweg wirkt schon jetzt weniger einladend als der Weg nach vorn.",
                    0,
                    0
            ),
            new RoomState(
                    new ArrayList<>(),
                    List.of(),
                    new ArrayList<>(List.of(noteEntrance, oldSatchel)),
                    List.of(),
                    List.of(
                            "Du setzt den ersten Schritt in den verlassenen Außenposten.",
                            "Alles riecht nach Stein, altem Papier und abgestandener Feuchtigkeit.",
                            "Noch wirkt der Ort still - zu still."
                    )
            )
    );

    var room2 = new Room(
            new RoomDefinition(
                    "2",
                    "Wachgang",
                    "Ein langer Korridor mit eingedrücktem Boden und Resten alter Halterungen an der Wand. Etwas flattert im Dunkeln.",
                    0,
                    1
            ),
            new RoomState(
                    new ArrayList<>(List.of(batEntrance, slimeHall1)),
                    List.of(),
                    new ArrayList<>(),
                    List.of(),
                    List.of(
                            "Der Gang war wohl einmal bewacht.",
                            "Heute patrouillieren hier nur noch Dinge, die auf Licht und Bewegung reagieren."
                    )
            )
    );

    var room3 = new Room(
            new RoomDefinition(
                    "3",
                    "Archivnische",
                    "Ein schmaler Seitenraum voller verkohlter Regale. Vieles ist zerstört, manches erstaunlich gut erhalten.",
                    1,
                    1
            ),
            new RoomState(
                    new ArrayList<>(),
                    List.of(),
                    new ArrayList<>(List.of(archiveBook)),
                    List.of(),
                    List.of(
                            "Abseits des Hauptwegs scheint hier jemand Wissen retten zu wollen versucht zu haben.",
                            "Zwischen Asche und Staub liegt noch ein Rest Ordnung."
                    )
            )
    );

    var room4 = new Room(
            new RoomDefinition(
                    "4",
                    "Zentraler Knoten",
                    "Mehrere Wege treffen in einer offenen Kammer zusammen. Kaputte Leitungen ziehen sich wie Adern durch die Decke.",
                    0,
                    2
            ),
            new RoomState(
                    new ArrayList<>(List.of(batHall1, slimeCrossing)),
                    List.of(),
                    new ArrayList<>(List.of(collapsedCrate)),
                    List.of(),
                    List.of(
                            "Hier verzweigt sich der Außenposten.",
                            "Von den Seiten dringen unterschiedliche Geräusche herüber: Tropfen, Kratzen, Flattern."
                    )
            )
    );

    var room5 = new Room(
            new RoomDefinition(
                    "5",
                    "Stillkammer",
                    "Ein überraschend ruhiger Raum mit halb eingestürzten Säulen. In der Mitte brennt etwas, das eigentlich längst erloschen sein sollte.",
                    1,
                    2
            ),
            new RoomState(
                    new ArrayList<>(),
                    List.of(),
                    new ArrayList<>(List.of(shrineRest)),
                    List.of(),
                    List.of(
                            "Der Lärm des Außenpostens fällt hier plötzlich ab.",
                            "Es ist der erste Ort, der nicht sofort feindselig wirkt."
                    )
            )
    );

    var room6 = new Room(
            new RoomDefinition(
                    "6",
                    "Nestkammer",
                    "Dunkle Fetzen hängen von der Decke. Der Boden ist mit Schleim und kleinen Knochen übersät.",
                    0,
                    3
            ),
            new RoomState(
                    new ArrayList<>(List.of(batNest1, batNest2, slimeNest)),
                    List.of(),
                    new ArrayList<>(List.of(wallScratchings)),
                    List.of(),
                    List.of(
                            "Du bist zu nah an der Quelle der Geräusche.",
                            "Hier lebt etwas – oder mehrere Dinge."
                    )
            )
    );

    var room7 = new Room(
            new RoomDefinition(
                    "7",
                    "Versorgungsraum",
                    "Hinter einer verstärkten Tür liegt ein überraschend intakter Lagerraum. Nicht sicher – aber sicherer als der Rest.",
                    0,
                    4
            ),
            new RoomState(
                    new ArrayList<>(List.of(batFinal, slimeFinal)),
                    List.of(),
                    new ArrayList<>(List.of(supplyCache, finalJournal, finalRest)),
                    List.of(),
                    List.of(
                            "Du hast den hinteren Teil des Außenpostens erreicht.",
                            "Zwischen Kisten, Notizen und Lagerresten wirkt das hier fast wie ein kleiner Abschluss der Expedition."
                    )
            )
    );

    /** Räume verbinden **/

    room1.setConnectedRooms(new ArrayList<>(List.of(room2)));
    room2.setConnectedRooms(new ArrayList<>(List.of(room1, room3, room4)));
    room3.setConnectedRooms(new ArrayList<>(List.of(room2)));
    room4.setConnectedRooms(new ArrayList<>(List.of(room2, room5, room6)));
    room5.setConnectedRooms(new ArrayList<>(List.of(room4)));
    room6.setConnectedRooms(new ArrayList<>(List.of(room4, room7)));
    room7.setConnectedRooms(new ArrayList<>(List.of(room6)));

    return new WorldState(
            room1,
            List.of(room1, room2, room3, room4, room5, room6, room7)
    );
}
}
