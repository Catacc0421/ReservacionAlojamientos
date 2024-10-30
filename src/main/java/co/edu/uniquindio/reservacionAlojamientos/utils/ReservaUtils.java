package co.edu.uniquindio.reservacionAlojamientos.utils;

import java.util.UUID;

public class ReservaUtils {

    public String generarCodigoEvento(String prefijo){
        UUID uuid = UUID.randomUUID();
        String codigo = prefijo + uuid.toString();
        return codigo;
    }
}
