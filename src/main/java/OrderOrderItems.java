import java.util.Scanner;

public class OrderOrderItems {
    Scanner scanner;
    Database dtb;

    public OrderOrderItems(Scanner scanner, Database dtb){
        this.scanner = scanner;
        this.dtb = dtb;
    }

    public void start(){
        dtb.printAllOrders();
        while(true){
            System.out.println("0. Zurück");
            System.out.println();
            System.out.println("Bestellnummer für Details: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                return;
            }
            else{
                return;
                //TODO
            }
        }
    }
}
