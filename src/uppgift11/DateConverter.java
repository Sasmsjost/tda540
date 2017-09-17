package uppgift11;

import javax.swing.*;
import java.util.Scanner;

public class DateConverter {
    public static void main(String[] args) {
        String rawInput = JOptionPane.showInputDialog("Enter a date in the format yymmdd");
        Scanner input = new Scanner(rawInput);

        int rest = input.nextInt();

        // Can be seen as using the mask 110000 and letting rest be 001111
        int year = rest / 10000;
        rest = rest % 10000;

        // Can be seen as using the mask 1100 and letting rest be 0011
        int month = rest / 100;
        rest = rest % 100;

        // Can be seen as using the mask 11
        int day = rest;

        String message = String.format("%02d/%02d/%02d", month, day, year);
        JOptionPane.showMessageDialog(null, message);
    }
}
