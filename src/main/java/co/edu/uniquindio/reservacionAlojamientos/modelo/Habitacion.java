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
    private float precio;
    private int capacidad;
    private File imagen;
    private String descripcion;
}
