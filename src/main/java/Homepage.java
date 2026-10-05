import java.util.Scanner;

public class Homepage {

    //Externe Klassen
    Scanner scanner = new Scanner(System.in);
    Database dtb = new Database();

    //Shop System Klassen
    AdminArea adminArea = new AdminArea(scanner,dtb);

    public void start(){
        while(true){
            System.out.println("========================================");
            System.out.println("            JAVA SHOP SYSTEM            ");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. Produkte anzeigen");
            System.out.println("2. Produkt in den Warenkorb legen");
            System.out.println("3. Warenkorb anzeigen");
            System.out.println("4. Bestellung abschließen");
            System.out.println("5. Bestellungen anzeigen");
            System.out.println("6. Admin-Bereich");
            System.out.println();
            System.out.println("7. Beenden");
            System.out.println();
            System.out.println("Auswahl:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    dtb.getAllProductsFromProducts();
                    break;
                case 2:
                    System.out.println("Zwei");
                    break;
                case 3:
                    System.out.println("Drei");
                    break;
                case 4:
                    System.out.println("Vier");
                    break;
                case 5:
                    System.out.println("Fünf");
                    break;
                case 6:
                    System.out.println("Sechs");
                    adminArea.start();
                    break;
                case 7:
                    System.out.println("Programm wird beendet...");
                    return;
                default:
                    System.out.println("Gebe eine Zahl zwischen 1 und 7 an!");
            }
        }
    }

}
