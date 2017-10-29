public class MyColorProgram { 
   public static void main(String[] args) throws Exception { 
      int[][][] original = ColorImage.read("lab4/src/mushroom.jpeg");
      int[][][] manipulated = leftRight(original);
      ColorImage.write("upDownMushroom.jpeg", manipulated); 
      ColorImageWindow iw = new ColorImageWindow(original, manipulated); 
   }//main 

   public static int[][][] upDown(int[][][] samples) { 
      int[][][] newSamples = new int[samples.length][samples[0].length][3]; 
      for (int row = 0; row < samples.length; row = row + 1)
         for (int col = 0; col < samples[row].length; col = col + 1) 
            for (int c = 0; c < samples[row][col].length; c = c + 1) 
               newSamples[row][col][c] = samples[samples.length-row-1][col][c]; 
      return newSamples; 
   }//upDown

   public static int[][][] leftRight(int[][][] samples){
       int[][][] newSamples = new int[samples.length][samples[0].length][3];
       for (int row = 0; row < samples.length; row = row + 1)
           for (int col = 0; col < samples[row].length; col = col + 1)
               for (int c = 0; c < samples[row][col].length; c = c + 1){
                   newSamples[row][col][c] = samples[row][samples[row].length - col - 1][c];
                }
       return newSamples;
   }
}//MyColorProgram
