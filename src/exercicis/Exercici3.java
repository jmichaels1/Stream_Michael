package exercicis;
import java.io.*;

import java.util.ArrayList;

/**
 * Created by Michael
 */
public class Exercici3 {

    public static void main(String[] args) throws IOException {

        llegirICopiarFitxer(displayDirectoryContents(new File("exercici3")));


    }

    public static ArrayList<File> displayDirectoryContents(File dir) {
        ArrayList<File> arrayListFiles = new ArrayList<>();

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                arrayListFiles.add(file);
                displayDirectoryContents(file);
            } else if (file.getName().endsWith(".txt")) {
                arrayListFiles.add(file);
            }
        }
        return arrayListFiles;
    }

    public static void llegirICopiarFitxer(ArrayList<File> fileArrayList) throws IOException {

        File fileConjunto = new File("exercici3/copiaDeTotsElsTxt.txt");
        if (fileConjunto.exists()) {
            fileConjunto.delete();
            fileConjunto.createNewFile();
        }
        fileConjunto.createNewFile();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Llista de fitxers del directori3 amb extencio 'txt' : \n\n");

        long total = 0;
        for (File file1 : fileArrayList) {

            String permisos = "";
            String ocult = "";


            if (file1.isFile()) {
                total = total + file1.length();


                stringBuilder.append(String.format(" FitxerFiltrat: %-40s \n........> és un FITXER amb extenció txt\n.......>Tamany del fitxer = %-10s\n......> llegirICopiarFitxer() =  ACABAT D'EXECUTAR\n\n", file1.getName(), file1.length()));


                try (
                        InputStream in = new FileInputStream(file1.getPath());
                        OutputStream out = new FileOutputStream(fileConjunto.getPath(), true);
                ) {

                    byte[] buf = new byte[1024];
                    int length;
                    while ((length = in.read(buf)) > 0) {
                        out.write(buf, 0, length);
                    }
                }

            } else {

                if (file1.getName().startsWith(".")) {
                    ocult = "true";
                } else {
                    ocult = "false";
                }

                if (file1.canRead()) {
                    permisos = permisos + "r";
                }
                if (file1.canWrite()) {
                    permisos = permisos + "w";
                }
                if (file1.canExecute()) {
                    permisos = permisos + "x";
                }
                stringBuilder.append(String.format(" FitxerFiltrat: %-40s \n........> és un DIRECTORI amb extenció txt\n.......>Permisos del directorio = %-10s\n......>Directori Ocult = %-10s\n\n", file1.getName(), permisos, ocult));

            }


        }

        stringBuilder.append(String.format("El tamany total dels fitxers amb extencio txt es %-10s", String.valueOf(total)));


        System.out.println(stringBuilder.toString());


    }


}
