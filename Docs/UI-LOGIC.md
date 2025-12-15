# UI-LOGIC

beschreibt die **Schnittstelle zwischen Game-Logik und UI**.
Es legt fest, **wer wofür verantwortlich ist**, wie Daten fließen und wie UI
und Logik voneinander entkoppelt bleiben.

Ziel ist es, die UI **austauschbar** zu halten:
- heute: Console-UI
- später: GameWindow, Touch-UI, Mobile

Die Spielregeln dürfen **niemals** von der UI abhängen.

---

## Grundprinzip

> **Die Game-Logik entscheidet,  
> die UI stellt dar und sammelt Input.**

Oder anders:
- Logik = *Was passiert?*
- UI = *Wie sieht es aus & wie wird es ausgewählt?*

---

## Verantwortlichkeiten

### UI-Layer (z. B. ConsoleMenu)

**Darf:**
- Texte darstellen
- Menüs anzeigen
- Input validieren (Zahl eingegeben? gültiger Bereich?)
- Navigation ermöglichen (Back, Abbrechen)

**Darf nicht:**
- Spielregeln implementieren
- Zustände eigenständig verändern
- Entscheidungen „vorwegnehmen“

---

## Kommunikationsmodell

### Intent - Result

Die UI kommuniziert mit der Logik über **Intents** (Spielerwünsche).
Die Logik antwortet mit **Results** (Ergebnisse).

#### Beispiele für Intents
- `ExplorationAction.MOVE`
- `ExplorationAction.INTERACT_POI`
- `CombatAction.BASIC_ATTACK`
- `CombatAction.CAST_SPELL`
