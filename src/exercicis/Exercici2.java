package exercicis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;

import java.util.*;

/**
 * Created by Michael
 */
public class Exercici2 {

    public static void main(String[] args) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        File directori1 = new File("exercici2/directori1");
        File directori2 = new File("exercici2/directori2");


        long directori1Length = FileUtils.sizeOfDirectory(directori1);
        long directori2Length = FileUtils.sizeOfDirectory(directori2);

        stringBuilder.append("-----------------------APARTAT 2.1 i 2.2-----------------------\n\n");
        stringBuilder.append("El tamany del directori1 es : " + directori1Length + "\n");
        stringBuilder.append("El tamany del directori2 es : " + directori2Length + "\n\n");

        List<File> llistaFitxers;
        if (directori1Length > directori2Length) {
            llistaFitxers = (List<File>) FileUtils.listFilesAndDirs(directori1, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
        } else {
            llistaFitxers = (List<File>) FileUtils.listFilesAndDirs(directori2, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
        }

        Iterator iterator = llistaFitxers.iterator();

        while (iterator.hasNext()) {
            File file = (File) iterator.next();
            if (!file.getName().endsWith(".txt")) {
                iterator.remove();
            } else {
                stringBuilder.append(String.format(" Nombre: %-40s \tEs un fitxer: %-5s\t\tData de modificació: %-10s\n", file.getName(), String.valueOf(file.isFile()), file.lastModified()));
            }
        }

        System.out.println(stringBuilder.toString());


        File directoryPatata = new File("exercici2/patata");


        if (!comprobarExistenciaDirectori(directoryPatata.getPath())) {
            FileUtils.forceMkdir(directoryPatata);
        } else {
            FileUtils.cleanDirectory(directoryPatata);
        }

        Iterator<File> it = llistaFitxers.iterator();
        while (it.hasNext()) {
            File file = it.next();
            FileUtils.moveToDirectory(file, directoryPatata, true);
        }

        File[] files = FileUtils.listFilesAndDirs(directoryPatata, TrueFileFilter.TRUE, TrueFileFilter.TRUE).toArray(new File[0]);
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("-----------------------APARTAT 2.3-----------------------\n\n");
        stringBuilder2.append("Noms dels fitxers ordenats de mes antics a mes nous\n\n");

        for (int i = 0; i < files.length; i++) {
            stringBuilder2.append(String.format(" Nombre: %-40s \tData de modificació: %-10s\n", files[i].getName(), convertirLongTimeEnCalendar(files[i].lastModified())));
        }

        System.out.println(stringBuilder2.toString());

    }

    public static boolean comprobarExistenciaDirectori(String pathFile) {

        File file = new File(pathFile);

        if (file.exists()) {
            return true;
        }

        return false;

    }

    public static String convertirLongTimeEnCalendar(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time);
        return (cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.YEAR) + " a les " + cal.get(Calendar.HOUR_OF_DAY) + "h "
                + cal.get(Calendar.MINUTE) + "' " + cal.get(Calendar.SECOND) + "'' " + cal.get(Calendar.MILLISECOND));

    }


}
