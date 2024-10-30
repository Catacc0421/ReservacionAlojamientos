package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import co.edu.uniquindio.reservacionAlojamientos.utils.EnvioEmail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservaPrincipal implements ReservaServicios {
    private final List<Usuario> usuarios;
    private final List<Alojamiento> alojamientos;
    public static ReservaPrincipal INSTANCIA;

    public ReservaPrincipal() {
        usuarios = new ArrayList<>();
        alojamientos = new ArrayList<>();
    }

    public String generarCodigoRecuperacion() {
        // Genera un código único utilizando UUID (8 caracteres)
        return UUID.randomUUID().toString().substring(0, 8);
    }
    public void enviarEmailRecuperacion(String email, String codigoRecuperacion) throws Exception {
            String mensaje = "" +
                    "Para cambiar su contraseña copie este código en nuestra página.\nCódigo de cambio de contraseña: " + codigoRecuperacion;

            //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
            new EnvioEmail(email, "Cambio de contraseña", mensaje).enviarNotificacion();
        }


    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        validarDatosRegistro(cedula, nombreCompleto, telefono, email, contrasena);

        if (obtenerUsuarioCedula(cedula) != null || obtenerUsuarioEmail(email) != null) {
            throw new Exception("Ya existe un usuario con la cédula o el email proporcionado.");
        }

        Usuario usuario = Usuario.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .telefono(telefono)
                .email(email)
                .contrasena(contrasena)
                .activo(false)
                .build();

        String codigoActivacion = "BC-" + UUID.randomUUID().toString().substring(0, 10);
        usuario.setCodigoActivacion(codigoActivacion);

        enviarNotificacionesRegistro(email, codigoActivacion);
        usuarios.add(usuario);

        return true;
    }
    public void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception {
        String mensaje = "Su registro en BookYourStay ha sido exitoso, solo queda un paso más.\nCódigo de activación de cuenta: " + codigoActivacion;

        //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
        new EnvioEmail(email, "Registro exitoso", mensaje).enviarNotificacion();
    }
    public void validarDatosRegistro(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        validarString(nombreCompleto, "El nombre es requerido");
        validarString(cedula, "La cédula es requerida");
        validarString(email, "El email es requerido");
        validarString(telefono, "El teléfono es requerido");
        validarString(contrasena, "La contraseña es requerida");
    }

    @Override
    public void enviarEmail(Usuario usuario, String mensaje, String asunto) throws Exception {

    }

    public Usuario obtenerUsuarioCedula(String cedula) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }
    public Usuario obtenerUsuarioEmail(String email) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }
    @Override
    public void validarString(String string, String mensaje) throws Exception {
        if (string == null || string.isEmpty() || string.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
    public ArrayList<String> listarCiudades(){
        ArrayList<String> ciudades = new ArrayList<>();
        ciudades.add("MEDELLÍN");
        ciudades.add("CARTAGENA");
        ciudades.add("BARRANQUILLA");
        ciudades.add("PEREIRA");
        ciudades.add("BOGOTÁ");
        ciudades.add("CALI");
        ciudades.add("SANTA MARTA");
        ciudades.add("SAN ANDRÉS");
        ciudades.add("MANIZALES");
        ciudades.add("CÚCUTA");
        ciudades.add("NEIVA");
        ciudades.add("ARMENIA");
        ciudades.add("PALMIRA");
        return ciudades;

    }
    public ArrayList<String> listarTiposAlojamientos() {
        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("CASA");
        tipos.add("APARTAMENTO");
        tipos.add("HOTEL");


        return tipos;
    }
    public List<Alojamiento> listarAlojamientos() {
        return alojamientos;
    }
    public List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, String tipo) {
        return alojamientos.stream()
                .filter(a -> (nombre == null || a.getNombreAlojamiento().toLowerCase().contains(nombre.toLowerCase())) &&
                        (ciudad == null || ciudad.equalsIgnoreCase(a.getCiudad())) &&
                        (tipo == null || tipo.equalsIgnoreCase(a.getTipoAlojamiento().toString())))
                .collect(Collectors.toList());
    }
    public Usuario iniciarSesion(String email, String contrasena) throws Exception {
        Usuario usuario = obtenerUsuarioEmail(email);
        if (usuario != null) {
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
            } else {
                throw new Exception("Contraseña incorrecta");
            }
        } else {
            throw new Exception("Usuario no encontrado");
        }
    }
    public static ReservaPrincipal getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ReservaPrincipal();
        }
        return INSTANCIA;
    }

    public boolean activarCuenta(String email, String codigoActivacion) throws Exception {
        Usuario usuario = obtenerUsuarioEmail(email);
        if (!usuario.isActivo()) {
            if (usuario.getCodigoActivacion().equals(codigoActivacion)) {
                usuario.setActivo(true);
                return true;
            }
            return false;
        }
        throw new Exception("El usuario ya se encuentra activo.");
    }
}
