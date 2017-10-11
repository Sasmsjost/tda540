import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiFunction;

public class MusicUtils {

    private static Random random = new Random();
    private static double dampening = 0.498;

    public static ArrayList<Pair<Integer, Double>> loadSongDescription(String fileName) {
        File file = new File(fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            ArrayList<Pair<Integer, Double>> songDescription = new ArrayList<>();
            while (fileScanner.hasNextDouble()) {
                int pitch = fileScanner.nextInt();
                double duration = fileScanner.nextDouble();
                Pair<Integer, Double> desc = new Pair<>(pitch, duration);

                songDescription.add(desc);
            }
            return songDescription;
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    public static Song descriptionToSong(ArrayList<Pair<Integer, Double>> songDescription, double durationModifier, BiFunction<Integer, Double, double[]> operation) {
        double totalDuration = 0;
        for (Pair<Integer, Double> desc : songDescription) {
            double duration = desc.getValue();
            totalDuration += duration * durationModifier;
        }

        int songDuration = (int) Math.round(Math.ceil(totalDuration));
        Song song = new Song(songDuration);

        for (Pair<Integer, Double> desc : songDescription) {
            int pitch = desc.getKey();
            double duration = desc.getValue() * durationModifier;
            double[] note = operation.apply(pitch, duration);
            song.add(note);
        }

        return song;
    }

    public static double[] harmonic(int pitch, double duration) {
        return averageMany(
                note(pitch, duration),
                note(pitch + 12, duration),
                note(pitch - 12, duration)
        );
    }

    public static double[] average(double[] tone1, double[] tone2) {
        return averageMany(tone1, tone2);
    }

    private static double[] averageMany(double[]... tones) {
        assert (tones.length > 0);
        int length = tones[0].length;

        for (int i = 0; i < tones.length; i++) {
            assert (tones.length == length);
        }
        double[] data = new double[length];

        for (int i = 0; i < length; i++) {
            double toneSum = 0;
            for (double[] tone : tones) {
                toneSum += tone[i];
            }
            data[i] = toneSum;
        }

        return data;
    }

    public static double[] note(int pitch, double duration) {
        double freq = 440 * Math.pow(2, pitch / 12.0);
        return pluck(freq, duration);
    }

    public static double[] pluck(double freq, double duration) {
        int length = (int) (duration * SoundDevice.SAMPLING_RATE);
        int randomLength = (int) (SoundDevice.SAMPLING_RATE / freq);
        double[] data = new double[length];

        for (int i = 0; i < randomLength; i++) {
            data[i] = random.nextDouble() * 2 - 1;
        }
        for (int i = randomLength; i < length; i++) {
            double sample1 = data[i - randomLength];
            double sample2 = data[i - (randomLength - 1)];
            data[i] = (sample1 + sample2) * dampening;
        }

        return data;
    }

    public static double[] sine(double freq, double duration) {
        int n = (int) (duration * SoundDevice.SAMPLING_RATE);
        double[] a = new double[n];
        double dx = 2 * Math.PI * freq / SoundDevice.SAMPLING_RATE;
        for (int i = 0; i < n; i = i + 1) {
            a[i] = Math.sin(i * dx);
        }
        return a;
    }//sine

}
