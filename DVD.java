
/**
 * Beschreiben sie hier die Klasse DVD.java.
 * 
 * @author christoph.stueber@mes-alsfeld.de
 *
 * @version 03.2018
 *          erstellt 21.03.2018
 *          geändert 21.03.2018
 *
 */
public class DVD extends Objekt
{
    /** */
    private int fsk;

    /** */
    private int jahr;

    /**
     * Konstruktor zum Erstellen von Objekten der Klasse DVD.java
     * 
     * @param idObjekt int
     */
    public DVD(int idObjekt)
    {
        super(idObjekt);
    }

    /**
     * Konstruktor zum Erstellen von Objekten der Klasse DVD.java
     * 
     * @param idObjekt int
     * @param signatur String
     * @param titel String
     * @param fsk int
     * @param jahr int
     */
    public DVD(int idObjekt, String signatur, String titel, int fsk, int jahr)
    {
        super(idObjekt, signatur, titel);
        this.fsk = fsk;
        this.jahr = jahr;
    }

    /**
     * @return the fsk
     */
    public int getFSK()
    {
        return fsk;
    }

    /**
     * @param f the fsk to set
     */
    public void setFSK(int f)
    {
        this.fsk = f;
    }

    /**
     * @return the jahr
     */
    public int getJahr()
    {
        return jahr;
    }

    /**
     * @param jahr the jahr to set
     */
    public void setJahr(int jahr)
    {
        this.jahr = jahr;
    }
    
    /**
     * toString-Methode.
     * 
     * @return String
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(getId()).append("\n");
        result.append("  Sig. : ").append(getSignatur()).append("\n");
        result.append("  Titel: ").append(getTitel()).append("\n");
        result.append("  Jahr : ").append(getJahr()).append("\n");
        result.append("  FSK  : ").append(getFSK()).append("\n");
        return result.toString();
    }

}
