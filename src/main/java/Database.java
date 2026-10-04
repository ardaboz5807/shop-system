import java.sql.*;

public class Database {

    public static String url = "jdbc:sqlite:identifier.sqlite.db";

    public Database() {
        createTables();
    }

    //Erstellt die drei Tabellen, nötig für den Shop
    public void createTables(){
        String sqlProducts = "CREATE TABLE IF NOT EXISTS products (id integer PRIMARY KEY, name text, price real, stock integer)";
        String sqlOrders = "CREATE TABLE IF NOT EXISTS orders (id integer PRIMARY KEY, total_price real, status text, order_date text)";
        String sqlOrderItems = "CREATE TABLE IF NOT EXISTS order_items (id integer PRIMARY KEY, order_id integer, product_id integer, quantity integer, price real)";
        String[] tables = {sqlProducts,sqlOrders,sqlOrderItems};
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

            for(String sql : tables){
                stmt.execute(sql);
            }
            System.out.println("Datenbank erfolgreich geladen!");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fehler beim Laden der Tabellen");
        }
    }

    //Fügt ein Produkt in die Tabelle products hinzu
    public void addProducts(String name, double price, int stock){
        String sql = "INSERT INTO products (name,price,stock) VALUES (?,?,?)";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,name);
            stmt.setDouble(2,price);
            stmt.setInt(3,stock);
            stmt.execute();

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fehler beim Hinzufügen der Produkte");
        }
    }

    //Bekommt ein Product aus der Tabelle products anhand der ID zurück
    public Product getProductFromProducts(int id){
        String sql = "SELECT * FROM products WHERE id = ?";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Product product = null;
            if(rs.next()){
                product = new Product(id,rs.getString("name"),rs.getDouble("price"),rs.getInt("stock"));
            }
            return product;
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fehler beim Aufrufen eines Produkts");
        }
        return null;

    }

    //Updatet ein Produkt aus der Tabelle products anhand der ID. Mithilfe der Klasse Product ist es einfacher zu updaten
    public void editProducts(int id, String name, double price, int stock){
        String sql =  "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,name);
            stmt.setDouble(2,price);
            stmt.setInt(3,stock);
            stmt.setInt(4,id);
            stmt.execute();

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fehler beim Editieren eines Products");
        }
    }

    //Printet alle Produkte aus products
    public void getAllProductsFromProducts(){
        String sql = "SELECT * FROM products";
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);){
            while(rs.next()){
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + " | Name: " + rs.getString("name")
                                + " | Preis: " + rs.getDouble("price") + " €"
                                + " | Im Lager: " + rs.getInt("stock")
                );
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Fehler beim Printen der Tabelle");
        }
    }

}
