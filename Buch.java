
/**
 * Beschreiben sie hier die Klasse Buch.java.
 * 
 * @author christoph.stueber@mes-alsfeld.de
 *
 * @version 03.2018
 *          erstellt 21.03.2018
 *          geändert 21.03.2018
 *
 */
public class Buch extends Objekt
{
    /** */
    private String verlag;

    /** */
    private String autor;

    /**
     * Konstruktor zum Erstellen von Objekten der Klasse Buch.java
     * 
     * @param idObjekt int
     * @param signatur String
     * @param titel String
     * @param autor String
     * @param verlag String
     */
    public Buch(int idObjekt, String signatur, String titel, String autor,
            String verlag)
    {
        super(idObjekt, signatur, titel);
        this.autor = autor;
        this.verlag = verlag;
    }

    /**
     * @return the verlag
     */
    public String getVerlag()
    {
        return verlag;
    }

    /**
     * @param verlag the verlag to set
     */
    public void setVerlag(String verlag)
    {
        this.verlag = verlag;
    }

    /**
     * @return the autor
     */
    public String getAutor()
    {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor)
    {
        this.autor = autor;
    }

    /**
     * toString-Methode.
     * 
     * @return String
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(String.valueOf(getId())).append("\n");
        result.append("  Sig. : ").append(getSignatur()).append("\n");
        result.append("  Titel: ").append(getTitel()).append("\n");
        result.append("  Autor: ").append(getAutor()).append("\n");
        result.append("  Verlag:").append(getVerlag()).append("\n");
        return result.toString();
    }

}
