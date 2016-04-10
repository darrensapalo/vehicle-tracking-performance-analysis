import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darren on 4/9/2016.
 */
public class Driver {
    public static void main(String[] args) {
        Path path;
        if (args.length == 0)
            path = Paths.get("D:\\Computer Vision video datasets\\classifications\\per class");
        else
            path = Paths.get(args[0]);

        List<Classification> classifications = new ArrayList<Classification>();
        try {
            classifications = Files.walk(path)
                    .filter(Files::isDirectory)
                    .map(p -> {
                        try {
                            return new Classification(p);
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                        return null;
                    })
                    .distinct()
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        GUI gui = new GUI(classifications);
    }
}
