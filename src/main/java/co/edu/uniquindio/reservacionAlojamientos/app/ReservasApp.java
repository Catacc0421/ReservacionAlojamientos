package co.edu.uniquindio.reservacionAlojamientos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ReservasApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(ReservasApp.class.getResource("/Inicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 500, 600);
        stage.setScene(scene);
        stage.setTitle("Aplicación contactos");
        //stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(ReservasApp.class, args);
    }
}
