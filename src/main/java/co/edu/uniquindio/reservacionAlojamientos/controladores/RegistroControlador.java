package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroControlador {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtContrasena;

    private final ControladorPrincipal controladorPrincipal;
    public RegistroControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void realizarRegistro() {
        try {
            controladorPrincipal.registrarUsuario(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtContrasena.getText()
            );

            limpiarCampos();
            controladorPrincipal.mostrarAlerta("Usted se ha registrado con éxito a BookYourStay", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            controladorPrincipal.mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

        public void volver() {
            controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
            controladorPrincipal.cerrarVentana(txtEmail);
        }
    private void limpiarCampos(){
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtContrasena.clear();
    }



    }


