package co.edu.uniquindio.reservacionAlojamientos.modelo.enums;

import lombok.Getter;

@Getter
public enum TipoServicio {
    WIFI("WIFI"),
    PISCINA("PISCINA"),
    DESAYUNO("DESAYUNO"),
    ESTACIONAMIENTO("ESTACIONAMIENTO"),
    RESTAURANTE_BAR("RESTAURANTEBAR"),
    TELEVISION("TELEVISION");


    private final String nombre;

    TipoServicio(String nombre){
        this.nombre = nombre;
    }



}
