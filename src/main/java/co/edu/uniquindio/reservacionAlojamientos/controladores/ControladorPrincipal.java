package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal implements ReservaServicios {

    private final ReservaPrincipal reservaPrincipal;
    private ControladorPrincipal() {
        reservaPrincipal = new ReservaPrincipal();
    }
    public static ControladorPrincipal INSTANCIA;
    public static ControladorPrincipal getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
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
    public ButtonType mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(mensaje);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        return alert.getResult();
    }

    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception{
        return reservaPrincipal.registrarUsuario(cedula, nombreCompleto, telefono, email, contrasena);
    }

    @Override
    public List<Alojamiento> listarAlojamientos() {
        return reservaPrincipal.listarAlojamientos();
    }
    @Override
    public Usuario iniciarSesion(String email, String contrasena)throws Exception{
        return reservaPrincipal.iniciarSesion(email, contrasena);
    }
    @Override
    public boolean activarCuenta(String email, String codigoActivacion) throws Exception{
        return reservaPrincipal.activarCuenta(email, codigoActivacion);
    }

    @Override
    public ArrayList<String> listarCiudades() {
        return reservaPrincipal.listarCiudades();
    }

    @Override
    public ArrayList<TipoAlojamiento> listarTiposAlojamientos() {
        return reservaPrincipal.listarTiposAlojamientos();
    }

    @Override
    public List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo) {
        return reservaPrincipal.buscarAlojamientos(nombre, ciudad, tipo);
    }

    @Override
    public String generarCodigoRecuperacion() {
        return reservaPrincipal.generarCodigoRecuperacion();
    }

    @Override
    public void enviarEmailRecuperacion(String email) throws Exception {
        reservaPrincipal.enviarEmailRecuperacion(email);
    }

    @Override
    public boolean actualizarContrasena(String correo, String codigoVerificacion, String nuevaContrasena) throws Exception {
        return reservaPrincipal.actualizarContrasena(correo, codigoVerificacion, nuevaContrasena);
    }
}
