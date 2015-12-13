package org.csproject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import org.csproject.model.Constants;

/**
 * @author Maike Keune-Staab on 30.10.2015.
 */
public class Utilities {
    /**
     * Maike Keune-Staab
     *tries to override a resource json with the given name or creates a new .json file.
     * @param fileName
     * @param json
     * @throws FileNotFoundException
     */
    public static void saveFile(String fileName, String json) throws FileNotFoundException {
        URL resource = Utilities.class.getResource(fileName);
        File file;
        PrintWriter writer;
        if (resource == null) {
            // create new file
            String path = "C:" + File.separator + "fields" + File.separator + fileName + Constants.JSON_POST_FIX;
            file = new File(path);
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = new File(resource.getPath());
        }
        writer = new PrintWriter(file);
        writer.print(json);
        writer.close();
    }

    /**
     * Maike Keune-Staab
     * loads a .json file with the given filename.
     * @param fileName
     * @return
     */
    public static String getResource(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        File file = new File(Utilities.class.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * Maike Keune-Staab
     * loads a .json file with the given filename.
     * @param fileName
     * @return
     */
    public static String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from absolut path
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
