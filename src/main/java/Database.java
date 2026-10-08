import java.sql.*;

// Verwaltet sämtliche Zugriffe auf die SQLite-Datenbank
public class Database {

    public static String url = "jdbc:sqlite:identifier.sqlite.db";

    // Erstellt beim Start die benötigten Tabellen, falls sie noch nicht existieren
    public Database() {
        createTables();
    }

    // Erstellt die Tabellen für Produkte, Bestellungen und Bestellpositionen
    public void createTables() {
        String sqlProducts = "CREATE TABLE IF NOT EXISTS products (id integer PRIMARY KEY, name text, price real, stock integer)";

        String sqlOrders = "CREATE TABLE IF NOT EXISTS orders (id INTEGER PRIMARY KEY AUTOINCREMENT, total_price real, status text, order_date text)";

        String sqlOrderItems = "CREATE TABLE IF NOT EXISTS order_items (id integer PRIMARY KEY, order_id integer, product_id integer, quantity integer, price real)";
        String[] tables = {sqlProducts, sqlOrders, sqlOrderItems};

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Führt alle CREATE-TABLE-Anweisungen nacheinander aus
            for (String sql : tables) {
                stmt.execute(sql);
            }
            System.out.println("[OK] Datenbank erfolgreich geladen!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Datenbank konnte nicht geladen werden!");
        }
    }

    //----------------------------------------------------------//
    // Produkt-Operationen

    // Fügt ein neues Produkt zur Tabelle products hinzu
    public void addProducts(String name, double price, int stock) {
        String sql = "INSERT INTO products (name,price,stock) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Produkt konnte nicht hinzugefügt werden!");
        }
    }

    // Gibt ein Produkt anhand seiner ID zurück
    public Product getProductFromProducts(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Product product = null;

            if (rs.next()) {
                product = new Product(id, rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
            }
            return product;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Produkt konnte nicht abgerufen werden!");
            return null;
        }
    }

    // Aktualisiert den Lagerbestand eines Produkts
    public boolean updateStock(int id, int stock){
        String sql = "UPDATE products SET stock = ? WHERE id = ?";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,stock);
            stmt.setInt(2,id);
            int affected = stmt.executeUpdate();

            return affected >= 1;

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("[FEHLER] Lagerbestand konnte nicht aktualisiert werden!");
            return false;
        }
    }

    // Aktualisiert Name, Preis und Bestand eines Produkts anhand seiner ID
    public void editProducts(int id, String name, double price, int stock) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setInt(4, id);
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Produkt konnte nicht bearbeitet werden!");
        }
    }

    // Gibt alle gespeicherten Produkte in der Konsole aus
    public void getAllProductsFromProducts() {
        String sql = "SELECT * FROM products";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + " | Produkt: " + rs.getString("name")
                                + " | Preis: " + rs.getDouble("price") + " €"
                                + " | Bestand: " + rs.getInt("stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Produkte konnten nicht angezeigt werden!");
        }
    }

    // Löscht ein Produkt anhand seiner ID
    public boolean deleteFromProducts(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Löscht alle Produkte aus der Tabelle products
    public void deleteEverythingFromProducts() {
        String sql = "DELETE FROM products";

        try (Connection connn = DriverManager.getConnection(url);
             Statement stmt = connn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //----------------------------------------------------------//
    // Bestell-Operationen

    // Erstellt eine neue Bestellung und gibt deren generierte ID zurück
    public int insertIntoOrders(double total_price, String status, String order_date) {
        String sql = "INSERT INTO orders (total_price,status,order_date) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, total_price);
            stmt.setString(2, status);
            stmt.setString(3, order_date);
            stmt.execute();

            // Liest die automatisch generierte Bestell-ID aus
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Bestellung konnte nicht gespeichert werden!");

        }
        return -1;
    }

    // Gibt alle gespeicherten Bestellungen in Tabellenform aus
    public void printAllOrders(){
        String sql = "SELECT * FROM orders";

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("========================================================================");
            System.out.println("                              BESTELLUNGEN                              ");
            System.out.println("========================================================================");
            System.out.println();

            System.out.printf("%-10s %14s     %-20s %-15s%n",
                    "ID",
                    "Gesamtpreis",
                    "Status",
                    "Datum"
            );

            System.out.println("------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %12.2f €     %-20s %-15s%n",
                        rs.getInt("id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getString("order_date")
                );
            }

            System.out.println("------------------------------------------------------------------------");

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("[FEHLER] Bestellungen konnten nicht angezeigt werden!");
        }
    }

    // Löscht alle gespeicherten Bestellungen
    public void deleteEverythingFromOrders(){
        String sql = "DELETE FROM orders";

        try(Connection connn = DriverManager.getConnection(url);
            Statement stmt = connn.createStatement()){

            stmt.execute(sql);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------//
    // Bestellpositions-Operationen

    // Speichert ein Produkt als Position innerhalb einer Bestellung
    public boolean insertIntoOrderItems(int order_id, int product_id, int quantity, double price){
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?,?,?,?) ";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,order_id);
            stmt.setInt(2,product_id);
            stmt.setInt(3,quantity);
            stmt.setDouble(4,price);
            stmt.execute();

            return true;

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("[FEHLER] Bestellposition konnte nicht gespeichert werden!");
            return false;
        }
    }

    // Ruft alle Bestellpositionen einer bestimmten Bestellung ab
    public boolean getOrder(int id) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        boolean found = false;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    // Überschrift wird nur einmal ausgegeben, sobald ein Eintrag gefunden wurde
                    if (!found) {
                        System.out.println("------------------------------------------------------------------------");
                        System.out.println("BESTELLDETAILS | Bestellnummer: " + id);
                        System.out.println("------------------------------------------------------------------------");
                        found = true;
                    }

                    int itemId = rs.getInt("id");
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");

                    System.out.printf("Position: %-5d | Produkt-ID: %-5d | Menge: %-5d | Preis: %.2f €%n",
                            itemId, productId, quantity, price);
                }

                if (found){
                    System.out.println("------------------------------------------------------------------------");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[FEHLER] Bestelldetails konnten nicht abgerufen werden!");
        }

        // true bedeutet, dass mindestens eine Bestellposition gefunden wurde
        return found;
    }
}