package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
@AllArgsConstructor
public class CodigoVerificacion {
    private String correo;
    private String codigo;
}
