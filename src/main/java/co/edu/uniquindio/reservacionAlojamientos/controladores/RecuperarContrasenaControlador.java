package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecuperarContrasenaControlador {
    @FXML
    private TextField txtCodigoActivacion;
    @FXML
    private TextField txtCorreoElectronico;

    @FXML
    private PasswordField txtContrasenaNueva;


    private final ControladorPrincipal controladorPrincipal;
    public RecuperarContrasenaControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    public void volver() {
        controladorPrincipal.navegarVentana("/IniciarSesion.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(txtCorreoElectronico);
    }
    public void actualizarContrasena() {
        String correo = txtCorreoElectronico.getText();
        String nueva = txtContrasenaNueva.getText();
        String codigo = txtCodigoActivacion.getText();
        try {
            boolean exito = controladorPrincipal.actualizarContrasena(correo, codigo, nueva);
            if(exito){
                volver();
            }else{
                controladorPrincipal.mostrarAlerta("No se pudo actualizar la contraseña", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    }

