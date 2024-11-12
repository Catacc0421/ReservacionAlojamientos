package co.edu.uniquindio.reservacionAlojamientos.servicios;

import co.edu.uniquindio.reservacionAlojamientos.factory.AlojamientoApartamento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;

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
    List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo);
    String generarCodigoRecuperacion();
    void enviarEmailRecuperacion(String email) throws Exception;

    boolean actualizarContrasena(String correo, String nuevaContrasena, String codigoVerificacion) throws Exception;
}
