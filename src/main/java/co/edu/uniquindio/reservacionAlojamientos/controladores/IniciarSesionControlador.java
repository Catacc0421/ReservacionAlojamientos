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

    private final ReservaPrincipal reservaPrincipal;
    public IniciarSesionControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
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
                    navegarVentana("/PanelAdministrador.fxml", "Panel Administrador");
                } else {
                    mostrarAlerta("Error de acceso", "Las credenciales de administrador son incorrectas.");
                }
            } else {
                // Intentar iniciar sesión como usuario normal
                Usuario usuario = reservaPrincipal.iniciarSesion(email, contrasena);
                Sesion.getInstanciaSesion().setUsuario(usuario);
                if (!usuario.isActivo()) {
                    navegarVentana("/ActivacionCuenta.fxml", "Activación de cuenta");
                }else{
                    if (usuario != null) {
                        // Redirigir al panel de usuario común
                        navegarVentana("/PanelUsuario.fxml", "Panel Cliente");
                    } else {
                        mostrarAlerta("Error de acceso", "Correo o contraseña incorrectos.");
                    }
                }

            }
        } catch (Exception e) {
            mostrarAlerta("Error de inicio de sesión", e.getMessage());
        }
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
    public void volver() {
        navegarVentana("/Inicio.fxml", "Inicio");
        cerrarVentana(txtEmail);

    }
    public void recuperarContrasena(){
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingrese su correo electrónico.");
            return;
        }

        try {
            // Generar un código aleatorio para cambiar la contraseña
            String codigoRecuperacion = reservaPrincipal.generarCodigoRecuperacion();

            // Enviar el correo electrónico al usuario
            reservaPrincipal.enviarEmailRecuperacion(email, codigoRecuperacion);

            // Mostrar mensaje de éxito
            mostrarAlerta("Correo Enviado", "Se ha enviado un correo electrónico con el código para cambiar la contraseña.");

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo enviar el correo electrónico. Inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
        navegarVentana("/RecuperarContrasena.fxml", "Recuperacion de contraseña");
    }
    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    }


