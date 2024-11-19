package co.edu.uniquindio.reservacionAlojamientos.modelo.factory;

import co.edu.uniquindio.reservacionAlojamientos.modelo.*;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;


@Setter
public class AlojamientoHotel extends Alojamiento {
    @Getter
    private List<Habitacion> habitaciones;

    @Builder
    public AlojamientoHotel(String id, String nombreAlojamiento, String ciudad, String descripcion, File image, List<TipoServicio> serviciosIncluidos, OfertaEstancia ofertaEstancia, OfertaRangoFechas ofertaRangoFechas, List<Habitacion> habitaciones, List<Resena> resenas) {
        super(id, nombreAlojamiento, ciudad, descripcion, image, serviciosIncluidos, ofertaEstancia, ofertaRangoFechas, resenas);
        this.habitaciones = habitaciones;
    }
}
