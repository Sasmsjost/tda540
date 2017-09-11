package uppgift12;

public class CalculatePi {

    // Using the formula PI/4 = 1 - 1/3 + 1/5 - ...
    public static void main(String[] args) {
        double pi1 = calculateWithIterationCount(500);
        double pi2 = calculateWithSmallestFraction(0.00001);
        System.out.println("With 500 iterations" + pi1);
        System.out.println("When breaking at the fraction 0.00001" + pi2);
    }

    /**
     * Calculate PI by iterating a certain amount of times
     */
    private static double calculateWithIterationCount(int iterations) {
        double PI = 0;

        for(int i = 0; i < iterations; i++) {
            double fraction = getFraction(i);
            PI += fraction;
        }

        return PI * 4;
    }

    /**
     * Calculate PI by breaking when a fraction reaches a certain size
     */
    private static double calculateWithSmallestFraction(double smallestFraction) {
        double PI = 0;
        double fraction;

        // This loop could be expressed through a while loop but we found this
        // solution to be clear enough.
        // 1. fraction = getFraction(i)
        // 2. continue while |fraction| > smallestFraction
        //
        // We also know that code should not be explained if not necessary and
        // in cases like this, refactoring the loop would be good
        for(int i = 0; Math.abs(fraction = getFraction(i)) > smallestFraction; i++) {
            PI += fraction;
        }

        return PI * 4;
    }

    /**
     * Get the sign and fraction for a certain iteration
     */
    private static double getFraction(int i) {
        int sign = (i % 2) * -2 + 1; // Map 1 -> -1 and 0 -> 1
        double fraction = 1 / (i * 2.0 + 1);

        return fraction * sign;
    }
}
