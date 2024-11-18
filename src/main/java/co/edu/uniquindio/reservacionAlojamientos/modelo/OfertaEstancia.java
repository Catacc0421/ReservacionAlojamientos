package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OfertaEstancia {
    private float descuento;
    private int numeroDias;
}
