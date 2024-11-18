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

public class InicioControlador {


    @FXML
    private Button buttonRegistrarse;
    @FXML
    private Button buttonIniciarSesion;

    private final ControladorPrincipal controladorPrincipal;
    public InicioControlador() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }



    public void iniciarSesion() {
        controladorPrincipal.navegarVentana("/IniciarSesion.fxml", "Iniciar sesión");
        controladorPrincipal.cerrarVentana(buttonRegistrarse);
    }

    public void registrarse() {
        controladorPrincipal.navegarVentana("/Registro.fxml", "Registro");
        controladorPrincipal.cerrarVentana(buttonRegistrarse);
    }


}
