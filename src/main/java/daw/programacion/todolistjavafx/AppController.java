package daw.programacion.todolistjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AppController implements Initializable{

    @FXML
    private VBox salida;
    @FXML
    private VBox crear;
    @FXML
    private VBox modificar;
    @FXML
    private TextField tituloNueva;
    @FXML
    private TextArea descNueva;
    @FXML
    private TextField tituloMod;
    @FXML
    private TextArea descMod;
    @FXML
    private TextFlow informacion;
    @FXML
    private Text mensaje;
    private Text refTitulo;
    private Text refTitulo2;
    private Text refDesc;

    @FXML
    protected void botonCrear() throws IOException, SQLException {
        var clases = informacion.getStyleClass();

        var nuevoTitulo = new Text(tituloNueva.getText());
        var nuevaDesc = new Text(descNueva.getText());
        var fila = new HBox();
        var botonModificar = new Button("Modificar");
        var botonEliminar = new Button("Eliminar");
        botonModificar.getStyleClass().setAll("btn", "btn-warning");
        botonEliminar.getStyleClass().setAll("btn", "btn-danger");

        botonEliminar.setOnAction(e -> {
            var hbox = botonEliminar.getParent();
            var vbox = botonModificar.getParent().getParent();
            ((VBox) vbox).getChildren().remove(hbox);

            var titulo = (Text) ((HBox) hbox).getChildren().get(0);

            try {
                AppIndex.tareas.borrarTarea(titulo.getText(), AppIndex.conexion);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            // AppIndex.tareas.almacenarTareaArchivo();

            clases.remove(1);
            clases.add("alert-warning");
            mostrarMensaje("Tarea eliminada: " + nuevoTitulo.getText() + " " + nuevaDesc.getText());
        });

        botonModificar.setOnAction(e -> {
            crear.setVisible(false);
            modificar.setVisible(true);

            var hbox = botonModificar.getParent();

            var titulo = (Text) ((HBox) hbox).getChildren().get(0);
            var descripcion = (Text) ((HBox) hbox).getChildren().get(1);

            tituloMod.setText(titulo.getText());
            descMod.setText(descripcion.getText());

            refTitulo = nuevoTitulo;
            refDesc = nuevaDesc;

            refTitulo2 = nuevoTitulo;
        });



        if (nuevoTitulo.getText().equals("") || nuevaDesc.getText().equals("")) {
            clases.remove(1);
            clases.add("alert-danger");
            mostrarMensaje("Tarea no creada, alguno de los campos se encuentra vacío");

        } else {
            boolean insertado = AppIndex.tareas.existeTarea(nuevoTitulo.getText(), AppIndex.conexion);

            if (!insertado) {
                fila.getChildren().addAll(nuevoTitulo, nuevaDesc, botonModificar, botonEliminar);
                salida.getChildren().add(fila);
                fila.setSpacing(10);

                AppIndex.tareas.asignarTarea(nuevoTitulo.getText(), nuevaDesc.getText(), AppIndex.conexion);
                // AppIndex.tareas.almacenarTareaArchivo();

                clases.remove(1);
                clases.add("alert-success");
                mostrarMensaje("Tarea creada: " + nuevoTitulo.getText() + " " + nuevaDesc.getText());
            } else {
                clases.remove(1);
                clases.add("alert-danger");
                mostrarMensaje("Tarea no creada, ya existe una tarea con el título: "+nuevoTitulo.getText());
            }
        }
    }

    @FXML
    protected void botonModificar() throws IOException, SQLException {
        var clases = informacion.getStyleClass();
        modificar.setVisible(false);
        crear.setVisible(true);
        if (tituloMod.getText().equals("") || descMod.getText().equals("")) {
            clases.remove(1);
            clases.add("alert-danger");
            mostrarMensaje("Tarea no modificada, alguno de los campos se encuentra vacío");
        } else{
            refTitulo.setText(tituloMod.getText());
            refDesc.setText(descMod.getText());

            AppIndex.tareas.modificarTarea(refTitulo2.getText(), refTitulo.getText(), refDesc.getText(), AppIndex.conexion);
            // AppIndex.tareas.almacenarTareaArchivo();
            clases.remove(1);
            clases.add("alert-warning");
            mostrarMensaje("Tarea modificada: " + refTitulo.getText() + " " + refDesc.getText());
        }
    }

    @FXML
    private void botonOcultar(){
        informacion.setVisible(false);
    }

    private void mostrarMensaje(String msj){
        mensaje.setText("Mensaje: " + msj);
        informacion.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            boolean hayTareas = AppIndex.tareas.existenTareas(AppIndex.conexion);
            if (hayTareas) {
                ResultSet rs = AppIndex.tareas.cargarTareas(AppIndex.conexion);
                while (rs.next()) {
                    var nuevoTitulo = new Text(rs.getString(1));
                    var nuevaDesc = new Text(rs.getString(2));

                    var fila = new HBox();
                    var botonModificar = new Button("Modificar");
                    var botonEliminar = new Button("Eliminar");
                    botonModificar.getStyleClass().setAll("btn", "btn-warning");
                    botonEliminar.getStyleClass().setAll("btn", "btn-danger");

                    // AppIndex.tareas.asignarTarea(nuevoTitulo.getText(), nuevaDesc.getText(), AppIndex.conexion);
                    //AppIndex.tareas.almacenarTareaArchivo();

                    botonEliminar.setOnAction(e -> {
                        var hbox = botonEliminar.getParent();
                        var vbox = botonModificar.getParent().getParent();
                        ((VBox) vbox).getChildren().remove(hbox);

                        var titulo = (Text) ((HBox) hbox).getChildren().get(0);

                        try {
                            AppIndex.tareas.borrarTarea(titulo.getText(), AppIndex.conexion);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        // AppIndex.tareas.borrarTarea(titulo.getText());
                        //AppIndex.tareas.almacenarTareaArchivo();
                    });

                    botonModificar.setOnAction(e -> {
                        crear.setVisible(false);
                        modificar.setVisible(true);

                        var hbox = botonModificar.getParent();

                        var titulo = (Text) ((HBox) hbox).getChildren().get(0);
                        var descripcion = (Text) ((HBox) hbox).getChildren().get(1);

                        tituloMod.setText(titulo.getText());
                        descMod.setText(descripcion.getText());

                        refTitulo = nuevoTitulo;
                        refDesc = nuevaDesc;

                        // AppIndex.tareas.borrarTarea(titulo.getText());
                        // AppIndex.tareas.almacenarTareaArchivo();
                    });

                    fila.getChildren().addAll(nuevoTitulo, nuevaDesc, botonModificar, botonEliminar);
                    fila.setSpacing(10);


                    salida.getChildren().add(fila);
                }
                //}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}