package co.edu.uniquindio.reservacionAlojamientos.utils;


import co.edu.uniquindio.reservacionAlojamientos.servicios.Notificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
@Getter
@Setter

@AllArgsConstructor
public class EnvioEmail implements Notificacion {

    private String destinatario, asunto, mensaje;

    @Override
    public void enviarNotificacion() {
        Email email = EmailBuilder.startingBlank()
                .from("bookyourestayalojamientos@gmail.com")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "bookyourestayalojamientos@gmail.com", "c r d q b l j r r k h n g s h w")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
