import java.util.Scanner;

// Verwaltet administrative Funktionen für Produkte
public class AdminArea {
    Scanner scanner;
    Database dtb;

    // Verwendet den gemeinsamen Scanner und die bestehende Datenbankinstanz
    public AdminArea(Scanner scanner, Database dtb){
        this.scanner = scanner;
        this.dtb = dtb;
    }

    // Startet das Admin-Menü
    public void start(){
        while(true){
            System.out.println("========================================================================");
            System.out.println("                             ADMIN-BEREICH                              ");
            System.out.println("========================================================================");
            System.out.println("[1] Produkt hinzufügen");
            System.out.println("[2] Produkt bearbeiten");
            System.out.println("[3] Produkt löschen");
            System.out.println("[4] Alle Produkte anzeigen");
            System.out.println();
            System.out.println("[5] Zurück");
            System.out.println();
            System.out.println(">> Auswahl:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Entfernt den verbleibenden Zeilenumbruch
            switch(choice){
                case 1:
                    add();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    dtb.getAllProductsFromProducts();
                    break;
                case 5:
                    return;
            }
        }
    }

    // Liest die Produktdaten ein und fügt ein neues Produkt zur Datenbank hinzu
    public void add(){
        System.out.println(">> Produktname: ");
        String name = scanner.nextLine();

        System.out.println(">> Preis: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println(">> Bestand: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        dtb.addProducts(name, price, stock);
    }

    // Bearbeitet Name, Preis oder Bestand eines vorhandenen Produkts
    public void edit(){
        System.out.println("========================================================================");
        System.out.println("                           PRODUKT BEARBEITEN                           ");
        System.out.println(">> Bitte Produkt-ID eingeben:");

        int id = scanner.nextInt();
        scanner.nextLine(); // Entfernt den verbleibenden Zeilenumbruch

        Product product = dtb.getProductFromProducts(id);

        // Bearbeitung wird abgebrochen, wenn die angegebene ID nicht existiert
        if (product == null) {
            System.out.println("[FEHLER] Produkt mit der ID " + id + " wurde nicht gefunden!");
            return;
        }

        while(true){

            System.out.println("[1] Produktname ändern");
            System.out.println("[2] Preis ändern");
            System.out.println("[3] Bestand ändern");
            System.out.println();
            System.out.println("[4] Zurück");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    System.out.println(">> Neuen Produktnamen eingeben:");
                    String name = scanner.nextLine();
                    dtb.editProducts(id,name,product.price, product.stock);
                    System.out.println("[OK] Produktname erfolgreich aktualisiert!");
                    break;
                case 2:
                    System.out.println(">> Neuen Preis eingeben:");
                    double price = scanner.nextDouble();
                    dtb.editProducts(id,product.name,price,product.stock);
                    scanner.nextLine();
                    System.out.println("[OK] Preis erfolgreich aktualisiert!");
                    break;
                case 3:
                    System.out.println(">> Neuen Bestand eingeben:");
                    int stock = scanner.nextInt();
                    dtb.editProducts(id,product.name,product.price,stock);
                    scanner.nextLine();
                    System.out.println("[OK] Bestand erfolgreich aktualisiert!");
                    break;
                case 4:
                    return;

            }
        }
    }

    // Löscht ein Produkt anhand seiner ID
    public void delete(){
        System.out.println("========================================================================");
        System.out.println("                            PRODUKT LÖSCHEN                             ");
        System.out.println(">> Bitte Produkt-ID eingeben:");

        int id = scanner.nextInt();
        scanner.nextLine();

        boolean deleted = dtb.deleteFromProducts(id);

        if(deleted){
            System.out.println("[OK] Produkt erfolgreich gelöscht!");
        }
        else{
            System.out.println("[FEHLER] Produkt wurde nicht gefunden!");
        }

    }
}