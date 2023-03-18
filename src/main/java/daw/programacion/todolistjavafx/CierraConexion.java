package daw.programacion.todolistjavafx;

import java.sql.Connection;

public class CierraConexion extends Thread{
    @Override
    public void run(){
        Connection refConexionAbierta=null;
        try{
            refConexionAbierta=MiConexion.abrirConexion();
            refConexionAbierta.close();
            System.out.println("Cerrando conexión a la BD");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
