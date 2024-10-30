package co.edu.uniquindio.reservacionAlojamientos.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Sesion {

    @Getter
    @Setter
    private Usuario usuario;
    public static Sesion SESION;

    public static Sesion getInstanciaSesion(){
        if (SESION == null) {
            SESION = new Sesion();
        }
        return SESION;
    }
    public void cerrarSesion(){
        SESION = null;
    }


}
