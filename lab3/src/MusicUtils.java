import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MusicUtils {

    private static Random random = new Random();
    public static double dampening = 0.498;

    public static Song loadSongFromFile(File file, double tempo) {
        ArrayList<double[]> notes = new ArrayList<>();
        double songDuration = 0;

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextDouble()) {
                int pitch = fileScanner.nextInt();
                double duration = fileScanner.nextDouble() * tempo;
                double[] note = harmonic(pitch, duration);

                notes.add(note);
                songDuration += duration;
            }
        } catch (FileNotFoundException | InputMismatchException ex) {
            return null;
        } finally {
            if(fileScanner != null) {
                fileScanner.close();
            }
        }

        int approximateSongDuration = (int) Math.ceil(songDuration);
        Song song = new Song(approximateSongDuration);

        for (double[] note : notes) {
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

    /**
     * All tones provided must be of the same length,
     * an IndexOutOfBoundsException will otherwise be thrown
     */
    private static double[] averageMany(double[]... tones) {
        int length = tones[0].length;
        double[] data = new double[length];

        for (int i = 0; i < length; i++) {
            double toneSum = 0;
            for (double[] tone : tones) {
                toneSum += tone[i];
            }
            data[i] = toneSum / tones.length;
        }

        return data;
    }

    public static double[] note(int pitch, double duration) {
        double freq = 440 * Math.pow(2, pitch / 12.0);
        return pluck(freq, duration);
    }

    public static double[] pluck(double freq, double duration) {
        int length = (int) (duration * SoundDevice.SAMPLING_RATE);
        double[] data = new double[length];

        int randomLength = (int) (SoundDevice.SAMPLING_RATE / freq);
        // Ensure that we don't get an IndexOutOfBoundsException for short durations
        randomLength = Math.min(length, randomLength);

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
    }

}
