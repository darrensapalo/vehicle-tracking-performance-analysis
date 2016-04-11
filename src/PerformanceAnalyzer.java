import java.util.List;

/**
 * Created by darrenkarlsapalo on 11/04/2016.
 */
public class PerformanceAnalyzer {

    private List<Classification> classifications;

    public PerformanceAnalyzer(List<Classification> classifications) {
        this.classifications = classifications;
    }

    public void analyzeConfusionMatrix(int classif){
        long predictions = classifications.stream()
                .filter(f -> f.getClassification() == classif)
                .count();

        long actual = classifications.stream()
                .filter(f -> f.getTrueClassification() == classif)
                .count();


        long truePositive = classifications.stream()
                .filter(f -> f.getTrueClassification() == classif && f.getTrueClassification() == classif)
                .count();

        long falsePositive = classifications.stream()
                .filter(f -> f.getClassification() == classif && f.getTrueClassification() != classif)
                .count();

        long trueNegative = classifications.stream()
                .filter(f -> f.getClassification() != classif && f.getTrueClassification() != classif)
                .count();

        long falseNegative = classifications.stream()
                .filter(f -> f.getClassification() != classif && f.getTrueClassification() == classif)
                .count();

        long total = trueNegative + truePositive + falseNegative + falsePositive;

        /*
        System.out.println("Total " + Classification.getReadableClassification(classif) + " predictions: " + predictions);
        System.out.println("Total actual " + Classification.getReadableClassification(classif) + " instances: " + actual);

        System.out.println("Total true positive " + Classification.getReadableClassification(classif) + " instances: " + truePositive);
        System.out.println("Total false positive " + Classification.getReadableClassification(classif) + " instances: " + falsePositive);

        System.out.println("Total true negative " + Classification.getReadableClassification(classif) + " instances: " + trueNegative);
        System.out.println("Total false negative " + Classification.getReadableClassification(classif) + " instances: " + falseNegative);

        */
        System.out.println("  Classification performance " + Classification.getReadableClassification(classif));
        System.out.println();
        System.out.printf("            Predicted NO    Predicted YES\n");
        System.out.printf("Actual NO     %10d       %10d\n", trueNegative, falsePositive);
        System.out.printf("Actual YES    %10d       %10d\n", falseNegative, truePositive);

        System.out.println();
        System.out.printf("Accuracy: %.4f\n", (double)(truePositive + trueNegative) / total);
        System.out.printf("Error rate: %.4f\n", (1 - (double)(truePositive + trueNegative) / total));
        System.out.printf("Recall: %.4f\n", (truePositive / (double)(truePositive + falseNegative)));
        System.out.printf("Specificity: %.4f\n", (trueNegative / (double)(trueNegative+ falsePositive)));
        System.out.printf("Precision: %.4f\n", (truePositive / (double)(truePositive + falsePositive)));
        System.out.println();
    }

}
