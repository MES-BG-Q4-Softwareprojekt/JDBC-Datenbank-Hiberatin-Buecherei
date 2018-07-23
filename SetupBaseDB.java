import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Beschreiben sie hier die Klasse InProcess.java.
 * 
 * @author christoph.stueber@mes-alsfeld.eu
 *
 * @version 2018.03 geändert 05.04.2018
 * 
 *          https://www.javadevjournal.com/java/zipping-and-unzipping-in-java/
 *
 */
public class SetupBaseDB
{

    /**
     * Löscht rekursiv ein Verzeichnis mit allen Unterverzeichnissen und
     * Dateien.
     * 
     * @param path String mit dem Name für das Verzeichnis
     */
    public static void deleteDir(File path)
    {
        for (File file : path.listFiles())
        {
            System.out.println("Deleting " + file.getAbsolutePath());
            if (file.isDirectory())
            {
                deleteDir(file);
            }
            else
            {
                file.delete();
            }
        }
        path.delete();
    }

    /**
     * Packt das mit sourceDirectory angegebene Verzeichnis in ein Archiv mit dem Namen zipFilePath (Java 7 und höher).
     * 
     * @param sourceDirectory
     * @param zipFilePath
     * @throws IOException
     */
    public static void zip(Path sourceDirectory, Path zipFilePath)
    throws IOException
    {

        try (

                FileOutputStream fileOutputStream = new FileOutputStream(
                    zipFilePath.toFile()
                );
                ZipOutputStream zipOutputStream = new ZipOutputStream (fileOutputStream) 
             )
        {
            Files.walkFileTree(sourceDirectory, new SimpleFileVisitor<Path>()
                {

                    public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException
                    {
                        zipOutputStream.putNextEntry(new ZipEntry(
                                sourceDirectory.relativize(file).toString()));
                        Files.copy(file, zipOutputStream);
                        zipOutputStream.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) throws IOException
                    {
                        zipOutputStream.putNextEntry(new ZipEntry(
                                sourceDirectory.relativize(dir).toString() + "/"));
                        zipOutputStream.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                });
        }
    }

    /**
     * Entpackt den angebenen Ordner (Java 7 und höher)
     * 
     * @param zipFilePath String
     * @param unzipLocation String
     * @throws IOException
     */
    public static void unzip(final String zipFilePath,
    final String unzipLocation) throws IOException
    {

        if (!(Files.exists(Paths.get(unzipLocation))))
        {
            Files.createDirectories(Paths.get(unzipLocation));
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(
                new FileInputStream(zipFilePath)))
        {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null)
            {
                Path filePath = Paths.get(unzipLocation, entry.getName());
                if (!entry.isDirectory())
                {
                    // create all non exists folders
                    // else you will hit FileNotFoundException for compressed folder
                    // eingefügt 06.04.2018 christoph.stueber@mes-alsfeld.eu
                    filePath.toFile().getParentFile().mkdirs();
                    unzipFiles(zipInputStream, filePath);
                }
                else
                {
                    Files.createDirectories(filePath);
                }

                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        }
    }

    /**
     * Entpackt die angegebene Datei.
     * 
     * @param zipInputStream ZipInputStream
     * @param unzipFilePath Path
     * @throws IOException
     */
    public static void unzipFiles(final ZipInputStream zipInputStream,
    final Path unzipFilePath) throws IOException
    {

        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(
                    unzipFilePath.toAbsolutePath().toString())))
        {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(bytesIn)) != -1)
            {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    /**
     * Packt eine Base-Datenbank im ZIP-Format aus den Unterverzeichnissen von
     * HSQL.
     *
     * @param fname String Name der Base Datenbank.
     */
    public static void hsql2base(String fname)
    {
        hsql2base(fname, false);
    }

    /**
     * Packt eine Base-Datenbank im ZIP-Format aus den Unterverzeichnissen von
     * HSQL.
     * 
     * @param fname String Name der Base Datenbank.
     * @param silent wenn false werden die Verzeichnisse beim Umbenennen
     *            ausgegeben.
     */
    private static void hsql2base(String fname, boolean silent)
    {
        File liste = new File(fname + "/database");
        for (File f : liste.listFiles())
        {

            if (f.getName().charAt(0) != '.')
            {

                String fn = fname + "/database/" + f.getName().split("\\.")[1];
                f.setExecutable(true, false);
                if (!silent)
                {
                    System.out.println("Renaming " + f.getPath() + " to " + fn);
                    System.out.println(f.getName() + " " + f.canExecute());
                }
                f.renameTo(new File(fn));
            }
        }

        File baseFile = new File(fname + ".odb");
        if (baseFile.exists())
        {
            baseFile.renameTo(new File(fname + ".bak"));
        }
        try
        {
            zip(Paths.get(fname), Paths.get(fname + ".odb"));
        } catch (IOException e)
        {
            System.err.println(
                "Fehler bei erstellen des ZIP-Archives " + e.getMessage());
        }
    }

    /**
     * Entpackt eine Base-Datenbank und legt Unterverzeichnisse an, die von HSQL
     * gelesen werden können.
     *
     * @param fname String Name der Base Datenbank.
     */
    public static void base2hsql(String fname)
    {
        base2hsql(fname, true);
    }

    /**
     * Entpackt eine Base-Datenbank und legt Unterverzeichnisse an, die von HSQL
     * gelesen werden können.
     *
     * @param debug wenn wahr werden die Verzeichnisse beim Umbenennen
     *            ausgegeben.
     * @param fname String Name der Base Datenbank.
     */
    public static void base2hsql(String fname, boolean silent)
    {
        String dname = fname;
        if (fname.contains("."))
        {
            dname = fname.substring(0, fname.indexOf("."));
        }
        File folder = new File(dname);
        if (folder.exists())
        {
            deleteDir(folder);
        }
        try
        {
            unzip(fname, dname);
        } catch (IOException e)
        {
            System.err.println("Fehler bei unzipping " + e.getMessage());
        }
        File liste = new File(dname + "/database");
        for (File f : liste.listFiles())
        {
            String fn = dname + "/database/" + dname + "." + f.getName();
            if (!silent)
                System.out.println("Renaming " + f.getPath() + " to " + fn);
            f.renameTo(new File(fn));
        }
    }

    /**
     * Main-Methode, die eine Base-Datenbank aufbereitet.
     * 
     * @param args String[]
     */
    public static void main(String[] args)
    {
        String dbName = "Bibliothek";
        System.out.println("Öffnen von " + dbName + ".odb");
        base2hsql(dbName + ".odb", false);
        //hsql2base(dbName, false);
    }

}
