public class MyColorProgram {
    public static void main(String[] args) throws Exception {
        int[][][] original = ColorImage.read("lab4/src/mushroom.jpeg");
        int[][][] manipulated = toBlackWhite(original);
//      ColorImage.write("upDownMushroom.jpeg", manipulated);
        ColorImageWindow iw = new ColorImageWindow(original, manipulated);
    }//main

    public static int[][][] upDown(int[][][] samples) {
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row = row + 1)
            for (int col = 0; col < samples[row].length; col = col + 1)
                for (int c = 0; c < samples[row][col].length; c = c + 1)
                    newSamples[row][col][c] = samples[samples.length - row - 1][col][c];
        return newSamples;
    }//upDown

    public static int[][][] leftRight(int[][][] samples) {
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row = row + 1)
            for (int col = 0; col < samples[row].length; col = col + 1)
                for (int c = 0; c < samples[row][col].length; c = c + 1) {
                    newSamples[row][col][c] = samples[row][samples[row].length - col - 1][c];
                }
        return newSamples;
    }

    public static int[][][] invert(int[][][] samples) {
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                for (int c = 0; c < samples[row][col].length; c++) {
                    newSamples[row][col][c] = 255 - (samples[row][col][c]);
                }
            }
        }
        return newSamples;
    }

    public static int[][][] toGray(int[][][] samples){
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                int grayColour = calculateLuminance(samples[row][col]);
                for (int c = 0; c < samples[row][col].length; c++) {
                    newSamples[row][col][c] = grayColour;
                }
            }
        }
        return newSamples;
    }

    public static int[][][] toBlackWhite(int[][][] samples){
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                int grayColour = calculateLuminance(samples[row][col]);
                int blackColor = sgnOfPixel(grayColour);
                for (int c = 0; c < samples[row][col].length; c++) {
                    newSamples[row][col][c] = blackColor;
                }
            }
        }
        return newSamples;
    }


    private static boolean isDarkPixel(int color) {
        return color < 128;
    }

    public static int[][][] sharpenOne(int[][][] samples) {
        final int[][] sharpeningMatrix = {
                {-1, -1, -1},
                {-1, 9, -1},
                {-1, -1, -1}
        };
        return applyMatrixToAll(sharpeningMatrix, samples);
    }

    public static int[][][] sharpenTwo(int[][][] samples) {
        final int[][] sharpeningMatrix = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        return applyMatrixToAll(sharpeningMatrix, samples);
    }

    public static int[][][] sobel(int[][][] samples) {
        final int[][] sobelX = {
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };
        final int[][] sobelY = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                for (int c = 0; c < samples[row][col].length; c++) {
                    int dx = applyMatrix(row, col, c, sobelX, samples);
                    int dy = applyMatrix(row, col, c, sobelY, samples);
                    int color = (int) Math.round(Math.sqrt(dx * dx + dy * dy));

                    newSamples[row][col][c] = constrain(color, 0, 255);
                }
            }
        }
        return newSamples;
    }

    private static int[][][] applyMatrixToAll(int[][] matrix, int[][][] samples) {
        int[][][] newSamples = new int[samples.length][samples[0].length][3];
        for (int row = 0; row < samples.length; row++) {
            for (int col = 0; col < samples[row].length; col++) {
                for (int c = 0; c < samples[row][col].length; c++) {
                    int color = applyMatrix(row, col, c, matrix, samples);
                    newSamples[row][col][c] = constrain(color, 0, 255);
                }
            }
        }
        return newSamples;
    }

    /**
     * The provided matrix must be of an odd length, ex [3][3] or [5][5],
     * Can return values outside of the 0-255 bounds
     */
    private static int applyMatrix(int row0, int col0, int index, int[][] matrix, int[][][] samples) {
        int width = samples.length - 1;
        int height = samples[0].length - 1;

        int rowOffset = -matrix.length / 2;
        int colOffset = -matrix[0].length / 2;

        int color = 0;
        for (int mRow = 0; mRow < matrix.length; mRow++) {
            int row = row0 + rowOffset + mRow;
            if (outOfBounds(row, 0, width))
                continue;

            for (int mCol = 0; mCol < matrix[0].length; mCol++) {
                int col = col0 + colOffset + mCol;
                if (outOfBounds(col, 0, height))
                    continue;

                int weight = matrix[mRow][mCol];
                int intensity = samples[row][col][index];
                color += intensity * weight;
            }
        }

        return color;
    }

    /**
     * Ensure that we do not have to recreate the luminance weights array at every call.
     * Java might be able to save the optimize this, but have not found any information about it.
     */
    private final static double[] luminanceWeights = {0.299, 0.587, 0.114};
    public static int calculateLuminance(int[] rgb){
        int luminance = 0;
        for (int c = 0; c < rgb.length; c++) {
            luminance += (int) ((double)rgb[c] * luminanceWeights[c]);
        }
        return luminance;
    }

    private static int sgnOfPixel(int color) {
        return isDarkPixel(color) ? 0 : 255;
    }

    private static boolean outOfBounds(int val, int min, int max) {
        return val < min || val > max;
    }

    private static int constrain(int val, int min, int max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }
}//MyColorProgram
