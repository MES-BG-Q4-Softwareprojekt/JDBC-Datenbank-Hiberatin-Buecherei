import java.util.ArrayList;

/**
 * Beschreiben sie hier die Klasse Objekt.java.
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 *
 * @version 03.2018
 *          erstellt 21.03.2018
 *          geändert 21.03.2018
 *
 */
public class Objekt
{

    /** */
    private int id;
    
    /** */
    private String signatur;

    /** */
    private String titel;
    
    /** */
    private ArrayList<Nutzer> meineNutzer = new ArrayList<Nutzer>();

    /**
     * Konstruktor zum Erstellen von Objekten der Klasse Objekt.java
     * 
     * @param idObjekt int
     * @param signatur String
     * @param titel String
     */
    public Objekt(int idObjekt, String signatur, String titel)
    {
        this.id = idObjekt;
        this.signatur = signatur;
        this.titel = titel;
    }
    /**
     * Konstruktor zum Erstellen von Objekten der Klasse Objekt.java
     * 
     * @param idObjekt int
     */
    public Objekt(int idObjekt)
    {
        this.id = idObjekt;
    }
    /**
     * @return the idObjekt
     */
    public int getId()
    {
        return id;
    }
    /**
     * @param idObjekt the idObjekt to set
     */
    public void setIdObjekt(int idObjekt)
    {
        this.id = idObjekt;
    }
    /**
     * @return the signatur
     */
    public String getSignatur()
    {
        return signatur;
    }
    /**
     * @param signatur the signatur to set
     */
    public void setSignatur(String signatur)
    {
        this.signatur = signatur;
    }
    /**
     * @return the titel
     */
    public String getTitel()
    {
        return titel;
    }
    /**
     * @param titel the titel to set
     */
    public void setTitel(String titel)
    {
        this.titel = titel;
    }
    
    /**
     * Hinzufuegen-Methode
     * 
     * @param n Nutzer
     */
    public void hinufuegen(Nutzer n)
    {
        meineNutzer.add(n);
    }
    
  
}
