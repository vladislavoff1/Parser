package files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vlad on 14/03/16.
 */
public class FileSystem {

    public static String readFile(String name) throws FileNotFoundException {
        Path path = Paths.get(name);

        FileReader fileReader = new FileReader(path.toFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();

        bufferedReader
                .lines()
                .forEachOrdered((s) -> stringBuilder.append(s).append("\n"));

        return stringBuilder.toString();
    }

}
