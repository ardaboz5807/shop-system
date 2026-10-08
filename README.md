# Java Shop System

Das ist ein kleines Shop-System, das ich in Java geschrieben habe.

Ich habe das Projekt hauptsächlich gemacht, um meine Java- und Backend-Kenntnisse weiter zu vertiefen. Mir war wichtig, nicht einfach nur einzelne kleine Übungen zu machen, sondern mal ein größeres Projekt zu bauen, bei dem mehrere Klassen zusammenarbeiten und Daten auch wirklich dauerhaft in einer Datenbank gespeichert werden.

Das Projekt habe ich selbst geschrieben und währenddessen Schritt für Schritt erweitert.

## Was kann das Programm?

Das Programm läuft aktuell komplett über die Konsole.

Man kann Produkte anzeigen, sie in einen Warenkorb legen und anschließend eine Bestellung abschließen.

Dabei wird unter anderem überprüft, ob ein Produkt überhaupt existiert und ob genug Bestand vorhanden ist. Im Warenkorb kann man außerdem Produkte wieder entfernen oder die Menge ändern.

Beim Abschließen einer Bestellung wird der Gesamtpreis berechnet und die Bestellung anschließend in einer SQLite-Datenbank gespeichert.

Die einzelnen Produkte einer Bestellung werden ebenfalls gespeichert, sodass man später noch sehen kann, welche Produkte zu welcher Bestellung gehört haben.

Nach einer erfolgreichen Bestellung wird außerdem der Lagerbestand der jeweiligen Produkte angepasst.

Es gibt zusätzlich einen kleinen Admin-Bereich, über den Produkte hinzugefügt, bearbeitet und gelöscht werden können.

## Funktionen

- Produkte anzeigen
- Produkte über ihre ID auswählen
- Produkte zum Warenkorb hinzufügen
- verfügbare Menge überprüfen
- Warenkorb anzeigen
- Gesamtpreis berechnen
- Produkte aus dem Warenkorb entfernen
- Menge im Warenkorb ändern
- Bestellung abschließen
- Bestellungen in SQLite speichern
- einzelne Bestellpositionen speichern
- Lagerbestand nach einer Bestellung aktualisieren
- vergangene Bestellungen anzeigen
- Details einer Bestellung über die Bestellnummer anzeigen
- Produkte über einen Admin-Bereich hinzufügen
- Produkte bearbeiten
- Produkte löschen

## Datenbank

Für das Projekt benutze ich SQLite.

Die Verbindung zwischen Java und der Datenbank läuft über JDBC.

Beim Start des Programms werden die benötigten Tabellen automatisch erstellt, falls sie noch nicht vorhanden sind.

Ich benutze aktuell drei Tabellen:

### products

Hier werden die Produkte gespeichert.

Gespeichert werden:

`id`, `name`, `price` und `stock`

### orders

Hier werden die eigentlichen Bestellungen gespeichert.

Eine Bestellung enthält unter anderem:

`id`, `total_price`, `status` und `order_date`

### order_items

Diese Tabelle verbindet die Produkte mit einer Bestellung.

Dadurch kann eine Bestellung mehrere Produkte enthalten und ich kann später trotzdem noch nachvollziehen, welche Produkte zu welcher Bestellung gehört haben.

Hier speichere ich unter anderem:

`order_id`, `product_id`, `quantity` und `price`

## Aufbau des Projekts

### Main.java

Hier startet das Programm.

Von dort wird die `Homepage` aufgerufen.

### Homepage.java

Die Homepage ist das Hauptmenü des Programms.

Von hier kommt man zu den Produkten, zum Warenkorb, zu den Bestellungen und zum Admin-Bereich.

### Product.java

Die `Product`-Klasse benutze ich als einfaches Modell für meine Produkte.

Ein Produkt hat eine ID, einen Namen, einen Preis und einen Bestand.

Zusätzlich gibt es eine Methode, mit der der Gesamtpreis anhand der Menge berechnet wird.

### ShoppingCard.java

Hier befindet sich ein großer Teil der eigentlichen Shop-Logik.

Die Klasse kümmert sich zum Beispiel darum, Produkte in den Warenkorb zu legen, Mengen zu ändern, Produkte zu entfernen und eine Bestellung abzuschließen.

Beim Abschluss der Bestellung wird auch überprüft, ob die Daten gespeichert werden konnten und anschließend wird der Lagerbestand angepasst.

### Database.java

In dieser Klasse habe ich meine Datenbankzugriffe gesammelt.

Hier befinden sich die SQL-Abfragen für Produkte, Bestellungen und Bestellpositionen.

Ich benutze dabei unter anderem:

`SELECT`, `INSERT`, `UPDATE` und `DELETE`

Für viele Abfragen benutze ich `PreparedStatement`, damit Werte sauber an die SQL-Abfragen übergeben werden können.

### AdminArea.java

Hier befindet sich der Admin-Bereich.

Damit können Produkte hinzugefügt, bearbeitet und gelöscht werden.

### OrderOrderItems.java

Diese Klasse ist dafür da, bereits gespeicherte Bestellungen anzuzeigen.

Über eine Bestellnummer kann man sich die einzelnen Produkte einer Bestellung anzeigen lassen.

## Was ich bei dem Projekt gelernt bzw. geübt habe

Das Projekt war vor allem dafür gedacht, meine Kenntnisse in Java und Backend-Entwicklung zu verbessern.

Dabei habe ich mich besonders mit folgenden Sachen beschäftigt:

- objektorientierter Programmierung
- Aufteilung eines Programms auf mehrere Klassen
- Zusammenarbeit verschiedener Klassen
- Übergabe von Objekten zwischen Klassen
- ArrayLists
- Scanner und Konsoleneingaben
- Schleifen und Switch-Statements
- Warenkorb-Logik
- Preisberechnungen
- Lagerbeständen
- Bestelllogik
- SQLite
- SQL
- JDBC
- PreparedStatements
- ResultSets
- CRUD-Operationen
- Fehlerbehandlung mit try/catch
- Try-with-Resources
- automatisch generierten IDs
- Speicherung von Bestellungen und einzelnen Bestellpositionen

Besonders interessant fand ich dabei die Logik hinter einer Bestellung.

Eine Bestellung besteht bei mir nicht einfach nur aus einem Gesamtpreis. Zuerst wird eine Bestellung erstellt und danach werden die einzelnen Produkte mit der dazugehörigen Bestell-ID gespeichert.

Anschließend wird für jedes Produkt der aktuelle Lagerbestand aus der Datenbank geladen und die gekaufte Menge davon abgezogen.

Dadurch konnte ich besser verstehen, wie verschiedene Datensätze miteinander zusammenhängen und wie man solche Abläufe mit Java und SQL umsetzen kann.

## Technologien

Java  
SQLite  
SQL  
JDBC

## Starten

Das Programm wird über die `Main.java` gestartet.

Beim Start wird automatisch die Datenbank geladen und überprüft, ob die benötigten Tabellen bereits vorhanden sind.

Danach öffnet sich das Hauptmenü in der Konsole.

Für das Projekt wird zusätzlich ein SQLite JDBC Driver benötigt.

## Warum eine Konsolenanwendung?

Bei diesem Projekt ging es mir nicht darum, ein aufwendiges Frontend zu bauen.

Ich wollte mich hauptsächlich auf die Logik hinter dem Programm konzentrieren und besser verstehen, wie Java mit einer Datenbank zusammenarbeiten kann.

Deshalb habe ich das Projekt bewusst als Konsolenanwendung umgesetzt.

In Zukunft könnte man darauf natürlich noch ein grafisches Interface oder beispielsweise eine Weboberfläche aufbauen.