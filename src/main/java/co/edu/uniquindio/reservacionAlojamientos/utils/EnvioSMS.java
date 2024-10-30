package co.edu.uniquindio.reservacionAlojamientos.utils;

import co.edu.uniquindio.reservacionAlojamientos.servicios.Notificacion;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class EnvioSMS implements Notificacion {
    private String mensaje;
    private String numero;

    public EnvioSMS(String mensaje, String numero) {
        this.mensaje = mensaje;
        this.numero = numero;
    }

    public void crearConexion(){
        Twilio.init(
                "AC0926436149344dd4f57d722119e98071",
                "a76bfd80ddcedd5280d1195f219761a1");
    }

    @Override
    public void enviarNotificacion() {
        crearConexion();
        Message.creator(
                new PhoneNumber(numero),
                new PhoneNumber("+573002529878"),
                mensaje
        ).create();
    }
}
