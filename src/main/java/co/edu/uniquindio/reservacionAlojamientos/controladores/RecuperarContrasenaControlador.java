package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecuperarContrasenaControlador {
    @FXML
    private TextField txtCodigoRecuperacion;

    @FXML
    private PasswordField txtNuevaContraseña;

    private final ReservaPrincipal reservaPrincipal;
    public RecuperarContrasenaControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
    }
    public void volver() {
        navegarVentana("/IniciarSesion.fxml", "Inicio");
    }
    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana){
        try {
            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();
            return loader;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }
}
