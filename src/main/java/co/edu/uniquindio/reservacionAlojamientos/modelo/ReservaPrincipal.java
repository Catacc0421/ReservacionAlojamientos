package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import co.edu.uniquindio.reservacionAlojamientos.utils.EnvioEmail;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservaPrincipal implements ReservaServicios {
    private final List<Usuario> usuarios;
    private final List<Alojamiento> alojamientos;
    private final List<Reserva> reservas;

    private final List<CodigoVerificacion> codigosVerificacion;
    public static ReservaPrincipal INSTANCIA;

    public ReservaPrincipal() {
        usuarios = new ArrayList<>();
        alojamientos = new ArrayList<>();
        reservas = new ArrayList<>();
        codigosVerificacion = new ArrayList<>();
    }
    public static ReservaPrincipal getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ReservaPrincipal();
        }
        return INSTANCIA;
    }


    //Método que permite registrar a un usuario con sus datos
    @Override
    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {

        if (obtenerUsuarioCedula(cedula) != null || obtenerUsuarioEmail(email) != null) {
            throw new Exception("Ya existe un usuario con la cédula o el email proporcionado.");
        }
        if (cedula == null || cedula.isBlank()) {
            throw new Exception("El número de cédula es obligatorio");
        }

        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (email == null || email.isBlank()) {
            throw new Exception("El correo es obligatorio");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new Exception("La contraseña es obligatoria");
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
    //Método que permite a un usuario activar su cuenta inmediatamente cuando inicia sesión con el codigo de activacion
    @Override
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
    //Método que permite enviar un email con el código de activación de la cuenta
    public void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception {
        String mensaje = "Su registro en BookYourStay ha sido exitoso, solo queda un paso más.\nCódigo de activación de cuenta: " + codigoActivacion;

        //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
        new EnvioEmail(email, "Registro exitoso", mensaje).enviarNotificacion();
    }
    //Método que permite a un usuario iniciar sesion
    @Override
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
    //Método que permite obtener un usuario con su número de cedula de la lista de usuarios
    public Usuario obtenerUsuarioCedula(String cedula) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }
    //Método que permite obtener un usuario con su email en la lista de usuarios
    public Usuario obtenerUsuarioEmail(String email) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }
    //Método que permite listar las ciudades disponibles
    @Override
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
    //Método que permite listar los tipos de alojamientos disponibles
    @Override
    public ArrayList<TipoAlojamiento> listarTiposAlojamientos() {
        return new ArrayList<>(List.of(TipoAlojamiento.values()));
    }
    //Método que permite listar todos los alojamientos
    @Override
    public List<Alojamiento> listarAlojamientos() {
        return alojamientos;
    }
    //Método que permite buscar alojamientos en la lista de alojamientos
    @Override
    public List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo) {
        return alojamientos.stream()
                .filter(a -> (nombre == null || a.getNombreAlojamiento().toLowerCase().contains(nombre.toLowerCase())) &&
                        (ciudad == null || ciudad.equalsIgnoreCase(a.getCiudad())) ) //&&
                        //(tipo == null || tipo.equalsIgnoreCase(a.getTipoAlojamiento().toString())))
                .collect(Collectors.toList());
    }

    /*
    Método que permite generar un código de recuperación aleatorio
     */
    @Override
    public String generarCodigoRecuperacion() {
        // Genera un código único utilizando UUID (6 caracteres)
        return UUID.randomUUID().toString().substring(0, 6);
    }
    /*
    Método que permite enviar un email con el código de recuperación para el cambio de su contraseña
     */
    @Override
    public void enviarEmailRecuperacion(String email, String codigoRecuperacion) throws Exception {
        String mensaje = "" +
                "Para cambiar su contraseña copie este código en la ventana de recuperación.\nCódigo de cambio de contraseña: " + codigoRecuperacion;

        //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
        new EnvioEmail(email, "Código de recuperación de contraseña", mensaje).enviarNotificacion();
    }
    @Override
    public boolean actualizarContrasena(String email, String codigoRecuperacion, String nuevaContrasena) throws Exception {
        return false;
    }

}
