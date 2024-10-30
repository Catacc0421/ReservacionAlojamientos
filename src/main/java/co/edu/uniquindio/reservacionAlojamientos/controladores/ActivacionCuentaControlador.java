package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class ActivacionCuentaControlador {

    @FXML
    private TextField txtCodigo;
    private final ReservaPrincipal reservaPrincipal;
    private Sesion sesion;
    public ActivacionCuentaControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
        sesion = Sesion.getInstanciaSesion();
    }

    public void volver(){
        navegarVentana("/IniciarSesion.fxml", "Iniciar sesion");
        sesion.cerrarSesion();
    }
    public void activarCuenta(){
        String codigoActivacionString = txtCodigo.getText();
        String email = sesion.getUsuario().getEmail();
        try {
            boolean activacionExitosa = reservaPrincipal.activarCuenta(email, codigoActivacionString);
            if (activacionExitosa){
                mostrarAlerta("Activacion de cuenta exitosa", Alert.AlertType.INFORMATION);
                navegarVentana("/PanelUsuario.fxml", "Panel usuario");
            }else{
               mostrarAlerta("El codigo de activacion no coincide", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
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
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
