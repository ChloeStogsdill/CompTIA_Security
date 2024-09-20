import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Steganography_Encoder {
    private static File file;
    private static List<Integer> binaryMessage;

    public static void setFile(File file) {
        Steganography_Encoder.file = file;
    }

    public static void setBinaryMessage(List<Integer> binaryMessage) {
        Steganography_Encoder.binaryMessage = binaryMessage;
    }

    public static BufferedImage leastSignificantBitEncoder() throws IOException {
        if (file == null) {
            throw new IOException("File has not been set.");
        }

        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("Could not read the image file: " + file.getPath());
        }

        int messageIndex = 0;

        outerLoop:
        for (int imgWidth = 0; imgWidth < image.getWidth(); imgWidth++) {
            for (int imgHeight = 0; imgHeight < image.getHeight(); imgHeight++) {
                if (messageIndex >= binaryMessage.size()) {
                    break outerLoop;
                }

                int pixel = image.getRGB(imgWidth, imgHeight);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                if (messageIndex < binaryMessage.size()) {
                    red = (red & 0xFE) | binaryMessage.get(messageIndex);
                    messageIndex++;
                }
                if (messageIndex < binaryMessage.size()) {
                    green = (green & 0xFE) | binaryMessage.get(messageIndex);
                    messageIndex++;
                }
                if (messageIndex < binaryMessage.size()) {
                    blue = (blue & 0xFE) | binaryMessage.get(messageIndex);
                    messageIndex++;
                }

                pixel = (red << 16) | (green << 8) | blue;
                image.setRGB(imgWidth, imgHeight, pixel);
            }
        }

        return image;
    }

    public static List<Integer> convertStringToBinary(String message) {
        List<Integer> result = new ArrayList<>();
        for (char character : message.toCharArray()) {
            String binaryString = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');
            for (char bit : binaryString.toCharArray()) {
                result.add(bit - '0');
            }
        }

        // Add a delimiter to indicate the end of the message
        String endDelimiter = "00000011";
        for (char bit : endDelimiter.toCharArray()) {
            result.add(bit - '0');
        }

        return result;
    }
}
