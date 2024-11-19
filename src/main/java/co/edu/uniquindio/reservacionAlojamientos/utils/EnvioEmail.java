package co.edu.uniquindio.reservacionAlojamientos.utils;


import co.edu.uniquindio.reservacionAlojamientos.servicios.Notificacion;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EnvioEmail implements Notificacion {

    private String destinatario, asunto, mensaje;

    @Override
    public void enviarNotificacion() {
            // Lógica de envío de correo sin adjuntos
            Email email = EmailBuilder.startingBlank()
                    .from("bookyourestayalojamientos@gmail.com")
                    .to(destinatario)
                    .withSubject(asunto)
                    .withPlainText(mensaje)
                    .buildEmail();

            try (Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "bookyourestayalojamientos@gmail.com", "vyse kdus pcij haap")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer()) {
                mailer.sendMail(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Método para enviar notificación con archivo adjunto
        public void enviarNotificacionConAdjunto(byte[] archivoAdjunto) {
            // Crear el objeto Email con el contenido y los datos de adjunto
            Email email = EmailBuilder.startingBlank()
                    .from("bookyourestayalojamientos@gmail.com")
                    .to(destinatario)
                    .withSubject(asunto)
                    .withPlainText(mensaje)
                    .withAttachment("codigo_qr.png", new ByteArrayDataSource(archivoAdjunto, "image/png"))
                    .buildEmail();

            try (Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "bookyourestayalojamientos@gmail.com", "vyse kdus pcij haap")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer()) {
                mailer.sendMail(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
