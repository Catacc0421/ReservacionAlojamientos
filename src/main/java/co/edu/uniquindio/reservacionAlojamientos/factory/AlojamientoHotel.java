package co.edu.uniquindio.reservacionAlojamientos.factory;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.Habitacion;
import co.edu.uniquindio.reservacionAlojamientos.modelo.OfertaEstancia;
import co.edu.uniquindio.reservacionAlojamientos.modelo.OfertaRangoFechas;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
public class AlojamientoHotel extends Alojamiento {
    private List<Habitacion> habitaciones;

    @Builder
    public AlojamientoHotel(String id, String nombreAlojamiento, String ciudad, String descripcion, File image, List<TipoServicio> serviciosIncluidos, OfertaEstancia ofertaEstancia, OfertaRangoFechas ofertaRangoFechas) {
        super(id, nombreAlojamiento, ciudad, descripcion, image, serviciosIncluidos, ofertaEstancia, ofertaRangoFechas);
        this.habitaciones = habitaciones;
    }
}
