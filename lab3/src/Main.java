public class Main {
    static String fileName;
    static double pace = 1;

    // Run with "filename pace" as cli arguments, both are required
    public static void main(String[] args) {
        parseArgs(args);

        Song song = MusicUtils.loadSongFromFile(fileName, pace);
        if(song != null) {
            SoundDevice device = new SoundDevice();
            song.play(device);
        } else {
            System.out.println(String.format("The file with name %s was not found", fileName));
        }
    }

    public static void parseArgs(String[] args) {
        if(args.length < 1) {
            System.out.println("Please specify a file path and a pace.\nEx.\nexecutable lab3/src/elise.txt 120\n\n");
            System.exit(1);
            return;
        }
        fileName = args[0];

        if(args.length >= 2) {
            pace = 240.0 / Double.parseDouble(args[1]);
        }

        if(args.length >= 4) {
            MusicUtils.dampening = Double.parseDouble(args[3]);
        }
    }

}
