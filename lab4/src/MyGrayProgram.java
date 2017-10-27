public class MyGrayProgram {
    public static void main(String[] args) throws Exception {
        int[][] original = GrayImage.read("lab4/src/mushroom.jpeg");
        int[][] manipulated = toBlackWhite(original);
        GrayImage.write("upDownMushroom.jpeg", manipulated);
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

    private static int sgnOfPixel(int pixelColour) {
        if (pixelColour < 128) {
            return 0;
        }
        return 255;
    }
/*
    public static int[][] contour(int[][] samples){

    }
*/

}