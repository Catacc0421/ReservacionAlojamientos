package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<TipoAlojamiento> comboBoxTipo;
    @FXML
    private ChoiceBox<String> chooseCiudad;

    @FXML
    private TableView<Alojamiento> tablaBusqueda;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, String> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colDescripcion;

    @FXML
    private TableColumn<Alojamiento, File> colImagen;
    @FXML
    private TableColumn<Alojamiento, String> colServicios;
    @FXML
    private TableColumn<Alojamiento, String> colTipoAlojamiento;
    @FXML
    private Button buttonRegistrarse;
    @FXML
    private Button buttonIniciarSesion;

    private ObservableList<Alojamiento> alojamientosObservable;

    private final ControladorPrincipal controladorPrincipal;
    public InicioControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Asignar las propiedades de la nota a las columnas de la tabla
        colImagen.setCellFactory(col -> new ImageTableCell());
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAlojamiento()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colImagen.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getImage()));
        colServicios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiciosIncluidos().toString()));
        //colTipoAlojamiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoAlojamiento().toString()));

        //Cargar categorias en el ComboBox
        comboBoxTipo.setItems( FXCollections.observableList(controladorPrincipal.listarTiposAlojamientos()) );
        chooseCiudad.setItems (FXCollections.observableList(controladorPrincipal.listarCiudades()));

        //Inicializar lista observable y cargar las notas
        alojamientosObservable = FXCollections.observableArrayList();
        cargarAlojamientos();

    }

    private void cargarAlojamientos() {
        alojamientosObservable.setAll(controladorPrincipal.listarAlojamientos());
        tablaBusqueda.setItems(alojamientosObservable);
    }

    public void iniciarSesion() {
        controladorPrincipal.navegarVentana("/IniciarSesion.fxml", "Iniciar sesión");
        controladorPrincipal.cerrarVentana(buttonRegistrarse);
    }

    public void registrarse() {
        controladorPrincipal.navegarVentana("/Registro.fxml", "Registro");
        controladorPrincipal.cerrarVentana(buttonRegistrarse);
    }
    public void buscarAlojamientos() {
        String nombre = txtNombre.getText();
        String ciudad = chooseCiudad.getValue();
        TipoAlojamiento tipo = comboBoxTipo.getValue();

        List<Alojamiento> alojamientosFiltrados = controladorPrincipal.buscarAlojamientos(nombre, ciudad, tipo);
        tablaBusqueda.setItems(FXCollections.observableArrayList(alojamientosFiltrados));
        limpiarCampos();
    }


    private void limpiarCampos(){
        txtNombre.clear();
    }
}
