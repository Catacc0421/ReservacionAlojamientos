package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.ImageTableCell;
import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> comboBoxTipo;
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

    private final ReservaPrincipal reservaPrincipal;
    public InicioControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
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
        colTipoAlojamiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoAlojamiento().toString()));

        //Cargar categorias en el ComboBox
        comboBoxTipo.setItems( FXCollections.observableList(reservaPrincipal.listarTiposAlojamientos()) );
        chooseCiudad.setItems (FXCollections.observableList(reservaPrincipal.listarCiudades()));

        //Inicializar lista observable y cargar las notas
        alojamientosObservable = FXCollections.observableArrayList();
        cargarAlojamientos();

    }

    private void cargarAlojamientos() {
        alojamientosObservable.setAll(reservaPrincipal.listarAlojamientos());
        tablaBusqueda.setItems(alojamientosObservable);
    }

    public void iniciarSesion() {
        navegarVentana("/IniciarSesion.fxml", "Iniciar sesión");
        cerrarVentana(buttonRegistrarse);
    }

    public void registrarse() {
        navegarVentana("/Registro.fxml", "Registro");
        cerrarVentana(buttonRegistrarse);
    }

    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    public void buscarAlojamientos() {
        String nombre = txtNombre.getText();
        String ciudad = chooseCiudad.getValue();
        String tipo = comboBoxTipo.getValue();

        List<Alojamiento> alojamientosFiltrados = reservaPrincipal.buscarAlojamientos(nombre, ciudad, tipo);
        tablaBusqueda.setItems(FXCollections.observableArrayList(alojamientosFiltrados));
        limpiarCampos();
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();
            return loader;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void limpiarCampos(){
        txtNombre.clear();
    }
}
