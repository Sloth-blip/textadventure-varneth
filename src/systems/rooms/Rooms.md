# Rooms

Zentraler Punkt im Game. Hier entscheidet der Spieler, was er macht.


## ExplorationPhase

Main-handler für die Rooms. Hier werden Entscheidungen ausgewählt, die
an den Gameloop weitergegeben werden, um die nächsten Phasen zu starten:

### DialogPhase

Hier werden nur Dialog-Chunks ausgegeben. Keine eigenständige Code-Phase.

### CombatScene

Startet und handelt den Kampf.

> Verweis: `Docs/WorldState.md`

### (ToDo) RiddlePhase

### (ToDo) Plans

- Aufteilung von Rooms in Area, Dungeons, City, etc. über Enums oder Vererbung?
