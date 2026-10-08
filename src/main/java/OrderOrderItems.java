import java.util.Scanner;

// Zeigt gespeicherte Bestellungen und deren Bestellpositionen an
public class OrderOrderItems {
    Scanner scanner;
    Database dtb;

    // Verwendet den gemeinsamen Scanner und die bestehende Datenbankinstanz
    public OrderOrderItems(Scanner scanner, Database dtb){
        this.scanner = scanner;
        this.dtb = dtb;
    }

    // Zeigt alle Bestellungen und ermöglicht den Abruf einzelner Bestelldetails
    public void start(){
        dtb.printAllOrders();

        while(true){
            System.out.println("[0] Zurück");
            System.out.println();
            System.out.println(">> Bestellnummer für Details: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 0){
                return;
            }
            else{
                boolean found = dtb.getOrder(choice);
                if(!found){
                    System.out.println("[FEHLER] Keine Bestellung mit dieser Bestellnummer gefunden!");
                }
            }
        }
    }
}