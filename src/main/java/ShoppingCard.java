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

    public void start(){
        System.out.println("Produkt-ID");

        int id = scanner.nextInt();
        scanner.nextLine();

        Product product = dtb.getProductFromProducts(id);

        if(product == null){
            System.out.println("So ein Produkt gibt es nicht");
            return;
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
            int stock = scanner.nextInt();
            scanner.nextLine();
            if(stock > product.stock || stock <= 0) {
                System.out.println("So viel gibt es nicht im Lager");
                System.out.println("Abbruch...");
                return;
            }
            else{
                return;
                //TODO
            }
        }
    }

}
