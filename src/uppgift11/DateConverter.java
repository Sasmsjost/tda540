package uppgift11;

import javax.swing.*;
import java.util.Scanner;

public class DateConverter {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Enter a date in the format yymmdd");
        Scanner parser = new Scanner(input);

        int date = parser.nextInt();

        // Can be seen as using the mask 110000 and letting date be 001111
        int year = date / 10000;
        date = date % 10000;

        // Can be seen as using the mask 1100 and letting date be 000011
        int month = date / 100;
        date = date % 100;

        // Can be seen as using the mask 11
        int day = date;

        String message = String.format("%02d/%02d/%02d", month, day, year);
        JOptionPane.showMessageDialog(null, message);
    }
}
