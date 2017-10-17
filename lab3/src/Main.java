import javafx.util.Pair;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class Main {
    static String fileName;
    static double speed = 1;
    static BiFunction<Integer, Double, double[]> instrument = MusicUtils::harmonic;

    // Run with filename speed instrument(harmonic or note) dampening
    // Required: filename
    // Optional: speed, instrument, dampening
    public static void main(String[] args) {
        parseArgs(args);

        ArrayList<Pair<Integer, Double>> desc = MusicUtils.loadSongDescription(fileName);
        if(desc != null) {
            Song song = MusicUtils.descriptionToSong(desc, speed, instrument);
            SoundDevice device = new SoundDevice();
            song.play(device);
        } else {
            System.out.println(String.format("The file with name %s was not found", fileName));
        }
    }//main


    public static void parseArgs(String[] args) {
        if(args.length < 1) {
            System.out.println("Please specify a file to read");
            System.exit(1);
            return;
        }
        fileName = args[0];

        if(args.length >= 2) {
            speed = 1 / Double.parseDouble(args[1]);
        }

        if(args.length >= 3) {
            String instrumentName = args[2];
            System.out.println(String.format("Using instrument %s", instrumentName));
            switch(instrumentName) {
                case "harmonic":
                    instrument = MusicUtils::harmonic;
                    break;
                case "note":
                    instrument = MusicUtils::note;
                    break;
                default:
                    System.out.println(String.format("Instrument of type %s was not found", instrumentName));
                    break;
            }
        }

        if(args.length >= 4) {
            MusicUtils.dampening = Double.parseDouble(args[3]);
        }
    }

}//Main
