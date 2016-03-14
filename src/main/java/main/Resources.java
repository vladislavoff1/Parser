package main;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by vlad on 14/03/16.
 */
public class Resources {

    private static Resources instance;

    private Resources() {

    }

    public static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }

        return instance;
    }

    public String readFile(String fileName) throws FileNotFoundException {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

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
