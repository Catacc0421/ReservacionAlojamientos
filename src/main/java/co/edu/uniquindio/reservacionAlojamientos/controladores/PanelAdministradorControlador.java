package co.edu.uniquindio.reservacionAlojamientos.controladores;

import co.edu.uniquindio.reservacionAlojamientos.modelo.ReservaPrincipal;

public class PanelAdministradorControlador {
    private final ReservaPrincipal reservaPrincipal;
    public PanelAdministradorControlador(){
        reservaPrincipal = ReservaPrincipal.getInstancia();
    }
}
