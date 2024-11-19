package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoServicio;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public abstract class Alojamiento {
    private String id;
    private String nombreAlojamiento;
    private String ciudad;
    private String descripcion;
    private File image;
    private List<TipoServicio> serviciosIncluidos;
    private OfertaEstancia ofertaEstancia;
    private OfertaRangoFechas ofertaRangoFechas;
    private List<Resena> resenas;

}