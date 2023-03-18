package daw.programacion.todolistjavafx;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

public class TareaModel {
    private String filePath;

    private FileOutputStream fos;

    private FileInputStream fis;

    private Properties lista = new Properties();

    public TareaModel(String file){
        this.filePath=file;
    }
    public TareaModel(){}

    public boolean existeTarea(String t, Connection bd) throws SQLException {
        String sql="SELECT COUNT(*) FROM tareas WHERE titulo=?";
        PreparedStatement pstmt=bd.prepareStatement(sql);
        pstmt.setString(1,t);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if (rs.getInt(1)>0){
            System.out.println("Ya existe ese registro en la BD");
            return true;
        }
        return false;
    }

    public boolean existenTareas(Connection bd) throws SQLException {
        String sql="SELECT COUNT(*) FROM tareas";
        PreparedStatement pstmt=bd.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if (rs.getInt(1)>0){
            return true;
        }
        System.out.println("No existen tareas en la BD");
        return false;
    }

    public boolean asignarTarea(String t, String d, Connection bd) throws SQLException {
        boolean b=false;
        if (!existeTarea(t,bd)) {
            String sql = "INSERT INTO tareas(titulo,descripcion) VALUES (?,?)";
            PreparedStatement pstmt = bd.prepareStatement(sql);
            pstmt.setString(1, t);
            pstmt.setString(2, d);
            if (pstmt.executeUpdate() != 0) {
                System.out.println("Registro insertado en la BD");
                b=true;
            }
        }
        return b;
    }
    public boolean borrarTarea(String t, Connection bd) throws SQLException {
        boolean b=false;
        if (existeTarea(t,bd)) {
            String sql = "DELETE FROM tareas WHERE titulo=?";
            PreparedStatement pstmt = bd.prepareStatement(sql);
            pstmt.setString(1, t);
            if (pstmt.executeUpdate() != 0) {
                System.out.println("Registro eliminado de la BD");
                b=true;
            }
        }
        return b;
    }

    public boolean modificarTarea(String te, String t, String d, Connection bd) throws SQLException {
        boolean b=false;
        if (existeTarea(t,bd)) {
            String sql = "UPDATE tareas SET titulo=?, descripcion=?  WHERE tarea.titulo=?;";
            PreparedStatement pstmt = bd.prepareStatement(sql);
            pstmt.setString(1, t);
            pstmt.setString(2, d);
            pstmt.setString(3, te);
            if (pstmt.executeUpdate() != 0) {
                System.out.println("Registro insertado en la BD");
                b=true;
            }
        }
        return b;
    }

    public ResultSet cargarTareas(Connection bd) throws SQLException {
        String sql = "SELECT * FROM tareas";
        PreparedStatement pstmt = bd.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    /* public void borrarTarea(String k){
        lista.remove(k);
    } */

    /* public void almacenarTareaArchivo(){
        try {
            fos = new FileOutputStream(filePath);
            lista.store(fos, "Tareas pendientes:");
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } */

    /* public boolean cargarTareasArchivo() throws IOException{
        File archivo = new File(filePath);
        boolean existe = false;
        if (archivo.exists() && archivo.isFile()){
            fis = new FileInputStream(filePath);
            lista.load(fis);
            fis.close();
            existe=true;
        }else System.out.println("¡No hay archivo de tareas!");
        return existe;
    } */

    public String getValor(String k){
        return (String) lista.get(k);
    }

    @Override
    public String toString() {
        return lista.toString();
    }

    public Iterator devuelveIteradorClaves() {
        return lista.keySet().iterator();
    }
}
