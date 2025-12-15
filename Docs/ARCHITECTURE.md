## Kernarchitektur

GameLoop als Direktor für die klar gegliederten Phasen:

- GameStart
- ExplorationPhase <-> DialogPhase
- CombatScene

Game-Logik erzeugt Ergebnisse, gibt sie an GameLoop weiter,
stellt sie ggf. über UI dar und entscheidet dann, wie es weitergeht.
