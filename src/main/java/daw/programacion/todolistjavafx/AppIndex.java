package daw.programacion.todolistjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AppIndex extends Application {
    public static Connection conexion;
    public static TareaModel tareas = new TareaModel();

    @Override
    public void start(Stage escenario) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppIndex.class.getResource("VistaApp.fxml"));
        Scene escena = new Scene(fxmlLoader.load(), 800, 650);
        escenario.setTitle("AppIndex Gestión");
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escenario.setScene(escena);
        escenario.show();
    }

    public static void main(String[] args) throws SQLException {
        conexion=MiConexion.abrirConexion();
        if(conexion!=null && conexion.isClosed())
            System.out.println("Conectando a la base de datos");
        System.out.println("Conexión: "+conexion);

        launch();
    }
}