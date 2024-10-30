package co.edu.uniquindio.reservacionAlojamientos.modelo;

import co.edu.uniquindio.reservacionAlojamientos.modelo.enums.TipoAlojamiento;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor

public class Alojamiento {
    private String nombreAlojamiento;
    private String ciudad;
    private String descripcion;
    private File image;
    private float precioNoche;
    private String codigo;
    private int capacidadMaxima;
    private List<String> serviciosIncluidos;
    private ArrayList<Habitacion> habitaciones;
    private TipoAlojamiento tipoAlojamiento;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("            Detalles del Alojamiento\n");
        sb.append("====================================\n");
        sb.append(String.format("Nombre       : %s\n", nombreAlojamiento));
        sb.append(String.format("Ciudad       : %s\n", ciudad));
        sb.append(String.format("Descripción  : %s\n", descripcion));
        sb.append(String.format("Precio por noche:    : %s\n", precioNoche));
        sb.append(String.format("Capacidad máxima de personas   : %s\n", capacidadMaxima));
        sb.append(String.format("Servicios que incluye:        : %s\n", serviciosIncluidos));
        for (Habitacion habitacion : habitaciones) {
            sb.append(String.format("  - %s (Capacidad: %d, Precio: %.2f)\n", habitacion.getNumeroHabitacion(), habitacion.getCapacidad(), habitacion.getPrecio(), habitacion.getDescripcion()));
        }
        sb.append("====================================\n");
        return sb.toString();
}

}