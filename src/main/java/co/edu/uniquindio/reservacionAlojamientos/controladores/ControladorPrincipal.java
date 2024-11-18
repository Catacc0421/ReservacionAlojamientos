package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.*;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ArrayList<String> listarRangosPrecios() {
        return reservaPrincipal.listarRangosPrecios();
    }

    @Override
    public List<Habitacion> listarHabitaciones() {
        return reservaPrincipal.listarHabitaciones();
    }

    @Override
    public List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo, Double precioMinCOP, Double precioMaxCOP) {
        return reservaPrincipal.buscarAlojamientos(nombre, ciudad, tipo, precioMinCOP, precioMaxCOP);
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
    public boolean agregarAlojamiento(String tipo, String nombre, String ciudad, String descripcion, List<TipoServicio> serviciosIncluidos, File imagen, Double costoAseo, Double costoMantenimiento, Double precioPorNoche, Integer capacidadMaxima, List<Habitacion> habitaciones) throws Exception {
        return reservaPrincipal.agregarAlojamiento(tipo, nombre, ciudad, descripcion, serviciosIncluidos, imagen, costoAseo, costoMantenimiento, precioPorNoche, capacidadMaxima, habitaciones);
    }

    @Override
    public void eliminarAlojamiento(String id) throws Exception {
        reservaPrincipal.eliminarAlojamiento(id);
    }

    @Override
    public List<Reserva> listarReservasPorPersona(String cedulaPersona) {
        return reservaPrincipal.listarReservasPorPersona(cedulaPersona);
    }

    @Override
    public void cancelarReserva(Reserva reserva) throws Exception {
        reservaPrincipal.cancelarReserva(reserva);
    }

    @Override
    public Usuario obtenerUsuarioCedula(String cedula) throws Exception {
        return reservaPrincipal.obtenerUsuarioCedula(cedula);
    }

    @Override
    public void recargarBilletera(double monto, Usuario usuario) throws Exception {
        reservaPrincipal.recargarBilletera(monto, usuario);
    }

    @Override
    public boolean eliminarCuenta(Usuario usuario) throws Exception {
        return reservaPrincipal.eliminarCuenta(usuario);
    }

    @Override
    public boolean eliminarOferta(Alojamiento alojamiento, String tipoOferta) {
        return reservaPrincipal.eliminarOferta(alojamiento, tipoOferta);
    }

    @Override
    public boolean editarOferta(Alojamiento alojamiento, String tipoOferta, Object nuevaOferta) {
        return reservaPrincipal.editarOferta(alojamiento, tipoOferta, nuevaOferta);
    }

    @Override
    public ArrayList<String> listarOfertas() {
        return reservaPrincipal.listarOfertas();
    }

    @Override
    public void agregarOfertaEspecial(Alojamiento alojamiento, Object oferta) throws Exception {
        reservaPrincipal.agregarOfertaEspecial(alojamiento, oferta);
    }

    @Override
    public boolean editarCuenta(Usuario usuario, String nuevoNombreCompleto, String nuevoTelefono, String nuevoEmail) throws Exception {
        return reservaPrincipal.editarCuenta(usuario, nuevoNombreCompleto, nuevoTelefono, nuevoEmail);
    }

    @Override
    public boolean editarAlojamiento(String id, String tipo, String nombre, String ciudad, String descripcion, List<TipoServicio> serviciosIncluidos, File imagen, Double costoAseo, Double costoMantenimiento, Double precioPorNoche, Integer capacidadMaxima, List<Habitacion> habitaciones) throws Exception {
        return reservaPrincipal.editarAlojamiento(id, tipo, nombre, ciudad, descripcion, serviciosIncluidos, imagen, costoAseo, costoMantenimiento, precioPorNoche, capacidadMaxima, habitaciones);
    }

    @Override
    public boolean actualizarContrasena(String correo, String codigoVerificacion, String nuevaContrasena) throws Exception {
        return reservaPrincipal.actualizarContrasena(correo, codigoVerificacion, nuevaContrasena);
    }

    @Override
    public void realizarReserva(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin, Alojamiento alojamiento, int numeroHuespedes, Habitacion habitacionSeleccionada) throws Exception {
        reservaPrincipal.realizarReserva(usuario, fechaInicio, fechaFin, alojamiento, numeroHuespedes, habitacionSeleccionada);
    }

}
