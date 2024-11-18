package co.edu.uniquindio.reservacionAlojamientos.modelo.factory;

import co.edu.uniquindio.reservacionAlojamientos.modelo.Alojamiento;
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
public class AlojamientoApartamento extends Alojamiento {
    private double costoAseo;
    private double costoMantenimiento;
    private double precioNoche;
    private int capacidadMaxima;

    @Builder
    public AlojamientoApartamento(String id, String nombreAlojamiento, String ciudad, String descripcion, File image, List<TipoServicio> serviciosIncluidos, double costoAseo,double costoMantenimiento, double precioNoche, int capacidadMaxima, OfertaRangoFechas ofertaRangoFechas, OfertaEstancia ofertaEstancia) {
        super(id, nombreAlojamiento, ciudad, descripcion, image, serviciosIncluidos, ofertaEstancia, ofertaRangoFechas);
        this.costoAseo = costoAseo;
        this.costoMantenimiento = costoMantenimiento;
        this.precioNoche = precioNoche;
        this.capacidadMaxima = capacidadMaxima;
    }
}
