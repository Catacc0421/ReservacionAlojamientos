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
    private Sesion sesion;
    private final ControladorPrincipal controladorPrincipal;
    public ActivacionCuentaControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
        sesion = Sesion.getInstanciaSesion();
    }

    public void volver(){
        controladorPrincipal.navegarVentana("/IniciarSesion.fxml", "Iniciar sesion");
        sesion.cerrarSesion();
    }
    public void activarCuenta(){
        String codigoActivacionString = txtCodigo.getText();
        String email = sesion.getUsuario().getEmail();
        try {
            boolean activacionExitosa = controladorPrincipal.activarCuenta(email, codigoActivacionString);
            if (activacionExitosa){
                controladorPrincipal.mostrarAlerta("Activacion de cuenta exitosa", Alert.AlertType.INFORMATION);
                controladorPrincipal.navegarVentana("/PanelUsuario.fxml", "Panel usuario");
            }else{
               controladorPrincipal.mostrarAlerta("El codigo de activacion no coincide", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}
