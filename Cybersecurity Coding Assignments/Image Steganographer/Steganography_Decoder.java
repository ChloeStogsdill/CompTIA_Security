import java.awt.image.BufferedImage;

public class Steganography_Decoder {

    public static String leastSignificantBitDecoder(BufferedImage image) {
        StringBuilder result = new StringBuilder();
        for (int imgWidth = 0; imgWidth < image.getWidth(); imgWidth++) {
            for (int imgHeight = 0; imgHeight < image.getHeight(); imgHeight++) {
                int pixel = image.getRGB(imgWidth, imgHeight);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                result.append(red & 0x01);
                result.append(green & 0x01);
                result.append(blue & 0x01);
            }
        }

        return convertToWords(result.toString());
    }

    private static String convertToWords(String binaryString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= binaryString.length() - 8; i += 8) {
            String byteString = binaryString.substring(i, i + 8);
            char letter = (char) Integer.parseInt(byteString, 2);

            // Check for the end delimiter
            if (letter == 3) { // ASCII for "ETX" (End of Text)
                break;
            }

            result.append(letter);
        }
        return result.toString();
    }
}
