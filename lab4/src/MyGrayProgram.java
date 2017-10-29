public class MyGrayProgram {
    public static void main(String[] args) throws Exception {
        int[][] original = GrayImage.read("lab4/src/mushroom.jpeg");
        int[][] manipulated = contour(original);
//        GrayImage.write("upDownMushroom.jpeg", manipulated);
        GrayImageWindow iw = new GrayImageWindow(original, manipulated);
    }//main

    public static int[][] upDown(int[][] samples) {
        int[][] newSamples = new int[samples.length][samples[0].length];
        for (int row = 0; row < samples.length; row = row + 1)
            for (int col = 0; col < samples[row].length; col = col + 1)
                newSamples[row][col] = samples[samples.length - row - 1][col];
        return newSamples;
    }

    public static int[][] leftRigth(int[][] samples) {
        int[][] newSamples = new int[samples.length][samples[0].length];
        for (int row = 0; row < samples.length; row = row + 1)
            for (int col = 0; col < samples[row].length; col = col + 1)
                newSamples[row][col] = samples[row][samples[row].length - col - 1];
        return newSamples;
    }

    public static int[][] invert(int[][] samples) {
        int[][] newSamples = new int[samples.length][samples[0].length];
        int orgColour = 0;
        for (int row = 0; row < samples.length; row = row + 1) {
            for (int col = 0; col < samples[row].length; col = col + 1) {
                orgColour = samples[row][col];
                newSamples[row][col] = 255 - orgColour;
            }
        }
        return newSamples;
    }

    public static int[][] toBlackWhite(int[][] samples) {
        int[][] newSamples = new int[samples.length][samples[0].length];
        int orgColour = 0;
        for (int row = 0; row < samples.length; row = row + 1) {
            for (int col = 0; col < samples[row].length; col = col + 1) {
                orgColour = samples[row][col];
                newSamples[row][col] = sgnOfPixel(orgColour);
            }
        }
        return newSamples;
    }

    public static int[][] contour(int[][] samples) {
        int[][] newSamples = new int[samples.length][samples[0].length];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                newSamples[row][col] = getContourColor(row, col, samples);
            }
        }
        return newSamples;
    }

    private static int sgnOfPixel(int color) {
        return isDarkPixel(color) ? 0 : 255;
    }

    private static boolean isDarkPixel(int color) {
        return color < 128;
    }

    private static boolean isLightPixel(int color) {
        return !isDarkPixel(color);
    }

    private static int getContourColor(int row, int col, int[][] samples) {
        return isContour(row, col, samples) ? 0 : 255;
    }

    /**
     * Is the pixel seen as a contour,
     * - case 1: On edge, just check the associated pixel
     * - case 2: Otherwise, make sure the current pixel is black and
     *           return true if any neighbouring pixels are white.
     */
    private static boolean isContour(int row, int col, int[][] samples) {
        int color = samples[row][col];
        boolean isDark = isDarkPixel(color);

        if (isPixelOnEdge(row, col, samples)) {
            return isDark;
        }

        return isDark && hasWhiteNeighbour(row, col, samples);
    }

    /**
     * Is the pixel on the edge of the image
     */
    private static boolean isPixelOnEdge(int row, int col, int[][] samples) {
        int width = samples.length - 1;
        int height = samples[0].length - 1;
        return row == 0
                || col == 0
                || row == width
                || col == height;
    }

    /**
     * Checks if a sample at a certain coordinate has a light neighbour around it.
     * Area which is checked, where * is the provided row/column:
     *   XXX
     *   X*X
     *   XXX
     *
     * Important:
     * - Also checks samples[row][col] but since we're guaranteed it's black, no harm done!
     * - Does not protect against index out of bounds, since that case is covered earlier.
     */
    private static boolean hasWhiteNeighbour(int row, int col, int[][] samples) {
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (isLightPixel(samples[row + dRow][col + dCol])) {
                    return true;
                }
            }
        }
        return false;
    }
}