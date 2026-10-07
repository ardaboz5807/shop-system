import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class ShoppingCard {
    ArrayList<Product> sc;  //The ShoppingCard List
    Scanner scanner;
    Database dtb;

    //Externe Variable
    String today = LocalDate.now().toString();

    public ShoppingCard(ArrayList<Product> sc, Scanner scanner, Database dtb){
        this.sc = sc;
        this.scanner = scanner;
        this.dtb = dtb;
    }

    //Homepage Funktion 2
    public void putInside(){
        while(true){
            System.out.println(">> Produkt-ID eingeben:");

            int id = scanner.nextInt();
            scanner.nextLine();

            Product product = dtb.getProductFromProducts(id);

            if(product == null){
                System.out.println("[FEHLER] Produkt wurde nicht gefunden!");
            }
            else{
                System.out.println("========================================");
                System.out.println("            PRODUKT AUSGEWÄHLT           ");
                System.out.println("========================================");
                System.out.println();
                System.out.println("Produkt:");
                System.out.println(product.name);
                System.out.println();
                System.out.println("Preis:");
                System.out.printf("%.2f €%n", product.price);
                System.out.println();
                System.out.println("Verfügbarer Bestand:");
                System.out.println(product.stock);
                System.out.println();
                System.out.println(">> Gewünschte Menge:");
                int amount = scanner.nextInt();
                scanner.nextLine();
                if(amount > product.stock || amount <= 0) {
                    System.out.println("[FEHLER] Gewünschte Menge ist nicht verfügbar!");
                    System.out.println("Vorgang abgebrochen...");
                }
                else{
                    System.out.println("[OK] " + amount + "x " + product.name + " wurde zum Warenkorb hinzugefügt!");
                    Product ci = new Product(product.id, product.name, product.price, amount);
                    sc.add(ci);
                }
            }

            System.out.println();
            System.out.println("[1] Weiter einkaufen");
            System.out.println("[2] Zurück zum Hauptmenü");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                continue;
            }
            System.out.println(">> Zurück zum Hauptmenü...");
            return;
        }
    }

    //Funktion 3
    public void seeInside(){
        while(true){
            double totalPrice = 0;
            System.out.println("========================================================================");
            System.out.println("                               WARENKORB                                ");
            System.out.println("========================================================================");
            System.out.println();
            System.out.printf("%-4s %-28s %8s %12s %14s%n",
                    "ID",
                    "Produkt",
                    "Menge",
                    "Preis",
                    "Gesamt"
            );
            System.out.println("------------------------------------------------------------------------");
            for(Product cartItem : sc){
                totalPrice += cartItem.getTotalPrice();
                System.out.printf("%-4d %-28s %8d %10.2f € %12.2f €%n",
                        cartItem.id,
                        cartItem.name,
                        cartItem.stock,
                        cartItem.price,
                        cartItem.getTotalPrice()
                );
            }
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("%-53s %14.2f €%n", "Gesamtpreis:", totalPrice);
            System.out.println();
            System.out.println();
            System.out.println("[1] Produkt entfernen");
            System.out.println("[2] Menge ändern");
            System.out.println("[3] Bestellung abschließen");
            System.out.println("[4] Zurück");
            System.out.println();
            System.out.println(">> Auswahl:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    removeProduct();
                    break;
                case 2:
                    changeProductAmount();
                    break;
                case 3:
                    finishBuying();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("[FEHLER] Bitte eine Zahl zwischen 1 und 4 eingeben!");
            }
        }
    }

    //Funktion 3.1
    public void removeProduct(){
        System.out.println("========================================");
        System.out.println("           PRODUKT ENTFERNEN             ");
        System.out.println("========================================");
        System.out.println(">> Produkt-ID eingeben:");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(int i = 0; i<sc.size(); i++){
            if(sc.get(i).id == id){
                System.out.println("[OK] Produkt erfolgreich aus dem Warenkorb entfernt!");
                sc.remove(i);
                return;
            }
        }
        System.out.println("[FEHLER] Produkt wurde im Warenkorb nicht gefunden!");
    }

    //Funktion 3.2
    public void changeProductAmount(){
        System.out.println("========================================");
        System.out.println("             MENGE ÄNDERN                ");
        System.out.println("========================================");
        System.out.println(">> Produkt-ID eingeben:");
        int id = scanner.nextInt();
        scanner.nextLine();
        for(Product product : sc){
            if(product.id == id){
                Product dtbproduct = dtb.getProductFromProducts(id);
                System.out.println(">> Neue Menge eingeben:");
                int amount = scanner.nextInt();
                scanner.nextLine();
                if(amount > dtbproduct.stock || amount <= 0){
                    System.out.println("[FEHLER] Gewünschte Menge ist nicht verfügbar!");
                    System.out.println("Vorgang abgebrochen...");
                    return;
                }
                else{
                    product.stock = amount;
                    System.out.println("[OK] Menge erfolgreich geändert!");
                }
            }
        }
        System.out.println("[FEHLER] Produkt wurde im Warenkorb nicht gefunden!");
    }

    //Funktion 3.3 und Homepage 3
    public void finishBuying(){
        System.out.println("========================================================================");
        System.out.println("                        BESTELLUNG ABSCHLIESSEN                         ");
        System.out.println("========================================================================");
        System.out.println();
        double total = 0;
        System.out.printf("%-28s %10s %16s%n",
                "Produkt",
                "Menge",
                "Gesamt"
        );
        System.out.println("--------------------------------------------------------");
        for(Product product : sc){
            total += product.getTotalPrice();
            System.out.printf("%-28s %10d %14.2f €%n",
                    product.name,
                    product.stock,
                    product.getTotalPrice()
            );
        }
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-39s %14.2f €%n", "Gesamtpreis:", total);
        System.out.println();
        System.out.println("Bestellung wirklich abschließen?");
        System.out.println("[1] Ja");
        System.out.println("[2] Nein");
        System.out.println();
        System.out.println(">> Auswahl:");

        while(true){

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    System.out.println();
                    System.out.println("Bestellung wird erstellt...");
                    System.out.println("Produkte werden gespeichert...");
                    System.out.println("Lagerbestand wird aktualisiert...");

                    //Wird in die Datenbank hinzugefügt und gibt die ID zurück
                    int orderId = dtb.insertIntoOrders(total, "ABGESCHLOSSEN", today);
                    if (orderId != -1) {
                        boolean successOrderItems = true;
                        for (Product product : sc) {
                            boolean inserted = dtb.insertIntoOrderItems(orderId, product.id, product.stock, product.getTotalPrice());
                            if (!inserted) {
                                successOrderItems = false;
                            }
                        }
                        if (successOrderItems) {
                            boolean allStocksUpdated = true;

                            for (Product productInSc : sc) {
                                Product tmp = dtb.getProductFromProducts(productInSc.id);

                                // Prüfen, ob Produkt existiert und der Bestand ausreicht (nochmal zu Sicherheit)
                                if (tmp != null) {
                                    int newStock = tmp.stock - productInSc.stock;
                                    boolean updated = dtb.updateStock(productInSc.id, newStock);
                                    if (!updated) {
                                        allStocksUpdated = false;
                                    }
                                }
                                else {
                                    allStocksUpdated = false;
                                }
                            }

                            if (allStocksUpdated) {
                                System.out.println();
                                System.out.println("========================================");
                                System.out.println("[OK] Bestellung erfolgreich gespeichert!");
                                System.out.println("========================================");
                                sc.clear(); // Warenkorb nach erfolgreicher Bestellung leeren
                            }
                            else {
                                System.out.println("[FEHLER] Mindestens ein Lagerbestand konnte nicht aktualisiert werden.");
                            }
                        }
                        else {
                            System.out.println("[FEHLER] Fehler beim Speichern der Bestellpositionen.");
                        }
                    }
                    else {
                        System.out.println("[FEHLER] Bestellung konnte nicht erstellt werden.");
                    }
                    return;

                case 2:
                    return;

                default:
                    System.out.println("[FEHLER] Bitte eine Zahl zwischen 1 und 2 eingeben!");
            }
        }
    }
}