package uppgift8;

import javax.swing.*;

public class ExchangeRate {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Please specify the exchange rate between EURO and SEK, \n\tEx. Enter '10' for 1 EURO = 10 SEK");

        double exchangeRate = Double.parseDouble(input);

        input = JOptionPane.showInputDialog("How many SEK would you like to exchange?");
        double sek = Double.parseDouble(input);

        double euro = sek / exchangeRate;

        String sekFormatted = String.format("%.2f", sek);
        String euroFormatted = String.format("%.2f", euro);

        JOptionPane.showMessageDialog(null, "Your conversion is complete!\n\n" +
                "SEK: " + sekFormatted + "\n" +
                "EURO: " + euroFormatted + "\n\n" +
                "Rate: " + exchangeRate);

    }
}
