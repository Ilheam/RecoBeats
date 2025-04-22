package controllers;

public class CosineSimilarity {

    public static double calculate(double[] vector1, double[] vector2) {
        double dotProduct = 0;
        double normVector1 = 0;
        double normVector2 = 0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            normVector1 += vector1[i] * vector1[i];
            normVector2 += vector2[i] * vector2[i];
        }

        double denominator = Math.sqrt(normVector1) * Math.sqrt(normVector2);
        if (denominator == 0) return 0.0;

        return dotProduct / denominator;
    }
}
