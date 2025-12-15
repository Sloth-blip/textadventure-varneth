# WorldBuilder

## Zweck

`WorldBuilder` ist verantwortlich für den **Aufbau einer konkreten Spielwelt**.
Er erzeugt Instanzen von:
- Räumen
- Gegnern
- Interactables (Points of Interest)
- Verbindungen zwischen Räumen

Der Builder kapselt **Test- und Entwicklungswelten** und dient aktuell als
zentrale Stelle, um Gameplay-Mechaniken zu testen.

---

## Verantwortung

### WorldBuilder ist verantwortlich für:
- das Erzeugen einer lauffähigen Welt (`WorldState`)
- das Platzieren von Interactables in Räumen
- das Spawnen von Gegnerinstanzen
- das Verbinden von Räumen

### WorldBuilder ist nicht verantwortlich für:
- Spiellogik (Exploration, Combat)
- UI oder Darstellung
- persistente Speicherung
- Regelentscheidungen

---

## Aktuelle Implementierung

### buildTestWorld()

```java
public static WorldState buildTestWorld();
```
 
# WorldState

## Zweck

`WorldState` repräsentiert den **aktuellen Zustand der gesamten Spielwelt**.
Er dient als zentrales Objekt für:
- Navigation
- Phasensteuerung
- zukünftige Save/Load-Mechaniken

---

## Struktur

```java
public class WorldState {
    private final RoomStateTest startRoom;
    private final List<RoomStateTest> allRooms;
}
```