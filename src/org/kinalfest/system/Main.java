package org.kinalfest.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        ventana.setTitle("KinalFest - Gestión de Eventos");
        mostrar("LoginView.fxml");
        ventana.show();
    }

    public static void mostrar(String archivo) {
        try {
            Parent root = FXMLLoader.load(
                Main.class.getResource("/org/kinalfest/view/" + archivo)
            );

            ventana.setScene(new Scene(root, 1080, 720));
            ventana.centerOnScreen();

        } catch (IOException e) {
            throw new RuntimeException(
                "No se pudo abrir " + archivo, e
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("Variable MySQL configurada: "
                + (System.getenv("KINALFEST_DB_PASSWORD") != null));

        launch(args);
    }
}
