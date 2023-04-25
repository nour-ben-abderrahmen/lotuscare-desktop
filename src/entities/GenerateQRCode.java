package entities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateQRCode {
    // static function that creates QR Code
    public static void generateQRcode(Produit p, String path) throws WriterException, IOException {
        // create the data string to encode in the QR code
        String data = p.getRef() + "\n"
                + p.getDescription() + "\n"
                + "Price: " + p.getPrix() + "\n"
                + "Pourcentage: " + p.getCategorie();

        // set the QR code parameters
        int width = 300;
        int height = 300;
        String charset = "UTF-8";
        Map<com.google.zxing.EncodeHintType, Object> hints = new HashMap<>();
        hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        // encode the data and create a buffered image from the bit matrix
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );
        BufferedImage image = toBufferedImage(matrix);

        // write the QR code image to the file
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }

    // function to convert a bit matrix to a buffered image
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
