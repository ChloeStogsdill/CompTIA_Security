import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("     _                                                     _           \n" +
                "    | |                                                   | |          \n" +
                " ___| |_ ___  __ _  __ _ _ __   ___   __ _ _ __ __ _ _ __ | |__  _   _ \n" +
                "/ __| __/ _ \\/ _` |/ _` | '_ \\ / _ \\ / _` | '__/ _` | '_ \\| '_ \\| | | |\n" +
                "\\__ | ||  __| (_| | (_| | | | | (_) | (_| | | | (_| | |_) | | | | |_| |\n" +
                "|___/\\__\\___|\\__, |\\__,_|_| |_|\\___/ \\__, |_|  \\__,_| .__/|_| |_|\\__, |\n" +
                "              __/ |                   __/ |         | |           __/ |\n" +
                "             |___/                   |___/          |_|          |___/ ");
        System.out.println("-----------------------------------------------------------------------");


        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Enter 'E' to encode, 'D' to decode, or 'X' to exit:");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "E":
                    encodeMessage(scanner);
                    break;
                case "D":
                    decodeMessage(scanner);
                    break;
                case "X":
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 'E' for encode, 'D' for decode, or 'X' to exit.");
                    break;
            }
        }

        scanner.close();
    }

    private static void encodeMessage(Scanner scanner) {
        try {
            System.out.print("Enter path to the input image file: ");
            String inputImagePath = scanner.nextLine().trim();
            File inputFile = new File(inputImagePath);

            if (!inputFile.exists()) {
                System.out.println("File not found: " + inputImagePath);
                return;
            }

            System.out.print("Enter message to encode: ");
            String message = scanner.nextLine();

            List<Integer> binaryMessage = Steganography_Encoder.convertStringToBinary(message);
            Steganography_Encoder.setFile(inputFile);
            Steganography_Encoder.setBinaryMessage(binaryMessage);

            File outputImageFile = new File("encoded_image.png");
            BufferedImage encodedImage = Steganography_Encoder.leastSignificantBitEncoder();
            ImageIO.write(encodedImage, "png", outputImageFile);
            System.out.println("Message encoded successfully to: " + outputImageFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private static void decodeMessage(Scanner scanner) {
        try {
            System.out.print("Enter path to the encoded image file: ");
            String encodedImagePath = scanner.nextLine().trim();
            File encodedImageFile = new File(encodedImagePath);

            if (!encodedImageFile.exists()) {
                System.out.println("File not found: " + encodedImagePath);
                return;
            }

            BufferedImage imageToDecode = ImageIO.read(encodedImageFile);
            String decodedMessage = Steganography_Decoder.leastSignificantBitDecoder(imageToDecode);
            System.out.println("Decoded Message: " + decodedMessage);

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
