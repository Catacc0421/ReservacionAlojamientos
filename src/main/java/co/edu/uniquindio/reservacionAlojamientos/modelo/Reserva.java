package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Reserva {
    private String id;
    private Usuario usuario;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroHuespedes;
    private String estado;
    private Factura factura;
}
