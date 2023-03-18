package daw.programacion.todolistjavafx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GestionPropertiesConexion {
    private static Properties cargaArchivo(FileInputStream fis) throws IOException{
        Properties miColeccion=new Properties();
        miColeccion.load(fis);
        return miColeccion;
    }
    public static Properties getProperties(String nombreArchivo) throws IOException {
        return cargaArchivo(new FileInputStream(nombreArchivo));
    }
}
