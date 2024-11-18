package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Sesion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class IniciarSesionControlador {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtContrasena;

    @FXML
    private CheckBox txtValidar;
    private static final String ADMIN_EMAIL = "administradorunico@gmail.com";
    private static final String ADMIN_PASSWORD = "12345admin";

    private final ControladorPrincipal controladorPrincipal;
    public IniciarSesionControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    public void iniciarSesion(ActionEvent event) {
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        boolean esAdmin = txtValidar.isSelected(); // Verificar si la CheckBox está marcada

        try {
            // Si el usuario ha marcado la casilla de administrador
            if (esAdmin) {
                // Verificar si el correo y la contraseña son del administrador
                if (email.equals(ADMIN_EMAIL) && contrasena.equals(ADMIN_PASSWORD)) {
                    // Redirigir al panel del administrador
                    controladorPrincipal.navegarVentana("/PanelAdministrador.fxml", "Panel Administrador");
                    controladorPrincipal.cerrarVentana(txtEmail);
                } else {
                    controladorPrincipal.mostrarAlerta("Error de acceso, Las credenciales de administrador son incorrectas.", Alert.AlertType.ERROR);
                }
            } else {
                // Intentar iniciar sesión como usuario normal
                Usuario usuario = controladorPrincipal.iniciarSesion(email, contrasena);
                Sesion.getInstanciaSesion().setUsuario(usuario);
                if (!usuario.isActivo()) {
                    controladorPrincipal.navegarVentana("/ActivacionCuenta.fxml", "Activación de cuenta");
                    controladorPrincipal.cerrarVentana(txtEmail);
                }else{
                    if (usuario != null) {
                        // Redirigir al panel de usuario común
                        controladorPrincipal.navegarVentana("/PanelUsuario.fxml", "Panel Cliente");
                        controladorPrincipal.cerrarVentana(txtEmail);
                    } else {
                        controladorPrincipal.mostrarAlerta("Error de acceso, Correo o contraseña incorrectos.", Alert.AlertType.ERROR );
                    }
                }

            }
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error de inicio de sesión", Alert.AlertType.valueOf(e.getMessage()));
        }
    }

    public void volver() {
        controladorPrincipal.navegarVentana("/Inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(txtEmail);

    }
    public void recuperarContrasena(){
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            controladorPrincipal.mostrarAlerta("Por favor, ingrese su correo electrónico.", Alert.AlertType.ERROR);
            return;
        }

        try {

            // Enviar el correo electrónico al usuario
            controladorPrincipal.enviarEmailRecuperacion(email);

            // Mostrar mensaje de éxito
            controladorPrincipal.mostrarAlerta("Se ha enviado un correo electrónico con el código para cambiar la contraseña.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("No se pudo enviar el correo electrónico. Inténtelo de nuevo más tarde.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        controladorPrincipal.navegarVentana("/RecuperarContrasena.fxml", "Recuperacion de contraseña");
    }

    }


