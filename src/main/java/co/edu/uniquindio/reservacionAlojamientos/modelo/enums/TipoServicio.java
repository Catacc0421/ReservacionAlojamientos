package co.edu.uniquindio.reservacionAlojamientos.modelo.enums;

import lombok.Getter;

@Getter
public enum TipoServicio {
    WIFI("Wifi"),
    PISCINA("Piscina"),
    DESAYUNO("Desayuno"),
    ESTACIONAMIENTO("Estacionamiento"),
    RESTAURANTE_BAR("Restaurante Bar"),
    TELEVISION("Televisión");


    private final String nombre;

    TipoServicio(String nombre){
        this.nombre = nombre;
    }



}
