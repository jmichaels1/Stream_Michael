package exercicis;

import java.io.*;
import java.net.URL;

/**
 * Created by Michael
 */
public class Exercici1 {

    private static final String LINK = "https://www.km77.com/   ";
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static int cantLineas = 0;

    private static FileWriter fw;
    private static File file;

    /**
     * @param link
     */
    private static void copiarAFitxerDadesDeLaWeb(String link) throws IOException {
        String tituloString = "";
        String metaContent = "";
        String line = "";

        try {

            br = new BufferedReader(new InputStreamReader(
                    new URL(link).openConnection().getInputStream()));

            file = new File("exercici1/dadesDeLaPagina.txt");
            if (file.exists()) file.delete();

            file.createNewFile();

            while ((line = br.readLine()) != null) {
                cantLineas++;
                String[] titulo = line.split("<title>");
                String[] meta = line.split("<meta name=\"description\" content=\"");

                if (titulo.length > 1) {

                    String[] title = titulo[1].split("</title");
                    tituloString = title[0];
                    copiarDadesAFitxer(1, tituloString);
                }

                if (meta.length > 1) {
                    String[] meta2 = meta[1].split("\">");
                    metaContent = meta2[0];
                    copiarDadesAFitxer(2, metaContent);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INICI copiarAFitxerDadesDeLaWeb()\n");
        stringBuilder.append(String.format("\t\tTÍTOL : %-100s\n\t\tDESCRIPCIÓ : %-50s\n\t\tQuantitat de linies que te la pàgina web : %-15s\n", tituloString +
                "", metaContent + "", cantLineas + ""));
        stringBuilder.append("FINAL copiarAFitxerDadesDeLaWeb()");

        System.out.println(stringBuilder.toString());

    }

    /**
     * copiarDadesAFitxer
     * @param tipo
     * @param datos
     * @throws IOException
     */
    private static void copiarDadesAFitxer(int tipo, String datos) throws IOException {
        String s = "";
        byte[] abyte;
        comprobarExistenciaDirectori("exercici1");

        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);

        if (tipo == 1) bw.write("TITOL      : " + datos + "\n");
        else bw.write("DESCRIPCIO : " + datos + "\n");

        bw.flush();

    }

    /**
     * comprobarExistenciaDirectori
     * @param dir
     */
    private static void comprobarExistenciaDirectori(String dir) {
        File f = new File(dir);
        if (!f.exists()) f.mkdir();
    }


    public static void main(String[] args) throws IOException {

        copiarAFitxerDadesDeLaWeb(LINK);
    }
}
