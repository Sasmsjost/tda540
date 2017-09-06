//This program reads the radius of a circle and prints its are

import javax.swing.*;

public class Circle2 {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Enter the radius of the circle: ");
        double radius = Double.parseDouble(input);
        double area = Math.PI * Math.pow(radius, 2);
        double circumference = Math.PI * radius * 2;

        String areaMessage = "The area of the circle is " + area;
        String circumferenceMessage = "The circumference of the circle is " + circumference;
        JOptionPane.showMessageDialog(null,  areaMessage + "\n" + circumferenceMessage);
    }
}
