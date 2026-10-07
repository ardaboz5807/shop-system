import java.sql.*;

public class Database {

    public static String url = "jdbc:sqlite:identifier.sqlite.db";

    public Database() {
        createTables();
    }

    //Erstellt die drei Tabellen, nötig für den Shop
    public void createTables() {
        //Tabelle für die Produkte
        String sqlProducts = "CREATE TABLE IF NOT EXISTS products (id integer PRIMARY KEY, name text, price real, stock integer)";

        //Extra Tabelle für die Bestellungen
        String sqlOrders = "CREATE TABLE IF NOT EXISTS orders (id INTEGER PRIMARY KEY AUTOINCREMENT, total_price real, status text, order_date text)";

        //Tabelle speichert, welche Produkte genau in dieser Bestellung enthalten sind
        String sqlOrderItems = "CREATE TABLE IF NOT EXISTS order_items (id integer PRIMARY KEY, order_id integer, product_id integer, quantity integer, price real)";
        String[] tables = {sqlProducts, sqlOrders, sqlOrderItems};
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            for (String sql : tables) {
                stmt.execute(sql);
            }
            System.out.println("Datenbank erfolgreich geladen!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim Laden der Tabellen");
        }
    }

    //----------------------------------------------------------//
    //Operationen für sqlProducts

    //Fügt ein Produkt in die Tabelle products hinzu
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
            System.out.println("Fehler beim Hinzufügen der Produkte");
        }
    }

    //Bekommt ein Product aus der Tabelle products anhand der ID zurück
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
            System.out.println("Fehler beim Aufrufen eines Produkts");
            return null;
        }
    }

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
            System.out.println("Fehler beim updaten vom lager");
            return false;
        }
    }

    //Updatet ein Produkt aus der Tabelle products anhand der ID. Mithilfe der Klasse Product ist es einfacher zu updaten
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
            System.out.println("Fehler beim Editieren eines Products");
        }
    }

    //Printet alle Produkte aus products
    public void getAllProductsFromProducts() {
        String sql = "SELECT * FROM products";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + " | Name: " + rs.getString("name")
                                + " | Preis: " + rs.getDouble("price") + " €"
                                + " | Im Lager: " + rs.getInt("stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim Printen der Tabelle");
        }
    }

    //Aus Tabelle Products löschen
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

    //Alles löschen
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
    //Operationen für sqlOrders

    public int insertIntoOrders(double total_price, String status, String order_date) {
        String sql = "INSERT INTO orders (total_price,status,order_date) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, total_price);
            stmt.setString(2, status);
            stmt.setString(3, order_date);
            stmt.execute();

            //Generierte ID abrufen
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Gibt die generierte order_id zurück
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fehler beim inserten in Orders");

        }
        return -1;
    }

    //Printet alle Orders
    public void printAllOrders(){
        String sql = "SELECT * FROM orders";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("===============================================================");
            System.out.println("                        BESTELLUNGEN                           ");
            System.out.println("===============================================================");
            System.out.println();

            System.out.printf("%-10s %14s     %-20s %-15s%n",
                    "ID",
                    "Gesamtpreis",
                    "Status",
                    "Datum"
            );

            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %12.2f €     %-20s %-15s%n",
                        rs.getInt("id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getString("order_date")
                );
            }

            System.out.println("---------------------------------------------------------------");

        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("[FEHLER] Bestellungen konnten nicht angezeigt werden!");
        }
    }

    //Alles löschen
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
    //Operationen für sqlOrder_Items

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
            System.out.println("Fehler beim inserten bei orderitems");
            return false;
        }
    }

}
