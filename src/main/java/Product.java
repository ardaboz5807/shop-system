// Datenmodell für ein Produkt aus der Datenbank oder dem Warenkorb
public class Product {
    public int id;
    public String name;
    public double price;
    public int stock;

    // Erstellt ein Produkt mit ID, Name, Preis und Bestand bzw. Menge
    public Product(int id, String name, double price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Berechnet den Gesamtpreis anhand von Preis und Menge
    public double getTotalPrice(){
        return price*stock;
    }
}