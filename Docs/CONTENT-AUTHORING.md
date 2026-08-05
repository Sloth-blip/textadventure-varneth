# Varneth Content Authoring

## Zweck

Dieses Dokument beschreibt, wie Story, Szenen, Dialoge, Quests und Rätsel in
Notion vorbereitet werden, damit sie zuverlässig in Varneth übertragen werden
können.

Der Vertrag ist absichtlich autorenfreundlich:

- **Notion ist die redaktionelle Quelle** für freigegebene Story-Inhalte.
- **Das Repository ist die ausführbare Kopie** aus Java beziehungsweise später
  validiertem JSON.
- Freie Ideen dürfen unfertig und widersprüchlich sein.
- Nur Inhalte mit dem Status **Bereit zur Integration** gelten als konkrete
  Übergabe.
- Technische IDs, JSON und Implementierungsdetails können bei der Übertragung
  gemeinsam ergänzt werden.

Das erste übertragene Spielintro dient als Test. Danach wird dieses Dokument
anhand der tatsächlichen Reibungspunkte angepasst.

## Empfohlener Notion-Bereich

~~~text
Varneth – Story Workshop
├── Start und Autorenregeln
├── Ideenlabor
├── Kapitel und Quests
├── Szenen
├── Lore und Figuren
└── Bereit zur Integration
~~~

"Bereit zur Integration" kann eine gefilterte Ansicht derselben
Szenen-Datenbank sein. Inhalte müssen nicht kopiert werden.

### Ideenlabor

Freier Fließtext für Einfälle, Varianten und offene Fragen. Hier sind keine
Pflichtfelder oder technischen IDs nötig.

### Kapitel und Quests

Beschreibt größere Handlungsbögen. Ein Kapitel ordnet Szenen zu einer
Haupthandlung. Eine Quest beschreibt vorerst einen erzählerischen Ziel- oder
Nebenstrang und ist nicht automatisch ein eigenes Runtime-System.

### Szenen

Die Szene ist die kleinste verbindliche Story-Einheit mit einem Einstieg und
mindestens einem Ergebnis oder Übergang. Dialoge, Kämpfe, Rätsel und
Erkundungsinteraktionen gehören zunächst in die Szene, die sie auslöst.

### Lore und Figuren

Nachschlagebereich für Weltwissen, Orte, Personen, Begriffe und Magieregeln.
Lore wird erst zu Spielerwissen, wenn eine Szene einen entsprechenden Effekt
auslöst.

## Statusworkflow

| Status | Bedeutung |
|---|---|
| Idee | Unverbindlicher Einfall |
| Entwurf | Wird aktiv geschrieben |
| Review | Inhalt soll gemeinsam geprüft werden |
| Bereit zur Integration | Darf ins Projekt übertragen werden |
| Integriert | Ausführbare Fassung ist im Repository vorhanden |
| Überarbeiten | Bereits integrierter Inhalt soll geändert werden |

Ich behandle ausschließlich **Bereit zur Integration** als
Implementierungsauftrag. Andere Einträge darf ich als Kontext lesen, aber nicht
ungefragt kanonisieren.

## Begriffe

### Kapitel

Ein größerer Storyabschnitt mit Startszene, beteiligten Szenen und einem oder
mehreren möglichen Endzuständen.

### Szene

Ein konkreter Storyknoten. Eine Szene kann Erzähltext, Dialog,
Spielerentscheidungen und eine Mechanik enthalten. Sie endet mit einem
definierten Übergang oder Ergebnis.

Vorläufige Szenentypen dienen nur der Verständigung und sind noch keine
festgelegten Java- oder JSON-Enums:

- Erzählung
- Dialog
- Erkundung
- Kampf
- Rätsel oder Challenge
- Übergang

### Dialog

Gesprochener oder innerer Text innerhalb einer Szene. Ein Dialog braucht erst
dann ein eigenes Datenmodell, wenn echte Szenen zeigen, dass einfache
Sprecherzeilen und Entscheidungen nicht ausreichen.

### Quest

Ein erzählerischer Zielstrang, der mehrere Szenen verbinden kann. Bitte
beschreibe Ziel, Auslöser und mögliche Abschlüsse. Fortschrittszähler und
Questlog sind keine automatische Voraussetzung.

### Rätsel oder Challenge

Eine Interaktion mit eigener Eingabe und explizitem Erfolg, Fehlschlag oder
Abbruch. Die bisherigen Prototypen sind in
[RIDDLE-PROTOTYPES.md](RIDDLE-PROTOTYPES.md) beschrieben.

## Eigenschaften einer Szene in Notion

Für die Szenen-Datenbank empfehlen sich folgende Eigenschaften:

| Eigenschaft | Pflicht bei Übergabe | Beschreibung |
|---|---:|---|
| Arbeitstitel | ja | Lesbarer Name der Szene |
| Status | ja | Workflowstatus |
| Kapitel oder Quest | ja | Zugehöriger Handlungsbogen |
| Szenentyp | ja | Erzählung, Dialog, Erkundung, Kampf, Challenge oder Übergang |
| Ort | ja | Wo die Szene stattfindet |
| Erzählerischer Zweck | ja | Warum die Szene existiert |
| Technische ID | nein | Wird spätestens bei Integration vorgeschlagen |
| Vorherige Szenen | nein | Bekannte Eingänge |
| Folgeszenen | ja | Ergebnisse und Ziele |
| Integrationsnotiz | nein | Commit, Abweichungen oder technische Hinweise |

Eine technische ID ist nach der Integration ein stabiler Vertrag. Sichtbarer
Titel und Text dürfen später geändert werden, die ID nicht ohne Migration.

Empfohlenes Format:

~~~text
chapter.<name>
scene.<kapitel>.<name>
choice.<szene>.<name>
challenge.<szene>.<name>
knowledge.<name>
world.<ort>.<zustand>
~~~

Du musst diese IDs nicht allein erfinden. Ein verständlicher Arbeitstitel
reicht, damit ich Vorschläge machen kann.

## Vorlage für eine vollständige Szene

Die folgende Vorlage kann direkt in eine Notion-Seite kopiert werden:

~~~markdown
# [Arbeitstitel]

Status: Entwurf
Kapitel oder Quest:
Szenentyp:
Ort:
Erzählerischer Zweck:
Technische ID: optional

## Zusammenfassung

Was passiert in dieser Szene?

## Einstiegsvoraussetzungen

- Was muss vorher passiert sein?
- Benötigtes Wissen, Item, Zauber oder Attribut?
- Darf die Szene immer betreten werden?

## Einstieg

Erzähltext, Atmosphäre und sichtbare Situation.

## Dialog

Erzähler:
Arenn:
Figur:
Arenn (Gedanke):

## Spielerhandlungen oder Entscheidungen

### [Sichtbarer Entscheidungstext]

Voraussetzung:
Reaktion:
Effekte:
Folgeszene:

## Mechanik

Nur ausfüllen, wenn Kampf, Rätsel, Rune oder eine andere Interaktion vorkommt.

## Ergebnisse und Übergänge

### Erfolg

Text:
Effekte:
Folgeszene:

### Fehlschlag

Text:
Effekte:
Folgeszene oder erneuter Versuch:

### Abbruch

Text:
Kosten:
Was bleibt unverändert?
Folgeszene:

## Wiederholung oder Rückkehr

Was sieht der Spieler, wenn die Szene bereits abgeschlossen wurde?

## Offene Fragen

- ...
~~~

Nicht benötigte Abschnitte dürfen entfallen.

## Minimalvorlage für den ersten Spieleinstieg

Für den geplanten Test-Einstieg reicht zunächst diese kürzere Vorlage:

~~~markdown
# [Titel des Spieleinstiegs]

Status: Bereit zur Integration
Ort:
Erzählerischer Zweck:

## Starttext

Der vollständige Text, den der Spieler zu Beginn liest.

## Erste Handlungsmöglichkeiten

1. [Anzeigetext]
   - Reaktion:
   - Folge:

2. [Anzeigetext]
   - Reaktion:
   - Folge:

## Gewünschter Übergang

Was soll nach dem Einstieg passieren oder welche Szene beginnt danach?

## Wichtige Zustandsänderungen

Welche Entscheidung, Information oder Konsequenz soll später noch relevant sein?

## Hinweise für die Umsetzung

Was ist verbindlich, und wo darf für den ersten Test ein Platzhalter verwendet
werden?
~~~

Wenn der Einstieg linear ist, dürfen die Handlungsmöglichkeiten entfallen.
Bitte schreibe dann, wann die Szene als abgeschlossen gilt.

## Dialogformat

Erzählung und Sprecherzeilen dürfen angenehm lesbar bleiben:

~~~markdown
Erzähler:
Der Wind drückt kalten Regen gegen das Fenster.

Arenn:
„Das war vor einer Stunde noch nicht da.“

Arenn (Gedanke):
Vielleicht hätte ich doch umkehren sollen.
~~~

Für eine Spielerentscheidung:

~~~markdown
### Die Tür öffnen

Anzeigetext:
Die Tür vorsichtig öffnen.

Voraussetzung:
Keine.

Reaktion:
Arenn legt die Hand auf den kalten Griff.

Effekte:
Die Tür gilt als geöffnet.

Folgeszene:
Der Raum hinter der Tür.
~~~

Wenn eine Option nur mit Wissen sichtbar sein soll, genügt zunächst:

~~~text
Nur sichtbar, wenn Arenn den wahren Namen des Siegels kennt.
~~~

Die technische Condition wird bei der Integration daraus abgeleitet.

## Bedingungen

Bedingungen beschreiben, wann eine Szene oder Option verfügbar ist. Sie dürfen
zunächst in natürlicher Sprache geschrieben werden.

Typische Bedingungen:

- vorherige Szene abgeschlossen
- bestimmte Entscheidung getroffen
- Wissen erlangt
- Item vorhanden
- Zauber gelernt oder aktuell wirkbar
- Attribut erreicht
- Gegner besiegt
- Weltzustand verändert
- Kombinationen mit UND oder ODER

Bitte unterscheide möglichst zwischen:

- **sichtbar:** Die Option wird überhaupt angezeigt.
- **nutzbar:** Die Option ist sichtbar, kann aber aktuell scheitern.
- **erklärt:** Der Spieler versteht erst durch Wissen, was die Option bedeutet.

Diese Unterschiede waren beim Feuersiegel bereits relevant.

## Effekte

Effekte beschreiben dauerhafte oder regelrelevante Folgen. Sie werden in Notion
semantisch, nicht als Java oder JSON geschrieben.

Beispiele:

- Arenn erhält den Feuermagiekristall.
- Arenn lernt Steinschleuder.
- Arenn versteht die Symbolreihenfolge des Siegels.
- Der Durchgang zur verborgenen Kammer öffnet sich.
- Eine neue Dialogoption bei Figur X wird sichtbar.
- Beziehung zu Figur X sinkt.
- Der nächste Kampf startet.
- Kapitel endet mit Ergebnis Y.

Bei der Integration werden diese Aussagen auf Spieler-, Story- oder Weltzustand
und stabile IDs abgebildet. Eine Dialogoption sollte möglichst aus Wissen oder
Weltzustand abgeleitet werden, statt denselben Fakt doppelt zu speichern.

## Kämpfe

Für einen Storykampf bitte angeben:

- Gegner oder gewünschte Gegnerrolle
- warum der Kampf stattfindet
- ob Flucht erlaubt ist
- Konsequenz bei Sieg
- Konsequenz bei Niederlage
- Konsequenz bei Flucht
- Belohnungen
- besonderer Dialog vor oder nach dem Kampf

Eine Niederlage muss nicht zum Game Over führen. Jeder erlaubte Ausgang braucht
eine erzählerische Folge.

## Rätsel und andere Challenges

Zusätzlich zur Szenenvorlage bitte beschreiben:

~~~markdown
## Challenge

Mechanik:
Was sieht oder weiß der Spieler?
Gewünschte Eingabe:
Voraussetzungen:
Hinweise:
Ist ein erneuter Versuch erlaubt?

### Erfolg

Text:
Effekte:
Folge:

### Falscher Versuch

Text:
Kosten oder Konsequenzen:
Erneuter Versuch:

### Abbruch

Text:
Kosten:
Später wiederholbar:

### Wiederholung nach Erfolg

Text:
~~~

Für einen Zauber am Rätsel bitte zusätzlich festlegen:

- Muss der Zauber gelernt sein oder reicht ein Kristall?
- Welche Magiequelle ist erlaubt?
- Wann wird die Rune gezeichnet?
- Wann werden Ressourcen bezahlt?
- Was passiert bei einem falschen Zauber?
- Reicht ein Teilcast?
- Darf fehlende Ladung den Storyfortschritt blockieren?

## Übertragung ins Projekt

Wenn eine Seite **Bereit zur Integration** ist, läuft die erste manuelle
Übertragung so:

1. Ich lese die freigegebene Seite und ihre direkt verknüpften Inhalte.
2. Ich liste unklare oder widersprüchliche Stellen getrennt auf.
3. Ich schlage fehlende stabile IDs und technische Zustände vor.
4. Wir bestätigen Entscheidungen, die den Storyverlauf materiell verändern.
5. Ich übertrage die Szene zunächst in die aktuelle ausführbare Form.
6. Bedingungen, Effekte und Übergänge erhalten automatisierte Tests.
7. Der Konsolenlauf wird gemeinsam geprüft.
8. Die Notion-Seite erhält den Status **Integriert** und eine Integrationsnotiz.

Eine automatische Notion-Synchronisation ist zunächst nicht vorgesehen. Wenn
integrierter Text in Notion geändert wird, wechselt sein Status auf
**Überarbeiten** und wird erneut übertragen.

## Übergabe-Checkliste

Eine Szene ist für den ersten Test ausreichend beschrieben, wenn:

- [ ] Status ist **Bereit zur Integration**.
- [ ] Ort und erzählerischer Zweck sind klar.
- [ ] Einstiegs- beziehungsweise Haupttext ist vorhanden.
- [ ] Spielerhandlungen oder Linearität sind erkennbar.
- [ ] Voraussetzungen sind verständlich beschrieben.
- [ ] Erfolg, Fehlschlag und Abbruch sind angegeben, sofern relevant.
- [ ] Dauerhafte Folgen sind genannt.
- [ ] Folgeszene oder Abschluss ist erkennbar.
- [ ] Platzhalter und verbindliche Texte sind unterscheidbar.

Fehlende technische IDs, JSON und Klassennamen verhindern die Übergabe nicht.

## Noch nicht festgelegt

Der erste Story-Einstieg soll zeigen, welche Teile dieser Vorlage tatsächlich
nützlich sind. Noch offen bleiben:

- endgültige Notion-Datenbanken und Relationen
- verbindliches JSON-Schema
- generische Condition- und Effect-Syntax
- eigene Runtime-Modelle für Quest und Dialog
- Name und Verantwortung einer möglichen RiddlePhase oder ChallengeScene
- automatischer Import oder Export aus Notion

Diese Punkte werden erst stabilisiert, wenn mindestens eine echte Storyszene
übertragen und gespielt wurde.
