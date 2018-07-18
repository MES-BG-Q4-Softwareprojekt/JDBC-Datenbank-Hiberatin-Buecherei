import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Beschreiben sie hier die Klasse readBaseFile
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 *
 * @version 2017.11
 *          erstellt 11.11.2017
 *          geändert 12.11.2017
 *
 */
public class BibliothekHSQL
{

    /**
     * Main-Methode, die eine HSQLDB-Datenbank in eine komplexe Datenstruktur
     * einliest und anschließen auf der Konsole ausgibt.
     * 
     * @param args String[]
     */
    public static void main(String[] args)
    {
        ArrayList<Nutzer> liste = new ArrayList<Nutzer>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String dbName = "Bibliothek";
        System.out.println("Öffnen von " + dbName + ".odb");

        try
        {
            Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
            String db = "jdbc:hsqldb:file:" + dbName + "/database/" + dbName;
            System.out.println("Open " + db);
            con = DriverManager.getConnection(db, "SA", null);

            // Alle Nutzer auslesen
            String query = "SELECT \"idNutzer\", \"vorname\",\"nachname\" FROM \"Nutzer\";";
            System.out.println("SQL: " + query);
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                liste.add(new Nutzer(rs.getInt("idNutzer"),
                        rs.getString("vorname"), rs.getString("nachname")));
            }

            // Alle Bücher auslesen
            query = "SELECT \"idNutzer\", \"Objekt\".\"idObjekt\", \"signatur\",\"titel\", \"autor\", \"verlag\" FROM \"Nutzer\" "
                    + "JOIN \"Ausleihe\" ON \"Nutzer\".\"idNutzer\"=\"Ausleihe\".\"idNutzer\" "
                    + "JOIN \"Objekt\" ON \"Ausleihe\".\"idObjekt\"=\"Objekt\".\"idObjekt\" "
                    + "JOIN \"Buch\" ON \"Ausleihe\".\"idObjekt\"=\"Buch\".\"idObjekt\";";

            System.out.println("SQL: " + query);
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                int id = rs.getInt("idNutzer");
                for (Nutzer gesucht : liste)
                {
                    if (gesucht.getId() == id)
                    {
                        Buch b = new Buch(rs.getInt("idObjekt"),
                                rs.getString("signatur"), rs.getString("titel"),
                                rs.getString("autor"), rs.getString("verlag"));
                        gesucht.ausleihen(b);
                        b.hinufuegen(gesucht);
                    }
                }
            }
            // DVD
            query = "SELECT \"idNutzer\", \"Objekt\".\"idObjekt\", \"signatur\",\"titel\", \"jahr\", \"fsk\" FROM \"Nutzer\" "
                    + "JOIN \"Ausleihe\" ON \"Nutzer\".\"idNutzer\"=\"Ausleihe\".\"idNutzer\" "
                    + "JOIN \"Objekt\" ON \"Ausleihe\".\"idObjekt\"=\"Objekt\".\"idObjekt\" "
                    + "JOIN \"DVD\" ON \"Ausleihe\".\"idObjekt\"=\"DVD\".\"idObjekt\";";

            System.out.println("SQL: " + query);
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next())
            {
                int id = rs.getInt("idNutzer");
                for (Nutzer gesucht : liste)
                {
                    if (gesucht.getId() == id)
                    {
                        DVD d = new DVD(rs.getInt("idObjekt"),
                                rs.getString("signatur"), rs.getString("titel"),
                                rs.getInt("jahr"), rs.getInt("fsk"));
                        gesucht.ausleihen(d);
                        d.hinufuegen(gesucht);
                    }
                }
            }
            
            rs.close();

            stmt.close();
            con.close();
        } catch (Exception e)
        {
            e.printStackTrace(System.out);
        }

        for (Nutzer n : liste)
        {
            System.out.println(n);
        }

    }
}
