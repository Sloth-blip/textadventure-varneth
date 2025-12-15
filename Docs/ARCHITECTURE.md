# Kernarchitektur

GameLoop als Direktor für die klar gegliederten Phasen:

- GameStart
- ExplorationPhase <-> DialogPhase
- CombatScene

Game-Logik erzeugt Ergebnisse, gibt sie an GameLoop weiter,
stellt sie ggf. über UI dar und entscheidet dann, wie es weitergeht.


## Definition - State - Instance

### Definition: 
- Unverändliche "Grundwerte" wie Name, BaseStats, StatsPerLevel, usw.

### State:
- Veränderliche Werte wie Level, CurrentHp, CurrtentStats usw.

### Instance:
- Verbindungsstück von beidem. Außer def & state keine weiteren Felder sondern nur die benötigten Methoden.
