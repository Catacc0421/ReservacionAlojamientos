package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.OfertaRangoFechas;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PanelAdministradorControlador implements Initializable {


    //TAB GESTIONAR ALOJAMIENTOS

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private TextField txtCostoAdicional;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecioNoche;

    @FXML
    private ComboBox<?> cbCiudad;

    @FXML
    private ComboBox<?> cbServicios;

    @FXML
    private ComboBox<?> cbTipoAlojamiento;
    @FXML
    private TableView<Alojamiento> tablaAlojamientos;

    @FXML
    private TableColumn<Alojamiento, String> colCapacidadMaxima;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colCosto;

    @FXML
    private TableColumn<Alojamiento, String> colDescripción;

    @FXML
    private TableColumn<Alojamiento, File> colImagen;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, String> colPrecioNoche;

    @FXML
    private TableColumn<Alojamiento, String> colServicios;

    @FXML
    private TableColumn<Alojamiento, String> colTipoAlojamiento;

    //TAB OBTENER ESTADÍSTICAS
    @FXML
    private PieChart pieChartPopularidad;

    @FXML
    private PieChart pieChartRentabilidad;

    @FXML
    private BarChart<String, Number> barChartOcupacionGanancias;

    //TAB GESTIONAR OFERTAS
    @FXML
    private TableView<OfertaRangoFechas> tablaDescuentos;
    @FXML
    private TableColumn<OfertaRangoFechas, String> colAlojamientoOferta;
    @FXML
    private TableColumn<OfertaRangoFechas, String> colDescuento;

    @FXML
    private TableColumn<OfertaRangoFechas, String> colFinOferta;
    @FXML
    private TableColumn<OfertaRangoFechas, String> colTipoOferta;
    @FXML
    private TableColumn<OfertaRangoFechas, String> ColInicioOferta;
    @FXML
    private ComboBox<String> cbTipoAlojamientoOferta;

    @FXML
    private ComboBox<String> cbTipoOferta;


    @FXML
    private DatePicker dpFinOferta;

    @FXML
    private DatePicker dpInicioOferta;

    private final ControladorPrincipal controladorPrincipal;
    public PanelAdministradorControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    private Alojamiento AlojamientoSeleccionado;
    private ObservableList<Alojamiento> alojamientosObservable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    //MÉTODOS DEL TAB GESTIONAR ALOJAMIENTOS
    @FXML
    void crearAlojamiento(ActionEvent event) {

    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {

    }

    @FXML
    void modificarAlojamiento(ActionEvent event) {

    }
    @FXML
    void seleccionarImagen(ActionEvent event) {

    }
    //TAB CREAR OFERTAS
    @FXML
    void crearOfertaEspecial(ActionEvent event) {

    }
    //TAB OBTENER ESTADÍSTICAS
    @FXML
    void actualizarOcupacionGanancias(ActionEvent event) {

    }

    @FXML
    void actualizarPopularidadAlojamientos(ActionEvent event) {

    }

    @FXML
    void actualizarRentabilidadTipos(ActionEvent event) {

    }

}


