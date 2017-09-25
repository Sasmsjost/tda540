package uppgift9;

import javax.swing.*;

public class ThrowCalculator {

    public static void main(String[] args) {
        final double GRAVITY = 9.82;

        String input = JOptionPane.showInputDialog("What is the velocity? (m/s)");
        if (input == null || input.isEmpty()){
            System.out.println("Cancel pressed or input was none, exiting");
            System.exit(0);
        }
        double velocity = Double.parseDouble(input);

        input = JOptionPane.showInputDialog("What angle are you firing at? (º)");
        if (input == null || input.isEmpty()){
            System.out.println("Cancel pressed or input was none, exiting");
            System.exit(0);
        }
        double angle = Integer.parseInt(input);
        double radAngle = Math.toRadians(angle);

        // The original formulas can be found in the course material
        double height = Math.pow(velocity * Math.sin(radAngle), 2) / (2 * GRAVITY);
        double distance = Math.pow(velocity, 2) * Math.sin(2 * radAngle) / GRAVITY;

        int decimalCount = 2;
        String formatString = "%." + decimalCount + "f";
        String formattedHeight = String.format(formatString, height);
        String formattedDistance = String.format(formatString, distance);

        JOptionPane.showMessageDialog(null, "Your ball was thrown:\n" +
                "With a maximum height of " + formattedHeight + " meters\n" +
                "and a distance of " + formattedDistance + " meters");

    }
}
