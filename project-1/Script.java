import main.ZipCopy;

import java.io.*;
import java.math.BigDecimal;

/**
 * Script that performs compression on different files using a modified version of Zip.java that has takes a buffer
 * length, a file name, and a file extension as arguments. The main method should have the additional
 * lines:
 *
    BUFFER_LENGTH = Integer.valueOf(args[0]);
    FILE_NAME = args[1];
    ZIP_NAME = FILE_NAME + ".zip";
    FILE_TO_COMPRESS = FILE_NAME + args[2];
 *
 * NOTE:
 *      The script ONLY COMPRESSES the files. It does not guarantee that the compression was correct. Please double
 *      check that Zip is running correctly before running the script.
 */
public class Script {
    public static void main(String[] args) {
        // List of buffer sizes to try
        String[] bufferSizes = {"200", "500", "1000", "1500", "2000"};
        // List of files to ZIP
        // It is HIGHLY recommended that the files listed below are placed in the same directory as the script, unless
        // you are familiar with changing working directories.
        String[] files = {"importantpicture.jpg", "constant-values.html", "Alphabet.txt", "WarAndPeace.txt"};
        try {
            checkFiles(files);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

        for (String fileName : files) {
            warmup(bufferSizes, fileName);
            runZIP(bufferSizes, name(fileName), extension(fileName));
        }
    }

    /*
     * Requires the `main` method of Zip.java to be modified to take parameters
     *
     * The output file is a Markdown file with a formatted table. This makes it easy to convert it into a CSV as well.
     * The data files will also be overwritten every time the evaluation script is ran.
     */
    public static int runZIP(String[] bufferSizes, String fileName, String fileExtension) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileWriter(new File("data/data"
                    + "--" + fileName + ".md")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputStream.printf("File: %s%n%n", fileName);
        outputStream.print("BufferSizes: ");
        for (int i = 0; i < bufferSizes.length; i++) {
            outputStream.print(bufferSizes[i] + " ");
        }
        outputStream.println("\n");

        drawRow(bufferSizes, "", outputStream);
        outputStream.print("|-|");
        for (String bufferSize : bufferSizes) {
            outputStream.print("-|");
        }
        outputStream.println();

        BigDecimal[] zipSizes = new BigDecimal[bufferSizes.length];
        Long[] zipTimes = new Long[bufferSizes.length];
        for (int i = 0; i < bufferSizes.length; i++) {
            String bufferSize = bufferSizes[i];
            long startTime = System.currentTimeMillis();
            try {
                ZipCopy.main(new String[]{bufferSize, fileName, fileExtension});       // In the original project, the modified version
                                                                        // Of Zip.java is just ZipCopy.java
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            // The sizes of the files is saved in KB
            BigDecimal fileSize = BigDecimal.valueOf(new File(fileName + ".zip").length()).divide(BigDecimal.valueOf(1024));
            zipSizes[i] = fileSize;
            zipTimes[i] = endTime - startTime;
        }

        // Printing the sizes
        drawRow(zipSizes, "Size", outputStream);

        // Printing the times
        drawRow(zipTimes, "Time", outputStream);

        outputStream.close();
        return 0;
    }

    private static <T> void drawRow(T[] array, String lead, PrintWriter outputStream) {
        outputStream.print("|" + lead + "|");
        for (int i = 0; i < array.length; i++) {
            outputStream.print(array[i] + "|");
        }
        outputStream.println();
    }

    private static String name(String fileName) {
       return fileName.substring(0, fileName.indexOf("."));
    }

    private static String extension(String fileName) {
        return fileName.substring(fileName.indexOf("."));
    }

    /**
     * Checks if the files specified can be seen in the current working directory
     *
     * @param files
     * @throws FileNotFoundException if the files are in a different directory
     */
    private static void checkFiles(String[] files) throws FileNotFoundException {
        for (String name : files) {
            File file = new File(name);
            if (!file.exists() || file.isDirectory()) {
                throw new FileNotFoundException(name + " not found in " + System.getProperty("user.dir"));
            }
        }
    }

    /*
     * Depending on how long you want the warm up to run, change `iter`.
     *
     * The runtime for the compression is significantly slower on larger buffer lengths, because of the nature of the
     * first write-up, the JVM being warmed up isn't completely necessary, and doesn't affect the results as much.
     * This is a recommendation to not use the warm-up for this specific project.
     *
     * However, it is still there if you would like to use it.
     */
    private static void warmup(String[] bufferSizes, String fileName) {
        System.out.println("Warming up the JVM");
        int iter = 0;    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Change this line
        for (int i = 0; i < iter; i++) {
            System.out.println("--------------------- WARM UP");
            runZIP(bufferSizes, name(fileName), extension(fileName));
        }
        System.out.println("Warm up complete.");
    }
}
