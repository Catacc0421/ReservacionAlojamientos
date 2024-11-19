package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoApartamento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoCasa;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoHotel;
import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import co.edu.uniquindio.reservacionAlojamientos.utils.EnvioEmail;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReservaPrincipal implements ReservaServicios {
    private static final String ADMIN_EMAIL = "admin@gmail.com"; // Email del administrador
    private static final String ADMIN_PASSWORD = "12345admin";   // Contraseña del administrador
    private final List<Usuario> usuarios;
    private final List<Alojamiento> alojamientos;
    private final List<Reserva> reservas;
    private final List<Habitacion> habitaciones;

    private final List<CodigoVerificacion> codigosVerificacion;
    public static ReservaPrincipal INSTANCIA;

    public ReservaPrincipal() {
        usuarios = new ArrayList<>();
        alojamientos = new ArrayList<>();
        reservas = new ArrayList<>();
        habitaciones = new ArrayList<>();
        codigosVerificacion = new ArrayList<>();
        try {
            registrarUsuario ("1092851309", "abajabjabajj", "ksnkakssk", "catalina.clavijoc@uqvirtual.edu.co", "12345");
            agregarAlojamiento(
                    "CASA","CASA 1", "ssssw", "", new ArrayList<>(), new File("C:\\Users\\catal\\Downloads\\Imagenes.jpg"), 0.0, 0.0, 50000.0, 20, new ArrayList<>()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        BilleteraVirtual billetera = new BilleteraVirtual(0.0);
        usuario.setBilleteraVirtual(billetera);

        String codigoActivacion = UUID.randomUUID().toString().substring(0, 10);
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
    @Override
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
    @Override
    public ArrayList<String> listarOfertas(){
        ArrayList<String> ofertas= new ArrayList<>();
        ofertas.add("ESTANCIA");
        ofertas.add("RANGO_FECHAS");
        return ofertas;

    }
    @Override
    public ArrayList<String> listarRangosPrecios() {
        ArrayList<String> rangosPrecios = new ArrayList<>();
        rangosPrecios.add("Menos de $50,000");
        rangosPrecios.add("$50,000 - $100,000");
        rangosPrecios.add("$100,000 - $200,000");
        rangosPrecios.add("$200,000 - $500,000");
        rangosPrecios.add("Más de $500,000");
        return rangosPrecios;
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
    @Override
    public List<Habitacion> listarHabitaciones(){return habitaciones;}
    //Método que permite buscar alojamientos en la lista de alojamientos
    @Override
    public List<Alojamiento> buscarAlojamientos(String nombre, String ciudad, TipoAlojamiento tipo, Double precioMinCOP, Double precioMaxCOP) {
        return alojamientos.stream()
                .filter(a ->
                        (nombre == null || a.getNombreAlojamiento().toLowerCase().contains(nombre.toLowerCase())) &&
                                (ciudad == null || ciudad.equalsIgnoreCase(a.getCiudad())) &&
                                (tipo == null || tipo.equals(determinarTipoAlojamiento(a))) &&
                                (precioMinCOP == null || obtenerPrecioEnCOP(a) >= precioMinCOP) &&
                                (precioMaxCOP == null || obtenerPrecioEnCOP(a) <= precioMaxCOP)
                )
                .collect(Collectors.toList());
    }

    private Double obtenerPrecioEnCOP(Alojamiento alojamiento) {
        if (alojamiento instanceof AlojamientoCasa) {
            return ((AlojamientoCasa) alojamiento).getPrecioNoche();
        } else if (alojamiento instanceof AlojamientoApartamento) {
            return ((AlojamientoApartamento) alojamiento).getPrecioNoche();
        } else if (alojamiento instanceof AlojamientoHotel) {
            return ((AlojamientoHotel) alojamiento).getHabitaciones().stream()
                    .mapToDouble(Habitacion::getPrecio)
                    .average()
                    .orElse(0.0); // Promedio de precios de habitaciones en un hotel
        }
        return 0.0; // Valor predeterminado si no es un tipo conocido
    }

    private TipoAlojamiento determinarTipoAlojamiento(Alojamiento alojamiento) {
        if (alojamiento instanceof AlojamientoCasa) {
            return TipoAlojamiento.CASA;
        } else if (alojamiento instanceof AlojamientoApartamento) {
            return TipoAlojamiento.APARTAMENTO;
        } else if (alojamiento instanceof AlojamientoHotel) {
            return TipoAlojamiento.HOTEL;
        }
        return null; // Si no es un tipo conocido
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
    public void enviarEmailRecuperacion(String email) throws Exception {
        String codigoRecuperacion = generarCodigoRecuperacion();

        Usuario usuario = obtenerUsuarioEmail(email);
        usuario.setCodigoRecuperacionPassword(codigoRecuperacion); //Queda guardado?

        String mensaje = "" +
                "Para cambiar su contraseña copie este código en la ventana de recuperación.\nCódigo de cambio de contraseña: " + codigoRecuperacion;

        //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
        new EnvioEmail(email, "Código de recuperación de contraseña", mensaje).enviarNotificacion();
    }
    @Override
    public boolean actualizarContrasena(String email, String codigoRecuperacion, String nuevaContrasena) throws Exception {
        Usuario usuario = obtenerUsuarioEmail(email);
        System.out.println(usuario);
        if (usuario.isActivo()) {
            if (usuario.getCodigoRecuperacionPassword().equals(codigoRecuperacion)) {
                usuario.setContrasena(nuevaContrasena);
                return true;
            }else{
                throw new Exception("El código de validación es incorrecto.");
            }
        }
        throw new Exception("El usuario no se encuentra activo.");
    }
    @Override
    public boolean agregarAlojamiento(String tipo, String nombre, String ciudad, String descripcion, List<TipoServicio> serviciosIncluidos, File imagen, Double costoAseo, Double costoMantenimiento, Double precioPorNoche, Integer capacidadMaxima, List<Habitacion> habitaciones) throws Exception {
        Alojamiento nuevoAlojamiento = null;

        switch (tipo) {
            case "CASA":
                nuevoAlojamiento = AlojamientoCasa.builder()
                        .id(UUID.randomUUID().toString())
                        .nombreAlojamiento(nombre)
                        .ciudad(ciudad)
                        .descripcion(descripcion)
                        .image(imagen)
                        .serviciosIncluidos(serviciosIncluidos)
                        .costoAseo(costoAseo)
                        .costoMantenimiento(costoMantenimiento)
                        .precioNoche(precioPorNoche)
                        .capacidadMaxima(capacidadMaxima)
                        .build();
                break;

            case "APARTAMENTO":
                nuevoAlojamiento = AlojamientoApartamento.builder()
                        .id(UUID.randomUUID().toString())
                        .nombreAlojamiento(nombre)
                        .ciudad(ciudad)
                        .descripcion(descripcion)
                        .image(imagen)
                        .serviciosIncluidos(serviciosIncluidos)
                        .costoAseo(costoAseo)
                        .costoMantenimiento(costoMantenimiento)
                        .precioNoche(precioPorNoche)
                        .capacidadMaxima(capacidadMaxima)
                        .build();
                break;

            case "HOTEL":
                if (habitaciones == null || habitaciones.isEmpty()) {
                    throw new Exception("Debe especificar al menos una habitación para un hotel.");
                }
                nuevoAlojamiento = AlojamientoHotel.builder()
                        .id(UUID.randomUUID().toString())
                        .nombreAlojamiento(nombre)
                        .ciudad(ciudad)
                        .descripcion(descripcion)
                        .image(imagen)
                        .serviciosIncluidos(serviciosIncluidos)
                        .habitaciones(habitaciones)
                        .build();
                break;

            default:
                throw new Exception("Tipo de alojamiento no reconocido.");
        }

        if (nuevoAlojamiento != null) {
            alojamientos.add(nuevoAlojamiento);
            return true;
        }
        return false;
    }
    @Override
    public boolean editarAlojamiento(String id, String tipo, String nombre, String ciudad, String descripcion,
                                     List<TipoServicio> serviciosIncluidos, File imagen, Double costoAseo,
                                     Double costoMantenimiento, Double precioPorNoche, Integer capacidadMaxima,
                                     List<Habitacion> habitaciones) throws Exception {
        // Buscar el alojamiento por id
        int posAlojamiento = obtenerAlojamiento(id);
        if (posAlojamiento == -1) {
            throw new Exception("Alojamiento no encontrado.");
        }

        // Validaciones
        if (nombre != null && nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacío.");
        }
        if (ciudad != null && ciudad.isEmpty()) {
            throw new Exception("La ciudad no puede estar vacía.");
        }
        if (descripcion != null && descripcion.isEmpty()) {
            throw new Exception("La descripción no puede estar vacía.");
        }
        if (costoAseo != null && costoAseo < 0) {
            throw new Exception("El costo de aseo no puede ser negativo.");
        }
        if (costoMantenimiento != null && costoMantenimiento < 0) {
            throw new Exception("El costo de mantenimiento no puede ser negativo.");
        }
        if (precioPorNoche != null && precioPorNoche < 0) {
            throw new Exception("El precio por noche no puede ser negativo.");
        }
        if (capacidadMaxima != null && capacidadMaxima <= 0) {
            throw new Exception("La capacidad máxima debe ser mayor a cero.");
        }

        Alojamiento alojamientoAEditar = alojamientos.get(posAlojamiento);

        // Actualizar los campos comunes
        if (nombre != null) alojamientoAEditar.setNombreAlojamiento(nombre);
        if (ciudad != null) alojamientoAEditar.setCiudad(ciudad);
        if (descripcion != null) alojamientoAEditar.setDescripcion(descripcion);
        if (imagen != null) alojamientoAEditar.setImage(imagen);
        if (serviciosIncluidos != null) alojamientoAEditar.setServiciosIncluidos(serviciosIncluidos);

        // Actualizar los campos específicos según el tipo de alojamiento
        switch (tipo) {
            case "CASA":
                if (alojamientoAEditar instanceof AlojamientoCasa) {
                    AlojamientoCasa alojamientoCasa = (AlojamientoCasa) alojamientoAEditar;
                    if (costoAseo != null) alojamientoCasa.setCostoAseo(costoAseo);
                    if (costoMantenimiento != null) alojamientoCasa.setCostoMantenimiento(costoMantenimiento);
                    if (precioPorNoche != null) alojamientoCasa.setPrecioNoche(precioPorNoche);
                    if (capacidadMaxima != null) alojamientoCasa.setCapacidadMaxima(capacidadMaxima);
                } else {
                    throw new Exception("El tipo de alojamiento no coincide con el tipo CASA.");
                }
                break;

            case "APARTAMENTO":
                if (alojamientoAEditar instanceof AlojamientoApartamento) {
                    AlojamientoApartamento alojamientoApartamento = (AlojamientoApartamento) alojamientoAEditar;
                    if (costoAseo != null) alojamientoApartamento.setCostoAseo(costoAseo);
                    if (costoMantenimiento != null) alojamientoApartamento.setCostoMantenimiento(costoMantenimiento);
                    if (precioPorNoche != null) alojamientoApartamento.setPrecioNoche(precioPorNoche);
                    if (capacidadMaxima != null) alojamientoApartamento.setCapacidadMaxima(capacidadMaxima);
                } else {
                    throw new Exception("El tipo de alojamiento no coincide con el tipo APARTAMENTO.");
                }
                break;

            case "HOTEL":
                if (alojamientoAEditar instanceof AlojamientoHotel) {
                    AlojamientoHotel alojamientoHotel = (AlojamientoHotel) alojamientoAEditar;
                    if (habitaciones != null && !habitaciones.isEmpty()) {
                        // Si se proporcionan nuevas habitaciones, actualizamos las habitaciones del hotel
                        alojamientoHotel.setHabitaciones(habitaciones);

                        // Aquí actualizamos los atributos de las habitaciones
                        for (Habitacion habitacion : habitaciones) {
                            if (precioPorNoche != null) habitacion.setPrecio(precioPorNoche);
                            if (capacidadMaxima != null) habitacion.setCapacidad(capacidadMaxima);
                        }
                    } else {
                        throw new Exception("Debe especificar al menos una habitación para un hotel.");
                    }
                } else {
                    throw new Exception("El tipo de alojamiento no coincide con el tipo HOTEL.");
                }
                break;

            default:
                throw new Exception("Tipo de alojamiento no reconocido.");
        }

        // Guardar los cambios
        alojamientos.set(posAlojamiento, alojamientoAEditar);
        return true;
    }

    @Override
    public void eliminarAlojamiento(String id) throws Exception{
        int posNota = obtenerAlojamiento(id);

        if(posNota == -1){
            throw new Exception("No existe el id proporcionado");
        }

        alojamientos.remove( alojamientos.get(posNota) );
    }


    //Método para obtener las instalaciones por su id en la lista de instalaciones
    private int obtenerAlojamiento(String id){

        for (int i = 0; i < alojamientos.size(); i++) {
            if( alojamientos.get(i).getId().equals(id) ){
                return i;
            }
        }

        return -1;
    }
    @Override
    public void realizarReserva(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin,
                                Alojamiento alojamiento, int numeroHuespedes, Habitacion habitacionSeleccionada) throws Exception {

        // 1. Validar la reserva (fecha, disponibilidad, etc.)
        validarReserva(fechaInicio, alojamiento, fechaFin, numeroHuespedes, habitacionSeleccionada);

        // 2. Calcular el costo total de la reserva
        double costoTotal = calcularCostoTotal(alojamiento, fechaInicio, fechaFin, habitacionSeleccionada);

        // 3. Verificar que el usuario tenga suficiente saldo en la billetera virtual
        if (!usuario.getBilleteraVirtual().tieneSuficienteSaldo(costoTotal)) {
            throw new Exception("No tiene suficiente saldo en su billetera virtual para realizar la reserva.");
        }

        // 4. Generar ID único para la reserva
        String idReserva = UUID.randomUUID().toString();

        // 5. Crear la factura con el costo total
        Factura factura = new Factura(costoTotal);

        // 6. Crear la nueva reserva
        Reserva nuevaReserva = new Reserva(idReserva, usuario, alojamiento, fechaInicio, fechaFin, numeroHuespedes, "Confirmada", factura);

        // 7. Agregar la reserva a la lista de reservas y descontar el saldo del usuario
        reservas.add(nuevaReserva);
        usuario.getBilleteraVirtual().restarSaldo(costoTotal);

        // 8. Enviar la confirmación al usuario (con código QR adjunto)
        enviarConfirmacionConQR(usuario, alojamiento, fechaInicio, fechaFin, numeroHuespedes, costoTotal, habitacionSeleccionada, idReserva);
    }
    public BufferedImage generarCodigoQR(String idReserva) {
        try {
            // Crear el generador de código QR
            MultiFormatWriter writer = new MultiFormatWriter();

            // Codificar el idReserva como un código QR
            BitMatrix bitMatrix = writer.encode(idReserva, BarcodeFormat.QR_CODE, 200, 200);

            // Convertir el BitMatrix en una imagen BufferedImage
            BufferedImage imagenQR = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    imagenQR.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF); // Color negro para el QR, blanco para el fondo
                }
            }
            return imagenQR;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Si ocurre un error, retornar null
        }
    }


    // Método auxiliar para enviar la confirmación con código QR
    private void enviarConfirmacionConQR(Usuario usuario, Alojamiento alojamiento, LocalDate fechaInicio,
                                         LocalDate fechaFin, int numeroHuespedes, double costoTotal,
                                         Habitacion habitacionSeleccionada, String idReserva) {
        try {
            // Crear el mensaje del correo
            String mensaje = String.format(
                    "Su reserva ha sido creada con éxito:\n\n" +
                            "Alojamiento: %s\nFecha de Inicio: %s\nFecha de Fin: %s\nHuéspedes: %d\nHabitación: %s\nCosto Total: $%.2f\n" +
                            "ID de Reserva: %s\n",
                    alojamiento.getNombreAlojamiento(), fechaInicio, fechaFin, numeroHuespedes,
                    (habitacionSeleccionada != null ? habitacionSeleccionada.getNumeroHabitacion() : "N/A"),
                    costoTotal, idReserva
            );

            // Generar el código QR con el ID de reserva
            BufferedImage imagenQR = generarCodigoQR(idReserva);  // Método que generará el QR
            byte[] qrBytes = obtenerImagenComoBytes(imagenQR);  // Convertir la imagen a bytes

            // Enviar el correo con la confirmación y el código QR adjunto
            EnvioEmail envioEmail = new EnvioEmail(usuario.getEmail(), "Confirmación de Reserva", mensaje);
            envioEmail.setDestinatario(usuario.getEmail());
            envioEmail.setAsunto("Confirmación de Reserva");
            envioEmail.setMensaje(mensaje);
            envioEmail.enviarNotificacionConAdjunto(qrBytes);  // Método que maneja el envío con adjunto (ver más abajo)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para convertir la imagen QR a bytes
    private byte[] obtenerImagenComoBytes(BufferedImage imagen) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "PNG", baos);
        return baos.toByteArray();
    }
    // Métodos auxiliares
    private void validarReserva(LocalDate fechaInicio, Alojamiento alojamiento, LocalDate fechaFin, int numeroHuespedes, Habitacion habitacionSeleccionada) throws Exception {
        if (!esFechaValida(fechaInicio)) {
            throw new Exception("La reserva debe realizarse con al menos 5 días de anticipación.");
        }
        if (!estaDisponibleAlojamiento(alojamiento, fechaInicio, fechaFin, numeroHuespedes)) {
            throw new Exception("El alojamiento no está disponible para las fechas y número de huéspedes seleccionados.");
        }
    }

    private double calcularCostoTotal(Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, Habitacion habitacionSeleccionada) {
        double costoAseo = 0, costoMantenimiento = 0, precioPorNoche = 0;
        long diasEstancia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        if (alojamiento instanceof AlojamientoCasa casa) {
            costoAseo = casa.getCostoAseo();
            costoMantenimiento = casa.getCostoMantenimiento();
            precioPorNoche = casa.getPrecioNoche();
        } else if (alojamiento instanceof AlojamientoApartamento apartamento) {
            costoAseo = apartamento.getCostoAseo();
            costoMantenimiento = apartamento.getCostoMantenimiento();
            precioPorNoche = apartamento.getPrecioNoche();
        } else if (alojamiento instanceof AlojamientoHotel) {
            precioPorNoche = habitacionSeleccionada.getPrecio();
        }

        double costoTotal = (precioPorNoche * diasEstancia) + costoAseo + costoMantenimiento;
        double descuento = calcularDescuento(alojamiento, fechaInicio, fechaFin);
        return costoTotal - (costoTotal * descuento / 100);
    }

    private void enviarConfirmacion(Usuario usuario, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, int numeroHuespedes, double costoTotal, Habitacion habitacionSeleccionada) {
        String mensaje = String.format(
                "Su reserva ha sido creada con éxito:\n\n" +
                        "Alojamiento: %s\nFecha de Inicio: %s\nFecha de Fin: %s\nHuéspedes: %d\nHabitación: %s\nCosto Total: $%.2f\n",
                alojamiento.getNombreAlojamiento(), fechaInicio, fechaFin, numeroHuespedes,
                (habitacionSeleccionada != null ? habitacionSeleccionada.getNumeroHabitacion() : "N/A"),
                costoTotal
        );
        enviarEmail(usuario, mensaje, "Confirmación de Reserva");
    }
    public boolean esFechaValida(LocalDate fecha) {
        return !fecha.isBefore(LocalDate.now().plusDays(5));
    }
    private int obtenerCapacidadMaximaAlojamiento(Alojamiento alojamiento) {
        if (alojamiento instanceof AlojamientoCasa) {
            return ((AlojamientoCasa) alojamiento).getCapacidadMaxima();
        } else if (alojamiento instanceof AlojamientoApartamento) {
            return ((AlojamientoApartamento) alojamiento).getCapacidadMaxima();
        } else if (alojamiento instanceof AlojamientoHotel) {
            // Para un hotel, se asume que se puede tener varias habitaciones
            // Puedes verificar la capacidad de las habitaciones o asumir un valor global
            List<Habitacion> habitaciones = ((AlojamientoHotel) alojamiento).getHabitaciones();
            int capacidadMaximaHotel = 0;
            for (Habitacion habitacion : habitaciones) {
                capacidadMaximaHotel += habitacion.getCapacidad(); // Asumiendo que la capacidad es acumulativa por habitación
            }
            return capacidadMaximaHotel;
        }
        return 0; // Si no es un tipo válido de alojamiento
    }
    public boolean estaDisponibleAlojamiento(Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, int numeroHuespedes) {
        // Verificar la capacidad máxima
        int capacidadMaxima = obtenerCapacidadMaximaAlojamiento(alojamiento);

        if (numeroHuespedes > capacidadMaxima) {
            // Si el número de huéspedes excede la capacidad máxima, no se puede reservar
            return false;
        }

        // Verificar disponibilidad de fechas
        List<Reserva> reservas = obtenerReservasParaAlojamiento(alojamiento);

        for (Reserva reserva : reservas) {
            // Comprobar si las fechas de la nueva reserva se solapan con alguna reserva existente
            if (!(fechaFin.isBefore(reserva.getFechaInicio()) || fechaInicio.isAfter(reserva.getFechaFin()))) {
                // Si las fechas se solapan, no está disponible
                return false;
            }
        }

        // Si pasa ambas verificaciones, el alojamiento está disponible
        return true;
    }
    private List<Reserva> obtenerReservasParaAlojamiento(Alojamiento alojamiento) {
        // Esto depende de cómo estés almacenando las reservas. Puede ser una base de datos o una lista en memoria.
        // Este es un ejemplo simple usando una lista de reservas:
        return reservas.stream()
                .filter(reserva -> reserva.getAlojamiento().equals(alojamiento))
                .collect(Collectors.toList());
    }
    public void enviarEmail(Usuario usuario, String mensaje, String asunto) {
        String email = usuario.getEmail();
        try {
            new EnvioEmail(email, asunto, mensaje).enviarNotificacion();
        } catch (Exception e) {
            System.out.println("Error al enviar correo electrónico a: " + email);
            e.printStackTrace();
        }
    }
    @Override
    public List<Reserva> listarReservasPorPersona(String cedulaPersona) {

        List<Reserva> historial = new ArrayList<>();

        for (Reserva reserva : reservas){
            if(reserva.getUsuario().getCedula().equals(cedulaPersona)){
                historial.add(reserva);
            }
        }

        return historial;
    }
    @Override
    public void cancelarReserva(Reserva reserva) throws Exception {
        // Verificar si la reserva existe en la lista de reservas
        if (!reservas.contains(reserva)) {
            throw new Exception("La reserva seleccionada no existe o ya ha sido cancelada.");
        }

        // Cambiar el estado de la reserva a 'Cancelada'
        reserva.setEstado("Cancelada");

        // Obtener el costo total de la reserva
        double costoTotal = reserva.getFactura().getValorTotal();

        // Calcular el 60% del costo total
        double porcentajeDevolucion = costoTotal * 0.60;

        // Obtener el usuario asociado a la reserva
        Usuario usuario = reserva.getUsuario();

        // Sumar el 60% del valor total de la reserva a la billetera virtual del usuario
        usuario.getBilleteraVirtual().sumarSaldo(porcentajeDevolucion);

        // Eliminar la reserva de la lista de reservas
        reservas.remove(reserva);

        // Crear el mensaje para el correo de confirmación de cancelación
        String mensaje = String.format(
                "Su reserva ha sido cancelada con éxito.\n\n" +
                        "Alojamiento: %s\n" +
                        "Fecha de Inicio: %s\n" +
                        "Fecha de Fin: %s\n" +
                        "Número de Huéspedes: %d\n" +
                        "Costo Total: $%.2f\n" +
                        "Monto devuelto: $%.2f\n\n" +
                        "Gracias por utilizar nuestro sistema de reservas.\n" +
                        "Atentamente,\nEl equipo de Reservas",
                reserva.getAlojamiento().getNombreAlojamiento(),
                reserva.getFechaInicio().toString(),
                reserva.getFechaFin().toString(),
                reserva.getNumeroHuespedes(),
                costoTotal,
                porcentajeDevolucion
        );

        // Enviar correo electrónico de confirmación de cancelación
        enviarEmail(reserva.getUsuario(), mensaje, "Cancelación de Reserva");
    }
    @Override
    public void recargarBilletera(double monto, Usuario usuario) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto debe ser positivo");
        }

        // Obtener la billetera del usuario (asegúrate de que no sea nula)
        if (usuario.getBilleteraVirtual() == null) {
            throw new Exception("La billetera virtual no está asociada al usuario.");
        }

        // Actualizar el saldo de la billetera
        BilleteraVirtual billetera = usuario.getBilleteraVirtual();
        billetera.setSaldo(billetera.getSaldo() + monto);


    }
    @Override
    public boolean editarCuenta(Usuario usuario, String nuevoNombreCompleto, String nuevoTelefono, String nuevoEmail) throws Exception {
        // Validar que los campos no estén vacíos
        if (nuevoNombreCompleto == null || nuevoNombreCompleto.isBlank()) {
            throw new Exception("El nombre es obligatorio.");
        }

        if (nuevoEmail == null || nuevoEmail.isBlank()) {
            throw new Exception("El correo es obligatorio.");
        }

        // Verificar que el nuevo email no esté siendo usado por otro usuario
        if (obtenerUsuarioEmail(nuevoEmail) != null && !usuario.getEmail().equals(nuevoEmail)) {
            throw new Exception("El correo electrónico ya está en uso.");
        }

        // Actualizar los datos del usuario
        usuario.setNombreCompleto(nuevoNombreCompleto);
        usuario.setTelefono(nuevoTelefono);
        usuario.setEmail(nuevoEmail);

        // Enviar confirmación por correo
        String mensaje = "Hola " + nuevoNombreCompleto + ",\n\nTu cuenta ha sido actualizada con éxito.";
        enviarEmail(usuario, mensaje, "Actualización de Cuenta");

        return true;
    }
    @Override
    public boolean eliminarCuenta(Usuario usuario) throws Exception {
        // Eliminar todas las reservas asociadas al usuario
        eliminarReservasUsuario(usuario);

        // Eliminar el usuario de la lista
        usuarios.remove(usuario);

        // Enviar confirmación por correo
        String mensaje = "Hola " + usuario.getNombreCompleto() + ",\n\nTu cuenta ha sido eliminada con éxito.";
        enviarEmail(usuario, mensaje, "Eliminación de Cuenta");

        return true;
    }

    private void eliminarReservasUsuario(Usuario usuario) {
        // Eliminar las reservas asociadas al usuario
        reservas.removeIf(reserva -> reserva.getUsuario().equals(usuario));
    }
    private Map<Alojamiento, List<Object>> ofertasEspeciales = new HashMap<>();
    @Override
    public void agregarOfertaEspecial(Alojamiento alojamiento, Object oferta) throws Exception {
        if (alojamiento == null) {
            throw new Exception("El alojamiento no puede ser nulo.");
        }
        if (oferta == null) {
            throw new Exception("La oferta no puede ser nula.");
        }
        if (!(oferta instanceof OfertaEstancia || oferta instanceof OfertaRangoFechas)) {
            throw new Exception("El tipo de oferta no es válido.");
        }

        // Si el alojamiento no tiene ofertas registradas, inicializamos la lista
        ofertasEspeciales.putIfAbsent(alojamiento, new ArrayList<>());

        // Agregamos la oferta a la lista correspondiente
        ofertasEspeciales.get(alojamiento).add(oferta);
    }
    public double calcularDescuento(Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) {
        if (!ofertasEspeciales.containsKey(alojamiento)) {
            return 0; // No hay ofertas asociadas al alojamiento
        }

        double descuentoTotal = 0;
        List<Object> ofertas = ofertasEspeciales.get(alojamiento);

        for (Object oferta : ofertas) {
            if (oferta instanceof OfertaEstancia) {
                OfertaEstancia ofertaEstancia = (OfertaEstancia) oferta;
                long diasEstancia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
                if (diasEstancia >= ofertaEstancia.getNumeroDias()) {
                    descuentoTotal += ofertaEstancia.getDescuento();
                }
            } else if (oferta instanceof OfertaRangoFechas) {
                OfertaRangoFechas ofertaRangoFechas = (OfertaRangoFechas) oferta;
                // Verificar si las fechas de la reserva coinciden con el rango de la oferta
                if (!fechaInicio.isAfter(ofertaRangoFechas.getFechaFin()) &&
                        !fechaFin.isBefore(ofertaRangoFechas.getFechaInicio())) {
                    descuentoTotal += ofertaRangoFechas.getPorcentajeDescuento();
                }
            }
        }

        return descuentoTotal; // Porcentaje total acumulado
    }
    @Override
    public boolean eliminarOferta(Alojamiento alojamiento, String tipoOferta) {
        // Validar que el alojamiento no sea nulo
        if (alojamiento == null) {
            throw new IllegalArgumentException("El alojamiento no puede ser nulo.");
        }

        // Buscar y eliminar la oferta según su tipo
        switch (tipoOferta.toUpperCase()) {
            case "ESTANCIA":
                if (alojamiento.getOfertaEstancia() != null) {
                    alojamiento.setOfertaEstancia(null);
                    return true; // Eliminada exitosamente
                }
                break;

            case "RANGO_FECHAS":
                if (alojamiento.getOfertaRangoFechas() != null) {
                    alojamiento.setOfertaRangoFechas(null);
                    return true; // Eliminada exitosamente
                }
                break;

            default:
                throw new IllegalArgumentException("Tipo de oferta no válido: " + tipoOferta);
        }

        return false; // No se encontró la oferta
    }
    @Override
    public boolean editarOferta(Alojamiento alojamiento, String tipoOferta, Object nuevaOferta) {
        // Validar que el alojamiento y la nueva oferta no sean nulos
        if (alojamiento == null || nuevaOferta == null) {
            throw new IllegalArgumentException("El alojamiento y la nueva oferta no pueden ser nulos.");
        }

        // Editar la oferta según su tipo
        switch (tipoOferta.toUpperCase()) {
            case "ESTANCIA":
                if (nuevaOferta instanceof OfertaEstancia) {
                    alojamiento.setOfertaEstancia((OfertaEstancia) nuevaOferta);
                    return true; // Editada exitosamente
                }
                break;

            case "RANGO_FECHAS":
                if (nuevaOferta instanceof OfertaRangoFechas) {
                    alojamiento.setOfertaRangoFechas((OfertaRangoFechas) nuevaOferta);
                    return true; // Editada exitosamente
                }
                break;

            default:
                throw new IllegalArgumentException("Tipo de oferta no válido: " + tipoOferta);
        }

        return false; // No se pudo editar
    }
    @Override
    public double calcularOcupacion(Alojamiento alojamiento) {
        int totalHuespedes = 0;
        int capacidadTotal = 0;

        if (alojamiento instanceof AlojamientoApartamento) {
            capacidadTotal = ((AlojamientoApartamento) alojamiento).getCapacidadMaxima();
        } else if (alojamiento instanceof AlojamientoCasa) {
            capacidadTotal = ((AlojamientoCasa) alojamiento).getCapacidadMaxima();
        } else if (alojamiento instanceof AlojamientoHotel) {
            capacidadTotal = ((AlojamientoHotel) alojamiento).getHabitaciones().stream()
                    .mapToInt(Habitacion::getCapacidad)
                    .sum();
        }

        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().equals(alojamiento)) {
                totalHuespedes += reserva.getNumeroHuespedes();
            }
        }

        return (capacidadTotal > 0) ? (totalHuespedes / (double) capacidadTotal) * 100 : 0;
    }
    @Override

    // Calcula la ganancia total de un alojamiento
    public double calcularGanancia(Alojamiento alojamiento) {
        double totalGanancia = 0;

        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().equals(alojamiento)) {
                totalGanancia += reserva.getFactura().getValorTotal();
            }
        }

        return totalGanancia;
    }
    @Override

    // Obtiene el número de reservas por cada alojamiento en una ciudad específica
    public Map<Alojamiento, Integer> obtenerReservasPorCiudad(String ciudad) {
        Map<Alojamiento, Integer> reservasPorAlojamiento = new HashMap<>();

        for (Reserva reserva : reservas) {
            Alojamiento alojamiento = reserva.getAlojamiento();
            if (alojamiento.getCiudad().equals(ciudad)) {
                reservasPorAlojamiento.put(
                        alojamiento, reservasPorAlojamiento.getOrDefault(alojamiento, 0) + 1
                );
            }
        }

        return reservasPorAlojamiento;
    }
    @Override

    // Calcula el porcentaje de rentabilidad por tipo de alojamiento
    public Map<String, Double> calcularRentabilidadPorTipo() {
        Map<String, Double> rentabilidadPorTipo = new HashMap<>();
        double totalGanancia = 0;

        for (Reserva reserva : reservas) {
            Alojamiento alojamiento = reserva.getAlojamiento();
            String tipo = alojamiento.getClass().getSimpleName();
            double ganancia = reserva.getFactura().getValorTotal();

            rentabilidadPorTipo.put(tipo, rentabilidadPorTipo.getOrDefault(tipo, 0.0) + ganancia);
            totalGanancia += ganancia;
        }

        // Convertir a porcentaje
        for (String tipo : rentabilidadPorTipo.keySet()) {
            rentabilidadPorTipo.put(tipo, (rentabilidadPorTipo.get(tipo) / totalGanancia) * 100);
        }

        return rentabilidadPorTipo;
    }
    public boolean haPasadoLaFechaDeReserva(Alojamiento alojamiento, Usuario usuario) {
        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().equals(alojamiento) && reserva.getUsuario().equals(usuario)) {
                // Compara la fecha actual con la fecha de fin de la reserva
                if (LocalDate.now().isAfter(reserva.getFechaFin())) {
                    return true; // La reserva ha terminado
                }
            }
        }
        return false; // No se ha encontrado una reserva pasada
    }
    @Override
    // Método para añadir una reseña a un alojamiento solo si la reserva ha pasado
    public boolean agregarResenaSiReservaHaPasado(Alojamiento alojamiento, Resena resena, Usuario usuario) {
        if (haPasadoLaFechaDeReserva(alojamiento, usuario)) {
            alojamiento.getResenas().add(resena);
            return true;
        }
        return false;
    }



}
