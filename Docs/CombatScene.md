# CombatScene

## Zweck

`CombatScene` kapselt den kompletten Ablauf eines Kampfes zwischen dem Spieler
und einer oder mehreren Gegnerinstanzen.

Die Klasse steuert:
- die Reihenfolge der Züge (Spieler → Gegner),
- die Auswertung von Kampfergebnissen,
- das Beenden des Kampfes (Sieg, Niederlage, Flucht).

Aktuell enthält die Klasse **sowohl Game-Logik als auch Console-UI-Aufrufe**.
Diese Vermischung ist **bewusst temporär** und wird schrittweise aufgelöst.

---

## Verantwortung

### CombatScene ist verantwortlich für:
- den Kampfflow (Rundenlogik),
- Schadensberechnung (über Player/Enemy),
- Death-Checks (Player / Enemy),
- das Auslösen von Rewards,
- das Zurückgeben eines `CombatResult`.

### CombatScene ist nicht verantwortlich für:
- persistente Spielzustände außerhalb des Kampfes,
- grafische Darstellung oder Animation,
- Menü-Layout oder Input-Validierung (langfristiges Ziel).

---

## Kampfergebnis

Der Kampf endet mit einem `CombatResult`:

```java
public enum CombatResult {
    WON,
    LOST,
    FLED
}
```
und gibt dieses an den GameLoop weiter.