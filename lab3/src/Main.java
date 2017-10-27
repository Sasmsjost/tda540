import java.io.File;

public class Main {
    static File file;
    static double tempo;

    // Run with "filename tempo" as cli arguments, both are required
    public static void main(String[] args) {
        parseArgs(args);

        Song song = MusicUtils.loadSongFromFile(file, tempo);
        if(song != null) {
            SoundDevice device = new SoundDevice();
            song.play(device);
        } else {
            String msg = String.format(
                    "The file with path:\n\t%s\n was not found or could not be read.",
                    file.getAbsoluteFile());
            System.out.println(msg);
        }
    }

    public static void parseArgs(String[] args) {
        if(args.length < 2) {
            System.out.println("Please specify a file path and a tempo.\nEx.\n\t./executable_name lab3/src/elise.txt 120");
            System.exit(1);
        }

        {
            String fileName = args[0];
            file = new File(fileName);
        }

        try {
            tempo = 240.0 / Double.parseDouble(args[1]);
        } catch (RuntimeException ex) {
            System.out.println("The tempo specified was invalid, please enter the tempo as a number");
            System.exit(1);
        }
    }

}
