/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.cnr.sigalm.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sv.gob.cnr.sigalm.util.Mensaje;

/**
 *
 * @author Jramos93
 */

@Named
@RequestScoped
public class AdministracionController implements Serializable{
    private static final long serialVersionUID = 1L;

    private Mensaje m;
    public void generarBackup() {
        m = new Mensaje();
        String respuesta = null;
        String salida = null;
        Calendar c = new GregorianCalendar();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        System.out.println(año);
        String comando = "cmd /C expdp SISUPA_OW/SISUPA_OW SCHEMAS=SISUPA_OW "
                + "DIRECTORY=CARPETA_EXPORT "
                + "DUMPFILE=backup_" + dia + "-" + (mes + 1) + "-" + año +"_"+hora+"-"+minuto+".dmp "
                + "LOGFILE=schema.log";
        try {
            // Ejecucion Basica del Comando
            Process proceso = Runtime.getRuntime().exec(comando);
            m.info("BACKUP REALIZADO");
        } catch (IOException e) {
            m.error("No se pudo realizar el backup");
            e.printStackTrace();
        }
    }
}
