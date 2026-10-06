import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCard {
    ArrayList<Product> sc;
    Scanner scanner;
    Database dtb;

    public ShoppingCard(ArrayList<Product> sc, Scanner scanner, Database dtb){
        this.sc = sc;
        this.scanner = scanner;
        this.dtb = dtb;
    }

    //Homepage Funktion 2
    public void putInside(){
        while(true){
            System.out.println("Produkt-ID: ");

            int id = scanner.nextInt();
            scanner.nextLine();

            Product product = dtb.getProductFromProducts(id);

            if(product == null){
                System.out.println("So ein Produkt gibt es nicht");
            }
            else{
                System.out.println("Produkt: ");
                System.out.println(product.name);
                System.out.println();
                System.out.println("Preis: ");
                System.out.println(product.price);
                System.out.println();
                System.out.println("Verfügbarer Bestand: ");
                System.out.println(product.stock);
                System.out.println();
                System.out.println("Menge: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                if(amount > product.stock || amount <= 0) {
                    System.out.println("So viel gibt es nicht im Lager");
                    System.out.println("Abbruch...");
                }
                else{
                    System.out.println(amount + "x " + product.name + " wurde zum Warenkorb hinzugefügt!");
                    Product ci = new Product(product.id, product.name, product.price, amount);
                    sc.add(ci);
                }
            }

            System.out.println();
            System.out.println("1. Weiter einkaufen");
            System.out.println("2. Zurück zum Hauptmenü");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                continue;
            }
            System.out.println("Zurück zum Hauptmenü");
            return;
        }
    }

    //Funktion 3
    public void seeInside(){
        while(true){
            double totalPrice = 0;
            System.out.println("========================================");
            System.out.println("                WARENKORB               ");
            System.out.println("========================================");
            System.out.println();
            System.out.println("ID  Produkt            Menge  Preis      Gesamt");
            System.out.println("--------------------------------------------------");
            for(Product cartItem : sc){
                totalPrice += cartItem.getTotalPrice();
                System.out.printf("%-3d %-18s %-6d %-10s %-10s%n",
                        cartItem.id,
                        cartItem.name,
                        cartItem.stock,
                        cartItem.price + " €",
                        cartItem.getTotalPrice() + " €"
                );
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Gesamtpreis: %38s%n", totalPrice + " €");
            System.out.println();
            System.out.println();
            System.out.println("1. Produkt entfernen");
            System.out.println("2. Menge ändern");
            System.out.println("3. Bestellung abschließen");
            System.out.println("4. Zurück");
            System.out.println();
            System.out.println("Auswahl: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Gebe eine Zahl zwischen 1 und 4 an!");
            }
        }
    }

}
