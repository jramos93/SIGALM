/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Jramos93
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String salida = null;
        Calendar c = new GregorianCalendar();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        System.out.println(año);
        String comando = "expdp SISUPA_OW/SISUPA_OW SCHEMAS=SISUPA_OW "
                + "DIRECTORY=CARPETA_EXPORT "
                + "DUMPFILE=backup_" + dia + "-" + (mes + 1) + "-" + año + ".dmp "
                + "LOGFILE=schema.log";

        try {
            // Ejecucion Basica del Comando
            Process proceso = Runtime.getRuntime().exec(comando);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

}
