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
            path = Paths.get("/Users/darrenkarlsapalo/Dropbox/School/Graduate/AY 2015-2016/TERM 2 - Jan to Apr/VISION/classifications");
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

        // GUI gui = new GUI(classifications);
        PerformanceAnalyzer performanceAnalyzer = new PerformanceAnalyzer(classifications);

        performanceAnalyzer.analyzeConfusionMatrix(1);
        performanceAnalyzer.analyzeConfusionMatrix(2);
        performanceAnalyzer.analyzeConfusionMatrix(3);
        performanceAnalyzer.analyzeConfusionMatrix(4);
        performanceAnalyzer.analyzeConfusionMatrix(5);
        performanceAnalyzer.analyzeConfusionMatrix(6);

        long occlusions = classifications.stream()
                .filter(f -> f.getTrueClassification() == 100)
                .count();

        long errors = classifications.stream()
                .filter(f -> f.getTrueClassification() == 200)
                .count();

        System.out.println("Occlusions detected: " + occlusions);
        System.out.println("Erroneous segmentations detected (no vehicles): " + errors);

    }
}
