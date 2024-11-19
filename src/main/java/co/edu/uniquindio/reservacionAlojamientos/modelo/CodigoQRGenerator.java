package co.edu.uniquindio.reservacionAlojamientos.modelo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CodigoQRGenerator {
    public static BufferedImage generarCodigoQR(String datos) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(datos, BarcodeFormat.QR_CODE, 300, 300, hints);

        // Convertir la matriz de bits a una imagen
        BufferedImage imagenQR = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                imagenQR.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);  // Negro y blanco
            }
        }
        return imagenQR;
    }

    public static byte[] obtenerImagenComoBytes(BufferedImage imagen) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "PNG", baos);
        return baos.toByteArray();
    }
}
