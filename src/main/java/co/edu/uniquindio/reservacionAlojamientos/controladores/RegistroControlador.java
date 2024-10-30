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

    private final ReservaPrincipal reservaPrincipal;
    public RegistroControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
    }

    public void realizarRegistro() {
        try {
            reservaPrincipal.registrarUsuario(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtContrasena.getText()
            );

            limpiarCampos();
            mostrarAlerta("Usted se ha registrado con éxito a BookYourStay", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

        public void volver() {
            navegarVentana("/inicio.fxml", "Inicio");
            cerrarVentana(txtEmail);
        }
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
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
    private void limpiarCampos(){
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtContrasena.clear();
    }



    }


