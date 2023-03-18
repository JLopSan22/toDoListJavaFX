module daw.programacion.todolistjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires mysql.connector.java;

    opens daw.programacion.todolistjavafx to javafx.fxml;
    exports daw.programacion.todolistjavafx;
}