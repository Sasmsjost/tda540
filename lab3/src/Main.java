import javafx.util.Pair;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class Main {
    // Run with filename speed instrument(harmonic or note)
    public static void main(String[] args) {
        BiFunction<Integer, Double, double[]> instrument = MusicUtils::harmonic;

        if(args.length < 1) {
            System.out.println("Please specify a file to read");
            return;
        }
        String fileName = args[0];

        double speed = 1;
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

        ArrayList<Pair<Integer, Double>> desc = MusicUtils.loadSongDescription(fileName);
        if(desc != null) {
            Song song = MusicUtils.descriptionToSong(desc, speed, instrument);
            SoundDevice device = new SoundDevice();
            song.play(device);
        } else {
            System.out.println(String.format("The file with name %s was not found", fileName));
        }
    }//main

}//Main
