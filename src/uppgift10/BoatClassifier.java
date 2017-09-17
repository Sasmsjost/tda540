package uppgift10;

import javax.swing.*;
import java.util.Scanner;

public class BoatClassifier {
    public static void main(String[] args) {
        final double CLASSIFICATION_MARGIN = 0.05;

        String rawInput = JOptionPane.showInputDialog("Enter the boats 'omfång'(d), 'segelyta'(A), 'längd'(L) and 'fribordshöjd'(f)");
        Scanner input = new Scanner(rawInput);

        double d = input.nextDouble();
        double A = input.nextDouble();
        double L = input.nextDouble();
        double f = input.nextDouble();

        // The original formula can be found in the course material
        double classification = (2 * d + Math.sqrt(A) + L - f) / 2.37;

        // We only test for boats which should classify as a 12
        if (Math.abs(classification - 12) < CLASSIFICATION_MARGIN) {
            System.out.println("The boat is classed a 12");
        } else {
            System.out.println("Sorry, you gotta buy a new boat");
        }
    }
}
