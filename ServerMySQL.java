
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Beschreiben Sie hier die Klasse ServerMySQL.
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 * @version Schuljahr 2017/2018
 *
 * erstellt 20.03.2018
 * geändert 22.03.2018
 */
public class ServerMySQL
{

    /**
     * Liest die Daten der Bibiotheksdatenbank vom MySQL-Server aus. Damit der Server externe Verbindungen zulässt, ist in myMain-Methode, die eine Base-Datenbank aufbereitet und anschließend einen SQL-Befehl testet,
     * 
     * Damit der MySQL-Server eine externe Verbindung zulässtist in /etc/mysql/my.cnf
     * 
     * # bind-address          = 127.0.0.1
     * bind-address = 0.0.0.0
     * 
     * zu ändern
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String dbName = "Bibliothek";
        if(args.length>0)
        {
            dbName = args[0];
        }
        System.out.println("Öffnen von " + dbName + ".odb");

        String url = "jdbc:mysql://v17218.php-friends.de:3306/"+dbName+"?user=test&password=bgdv";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            System.out.println("Open " + url);
            con = DriverManager.getConnection(url);

            String query = "SELECT vorname,nachname,titel FROM Nutzer JOIN Ausleihe ON Nutzer.idNutzer=Ausleihe.idNutzer JOIN Objekt ON Ausleihe.idObjekt=Objekt.idObjekt;";
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
