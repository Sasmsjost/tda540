package uppgift9;

import Helpers.ValidInput;

import javax.swing.*;

public class ThrowCalculator {

    public static void main(String[] args) {
        final double GRAVITY = 9.82;

        String input = JOptionPane.showInputDialog("What is the velocity? (m/s)");
        ValidInput.ensure(input);
        double velocity = Double.parseDouble(input);

        input = JOptionPane.showInputDialog("What angle are you firing at? (º)");
        ValidInput.ensure(input);
        double angle = Integer.parseInt(input);
        double radAngle = Math.toRadians(angle);

        // The original formulas can be found in the course material
        double height = Math.pow(velocity, 2) * Math.pow(Math.sin(radAngle), 2) / (2 * GRAVITY);
        double distance = Math.pow(velocity, 2) * Math.sin(2 * radAngle) / GRAVITY;

        String message = String.format(
                "Your ball was thrown:\n" +
                "With a maximum height of %.2f meters\n" +
                "and a distance of %.2f meters"
            ,height, distance);

        JOptionPane.showMessageDialog(null, message);
    }
}
