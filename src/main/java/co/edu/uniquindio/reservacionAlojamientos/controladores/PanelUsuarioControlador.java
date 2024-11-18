package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.controladores.observer.Observer;
import co.edu.uniquindio.reservacionAlojamientos.modelo.*;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoApartamento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoCasa;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoHotel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PanelUsuarioControlador implements Initializable, Observer {

    //GESTIONAR RESERVAS
    @FXML
    private ComboBox<String> cbRangoPrecios;
    @FXML
    private ComboBox<Habitacion> cbListarHabitaciones;

    @FXML
    private ChoiceBox<String> chooseCiudad;

    @FXML
    private TableColumn<Reserva, String> colAlojamiento;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colDescripcion;
    @FXML
    private TableColumn<Alojamiento, File> colImagen;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;
    @FXML
    private TableColumn<Alojamiento, String> colOfertas;
    @FXML
    private TableColumn<Alojamiento, String> colHabitaciones;
    @FXML
    private TableColumn<Alojamiento, String> colCapacidad;
    @FXML
    private TableColumn<Alojamiento, String> colPrecioNoche;
    @FXML
    private TableColumn<Alojamiento, String> colCostoMantenimiento;
    @FXML
    private TableColumn<Alojamiento, String> colCostoAseo;

    @FXML
    private Button btCerrarSesion;
    @FXML
    private TableColumn<Alojamiento, String> colServicios;

    @FXML
    private TableColumn<Alojamiento, String> colTipoAlojamiento;

    @FXML
    private ComboBox<TipoAlojamiento> comboBoxTipo;
    @FXML
    private Label lbNombreUsuario;

    @FXML
    private Label lbSaldoBilletera;
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumeroHuespedes;
    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private TableView<Alojamiento> tablaBusqueda;

    //HISTORIAL DE RESERVAS
    @FXML
    private Button btCancelarReserva;
    @FXML
    private TableColumn<Reserva, String> colEstado;

    @FXML
    private TableColumn<Reserva, String> colFechaFin;

    @FXML
    private TableColumn<Reserva, String> colFechaInicio;

    @FXML
    private TableColumn<Reserva, String> colNumeroHuespedes;

    @FXML
    private TableView<Reserva> tablaHistorial;

    //RECARGAR BILLETERA

    @FXML
    private Button btRecargar;

    @FXML
    private TextField txtMontoSaldo;


    //ACTUALIZAR O ELIMINAR DATOS
    @FXML
    private TextField txtNombrePersona;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    private ObservableList<Alojamiento> alojamientosObservable;
    private Alojamiento alojamientoSeleccionado;
    private Reserva reservaSeleccionada;
    private Habitacion habitacionSeleccionada;
    private ObservableList<Reserva> reservasObservable;

    private final ControladorPrincipal controladorPrincipal;
    private Usuario usuarioActual;
    private List<Habitacion> habitaciones;
    public PanelUsuarioControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
        usuarioActual = Sesion.getInstanciaSesion().getUsuario();
        this.habitaciones = new ArrayList<>();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombreAlojamiento())));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCiudad())));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDescripcion())));
        colCostoAseo.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getCostoAseo()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getCostoAseo()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no es ni casa ni apartamento, mostrar "N/A"
            }
        });
        colCostoMantenimiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getCostoMantenimiento()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getCostoMantenimiento()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no es ni casa ni apartamento, mostrar "N/A"
            }
        });
        colPrecioNoche.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getPrecioNoche()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getPrecioNoche()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no tiene precio por noche, mostrar "N/A"
            }
        });
        colCapacidad.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getCapacidadMaxima()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getCapacidadMaxima()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no tiene precio por noche, mostrar "N/A"
            }
        });
        colServicios.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getServiciosIncluidos()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getServiciosIncluidos()));
            } else if (alojamiento instanceof AlojamientoHotel) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoHotel) alojamiento).getServiciosIncluidos()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no tiene precio por noche, mostrar "N/A"
            }
        });
        colImagen.setCellFactory(col -> new ImageTableCell());
        colImagen.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getImage()));
        colHabitaciones.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoHotel) {
                AlojamientoHotel hotel = (AlojamientoHotel) alojamiento;
                List<Habitacion> habitaciones = hotel.getHabitaciones();
                StringBuilder habitacionesStr = new StringBuilder();
                for (Habitacion habitacion : habitaciones) {
                    habitacionesStr.append(habitacion.toString()).append("\n");
                }
                return new SimpleStringProperty(habitacionesStr.toString().trim());
            } else {
                return new SimpleStringProperty(""); // Retorna una cadena vacía si no es un hotel
            }
        });
        colOfertas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOfertaEstancia())));
        colOfertas.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            OfertaEstancia ofertaEstancia = alojamiento.getOfertaEstancia();
            OfertaRangoFechas ofertaRangoFechas = alojamiento.getOfertaRangoFechas();

            // Manejo de texto para las ofertas
            String textoOfertaEstancia = (ofertaEstancia != null) ? ofertaEstancia.toString() : "Sin oferta de estancia";
            String textoOfertaRangoFechas = (ofertaRangoFechas != null) ? ofertaRangoFechas.toString() : "Sin oferta por fechas";

            // Combinar ambas ofertas
            String textoFinal = textoOfertaEstancia + " | " + textoOfertaRangoFechas;
            return new SimpleStringProperty(textoFinal);
        });
        colTipoAlojamiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            String tipo = "Desconocido";

            if (alojamiento instanceof AlojamientoCasa) {
                tipo = "Casa";
            } else if (alojamiento instanceof AlojamientoApartamento) {
                tipo = "Apartamento";
            } else if (alojamiento instanceof AlojamientoHotel) {
                tipo = "Hotel";
            }

            return new SimpleStringProperty(tipo);
        });
        //Cargar categorias en el ComboBox
        comboBoxTipo.setItems( FXCollections.observableList(controladorPrincipal.listarTiposAlojamientos()) );
        chooseCiudad.setItems (FXCollections.observableList(controladorPrincipal.listarCiudades()));
        cbRangoPrecios.setItems(FXCollections.observableList(controladorPrincipal.listarRangosPrecios()));
        tablaBusqueda.setItems(alojamientosObservable);
        cbListarHabitaciones.setItems(FXCollections.observableList(controladorPrincipal.listarHabitaciones()));
        tablaHistorial.setItems(reservasObservable);


        // Inicializar comboBox de habitaciones (vacío al inicio)
        cbListarHabitaciones.setVisible(false);

        //Inicializar lista observable y cargar las notas
        alojamientosObservable = FXCollections.observableArrayList();
        reservasObservable = FXCollections.observableArrayList();
        cargarAlojamientos();
        cargarHabitaciones();
        cargarReservas();
        // Evento de clic en la tabla de instalaciones
        tablaBusqueda.setOnMouseClicked(e -> {
            // Obtener la instalación seleccionada en la tabla
            alojamientoSeleccionado = tablaBusqueda.getSelectionModel().getSelectedItem();

            if (alojamientoSeleccionado != null) {
                // Mostrar mensaje informativo sobre la instalación seleccionada
                controladorPrincipal.mostrarAlerta("Alojamiento seleccionado: " + alojamientoSeleccionado.getNombreAlojamiento(), Alert.AlertType.INFORMATION);

                // Si el alojamiento seleccionado es un hotel, cargar sus habitaciones
                if (alojamientoSeleccionado instanceof AlojamientoHotel) {
                    AlojamientoHotel hotelSeleccionado = (AlojamientoHotel) alojamientoSeleccionado;

                    // Obtener la lista de habitaciones del hotel
                    List<Habitacion> habitaciones = hotelSeleccionado.getHabitaciones();

                    // Actualizar el ComboBox de habitaciones con las habitaciones del hotel
                    cbListarHabitaciones.getItems().clear(); // Limpiar las habitaciones anteriores
                    cbListarHabitaciones.getItems().addAll(habitaciones); // Agregar las habitaciones del hotel seleccionado
                    cbListarHabitaciones.setVisible(true); // Hacer visible el ComboBox para seleccionar la habitación
                } else {
                    // Si no es un hotel, ocultar el ComboBox
                   cbListarHabitaciones.setVisible(false);
                }
            } else {
                // Si no se ha seleccionado ninguna instalación, mostrar alerta
                controladorPrincipal.mostrarAlerta("Seleccione una instalación de la tabla para reservar", Alert.AlertType.WARNING);
            }
        });
        tablaHistorial.setOnMouseClicked(e -> {
            reservaSeleccionada = tablaHistorial.getSelectionModel().getSelectedItem();

            if (reservaSeleccionada != null) {
                controladorPrincipal.mostrarAlerta("Reserva Seleccionada: " + reservaSeleccionada.getAlojamiento(), Alert.AlertType.INFORMATION);
            } else {
                controladorPrincipal.mostrarAlerta("Seleccione una reserva para cancelarla", Alert.AlertType.WARNING);
            }
        });
        cbListarHabitaciones.setOnMouseClicked(e -> {
            habitacionSeleccionada = cbListarHabitaciones.getSelectionModel().getSelectedItem();

            if (reservaSeleccionada != null) {
                controladorPrincipal.mostrarAlerta("Habitación seleccionada: " + habitacionSeleccionada.getNumeroHabitacion(), Alert.AlertType.INFORMATION);
            } else {
                controladorPrincipal.mostrarAlerta("Seleccione una habitacion", Alert.AlertType.WARNING);
            }
        });
        //COLUMNAS DE LA TABLA HISTORIAL
        colAlojamiento.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue().getAlojamiento();
            if (alojamiento != null) {
                return new SimpleStringProperty(alojamiento.getNombreAlojamiento()); // Suponiendo que getNombreAlojamiento() es el nombre del alojamiento
            }
            return new SimpleStringProperty(""); // En caso de que sea nulo
        });

        // Aquí se configura el resto de las columnas (como colEstado, colFechaInicio, etc.)
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        colFechaInicio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaInicio()).asString());
        colFechaFin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaFin()).asString());
        colNumeroHuespedes.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumeroHuespedes()).asObject().asString());

        lbNombreUsuario.setText( usuarioActual.getNombreCompleto() + "   Bienvenido al panel de usuario de BookYourStayAlojamientos " );
        lbSaldoBilletera.setText( "Su monto actual es: " + usuarioActual.getBilleteraVirtual().getSaldo());
    }

    private void cargarAlojamientos() {
        alojamientosObservable.setAll(controladorPrincipal.listarAlojamientos());
        tablaBusqueda.setItems(alojamientosObservable);
    }

    @FXML
    void buscarAlojamientos(ActionEvent event) {
        try {
            // Obtener criterios de búsqueda
            String nombre = txtNombre.getText().isEmpty() ? null : txtNombre.getText();
            String ciudad = chooseCiudad.getValue();
            TipoAlojamiento tipo = comboBoxTipo.getValue(); // Sigue siendo del tipo enum
            String rangoSeleccionado = cbRangoPrecios.getValue();

            // Convertir el rango seleccionado en límites
            Double[] limitesPrecios = obtenerLimitesPrecio(rangoSeleccionado);
            Double precioMinCOP = limitesPrecios[0];
            Double precioMaxCOP = limitesPrecios[1];

            // Llamar al método en ReservaPrincipal
            List<Alojamiento> resultados = controladorPrincipal.buscarAlojamientos(
                    nombre, ciudad, tipo, precioMinCOP, precioMaxCOP
            );

            // Actualizar la tabla con los resultados
            tablaBusqueda.setItems(FXCollections.observableArrayList(resultados));

            // Mostrar mensaje si no hay resultados
            if (resultados.isEmpty()) {
                controladorPrincipal.mostrarAlerta("No se encontraron alojamientos que coincidan con los criterios.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        limpiarCampos();

    }
    private Double[] obtenerLimitesPrecio(String rango) {
        if (rango == null || rango.isEmpty()) {
            return new Double[]{null, null}; // Sin límites si no se seleccionó ningún rango
        }
        switch (rango) {
            case "Menos de $50,000":
                return new Double[]{0.0, 50000.0};
            case "$50,000 - $100,000":
                return new Double[]{50000.0, 100000.0};
            case "$100,000 - $200,000":
                return new Double[]{100000.0, 200000.0};
            case "$200,000 - $500,000":
                return new Double[]{200000.0, 500000.0};
            case "Más de $500,000":
                return new Double[]{500000.0, Double.MAX_VALUE};
            default:
                return new Double[]{null, null}; // Sin límites por defecto
        }
    }
    private void limpiarCampos(){

        txtNombre.clear();
        chooseCiudad.setValue(null);
        comboBoxTipo.setValue(null);
        cbRangoPrecios.setValue(null);
    }

    @FXML
    void cancelarReserva(ActionEvent event) {
        try {
            // Obtener la reserva seleccionada en la tabla
            Reserva reservaSeleccionada = tablaHistorial.getSelectionModel().getSelectedItem();

            // Verificar que una reserva ha sido seleccionada
            if (reservaSeleccionada == null) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione una reserva para cancelar.", Alert.AlertType.WARNING);
                return;
            }

            // Llamar al método cancelarReserva de la clase ReservaPrincipal
            controladorPrincipal.cancelarReserva(reservaSeleccionada);

            // Notificar al usuario que la reserva ha sido cancelada
            controladorPrincipal.mostrarAlerta("La reserva ha sido cancelada con éxito.", Alert.AlertType.INFORMATION);

            // Actualizar la tabla de reservas después de la cancelación
            reservasObservable.remove(reservaSeleccionada);

        } catch (Exception e) {
            // Manejar errores en caso de que no se pueda cancelar la reserva
            controladorPrincipal.mostrarAlerta("No se pudo cancelar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        controladorPrincipal.cerrarVentana(btCerrarSesion);
        controladorPrincipal.navegarVentana("/inicio.fxml", "Pantalla de inicio");
    }

    @FXML
    void hacerReserva(ActionEvent event) {
        try {
            // Verificar que se haya seleccionado una habitación si es un hotel
            if (alojamientoSeleccionado instanceof AlojamientoHotel) {
                Habitacion habitacionSeleccionada = cbListarHabitaciones.getSelectionModel().getSelectedItem();
                if (habitacionSeleccionada == null) {
                    controladorPrincipal.mostrarAlerta("Por favor seleccione una habitación para hacer la reserva.", Alert.AlertType.WARNING);
                    return;
                }
            }

            // Verificar que la fecha y el número de huéspedes estén completos
            LocalDate fechaInicio = dpFechaInicio.getValue();
            LocalDate fechaFin = dpFechaFin.getValue(); // Asegúrate de tener este campo para la fecha de fin
            int numeroHuespedes = Integer.parseInt(txtNumeroHuespedes.getText()); // El ComboBox para el número de huéspedes

            if (fechaInicio == null || fechaFin == null || numeroHuespedes <= 0) {
                controladorPrincipal.mostrarAlerta("Por favor complete todos los campos necesarios para la reserva", Alert.AlertType.WARNING);
                return;
            }

            // Intentar realizar la reserva (necesitas tener un método para crear reservas)
            controladorPrincipal.realizarReserva(usuarioActual, fechaInicio, fechaFin, alojamientoSeleccionado, numeroHuespedes, habitacionSeleccionada);
            // Notificar y mostrar mensaje de confirmación
            notificar();
            actualizarInterfazSaldo();
            controladorPrincipal.mostrarAlerta("Su reserva ha sido creada exitosamente. Verifique su correo para más detalles.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("No se pudo realizar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void mostrarAlojamientos(ActionEvent event) {
        List<Alojamiento> todosLosAlojamientos = controladorPrincipal.listarAlojamientos();

        // Actualiza la tabla con todos los contactos
        tablaBusqueda.setItems(FXCollections.observableArrayList(todosLosAlojamientos));
    }

    @FXML
    void recargarBilletera(ActionEvent event) {
        try {
            // Obtener el monto ingresado por el usuario en un campo de texto
            double monto = Double.parseDouble(txtMontoSaldo.getText());

            // Verificar si el monto es válido
            if (monto <= 0) {
                controladorPrincipal.mostrarAlerta("El monto debe ser mayor a cero.", Alert.AlertType.WARNING);
                return;
            }

            // Llamar al método de recarga de billetera de ReservaPrincipal
            controladorPrincipal.recargarBilletera(monto, usuarioActual);

            // Mostrar mensaje de éxito
            controladorPrincipal.mostrarAlerta("Billetera recargada con éxito. Nuevo saldo: $" + usuarioActual.getBilleteraVirtual().getSaldo(), Alert.AlertType.INFORMATION);

            // Actualizar la interfaz con el nuevo saldo
            actualizarInterfazSaldo();

        } catch (NumberFormatException e) {
            controladorPrincipal.mostrarAlerta("Por favor ingrese un monto válido (número).", Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Captura cualquier otra excepción generada por el método recargarBilletera
            controladorPrincipal.mostrarAlerta("No se pudo recargar la billetera: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    @FXML
    void actualizarDatos(){
        try {
            // Obtener los datos desde los campos del formulario
            String nuevoNombre = txtNombrePersona.getText(); // Nuevo nombre
            String nuevoTelefono = txtTelefono.getText(); // Nuevo teléfono
            String nuevoEmail = txtCorreo.getText(); // Nuevo email

            // Validaciones
            if (nuevoNombre.isBlank() || nuevoEmail.isBlank()) {
                throw new Exception("El nombre y el correo son obligatorios.");
            }

            // Llamar al método de editar cuenta usando la instancia personaActual
            boolean cuentaActualizada = controladorPrincipal.editarCuenta(usuarioActual, nuevoNombre, nuevoTelefono, nuevoEmail);

            if (cuentaActualizada) {
                // Informar al usuario que los datos han sido actualizados
                controladorPrincipal.mostrarAlerta("Los datos de su cuenta han sido actualizados correctamente.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error en caso de que algo falle
            controladorPrincipal.mostrarAlerta("Error, no se pudo actualizar la cuenta", Alert.AlertType.ERROR);
        }
    }
    @FXML
    void eliminarCuenta(){
        try {
            // Eliminar la cuenta directamente usando la instancia personaActual
            boolean cuentaEliminada = controladorPrincipal.eliminarCuenta(usuarioActual);

            if (cuentaEliminada) {
                // Informar al usuario que su cuenta ha sido eliminada correctamente
                controladorPrincipal.mostrarAlerta("Tu cuenta ha sido eliminada correctamente.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error en caso de que algo falle
            controladorPrincipal.mostrarAlerta("No se pudo eliminar la cuenta: ", Alert.AlertType.ERROR);
        }

    }
    private void actualizarInterfazSaldo() {
        // Suponiendo que tienes una etiqueta que muestra el saldo de la billetera
        lbSaldoBilletera.setText("Saldo actual: $" + usuarioActual.getBilleteraVirtual().getSaldo());
    }
    @Override
    public void notificar() {
        cargarReservas();
    }
    private void cargarReservas() {
        reservasObservable.setAll(controladorPrincipal.listarReservasPorPersona(usuarioActual.getCedula()));
        tablaBusqueda.setItems(alojamientosObservable);
    }
    private void cargarHabitaciones() {
        cbListarHabitaciones.setItems(FXCollections.observableArrayList(habitaciones));
    }
}
