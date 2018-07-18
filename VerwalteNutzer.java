
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author christoph.stueber@mes-alsfeld.eu
 * 
 * @version 2018.07 erstellt 16.07.2018 geändert 17.07.2018
 *
 */
public class VerwalteNutzer
{

    private static SessionFactory factory;

    public static void main(String[] args)
    {

	try
	{
	    factory = new Configuration().configure().buildSessionFactory();
	} catch (Throwable ex)
	{
	    System.err.println("Failed to create sessionFactory object." + ex);
	    throw new ExceptionInInitializerError(ex);
	}

	VerwalteNutzer verwaltung = new VerwalteNutzer();

	/* List down all the employees */
	verwaltung.listEmployees();

	/* Add few nutzer records in database */
	Integer id1 = verwaltung.addNutzer("Zara", "Ali");
	Integer id2 = verwaltung.addNutzer("Daisy", "Das");
	Integer id3 = verwaltung.addNutzer("John", "Paul");

	/* List down all the employees */
	verwaltung.listEmployees();
    }

    /** Method to CREATE an nutzer in the database */
    public Integer addNutzer(String fname, String lname)
    {
	Session session = factory.openSession();
	Transaction tx = null;
	Integer einNutzerId = null;

	try
	{
	    tx = session.beginTransaction();
	    Nutzer einNutzer = new Nutzer(fname, lname);
	    einNutzerId = (Integer) session.save(einNutzer);
	    tx.commit();
	} catch (HibernateException e)
	{
	    if (tx != null)
		tx.rollback();
	    e.printStackTrace();
	} finally
	{
	    session.close();
	}
	return einNutzerId;
    }

    /* Method to READ all the employees */
    public void listEmployees()
    {
	Session session = factory.openSession();
	// Session session = factory.getCurrentSession();
	Transaction tx = null;
	try
	{
	    tx = session.beginTransaction();
	    List<Nutzer> alleNutzer = session.createQuery("FROM Nutzer").list();
	    for (Nutzer n : alleNutzer)
	    {
		System.out.println("\n+ + + +");
		System.out.println("Vorname:  " + n.getVorname());
		System.out.println("Nachname: " + n.getNachname());
		System.out.println("+ + + +");
	    }
	    tx.commit();
	} catch (HibernateException e)
	{
	    if (tx != null)
		tx.rollback();
	    e.printStackTrace();
	} finally
	{
	    session.close();
	}
    }

}
