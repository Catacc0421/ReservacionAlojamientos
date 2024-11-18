package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.OfertaEstancia;
import co.edu.uniquindio.reservacionAlojamientos.modelo.OfertaRangoFechas;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoApartamento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoCasa;
import co.edu.uniquindio.reservacionAlojamientos.modelo.factory.AlojamientoHotel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PanelAdministradorControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal;
    public PanelAdministradorControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
        this.habitaciones = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }
    //FILE CHOOSER
    @FXML
    private ImageView imagenSeleccionada;
    private File imagen;
    private List<Habitacion> habitaciones;
    private List<TipoServicio> servicios;
    private Alojamiento alojamientoSeleccionado;
    private Alojamiento ofertaSeleccionada;
    private ObservableList<Alojamiento> alojamientosObservable;
    private ObservableList<Alojamiento> ofertasObservable;



    //TAB GESTIONAR ALOJAMIENTOS
    @FXML
    private Button btCerrarSesion;
    @FXML
    private ComboBox<String> cbCiudad;
    @FXML
    private ComboBox<Habitacion> cbListaHabitaciones;
    @FXML
    private Button btImagen;
    @FXML
    private Button btAgregar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btAgregarHabitacion;
    @FXML
    private Button btEditarHabitacion;
    @FXML
    private ComboBox<TipoServicio> cbListaServicios;
    @FXML
    private ComboBox<TipoAlojamiento> cbTipoAlojamiento;
    @FXML
    private TableView<Alojamiento> tablaAlojamientos;
    @FXML
    private TableColumn<Alojamiento, String> colCapacidadMaxima;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colCostoAseo;

    @FXML
    private TableColumn<Alojamiento, String> colCostoMantenimiento;

    @FXML
    private TableColumn<Alojamiento, String> colDescripcion;

    @FXML
    private TableColumn<Alojamiento, String> colHabitaciones;

    @FXML
    private TableColumn<Alojamiento, File> colImagen;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, String> colPrecioNoche;

    @FXML
    private TableColumn<Alojamiento, String> colServiciosIncluidos;
    @FXML
    private TextField txtCapacidadHabitacion;

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private TextField txtCostoAseo;

    @FXML
    private TextField txtCostoMantenimiento;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtDescuentoOferta;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumeroHabitacion;

    @FXML
    private TextField txtPrecioHabitacion;

    @FXML
    private TextField txtPrecioNoche;

    @FXML
    private TextField txtServicio;

    //TAB OBTENER ESTADÍSTICAS

    @FXML
    private BarChart<String, Number> barChartAlojamientosPopulares;

    @FXML
    private BarChart<String, Number> barChartOcupacionGanancias;


    @FXML
    private ComboBox<String> cbCiudadEstadisticas;

    @FXML
    private ComboBox<String> cbListaAlojamientos;
    @FXML
    private PieChart pieChartRentabilidad;

    //TAB GESTIONAR OFERTAS

    @FXML
    private TableColumn<OfertaRangoFechas, String> ColInicioOferta;
    @FXML
    private TableColumn<OfertaRangoFechas, String> colDescuentoDias;
    @FXML
    private TableColumn<OfertaEstancia, String> colDescuentoEstancia;
    @FXML
    private TableView<Alojamiento> tablaDescuentos;


    @FXML
    private TableColumn<OfertaRangoFechas, String> colFinOferta;

    @FXML
    private ComboBox<String> cbTipoOferta;

    @FXML
    private DatePicker dpFinOferta;

    @FXML
    private DatePicker dpInicioOferta;
    @FXML
    private ComboBox<Alojamiento> cbAlojamientos;
    @FXML
    private TextField txtNumeroDias;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //GESTIONAR ALOJAMIENTOS
        //INICIALIZACION DE COLUMNAS DE LA TABLA ALOJAMIENTOS
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
        colCapacidadMaxima.setCellValueFactory(cellData -> {
            Alojamiento alojamiento = cellData.getValue();
            if (alojamiento instanceof AlojamientoCasa) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoCasa) alojamiento).getCapacidadMaxima()));
            } else if (alojamiento instanceof AlojamientoApartamento) {
                return new SimpleStringProperty(String.valueOf(((AlojamientoApartamento) alojamiento).getCapacidadMaxima()));
            } else {
                return new SimpleStringProperty("N/A"); // Si no tiene precio por noche, mostrar "N/A"
            }
        });
        colServiciosIncluidos.setCellValueFactory(cellData -> {
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
        alojamientosObservable = FXCollections.observableArrayList();
        tablaAlojamientos.setItems(alojamientosObservable);
        actualizarAlojamientos();
        //CARGAR CATEGORÍAS EN EL COMBOBOX
        cbTipoAlojamiento.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                configurarCamposSegunTipo(String.valueOf(newValue));
            }
        });
        cbTipoAlojamiento.setItems(FXCollections.observableList(controladorPrincipal.listarTiposAlojamientos()));
        cbCiudad.setItems(FXCollections.observableList(controladorPrincipal.listarCiudades()));
        cbListaHabitaciones.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                txtNumeroHabitacion.setText(newItem.getNumeroHabitacion());
                txtPrecioHabitacion.setText(String.valueOf(newItem.getPrecio()));
                txtCapacidadHabitacion.setText(String.valueOf(newItem.getCapacidad()));
            }
        });
        cbListaServicios.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                txtServicio.setText(newItem.getNombre());
            }
        });

        alojamientosObservable = FXCollections.observableArrayList();
        tablaAlojamientos.setItems(alojamientosObservable);
        actualizarAlojamientos();
// Evento click en la tabla
        tablaAlojamientos.setOnMouseClicked(e -> {
            // Obtener el alojamiento seleccionado
            alojamientoSeleccionado = tablaAlojamientos.getSelectionModel().getSelectedItem();

            // Si hay un alojamiento seleccionado
            if (alojamientoSeleccionado != null) {
                // Hacer visibles todos los campos comunes
                txtNombre.setVisible(true);
                cbCiudad.setVisible(true);
                txtDescripcion.setVisible(true);
                btImagen.setVisible(true);
                txtServicio.setVisible(true);
                btAgregar.setVisible(true);
                btEditar.setVisible(true);
                cbListaServicios.setVisible(true);

                // Llenar los campos comunes
                txtNombre.setText(alojamientoSeleccionado.getNombreAlojamiento());
                cbCiudad.setValue(alojamientoSeleccionado.getCiudad());
                txtDescripcion.setText(alojamientoSeleccionado.getDescripcion());
                txtServicio.setText(alojamientoSeleccionado.getServiciosIncluidos().toString());


                // Limpiar y ocultar todos los campos específicos inicialmente
                txtCostoAseo.setVisible(false);
                txtCostoMantenimiento.setVisible(false);
                txtNumeroHabitacion.setVisible(false);
                txtPrecioHabitacion.setVisible(false);
                txtCapacidadHabitacion.setVisible(false);
                cbListaHabitaciones.setVisible(false);
                btAgregarHabitacion.setVisible(false);
                btEditarHabitacion.setVisible(false);


                // Comprobar el tipo de alojamiento y mostrar los campos específicos
                if (alojamientoSeleccionado instanceof AlojamientoCasa) {
                    AlojamientoCasa casa = (AlojamientoCasa) alojamientoSeleccionado;
                    // Mostrar y llenar los campos específicos de casa
                    txtCostoAseo.setVisible(true);
                    txtCostoMantenimiento.setVisible(true);
                    txtCostoAseo.setText(String.valueOf(casa.getCostoAseo()));
                    txtCostoMantenimiento.setText(String.valueOf(casa.getCostoMantenimiento()));
                    btEditar.setVisible(true);
                    btAgregar.setVisible(true);


                } else if (alojamientoSeleccionado instanceof AlojamientoApartamento) {
                    AlojamientoApartamento apartamento = (AlojamientoApartamento) alojamientoSeleccionado;
                    // Mostrar y llenar los campos específicos de apartamento
                    txtCostoAseo.setVisible(true);
                    txtCostoMantenimiento.setVisible(true);
                    txtCostoAseo.setText(String.valueOf(apartamento.getCostoAseo()));
                    txtCostoMantenimiento.setText(String.valueOf(apartamento.getCostoMantenimiento()));
                    btEditar.setVisible(true);
                    btAgregar.setVisible(true);

                } else if (alojamientoSeleccionado instanceof AlojamientoHotel) {
                    AlojamientoHotel hotel = (AlojamientoHotel) alojamientoSeleccionado;
                    // Mostrar y llenar los campos específicos de hotel
                    txtNumeroHabitacion.setVisible(true);
                    txtPrecioHabitacion.setVisible(true);
                    txtCapacidadHabitacion.setVisible(true);
                    btAgregarHabitacion.setVisible(true);
                    btEditarHabitacion.setVisible(true);
                    cbListaHabitaciones.setVisible(true);

                    List<Habitacion> habitaciones = ((AlojamientoHotel) alojamientoSeleccionado).getHabitaciones();
                    if (!hotel.getHabitaciones().isEmpty()) {
                        Habitacion primeraHabitacion = hotel.getHabitaciones().get(0);
                        txtNumeroHabitacion.setText(primeraHabitacion.getNumeroHabitacion());
                        txtPrecioHabitacion.setText(String.valueOf(primeraHabitacion.getPrecio()));
                        txtCapacidadHabitacion.setText(String.valueOf(primeraHabitacion.getCapacidad()));
                        this.habitaciones = habitaciones;
                    }
                }
            } else {
                // Si no hay un alojamiento seleccionado, limpiar y ocultar todos los campos
                txtNombre.setVisible(false);
                cbCiudad.setVisible(false);
                txtDescripcion.setVisible(false);
                btImagen.setVisible(false);
                txtServicio.setVisible(false);
                txtCapacidadMaxima.setVisible(false);
                txtCostoAseo.setVisible(false);
                txtCostoMantenimiento.setVisible(false);
                txtNumeroHabitacion.setVisible(false);
                txtPrecioHabitacion.setVisible(false);
                txtCapacidadHabitacion.setVisible(false);
                cbListaServicios.setVisible(false);
                cbListaHabitaciones.setVisible(false);
                btEditarHabitacion.setVisible(false);
                btAgregarHabitacion.setVisible(false);
                btAgregar.setVisible(false);
                btEditar.setVisible(false);

                // Limpiar los valores de los campos
                txtNombre.clear();
                txtDescripcion.clear();
                txtCapacidadMaxima.clear();
                txtCostoAseo.clear();
                txtCostoMantenimiento.clear();
                txtNumeroHabitacion.clear();
                txtPrecioHabitacion.clear();
                txtCapacidadHabitacion.clear();
            }
        });
        ofertasObservable = FXCollections.observableArrayList();
        tablaDescuentos.setItems(alojamientosObservable);
        actualizarOfertas();
        //COLUMNAS TABLA GESTIONAR OFERTAS
        ColInicioOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaInicio())));
        colFinOferta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaFin())));
        colDescuentoEstancia.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDescuento())));
        colDescuentoDias.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPorcentajeDescuento())));

    }
    //Metodos para gestionar alojamientos
    @FXML
    void agregarAlojamiento(ActionEvent event) {
        try {
            // Verificar que se ha seleccionado una imagen
            File imagen = new File(btImagen.getText());
            if (imagen == null || !imagen.exists()) {
                throw new Exception("Debe seleccionar una imagen válida para el alojamiento.");
            }

            // Obtener el tipo de alojamiento seleccionado
            String tipoSeleccionado = String.valueOf(cbTipoAlojamiento.getValue());

            // Verificar que el tipo de alojamiento no sea vacío
            if (tipoSeleccionado.isEmpty()) {
                throw new Exception("Debe seleccionar un tipo de alojamiento.");
            }

            // Inicializar las variables comunes de alojamiento
            String nombre = txtNombre.getText();
            String ciudad = cbCiudad.getValue();
            String descripcion = txtDescripcion.getText();

            // Verificar que los campos obligatorios no estén vacíos
            if (nombre.isEmpty() || ciudad == null || descripcion.isEmpty()) {
                throw new Exception("Todos los campos obligatorios deben estar completos.");
            }

            // Validaciones comunes
            List<TipoServicio> servicios = new ArrayList<>();
            if(tipoSeleccionado.equals ("CASA") || tipoSeleccionado.equals("APARTAMENTO") || tipoSeleccionado.equals("HOTEL")){
                if (cbListaServicios.getItems().isEmpty()) {
                    throw new Exception("Debe agregar al menos un servicio para el alojamiento.");
                }

                // Agregar los servicios seleccionadas
                servicios.addAll(cbListaServicios.getItems());
            }

            // Variables específicas para casas y apartamentos
            double costoAseo = 0;
            double costoMantenimiento = 0;
            double precioPorNoche = 0;
            int capacidadMaxima = 0;

            // Solo para casas y apartamentos, se validan estos campos
            if (tipoSeleccionado.equals("CASA") || tipoSeleccionado.equals("APARTAMENTO")) {
                costoAseo = Double.parseDouble(txtCostoAseo.getText());
                costoMantenimiento = Double.parseDouble(txtCostoMantenimiento.getText());
                precioPorNoche = Double.parseDouble(txtPrecioNoche.getText());
                capacidadMaxima = Integer.parseInt(txtCapacidadMaxima.getText());
            }

            // Para casas y apartamentos, no se agregan habitaciones
            List<Habitacion> habitaciones = new ArrayList<>();

            // Solo para hoteles, pedimos las habitaciones
            if (tipoSeleccionado.equals("HOTEL")) {
                // Validar que se hayan seleccionado habitaciones para el hotel
                if (cbListaHabitaciones.getItems().isEmpty()) {
                    throw new Exception("Debe agregar al menos una habitación para el hotel.");
                }

                // Agregar las habitaciones seleccionadas
                habitaciones.addAll(cbListaHabitaciones.getItems());
            }

            // Llamar al método en ReservaPrincipal para crear el alojamiento
            boolean exito = controladorPrincipal.agregarAlojamiento(
                    tipoSeleccionado, nombre, ciudad, descripcion, servicios, imagen,
                    costoAseo, costoMantenimiento, precioPorNoche, capacidadMaxima, habitaciones
            );

            // Mostrar un mensaje según el resultado
            if (exito) {
                controladorPrincipal.mostrarAlerta("Alojamiento creado exitosamente.", Alert.AlertType.INFORMATION);
                actualizarAlojamientos();
            } else {
                controladorPrincipal.mostrarAlerta("No se pudo crear el alojamiento.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void actualizarAlojamientos() {
        alojamientosObservable.setAll(controladorPrincipal.listarAlojamientos());
    }
    private void configurarCamposSegunTipo(String tipoSeleccionado) {
        // Campos comunes siempre visibles
        txtNombre.setVisible(true);
        cbCiudad.setVisible(true);
        txtDescripcion.setVisible(true);
        btImagen.setVisible(true);
        txtServicio.setVisible(true);
        btAgregar.setVisible(true);
        btEditar.setVisible(true);
        cbListaServicios.setVisible(true);

        // Configuración específica para cada tipo
        if (tipoSeleccionado.equals("CASA") || tipoSeleccionado.equals("APARTAMENTO")) {
            // Mostrar campos específicos para CASA o APARTAMENTO
            txtCostoAseo.setVisible(true);
            txtCostoMantenimiento.setVisible(true);
            txtPrecioNoche.setVisible(true);
            txtCapacidadMaxima.setVisible(true);
            btAgregar.setVisible(true);
            btEditar.setVisible(true);
            txtServicio.setVisible(true);

            // Ocultar campos no relacionados con CASA o APARTAMENTO
            txtNumeroHabitacion.setVisible(false);
            txtPrecioHabitacion.setVisible(false);
            txtCapacidadHabitacion.setVisible(false);
            btAgregarHabitacion.setVisible(false);
            btEditarHabitacion.setVisible(false);
            cbListaHabitaciones.setVisible(false);

        } else if (tipoSeleccionado.equals("HOTEL")) {
            // Mostrar campos específicos para HOTEL
            txtNumeroHabitacion.setVisible(true);
            txtPrecioHabitacion.setVisible(true);
            txtCapacidadHabitacion.setVisible(true);
            btAgregarHabitacion.setVisible(true);
            btEditarHabitacion.setVisible(true);
            cbListaHabitaciones.setVisible(true);

            // Ocultar campos no relacionados con HOTEL
            txtCostoAseo.setVisible(false);
            txtCostoMantenimiento.setVisible(false);
            txtPrecioNoche.setVisible(false);
            txtCapacidadMaxima.setVisible(false);


        } else {
            // Si no se selecciona un tipo válido, ocultar campos específicos
            txtCostoAseo.setVisible(false);
            txtCostoMantenimiento.setVisible(false);
            txtPrecioNoche.setVisible(false);
            txtCapacidadMaxima.setVisible(false);
            txtNumeroHabitacion.setVisible(false);
            txtPrecioHabitacion.setVisible(false);
            txtCapacidadHabitacion.setVisible(false);
            btAgregarHabitacion.setVisible(false);
            btEditarHabitacion.setVisible(false);
            cbListaHabitaciones.setVisible(false);
        }
    }

    @FXML
    void agregarHabitacion(ActionEvent event) {
        String numeroHabitacion = txtNumeroHabitacion.getText();
        String precioHabitacion = txtPrecioHabitacion.getText();
        String capacidadHabitacion = txtCapacidadHabitacion.getText();

        Habitacion habitacion = new Habitacion(
                numeroHabitacion,
                Float.parseFloat(precioHabitacion),
                Integer.parseInt(capacidadHabitacion)
        );

        habitaciones.add(habitacion);
        cbListaHabitaciones.setItems(FXCollections.observableArrayList(habitaciones));

    }
    @FXML
    void editarHabitación(ActionEvent event) {
        // Verifica que haya una habitación seleccionada en el ComboBox
        Habitacion habitacionSeleccionada = cbListaHabitaciones.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada != null) {
            try {
                // Obtener los valores nuevos desde los campos de texto
                String nuevoNumeroHabitacion = txtNumeroHabitacion.getText();
                String nuevoPrecioHabitacion = txtPrecioHabitacion.getText();
                String nuevaCapacidadHabitacion = txtCapacidadHabitacion.getText();

                // Actualizar el horario seleccionado con los nuevos valores
                habitacionSeleccionada.setNumeroHabitacion(nuevoNumeroHabitacion);
                habitacionSeleccionada.setPrecio(Float.parseFloat(nuevoPrecioHabitacion));
                habitacionSeleccionada.setCapacidad(Integer.parseInt(nuevaCapacidadHabitacion));

                // Actualizar la lista de horarios en el ComboBox y la vista
                cbListaHabitaciones.setItems(FXCollections.observableArrayList(habitaciones));
                cbListaHabitaciones.getSelectionModel().select(habitacionSeleccionada); // Seleccionar el horario editado
                controladorPrincipal.mostrarAlerta("Habitación editada con éxito", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                controladorPrincipal.mostrarAlerta("Error al editar la habitación: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            controladorPrincipal.mostrarAlerta("Debe seleccionar una habitación para editar", Alert.AlertType.WARNING);
        }
    }
    @FXML
    void agregarServicio(ActionEvent event) {
        String nuevoServicio = txtServicio.getText();
        if (nuevoServicio.isEmpty()) {
            controladorPrincipal.mostrarAlerta("Por favor, introduzca un servicio válido.", Alert.AlertType.INFORMATION);
        }
        // Validar y agregar el servicio a la lista global (o del alojamiento específico)
            servicios.add(TipoServicio.valueOf(nuevoServicio));

            // Actualizar la interfaz si es necesario mostrar los servicios
            cbListaServicios.setItems(FXCollections.observableArrayList(servicios));
            controladorPrincipal.mostrarAlerta("Servicio agregado", Alert.AlertType.INFORMATION);

            // Limpiar el campo de texto
            txtServicio.clear();
    }
    @FXML
    void editarServicio(ActionEvent event) {
        TipoServicio servicioSeleccionado = cbListaServicios.getSelectionModel().getSelectedItem();

        if (servicioSeleccionado != null) {
            try {
                String nuevoServicio = txtServicio.getText().trim();

                if (nuevoServicio.isEmpty()) {
                    controladorPrincipal.mostrarAlerta("Por favor, introduzca un servicio válido.", Alert.AlertType.INFORMATION);
                    return;
                }

                TipoServicio nuevoTipoServicio = TipoServicio.valueOf(nuevoServicio);
                if (!servicios.contains(nuevoTipoServicio)) {
                    int indice = servicios.indexOf(servicioSeleccionado);
                    servicios.set(indice, nuevoTipoServicio);

                    cbListaServicios.setItems(FXCollections.observableArrayList(servicios));
                    cbListaServicios.getSelectionModel().select(nuevoTipoServicio);
                    txtServicio.clear();
                    controladorPrincipal.mostrarAlerta("Servicio editado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    controladorPrincipal.mostrarAlerta("El servicio ya existe en la lista.", Alert.AlertType.WARNING);
                }
            } catch (IllegalArgumentException e) {
                controladorPrincipal.mostrarAlerta("El servicio no es válido. Verifique la entrada.", Alert.AlertType.ERROR);
            }
        } else {
            controladorPrincipal.mostrarAlerta("Debe seleccionar un servicio para editar", Alert.AlertType.WARNING);
        }
    }
    public void actualizarOfertas() {
        ofertasObservable.setAll(controladorPrincipal.listarAlojamientos());
    }

    @FXML
    void agregarImagen(ActionEvent event) {
        try {
            // Abrir un cuadro de diálogo para seleccionar una imagen
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File file = fileChooser.showOpenDialog(null);

            // Comprobar si se seleccionó un archivo
            if (file != null) {
                // Asignar la imagen seleccionada al ImageView
                Image image = new Image(file.toURI().toString());

                // Comprobar si imagenSeleccionada (ImageView) no es null
                if (imagenSeleccionada != null) {
                    imagenSeleccionada.setImage(image);
                } else {
                    // Si imagenSeleccionada es null, lanzamos una alerta o manejamos el error adecuadamente
                    controladorPrincipal.mostrarAlerta("Error: No se ha inicializado correctamente el ImageView.", Alert.AlertType.ERROR);
                }

                // Guardar la ruta de la imagen (por ejemplo, para utilizarla en la creación del alojamiento)
                btImagen.setText(file.getPath());  // Suponiendo que btImagen es un TextField
            } else {
                controladorPrincipal.mostrarAlerta("No se seleccionó ninguna imagen.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error al agregar la imagen: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void editarAlojamiento(ActionEvent event) {
        try {
            // Verificar si se ha seleccionado un alojamiento de la tabla
            if (alojamientoSeleccionado == null) {
                throw new Exception("Debe seleccionar un alojamiento para editar.");
            }

            // Obtener el tipo de alojamiento del alojamiento seleccionado
            String tipoSeleccionado = String.valueOf(cbTipoAlojamiento.getValue());

            // Cargar los datos del alojamiento seleccionado en los campos de texto
            txtNombre.setText(alojamientoSeleccionado.getNombreAlojamiento());
            cbCiudad.setValue(alojamientoSeleccionado.getCiudad());
            txtDescripcion.setText(alojamientoSeleccionado.getDescripcion());

            // Validar que los servicios ya estén seleccionados
            List<TipoServicio> serviciosSeleccionados = alojamientoSeleccionado.getServiciosIncluidos();
            cbListaServicios.getItems().clear();
            cbListaServicios.getItems().addAll(serviciosSeleccionados);

            // Cargar los atributos específicos del alojamiento según el tipo
            double costoAseo = 0;
            double costoMantenimiento = 0;
            double precioPorNoche = 0;
            int capacidadMaxima = 0;
            List<Habitacion> habitaciones = new ArrayList<>();

            if (alojamientoSeleccionado instanceof AlojamientoCasa || alojamientoSeleccionado instanceof AlojamientoApartamento) {
                // Cargar atributos específicos de Casa o Apartamento
                if (alojamientoSeleccionado instanceof AlojamientoCasa) {
                    AlojamientoCasa alojamientoCasa = (AlojamientoCasa) alojamientoSeleccionado;
                    costoAseo = alojamientoCasa.getCostoAseo();
                    costoMantenimiento = alojamientoCasa.getCostoMantenimiento();
                    precioPorNoche = alojamientoCasa.getPrecioNoche();
                    capacidadMaxima = alojamientoCasa.getCapacidadMaxima();
                } else if (alojamientoSeleccionado instanceof AlojamientoApartamento) {
                    AlojamientoApartamento alojamientoApartamento = (AlojamientoApartamento) alojamientoSeleccionado;
                    costoAseo = alojamientoApartamento.getCostoAseo();
                    costoMantenimiento = alojamientoApartamento.getCostoMantenimiento();
                    precioPorNoche = alojamientoApartamento.getPrecioNoche();
                    capacidadMaxima = alojamientoApartamento.getCapacidadMaxima();
                }

                // Mostrar los valores en los campos correspondientes
                txtCostoAseo.setText(String.valueOf(costoAseo));
                txtCostoMantenimiento.setText(String.valueOf(costoMantenimiento));
                txtPrecioNoche.setText(String.valueOf(precioPorNoche));
                txtCapacidadMaxima.setText(String.valueOf(capacidadMaxima));

            } else if (alojamientoSeleccionado instanceof AlojamientoHotel) {
                // Cargar atributos específicos de Hotel
                AlojamientoHotel alojamientoHotel = (AlojamientoHotel) alojamientoSeleccionado;
                habitaciones = alojamientoHotel.getHabitaciones();
                cbListaHabitaciones.getItems().clear();
                cbListaHabitaciones.getItems().addAll(habitaciones);
            }

            // Llamar al método en ReservaPrincipal para editar el alojamiento
            boolean exito = controladorPrincipal.editarAlojamiento(
                    alojamientoSeleccionado.getId(), tipoSeleccionado, txtNombre.getText(), cbCiudad.getValue(),
                    txtDescripcion.getText(), new ArrayList<>(cbListaServicios.getItems()),
                    new File(btImagen.getText()), costoAseo, costoMantenimiento,
                    Double.parseDouble(txtPrecioNoche.getText()), Integer.parseInt(txtCapacidadMaxima.getText()),
                    habitaciones
            );

            // Mostrar un mensaje según el resultado
            if (exito) {
                limpiarCampos();
                actualizarAlojamientos();
                controladorPrincipal.mostrarAlerta("Alojamiento editado exitosamente.", Alert.AlertType.INFORMATION);
            } else {
                controladorPrincipal.mostrarAlerta("No se pudo editar el alojamiento.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {
        try {
            // Verificar que haya una instalación seleccionada en la tabla
            Alojamiento alojamientoSeleccionado = cbAlojamientos.getSelectionModel().getSelectedItem();

            if (alojamientoSeleccionado == null) {
                controladorPrincipal.mostrarAlerta("Debe seleccionar una instalación para eliminar", Alert.AlertType.WARNING);
                return;
            }

            // Confirmar la eliminación antes de proceder
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar esta instalación?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                controladorPrincipal.eliminarAlojamiento(alojamientoSeleccionado.getId());
                limpiarCampos();
                actualizarAlojamientos();
                controladorPrincipal.mostrarAlerta("Instalación eliminada con éxito", Alert.AlertType.INFORMATION);
                //tablaInstalaciones.setItems(FXCollections.observableArrayList(controladorPrincipal.listarInstalaciones()));
            }

        } catch (Exception ex) {
            controladorPrincipal.mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }

//Metodos para obtener estadisticas
    @FXML
    void actualizarOcupacionGanancias(ActionEvent event) {

    }

    @FXML
    void actualizarPopularidadAlojamientos(ActionEvent event) {

    }

    @FXML
    void actualizarRentabilidadTipos(ActionEvent event) {

    }

    //Métodos para gestionar descuentos
    @FXML
    void agregarOferta(ActionEvent event) {
        try {
            // Verificar que se haya seleccionado un tipo de oferta
            String tipoOferta = cbTipoOferta.getValue(); // ComboBox con opciones "ESTANCIA" y "RANGO_FECHAS"
            if (tipoOferta == null || tipoOferta.isEmpty()) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione el tipo de oferta a agregar.", Alert.AlertType.WARNING);
                return;
            }

            // Crear la oferta según el tipo seleccionado
            Object oferta = null;
            if (tipoOferta.equalsIgnoreCase("ESTANCIA")) {
                // Obtener datos para OfertaEstancia
                float descuento = Float.parseFloat(txtDescuentoOferta.getText());
                int numeroDias = Integer.parseInt(txtNumeroDias.getText());
                oferta = new OfertaEstancia(descuento, numeroDias);
            } else if (tipoOferta.equalsIgnoreCase("RANGO_FECHAS")) {
                // Obtener datos para OfertaRangoFechas
                float porcentajeDescuento = Float.parseFloat(txtDescuentoOferta.getText());
                LocalDate fechaInicio = dpInicioOferta.getValue();
                LocalDate fechaFin = dpFinOferta.getValue();

                // Validar fechas
                if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
                    controladorPrincipal.mostrarAlerta("Por favor introduzca un rango de fechas válido.", Alert.AlertType.WARNING);
                    return;
                }

                oferta = new OfertaRangoFechas(porcentajeDescuento, fechaInicio, fechaFin);
            }

            // Validar que haya un alojamiento seleccionado
            if (alojamientoSeleccionado == null) {
                controladorPrincipal.mostrarAlerta("Debe seleccionar un alojamiento para agregar una oferta.", Alert.AlertType.WARNING);
                return;
            }

            // Llamar al método de ReservaPrincipal para agregar la oferta
            controladorPrincipal.agregarOfertaEspecial(alojamientoSeleccionado, oferta);

            // Mostrar mensaje de éxito
            controladorPrincipal.mostrarAlerta("Oferta agregada con éxito.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            controladorPrincipal.mostrarAlerta("Por favor introduzca valores válidos para los campos numéricos.", Alert.AlertType.WARNING);
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error al agregar la oferta: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void editarOferta(ActionEvent event) {
        try {
            // Verificar que se haya seleccionado un alojamiento
            if (alojamientoSeleccionado == null) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione un alojamiento para editar una oferta.", Alert.AlertType.WARNING);
                return;
            }

            // Obtener el tipo de oferta a editar
            String tipoOferta = cbTipoOferta.getValue();
            if (tipoOferta == null || tipoOferta.isEmpty()) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione el tipo de oferta a editar.", Alert.AlertType.WARNING);
                return;
            }

            // Crear la nueva oferta según el tipo
            if (tipoOferta.equalsIgnoreCase("ESTANCIA")) {
                float descuento = Float.parseFloat(txtDescuentoOferta.getText());
                int numeroDias = Integer.parseInt(txtNumeroDias.getText());
                OfertaEstancia nuevaOferta = new OfertaEstancia(descuento, numeroDias);

                controladorPrincipal.editarOferta(alojamientoSeleccionado, "ESTANCIA", nuevaOferta);

            } else if (tipoOferta.equalsIgnoreCase("RANGO_FECHAS")) {
                float porcentajeDescuento = Float.parseFloat(txtDescuentoOferta.getText());
                LocalDate fechaInicio = dpInicioOferta.getValue();
                LocalDate fechaFin = dpFinOferta.getValue();

                if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
                    throw new IllegalArgumentException("Las fechas de la oferta no son válidas.");
                }

                OfertaRangoFechas nuevaOferta = new OfertaRangoFechas(porcentajeDescuento, fechaInicio, fechaFin);

                controladorPrincipal.editarOferta(alojamientoSeleccionado, "RANGO_FECHAS", nuevaOferta);
            }

            controladorPrincipal.mostrarAlerta("Oferta editada con éxito.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error al editar la oferta: " + e.getMessage(), Alert.AlertType.ERROR);
        }


    }
    @FXML
    void eliminarOferta(ActionEvent event) {
        try {
            // Verificar que se haya seleccionado un alojamiento
            if (ofertaSeleccionada == null) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione un alojamiento para eliminar una oferta.", Alert.AlertType.WARNING);
                return;
            }

            // Obtener el tipo de oferta a eliminar
            String tipoOferta = cbTipoOferta.getValue(); // Supongamos que este ComboBox contiene "ESTANCIA" o "RANGO_FECHAS"
            if (tipoOferta == null || tipoOferta.isEmpty()) {
                controladorPrincipal.mostrarAlerta("Por favor seleccione el tipo de oferta a eliminar.", Alert.AlertType.WARNING);
                return;
            }

            // Llamar al método de ReservaPrincipal
            boolean exito = controladorPrincipal.eliminarOferta(alojamientoSeleccionado, tipoOferta);

            if (exito) {
                controladorPrincipal.mostrarAlerta("Oferta eliminada con éxito.", Alert.AlertType.INFORMATION);
            } else {
                controladorPrincipal.mostrarAlerta("No se encontró una oferta para eliminar.", Alert.AlertType.WARNING);
            }

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error al eliminar la oferta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    void cerrarSesion(ActionEvent event){
        controladorPrincipal.cerrarVentana(btCerrarSesion);
        controladorPrincipal.navegarVentana("/inicio.fxml", "Pantalla de inicio");

    }
    private void limpiarCampos(){
        txtNombre.clear();
        cbCiudad.setValue(null);
        txtDescripcion.clear();
        txtServicio.clear();
        txtCostoAseo.clear();
        txtCostoMantenimiento.clear();
        cbListaServicios.setValue(null);
        txtPrecioNoche.clear();
        txtCapacidadHabitacion.clear();
        txtPrecioHabitacion.clear();
        txtNumeroHabitacion.clear();
        cbListaHabitaciones.setValue(null);

    }
}


