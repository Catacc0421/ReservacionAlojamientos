package co.edu.uniquindio.reservacionAlojamientos.servicios;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;

import java.util.ArrayList;
import java.util.List;

public interface CreacionAlojamiento {
    Alojamiento crearAlojamiento (String nombreAlojamiento, String ciudad, String descripcion, TipoAlojamiento tipoAlojamiento, float precioNoche, int capacidadMaxima, ArrayList<Habitacion> habitaciones, List<String> serviciosIncluidos) throws Exception;

    }
