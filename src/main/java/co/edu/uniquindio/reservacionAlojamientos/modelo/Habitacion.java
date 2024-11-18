package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.*;

import java.io.File;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor

public class Habitacion {
    private String numeroHabitacion;
    private double precio;
    private int capacidad;
}
