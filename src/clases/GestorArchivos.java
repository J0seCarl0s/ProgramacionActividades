package clases;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vista.ProjectController;

public class GestorArchivos {

    public static List<Actividad> abrir() {
        List<Actividad> lista = new LinkedList<>();

        FileChooser fc = new FileChooser();
        fc.setTitle("Abrir");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de Excel", "*.xlsx"));
        File fil = fc.showOpenDialog(null);

        if (fil != null) {
            try {
                FileInputStream file = new FileInputStream(fil);
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                XSSFSheet sheet = workbook.getSheetAt(0);
                //obteniendo la cantidad de elemtnso de la celda 0,3
                XSSFRow rwCant = sheet.getRow(0);
                String valor = rwCant.getCell(3).toString();
                Double caux = Double.parseDouble(valor);
                int cant = caux.intValue();

//                JOptionPane.showMessageDialog(null, "VAlor obt = " + cant);
                int fil_inicial = 2,
                        col_inicial = 0,
                        fil_final = cant + fil_inicial - 1,
                        col_final = 3;

                String rpta[][] = new String[fil_final - fil_inicial + 1][col_final - col_inicial + 1];
                for (int fAux = fil_inicial; fAux <= fil_final; fAux++) {
                    XSSFRow rw = sheet.getRow(fAux);
                    if (rw != null) {
                        for (int cAux = col_inicial; cAux <= col_final; cAux++) {
                            Cell celda = rw.getCell(cAux);
//                    System.out.println("f=" + (fAux - fil_inicial) + " c=" + (cAux - col_inicial));
                            if (celda != null) {
                                rpta[fAux - fil_inicial][cAux - col_inicial] = celda.toString();
//                        System.out.println(celda.toString());//imprime lo q se va cargando
                            } else {
                                rpta[fAux - fil_inicial][cAux - col_inicial] = null;
                            }
                        }
                    }
                }
                Actividad.resetearInidice();
                for (int i = 0; i < rpta.length; i++) {
                    lista.add(new Actividad(rpta[i][1], Double.parseDouble(rpta[i][2])));
                }
                for (int i = 0; i < rpta.length; i++) {
                    String predecesores = rpta[i][3];
                    String[] arregloPredecesores = predecesores.split(", ");
                    for (String nomPred : arregloPredecesores) {
                        for (int j = 0; j < lista.size(); j++) {
                            if (lista.get(j).getNombreDeTarea().equals(nomPred)) {
                                lista.get(i).getPredecesoras().add(lista.get(j));
                                break;
                            }
                        }

                    }

                }
                ProjectController.archivoActual = fil;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "El archivo de excel no tiene el formato requerido");
//                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
        return lista;
    }

    public static void guardar(List<Actividad> lista, File file) {

        //creando archivo excel
        String hoja = "Hoja1";

        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(hoja);

        int totElem = lista.size();
        String[][] datos = new String[totElem][4];

        String cantidad[] = new String[]{"Cantidad de actividades:", null, null, String.valueOf(totElem)};
//        cantidad[0][0] = "Cantidad de Actividades:";
//        document[0][1] = "";
//        document[totElem][2] = ;
//        datos[0][3] = String.valueOf(totElem);
        for (int i = 0; i < totElem; i++) {
            datos[i][0] = String.valueOf(lista.get(i).getNumeroDeActividad());
            datos[i][1] = lista.get(i).getNombreDeTarea();
            datos[i][2] = String.valueOf(lista.get(i).getDuracion());
            List<Actividad> laux = lista.get(i).getPredecesoras();
            StringBuilder predecesoras = new StringBuilder();
            for (int j = 0; j < laux.size(); j++) {
                predecesoras.append(laux.get(j).getNombreDeTarea());
                predecesoras.append(", ");
            }
            if (predecesoras.length() >= 2) {
                datos[i][3] = predecesoras.toString().substring(0, predecesoras.toString().length() - 2);
            } else {
                datos[i][3] = "";
            }
        }
        //cabecera de la hoja de excel
        String[] header = new String[]{"Nro Actividad", "Nombre", "Duracion", "Predecesoras"};

        //contenido de la hoja de excel
//        String[][] document = new String[][]{
//            {"AP150", "ACCESS POINT TP-LINK TL-WA901ND 450Mbps Wireless N 1RJ45 10-100 3Ant.", "112.00", "50"},
//            {"RTP150", "ROUTER TP-LINK TL-WR940ND 10-100Mbpps LAN WAN 2.4 - 2.4835Ghz", "19.60", "25"},
//            {"TRT300", "TARJETA DE RED TPLINK TL-WN881ND 300Mpbs Wire-N PCI-Exp.", "10.68", "15"},
//            {"TRT300", "DE RED TPLINK TL-WN881ND 300Mpbs Wire-N PCI-Exp.", "10.68", "15"},
//            {"TR0", "DE RED TPLINK TL-WN881ND 300Mpbs Wire-N PCI-Exp.", "10.68", "15"}
//        };
        //poner negrita a la cabecera
        CellStyle style = libro.createCellStyle();
        Font font = libro.createFont();
        font.setBold(true);
        style.setFont(font);

        //generar los datos para el documento
        for (int i = 0; i <= datos.length + 1; i++) {
            XSSFRow row = hoja1.createRow(i);//se crea las filas
            for (int j = 0; j < header.length; j++) {
                if (i == 0) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(cantidad[j]);
                } else if (i == 1) {//para la cabecera
                    XSSFCell cell = row.createCell(j);//se crea las celdas para la cabecera, junto con la posición
                    cell.setCellStyle(style); // se añade el style crea anteriormente 
                    cell.setCellValue(header[j]);//se añade el contenido					
                } else {//para el contenido
                    XSSFCell cell = row.createCell(j);//se crea las celdas para la contenido, junto con la posición
                    cell.setCellValue(datos[i - 2][j]); //se añade el contenido
                }
            }
        }
//        File file;
//        file = new File(rutaArchivo);
        try (FileOutputStream fileOuS = new FileOutputStream(file)) {
            if (file.exists()) {// si el archivo existe se elimina
                file.delete();
                System.out.println("Archivo eliminado");
            }
            libro.write(fileOuS);
            fileOuS.flush();
            fileOuS.close();
            System.out.println("Archivo Creado");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //fin crear archivo
    }

    public static void guardarComo(List<Actividad> listaActividades) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de Excel", "*.xlsx"));

        File file = fc.showSaveDialog(null);
        if (file != null) {
            System.out.println("ruta elegida = " + file);
            guardar(listaActividades, file);
        }
    }

    private static String[][] leer(File rutaExcel, int pagina, int fil_inicial, int col_inicial, int fil_final, int col_final) throws FileNotFoundException, IOException {

        FileInputStream file = new FileInputStream(rutaExcel);
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(pagina);

        String rpta[][] = new String[fil_final - fil_inicial + 1][col_final - col_inicial + 1];
        for (int fAux = fil_inicial; fAux <= fil_final; fAux++) {
            XSSFRow rw = sheet.getRow(fAux);
            if (rw != null) {
                for (int cAux = col_inicial; cAux <= col_final; cAux++) {
                    Cell celda = rw.getCell(cAux);
//                    System.out.println("f=" + (fAux - fil_inicial) + " c=" + (cAux - col_inicial));

                    if (celda != null) {
                        rpta[fAux - fil_inicial][cAux - col_inicial] = celda.toString();
//                        System.out.println(celda.toString());//imprime lo q se va cargando
                    } else {
                        rpta[fAux - fil_inicial][cAux - col_inicial] = null;
                    }
                }
            }
        }
        return rpta;
    }
}
