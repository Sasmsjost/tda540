import javax.swing.*;
import java.util.Scanner;

public class BoatClassifier {
    public static void main(String[] args) {
        final double MARGIN = 0.05;

        String input = JOptionPane.showInputDialog("Enter d, A, L, f");
        Scanner parser = new Scanner(input);

        double d = parser.nextDouble();
        double A = parser.nextDouble();
        double L = parser.nextDouble();
        double f = parser.nextDouble();

        double classification = (2 * d + Math.sqrt(A) + L - f) / 2.37;

        if (Math.abs(classification - 12) < MARGIN) {
            System.out.println("The boat is classed a 12");
        } else {
            System.out.println("Sorry, you gotta buy a new boat");
        }
    }
}
