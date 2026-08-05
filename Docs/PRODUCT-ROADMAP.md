# Varneth Product Roadmap

## Vision

Varneth wird ein erzählorientiertes, offline spielbares Text-RPG. Geschichte,
Atmosphäre, Erkundung und Entscheidungen stehen im Mittelpunkt. Kampf ist kein
Selbstzweck, sondern ein Werkzeug des Storytellings und kann den weiteren Verlauf
verändern.

Langfristiges Ziel ist eine Android-App für den Google Play Store. Entwicklung
und Tests finden zunächst auf dem PC statt. Ein gemeinsamer, plattformunabhängiger
Core liefert Desktop und Android dieselben Inhalte, Spielstände und Regeln.

## Festgelegte Produktentscheidungen

- Verzweigte Kapitel bilden eine geführte Haupthandlung mit optionalen Wegen und
  dauerhaften Konsequenzen.
- Das UI wird für Hochformat entworfen und am PC in einem skalierbaren Fenster
  getestet.
- Text hat Priorität; Entscheidungen, Charakterwerte, Inventar, Karte und
  Illustrationen unterstützen ihn.
- Story-Szenen können Kämpfe auslösen. Sieg, Niederlage und Flucht führen in
  definierte Folgeszenen.
- Eine Niederlage ist normalerweise kein Game Over, sondern kann Verletzung,
  Verlust, Gefangenschaft oder einen alternativen Handlungszweig auslösen.
- Das Spiel speichert lokal und automatisch nach abgeschlossenen
  Szenenübergängen. Der Start bietet mindestens `Fortsetzen` und `Neues Spiel`.
- Story-Inhalte werden zunächst in JSON gepflegt. Ein späterer visueller
  Story-Editor liest und schreibt dasselbe Format, ist aber kein Runtime-Bestandteil.
- Zauber werden durch die Story auf Level 1 entdeckt. Nutzung oder festgelegte
  Story-Ereignisse geben Zauber-XP; höhere Level schalten Varianten oder
  zusätzliche Wirkungen frei.
- Der Spieler wählt einen freigeschalteten Zauber und zeichnet dessen Rune frei
  mit Maus oder Finger. Im ersten Vertical Slice wird die Zeichnung nicht erkannt
  oder bewertet, sondern dient als atmosphärische Geste.

## Technische Zielarchitektur

### Plattform und Module

- **Core:** Java 17 ohne Desktop- oder Android-spezifische APIs.
- **Desktop:** LibGDX mit LWJGL3 als primäre Entwicklungs- und Testplattform.
- **Android:** LibGDX-Android-Modul nach dem PC-Vertical-Slice.
- **UI:** LibGDX Scene2D für Screens, Layout, Eingabe und Overlays.
- **Content/Saves:** JSON hinter kleinen Lade- und Speicher-Schnittstellen.

Der Build verwendet bereits Java 17 als gemeinsame zukünftige Basis für Desktop
und Android. Bei der Migration wird die dann aktuelle stabile LibGDX-Version
verwendet, keine Snapshot-Version.

```text
core
├── domain          Definition, State und Instance
├── story           Kapitel, Szenen, Bedingungen, Effekte und Übergänge
├── gameplay        Exploration, Runen, Kampf, Rewards und Progression
├── persistence     Save-Modell und plattformneutrale Schnittstellen
└── content         JSON-Modelle, Laden und Validierung

lwjgl3              Desktop-Launcher und Desktop-Konfiguration
android             Android-Launcher und -Konfiguration (später)
assets              Story-JSON, Schriften, Bilder, Audio und UI-Skin
tools               zukünftiger Story-Editor (nicht Teil der Runtime)
```

Die heutige `systems`-Schicht und `GameStart -> GameLoop` sind der Ausgangspunkt.
Gute entkoppelte Ideen aus der späteren `application`-Demo dürfen selektiv
übernommen werden. Webserver und Vue-Frontend werden vorerst eingefroren und nicht
parallel als zweite maßgebliche Spiellogik gepflegt.

### Zentrale Datenverträge

- Ein Kapitel besitzt stabile ID, Startszene und Szenen.
- Eine Szene besitzt stabile ID, Typ und Text sowie typabhängig Entscheidungen,
  Story-Effekte, Kampf oder Runeninteraktion.
- Eine Entscheidung besitzt ID, Anzeigetext, optionale Bedingungen, Effekte und
  die ID der Folgeszene.
- Ein Story-Effekt verändert explizite Teile des Zustands, etwa Flags, Inventar,
  Beziehungen, Charakter- oder Zauberfortschritt.
- Der zentrale `GameState` enthält Kapitel-/Szenen-ID, Storyflags,
  Spielerzustand, Weltzustand, relevante Entscheidungen und Zauberfortschritt.
- Ein `SaveGame` enthält `schemaVersion` und den serialisierbaren `GameState`;
  temporäre UI-Zustände und Animationen werden nicht gespeichert.
- Ein Storykampf liefert `VICTORY`, `DEFEAT` oder `FLED`; Storydaten ordnen
  jedem erlaubten Ergebnis eine Folgeszene zu.
- Eine Runeninteraktion zeichnet einen Pointer-Pfad auf und endet mit `confirm`
  oder `cancel`. Erkennung oder Qualitätswertung bleiben späteren Versionen
  vorbehalten.

IDs sind dauerhafte technische Verträge. Sichtbare Namen und Texte dürfen sich
ändern, ohne Savegames oder Storyverweise zu brechen.

## Meilensteine

### 0. Richtung dokumentieren

Diese Roadmap aus `AGENTS.md` referenzieren und Demo-/Produktpfad eindeutig
kennzeichnen.

**Fertig, wenn:** Eine neue Session Story-first-Ausrichtung, aktiven Java-Core und
das Ziel Desktop -> Android ohne weitere Erklärung erkennt.

### 1. Ursprünglichen Core stabilisieren

- `Main` wieder kontrolliert in `GameStart -> GameLoop` führen; Demo-/Webstarts
  nur explizit auslösen.
- Konsolenausgaben schrittweise aus Actor-, Reward- und Kampfregeln entfernen;
  Ergebnisse oder Events an die Darstellung geben.
- Einen zentralen laufenden Spielzustand einführen.
- Gold/Inventar, Manakosten, Zauber-XP, verwendete POIs und Kampfresultate
  vervollständigen.
- JUnit-Tests für Actor-Leveling, Rewards, POIs, Zauberressourcen und Kampf bauen.

**Fertig, wenn:** Ein Konsolenlauf Start, Exploration, POI, Kampf, Reward und
Raumwechsel umfasst und die Kernregeln automatisiert testbar sind.

### 2. Datengetriebene Story und Autosave

- Story-JSON für Kapitel, Szenen, Entscheidungen, Bedingungen, Effekte und
  Ergebnisübergänge definieren.
- IDs, Verweise, Szenentypen und erforderliche Ergebnisübergänge beim Laden
  validieren; fehlerhafte Inhalte verhindern den Spielstart mit klarer Meldung.
- Story-Engine über den zentralen `GameState` ausführen.
- Versioniertes Autosave nach vollständig verarbeitetem Szenenübergang atomar
  schreiben, ohne bei Fehlern den vorherigen Save zu zerstören.
- Ein kleines Entwicklungskapitel ausschließlich aus JSON laden.

**Fertig, wenn:** Das Kapitel in der Konsole verzweigt spielbar ist, nach Neustart
fortgesetzt wird und ungültige Storydaten automatisiert erkannt werden.

### 3. LibGDX-Desktop-Grundgerüst

- Gradle in `core` und `lwjgl3` aufteilen.
- Scene2D-Screens für Start, Geschichte und einfache Menüs erstellen.
- Portrait-orientierten virtuellen Viewport skalierbar umsetzen.
- Storytext scrollbar und Entscheidungen per Maus sowie Tastatur bedienbar machen.
- Fontgröße und Textabstände zentral konfigurierbar halten.

**Fertig, wenn:** Das Entwicklungskapitel im Desktopfenster läuft und Core-Tests
keine LibGDX-Anwendung starten müssen.

### 4. Erster PC-Vertical-Slice

- Ein kurzes Kapitel mit mehreren Szenen und bedeutsamer Verzweigung schreiben.
- Einen Zauber durch die Story entdecken und dauerhaft freischalten.
- Zauber-XP, Levelaufstieg und eine levelgebundene Variante implementieren.
- Runen-Overlay mit Zeichnen, Löschen, Abbrechen und Bestätigen bauen. Abbrechen
  kostet nichts; Bestätigen löst den Zauber genau einmal aus.
- Einen optionalen Storykampf mit Folgeszenen für Sieg, Niederlage und Flucht
  integrieren.
- Autosave, Fortsetzen und Neues Spiel vollständig in der UI anbieten.

**Fertig, wenn:** Das Kapitel am PC ohne Konsole vollständig spielbar ist und
Entscheidung, Rune, optionaler Kampf, Niederlagenpfad und Save/Load enthält.

### 5. Ausbau und Content-Werkzeuge

- JSON-Schema nach dem Vertical-Slice stabilisieren und dokumentieren.
- Kapitel, Konsequenzen, Items, Zauberwirkungen, Gegner und Wege erweitern.
- Prüfungen für Storygraph, unerreichbare Szenen und fehlende Texte ergänzen.
- Erst wenn JSON-Pflege zum Engpass wird, einen separaten Editor planen, der nur
  gültiges Runtime-JSON importiert und exportiert.

**Fertig, wenn:** Ein Kapitel ohne Java-Änderung ergänzt und automatisch validiert
werden kann.

### 6. Android und Google Play

- Android-Modul ergänzen; Core und Assets mit Desktop teilen.
- Maus und Touch über dieselbe Pointer-Abstraktion verarbeiten.
- Hochformatgrößen, Cutouts, Pause, Fortsetzung und Prozessneustart testen.
- Saves im privaten App-Speicher ablegen und unnötige Berechtigungen vermeiden.
- Signiertes Android App Bundle, Paket-ID, Icons, Versionierung, Store-Texte,
  Screenshots, Datenschutzangaben und Inhaltsfreigabe vorbereiten.
- Vor jedem Release die aktuellen Ziel-API- und Play-Vorgaben prüfen. Ab dem
  31. August 2026 verlangen neue Apps und Updates derzeit Android 16/API 36.

**Fertig, wenn:** Derselbe Slice auf einem physischen Android-Gerät läuft,
Unterbrechungen ohne Fortschrittsverlust übersteht und ein signiertes AAB die
aktuellen Play-Console-Prüfungen besteht.

## Offene Produktentscheidung: Rätsel

Nach der Einführung des zentralen `GameState` wird geprüft, ob Rätsel das Spiel
als wiederkehrende Mechanik sinnvoll bereichern. Eine `RiddlePhase` ist noch keine
festgelegte Zielarchitektur.

Ein lokales Rätsel kann zunächst eine Story-Szene oder POI-Interaktion mit
Bedingungen und Folgen sein. Eine eigene Phase lohnt sich erst, wenn Rätsel
wiederholt einen eigenen Eingabeablauf, fortlaufenden Zustand, Abbruch sowie
explizite Erfolgs- und Fehlschlagsresultate benötigen.

Vor einer Implementierung werden daher zuerst die erzählerische Aufgabe und ein
konkretes Beispielrätsel entworfen. So entscheidet der benötigte Ablauf über die
Architektur und nicht allein der Wunsch nach einer zusätzlichen Phase.

## Bewusst nicht im ersten Vertical-Slice

- Handschrift-/Runenerkennung oder Qualitätswertung
- Cloud-Saves, Accounts, Onlinezwang oder Mehrspieler
- mehrere manuell verwaltete Spielstände
- visueller Story-Editor
- parallele Vue-Demo-Weiterentwicklung
- vollständige offene Welt

Diese Punkte dürfen die Fertigstellung des ersten kurzen Kapitels nicht blockieren.

## Plattformreferenzen

- [LibGDX-Projektgenerierung](https://libgdx.com/wiki/start/project-generation)
- [LibGDX JDK-Auswahl](https://libgdx.com/wiki/articles/java-development-kit-selection)
- [Android Java-Versionen](https://developer.android.com/build/jdks)
- [Google-Play-Ziel-API](https://developer.android.com/google/play/requirements/target-sdk)
