package uppgift12;

public class CalculatePi {

    public static void main(String[] args) {
        double pi1 = calculateWithIterationCount(500);
        double pi2 = calculateWithSmallestFraction(0.00001);
        System.out.println("With 500 iterations: " + pi1);
        System.out.println("When breaking at the fraction 0.00001: " + pi2);
    }

    /**
     * Calculate PI by iterating a certain amount of times
     */
    private static double calculateWithIterationCount(int iterations) {
        double PI = 0;

        for(int i = 0; i < iterations; i++) {
            PI += getFraction(i);
        }

        return PI * 4;
    }

    /**
     * Calculate PI by breaking when a fraction reaches a certain size.
     * The smallestFraction provided will be included in the calculation of PI
     */
    private static double calculateWithSmallestFraction(double smallestFraction) {
        double PI = 0;
        double fraction;

        // This loop could be expressed through a while loop or
        // recursion (Does Java do tail call optimization? might otherwise blow stack for more precise calculations)
        // but we found this solution to be clear enough.
        // 1. fraction = getFraction(i)
        // 2. continue while |fraction| > smallestFraction
        // 3. increment PI with the positive or negative fraction
        // 4. increment i by 1
        // 5. goto [1]
        //
        // We also know that code should not be explained if not necessary and
        // in cases like this, refactoring the loop would be good. This solution
        // was on the other hand nice to look at.
        for(int i = 0; Math.abs(fraction = getFraction(i)) >= smallestFraction; i++) {
            PI += fraction;
        }

        return PI * 4;
    }

    /**
     * Get the sign and fraction for a certain iteration
     * using the formula PI/4 = 1 - 1/3 + 1/5 - ...
     * where each iteration can be mapped to a fraction.
     * 0 -> 1
     * 1 -> -1/3
     * 2 -> 1/5
     */
    private static double getFraction(int i) {
        int sign = (i % 2) * -2 + 1; // Map 0,2,4,6,... -> 1 and 1,3,5,7,... -> -1
        double fraction = 1 / (i * 2.0 + 1);

        return fraction * sign;
    }
}
