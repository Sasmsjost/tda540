package uppgift8;

import Helpers.ValidInput;

import javax.swing.*;

public class ExchangeRate {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Please specify the exchange rate between EURO and SEK, \n\tEx. Enter '10' for 1 EURO = 10 SEK");
        ValidInput.ensure(input);
        double exchangeRate = Double.parseDouble(input);

        input = JOptionPane.showInputDialog("How many SEK would you like to exchange?");
        ValidInput.ensure(input);
        double sek = Double.parseDouble(input);
        double euro = sek / exchangeRate;

        String sekFormatted = String.format("%.2f", sek);
        String euroFormatted = String.format("%.2f", euro);

        String message = String.format(
                "Your conversion is complete!\n\n" +
                "SEK: %.2f" + "\n" +
                "EURO: %.2f" + "\n\n" +
                "Rate: %.2f"
            ,sek, euro, exchangeRate);

        JOptionPane.showMessageDialog(null, message);
    }
}
