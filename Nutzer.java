import java.util.ArrayList;

/**
 * Beschreiben sie hier die Klasse Nutzer.java.
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 *
 * @version 07.2018
 * erstellt 16.07.2018
 * geändert 17.07.2018
 *
 */
public class Nutzer
{
    /** */
    private int id;
    
    /** */
    private String vorname;
    
    /** */
    private String nachname;

    /** */
    private ArrayList<Objekt> geliehen = new ArrayList<Objekt>();
    
    /**
     * Konstruktor zum Erstellen von Objekten der Klasse Nutzer.java
     * 
     * @param vorname String
     * @param nachname String
     */
    public Nutzer(String vorname, String nachname)
    {
        this.vorname = vorname;
        this.nachname = nachname;       
    }/**
     * Konstruktor zum Erstellen von Objekten der Klasse Nutzer.java
     * 
     * @param vorname String
     * @param nachname String
     */
    public Nutzer()
    {
    }
    /**
     * Konstruktor zum Erstellen von Objekten der Klasse Nutzer.java
     * 
     * @param int id
     * @param vorname String
     * @param nachname String
     */
    
    public Nutzer(int id, String vorname, String nachname)
    {
	this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;       
    }
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the vorname
     */
    public String getVorname()
    {
        return vorname;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname()
    {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname)
    {
        this.nachname = nachname;
    }
    
    /**
     * ausleihen-Methode
     * 
     * @param o Objekt
     */
    public void ausleihen(Objekt o)
    {
        geliehen.add(o);
    }
    
    
    /**
     * toString()-Methode
     * 
     * @return String
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Vorname  : ").append(vorname).append("\n");
        result.append("Nachname : ").append(nachname).append("\n");
        for (Objekt o : geliehen)
        {
            result.append(o.toString());
        }
        return result.toString();
        
    }

}
