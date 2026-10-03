# Java Shop System

Eine konsolenbasierte Shop-Anwendung, die in Java entwickelt wird.

Das Projekt befindet sich aktuell noch **in Bearbeitung** und soll die grundlegende Logik eines einfachen Online-Shops abbilden.

Die Anwendung ermöglicht es, Produkte zu verwalten, Produkte in einen Warenkorb zu legen und Bestellungen über die Konsole abzuschließen.

---

## Funktionen

Geplant bzw. teilweise umgesetzt sind folgende Funktionen:

- Produkte anzeigen
- Produkte hinzufügen
- Produkte bearbeiten
- Produkte löschen
- Produkte in den Warenkorb legen
- Produkte aus dem Warenkorb entfernen
- Gesamtpreis des Warenkorbs berechnen
- Lagerbestand prüfen
- Bestellungen abschließen
- Bestellungen in der Datenbank speichern
- Lagerbestand nach einer Bestellung aktualisieren
- Frühere Bestellungen anzeigen
- Ungültige Eingaben prüfen

---

## Womit gearbeitet wird

In diesem Projekt werden unter anderem folgende Konzepte verwendet:

- **CRUD-Operationen** – Produkte erstellen, anzeigen, bearbeiten und löschen
- **Business Logic** – Bestellungen verarbeiten, Gesamtpreise berechnen und Lagerbestände aktualisieren
- **Eingabevalidierung** – ungültige Mengen, nicht vorhandene Produkte oder zu geringe Lagerbestände abfangen
- **Objektorientierte Programmierung** – Aufteilung der Anwendung in verschiedene Klassen und Objekte
- **Relationale Datenbanken** – Speicherung von Produkten und Bestellungen in mehreren miteinander verbundenen Tabellen
- **Datenbankbeziehungen** – Verknüpfung von `orders`, `order_items` und `products`
- **SQL-Abfragen** – Daten lesen, speichern, aktualisieren und löschen
- **JDBC** – Kommunikation zwischen Java und der SQLite-Datenbank
- **Warenkorb-Logik** – Produkte hinzufügen, entfernen und Mengen verwalten
- **Bestelllogik** – Warenkorb prüfen, Bestellung speichern und Lagerbestand anpassen
- **Fehlerbehandlung** – Fehler und ungültige Eingaben kontrolliert behandeln
- **Trennung von Programmlogik und Datenbankzugriff** – unterschiedliche Aufgaben werden auf verschiedene Klassen verteilt


---

## Beispiel der Konsolenoberfläche

```text
====================================
         JAVA SHOP SYSTEM
====================================

1. Produkte anzeigen
2. Produkt zum Warenkorb hinzufügen
3. Warenkorb anzeigen
4. Bestellung abschließen
5. Bestellungen anzeigen
6. Admin-Bereich
0. Beenden

Auswahl: