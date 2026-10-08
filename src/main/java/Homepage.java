import java.util.ArrayList;
import java.util.Scanner;

// Zentrale Benutzeroberfläche des Shop-Systems
public class Homepage {

    Scanner scanner = new Scanner(System.in); // Gemeinsamer Scanner für Benutzereingaben
    ArrayList<Product> sc = new ArrayList<>();   // Speichert die Produkte des Warenkorbs

    // Gemeinsame Datenbankverbindung
    Database dtb = new Database();

    // Bereiche des Shop-Systems
    ShoppingCard shoppingCard = new ShoppingCard(sc,scanner,dtb);
    OrderOrderItems orderOrderItems = new OrderOrderItems(scanner,dtb);
    AdminArea adminArea = new AdminArea(scanner,dtb);

    // Startet das Hauptmenü und leitet zur ausgewählten Funktion weiter
    public void start(){
        while(true){
            System.out.println("========================================================================");
            System.out.println("                            JAVA SHOP SYSTEM                            ");
            System.out.println("========================================================================");
            System.out.println();
            System.out.println("[1] Produkte anzeigen");
            System.out.println("[2] Produkt in den Warenkorb legen");
            System.out.println("[3] Warenkorb anzeigen");
            System.out.println("[4] Bestellung abschließen");
            System.out.println("[5] Bestellungen anzeigen");
            System.out.println("[6] Admin-Bereich");
            System.out.println();
            System.out.println("[7] Beenden");
            System.out.println();
            System.out.println(">> Auswahl:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    dtb.getAllProductsFromProducts();
                    break;
                case 2:
                    shoppingCard.putInside();
                    break;
                case 3:
                    shoppingCard.seeInside();
                    break;
                case 4:
                    shoppingCard.finishBuying();
                    break;
                case 5:
                    orderOrderItems.start();
                    break;
                case 6:
                    System.out.println("[INFO] Admin-Bereich wird geöffnet...");
                    adminArea.start();
                    break;
                case 7:
                    System.out.println("[INFO] Programm wird beendet...");
                    return;
                default:
                    System.out.println("[FEHLER] Bitte eine Zahl zwischen 1 und 7 eingeben!");
            }
        }
    }

}