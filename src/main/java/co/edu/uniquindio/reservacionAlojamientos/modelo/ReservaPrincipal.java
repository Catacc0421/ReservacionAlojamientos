package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoApartamento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoCasa;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoHotel;
import co.edu.uniquindio.reservacionAlojamientos.servicios.ReservaServicios;
import co.edu.uniquindio.reservacionAlojamientos.utils.EnvioEmail;
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

        // Validar que la fecha de reserva sea con al menos dos días de anticipación
        if (!esFechaValida(fechaInicio)) {
            throw new Exception("La reserva debe realizarse con al menos 5 días de anticipación.");
        }

        // Validar disponibilidad del alojamiento para las fechas seleccionadas y el número de huéspedes
        if (!estaDisponibleAlojamiento(alojamiento, fechaInicio, fechaFin, numeroHuespedes)) {
            throw new Exception("El alojamiento no está disponible para las fechas y número de huéspedes seleccionados.");
        }

        // Calcular el costo de la reserva
        double costoAseo = 0;
        double costoMantenimiento = 0;
        double precioPorNoche = 0;
        long diasEstancia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        // Si es una Casa o Apartamento, obtenemos el precio de la clase correspondiente
        if (alojamiento instanceof AlojamientoCasa) {
            AlojamientoCasa casa = (AlojamientoCasa) alojamiento;
            costoAseo = casa.getCostoAseo();
            costoMantenimiento = casa.getCostoMantenimiento();
            precioPorNoche = casa.getPrecioNoche();
        } else if (alojamiento instanceof AlojamientoApartamento) {
            AlojamientoApartamento apartamento = (AlojamientoApartamento) alojamiento;
            costoAseo = apartamento.getCostoAseo();
            costoMantenimiento = apartamento.getCostoMantenimiento();
            precioPorNoche = apartamento.getPrecioNoche();
        }

        // Si es un Hotel, obtenemos el precio de la habitación seleccionada
        if (alojamiento instanceof AlojamientoHotel) {
            if (habitacionSeleccionada == null) {
                throw new Exception("Debe seleccionar una habitación para realizar la reserva.");
            }
            precioPorNoche = habitacionSeleccionada.getPrecio();
        }

        // Calcular el costo total
        double costoTotal = (precioPorNoche * diasEstancia) + costoAseo + costoMantenimiento;

        // Calcular el descuento por ofertas especiales
        double descuento = calcularDescuento(alojamiento, fechaInicio, fechaFin);
        double montoDescuento = (costoTotal * descuento) / 100;
        costoTotal -= montoDescuento; // Aplicar el descuento al costo total

        // Verificar que el usuario tenga suficiente saldo en su billetera virtual
        if (!usuario.getBilleteraVirtual().tieneSuficienteSaldo(costoTotal)) {
            throw new Exception("No tiene suficiente saldo en su billetera virtual para realizar la reserva.");
        }

        // Crear el ID único para la reserva (UUID)
        String idReserva = UUID.randomUUID().toString();

        // Crear la factura para la reserva
        Factura factura = new Factura(costoTotal); // Suponiendo que la factura solo tiene el costo total

        // Crear la reserva con el ID generado
        Reserva nuevaReserva = new Reserva(idReserva, usuario, alojamiento, fechaInicio, fechaFin, numeroHuespedes, "Confirmada", factura);
        nuevaReserva.setId(idReserva); // Asignar el ID generado a la reserva

        // Agregar la reserva a la lista de reservas
        reservas.add(nuevaReserva);

        // Descontar el costo de la billetera virtual
        usuario.getBilleteraVirtual().restarSaldo(costoTotal);

        // Enviar mensaje de confirmación de reserva
        String mensaje = String.format(
                "Su reserva ha sido creada con éxito con los siguientes detalles:\n\n" +
                        "Alojamiento: %s\n" +
                        "Fecha de Inicio: %s\n" +
                        "Fecha de Fin: %s\n" +
                        "Número de Huéspedes: %d\n" +
                        "Costo por Noche: $%.2f\n" +
                        "Costo Aseo: $%.2f\n" +
                        "Costo Mantenimiento: $%.2f\n" +
                        "Descuento Aplicado: $%.2f\n" +
                        "Costo Total: $%.2f\n\n" +
                        "Gracias por utilizar nuestro sistema de reservas.\n" +
                        "Atentamente,\nEl equipo de Reservas",
                alojamiento.getNombreAlojamiento(),
                fechaInicio.toString(),
                fechaFin.toString(),
                numeroHuespedes,
                precioPorNoche,
                costoAseo,
                costoMantenimiento,
                montoDescuento,
                costoTotal
        );

        // Enviar correo electrónico con los detalles de la reserva
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

        // Si es necesario, devolver el dinero al usuario (en función de la lógica de tu aplicación)
        // Asumiendo que el costo total está en la factura
        double costoTotal = reserva.getFactura().getValorTotal();
        Usuario usuario = reserva.getUsuario();

        // Devolver el saldo a la billetera virtual del usuario
        usuario.getBilleteraVirtual().sumarSaldo(costoTotal);

        // Eliminar la reserva de la lista de reservas
        reservas.remove(reserva);

        // Crear el mensaje para el correo de confirmación de cancelación
        String mensaje = String.format(
                "Su reserva ha sido cancelada con éxito.\n\n" +
                        "Alojamiento: %s\n" +
                        "Fecha de Inicio: %s\n" +
                        "Fecha de Fin: %s\n" +
                        "Número de Huéspedes: %d\n" +
                        "Costo Total: $%.2f\n\n" +
                        "Gracias por utilizar nuestro sistema de reservas.\n" +
                        "Atentamente,\nEl equipo de Reservas",
                reserva.getAlojamiento().getNombreAlojamiento(),
                reserva.getFechaInicio().toString(),
                reserva.getFechaFin().toString(),
                reserva.getNumeroHuespedes(),
                costoTotal
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



}
