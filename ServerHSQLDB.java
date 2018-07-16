
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Beschreiben Sie hier die Klasse ServerHSQLDB.
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 * @version Schuljahr 2017/2018
 *
 * erstellt 20.03.2018
 * geändert 20.03.2018
 */
public class ServerHSQLDB
{

    /*
     * Webserver starten mit
     * 
     * java -cp ../lib/hsqldb.jar org.hsqldb.Server -database.0 file:database/Bibliothek -dbname.0 Bibliothek
     * sudo java -cp ../lib/hsqldb.jar org.hsqldb.server.WebServer -database.0 file:database/Bibliothek -dbname.0 Bibliothek
     */
    
 /**
     * Main-Methode, die eine HSQL-Datenbank von einem Webserver liest
     * und anschließend einen SQL-Befehl testet,
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String dbName = "Bibliothek";
        System.out.println("Öffnen von " + dbName + ".odb");

        try
        {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //geht nicht mit server.WebServer???
            //String db = "jdbc:hsqldb:hsql://v17218.php-friends.de:80/" + dbName;
            //geht mit Server           
            //String db = "jdbc:hsqldb:hsql://v17218.php-friends.de:9001/" + dbName;
            //geht auch mit Server
            String db = "jdbc:hsqldb:hsql://v17218.php-friends.de/" + dbName;
            
            System.out.println("Open " + db);
            con = DriverManager.getConnection(db, "SA", null);

            String query = "SELECT \"vorname\",\"nachname\",\"titel\" FROM \"Nutzer\" JOIN \"Ausleihe\" "
            + "ON \"Nutzer\".\"idNutzer\"=\"Ausleihe\".\"idNutzer\" "
            + "JOIN \"Objekt\" ON \"Ausleihe\".\"idObjekt\"=\"Objekt\".\"idObjekt\";";
            System.out.println("SQL: " + query);
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                System.out.print(rs.getString("vorname"));
                System.out.print(" ");
                System.out.print(rs.getString("nachname"));
                System.out.print(": ");
                System.out.println(rs.getString("titel"));
            }
            rs.close();

            stmt.close();
            con.close();
        } catch (Exception e)
        {
            e.printStackTrace(System.out);
        }

    }
}
