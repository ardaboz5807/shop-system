import java.util.Scanner;

public class AdminArea {
    Scanner scanner;
    Database dtb;

    public AdminArea(Scanner scanner, Database dtb){
        this.scanner = scanner;
        this.dtb = dtb;
    }

    public void start(){
        boolean valid = true;  //To stop the while-loop
        while(true){
            System.out.println("========================================");
            System.out.println("            ADMIN BEREICH               ");
            System.out.println("========================================");
            System.out.println("1. Produkt hinzufügen");
            System.out.println("2. Produkt bearbeiten");
            System.out.println("3. Produkt löschen");
            System.out.println("4. Alle Produkte anzeigen");
            System.out.println();
            System.out.println("5. Zurück");
            System.out.println();
            System.out.println("Auswahl:");
            int choice = scanner.nextInt();
            scanner.nextLine(); //Enter entfernen
            switch(choice){
                case 1:
                    add();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    break;
                case 4:
                    dtb.getAllProductsFromProducts();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void add(){
        System.out.println("Produktname: ");
        String name = scanner.nextLine();

        System.out.println("Preis: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Bestand: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        dtb.addProducts(name, price, stock);
    }

    public void edit(){
        System.out.println("Was möchtest du bearbeiten?");
        System.out.println("Gebe die ID an");

        int id = scanner.nextInt();
        scanner.nextLine(); //Enter entfernen

        Product product = dtb.getProductFromProducts(id);

        while(true){

            System.out.println("1. Produktname");
            System.out.println("2. Preis");
            System.out.println("3. Bestand");
            System.out.println();
            System.out.println("4. Zurück");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    System.out.println("Gebe neuen Namen ein:");
                    String name = scanner.nextLine();
                    dtb.editProducts(id,name,product.price, product.stock);
                    System.out.println("Erfolgreich geupdatet!");
                    break;
                case 2:
                    System.out.println("Gebe neuen Preis an:");
                    double price = scanner.nextDouble();
                    dtb.editProducts(id,product.name,price,product.stock);
                    scanner.nextLine();
                    System.out.println("Erfolgreich geupdatet!");
                    break;
                case 3:
                    System.out.println("Gebe neuen Bestand an:");
                    int stock = scanner.nextInt();
                    dtb.editProducts(id,product.name,product.price,stock);
                    scanner.nextLine();
                    System.out.println("Erfolgreich geupdatet!");
                    break;
                case 4:
                    return;

            }
        }
    }
}
