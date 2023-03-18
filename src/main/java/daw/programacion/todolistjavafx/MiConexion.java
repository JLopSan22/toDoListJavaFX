package daw.programacion.todolistjavafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MiConexion {
    private static Properties properties=null;
    private static Connection conexion=null;
    private MiConexion() throws SQLException, IOException {
        properties=GestionPropertiesConexion.getProperties("conexion.properties");
        String url=properties.getProperty("url");
        String usr=properties.getProperty("usr");
        String pwd=properties.getProperty("pwd");
        conexion = DriverManager.getConnection(url, usr, pwd);
    }
    public static Connection abrirConexion(){
        String url="""
                jdbc:mysql://localhost:3306/database?allowPublicKeyRetrieval=true&useSSL=false
                """;
        try {
            if (conexion==null || conexion.isClosed())
                new MiConexion();
            return conexion;
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("ErrorCode: "+e.getErrorCode());
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
