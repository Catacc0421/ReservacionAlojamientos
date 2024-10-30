package co.edu.uniquindio.reservacionAlojamientos.servicios;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Usuario;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ReservaServicios {
    void validarString(String string, String mensaje) throws Exception;

    void validarDatosRegistro(String nombreCompleto, String cedula, String telefono, String email, String contrasena) throws Exception;

    //void validarDatosAlojamiento(String nombreAlojamiento, String ciudad, String descripcion, TipoAlojamiento tipoAlojamiento, float precioNoche, int capacidadMaxima, ArrayList<Habitacion> habitaciones, List<String> serviciosIncluidos) throws Exception;

    void enviarEmail(Usuario usuario, String mensaje, String asunto) throws Exception;

    void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception;
}
