public class CalculatePi {

    // Using the formula PI/4 = 1 - 1/3 + 1/5 - ...
    public static void main(String[] args) {
        double pi1 = calculateWithAccuracy(500);
        double pi2 = calculateWithSmallestFraction(0.00001);
        System.out.println(pi1 + " / " + pi2);
    }

    private static double calculateWithAccuracy(int accuracy) {
        double PI = 0;

        for(int i = 0; i < accuracy; i++) {
            double fraction = getFraction(i);
            PI += fraction;
        }

        return PI * 4;
    }

    private static double calculateWithSmallestFraction(double smallestFraction) {
        double PI = 0;
        double fraction;

        for(int i = 0; Math.abs(fraction = getFraction(i)) > smallestFraction; i++) {
            PI += fraction;
        }

        return PI * 4;
    }

    private static double getFraction(int i) {
        int sign = (i % 2) * -2 + 1; // Map 1 -> -1 and 0 -> 1
        double fraction = 1 / (i * 2.0 + 1);

        return fraction * sign;
    }
}
