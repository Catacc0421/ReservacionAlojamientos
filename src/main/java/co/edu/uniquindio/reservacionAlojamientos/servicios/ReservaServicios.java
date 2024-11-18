package co.edu.uniquindio.reservacionAlojamientos.servicios;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Reserva;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public interface ReservaServicios {
    boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception;

    List<Alojamiento> listarAlojamientos();
    Usuario iniciarSesion(String email, String contrasena) throws Exception;

    boolean activarCuenta(String email, String codigoActivacion) throws Exception;
    ArrayList<String> listarCiudades();
    ArrayList<TipoAlojamiento> listarTiposAlojamientos();
    ArrayList<String> listarRangosPrecios();
    List<Habitacion> listarHabitaciones();
    List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo, Double precioMinCOP, Double precioMaxCOP);
    String generarCodigoRecuperacion();
    void enviarEmailRecuperacion(String email) throws Exception;
    boolean agregarAlojamiento(String tipo, String nombre, String ciudad, String descripcion,
                                      List<TipoServicio> serviciosIncluidos, File imagen,
                                      Double costoAseo, Double costoMantenimiento,
                                      Double precioPorNoche, Integer capacidadMaxima,
                                      List<Habitacion> habitaciones) throws Exception;
    void eliminarAlojamiento(String id) throws Exception;
    List<Reserva> listarReservasPorPersona(String cedulaPersona);
    void cancelarReserva(Reserva reserva) throws Exception;
    Usuario obtenerUsuarioCedula(String cedula) throws Exception;
    void recargarBilletera(double monto, Usuario usuario) throws Exception;
    boolean eliminarCuenta(Usuario usuario) throws Exception;
    boolean eliminarOferta(Alojamiento alojamiento, String tipoOferta);
    boolean editarOferta(Alojamiento alojamiento, String tipoOferta, Object nuevaOferta);
    ArrayList<String> listarOfertas();
    void agregarOfertaEspecial(Alojamiento alojamiento, Object oferta) throws Exception;
    boolean editarCuenta(Usuario usuario, String nuevoNombreCompleto, String nuevoTelefono, String nuevoEmail) throws Exception;
    boolean editarAlojamiento(String id, String tipo, String nombre, String ciudad, String descripcion,
                                     List<TipoServicio> serviciosIncluidos, File imagen, Double costoAseo,
                                     Double costoMantenimiento, Double precioPorNoche, Integer capacidadMaxima,
                                     List<Habitacion> habitaciones) throws Exception;
    boolean actualizarContrasena(String correo, String nuevaContrasena, String codigoVerificacion) throws Exception;
    void realizarReserva(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin,
                         Alojamiento alojamiento, int numeroHuespedes, Habitacion habitacionSeleccionada) throws Exception;
}
