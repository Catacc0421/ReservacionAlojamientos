package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class OfertaRangoFechas {
    private float porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
