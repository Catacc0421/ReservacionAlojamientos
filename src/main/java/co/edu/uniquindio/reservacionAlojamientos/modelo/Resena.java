package co.edu.uniquindio.reservacionAlojamientos.modelo;

import javafx.fxml.FXML;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Resena {
    private String comentario;
    private int calificacion;
    private Usuario usuario;
}
