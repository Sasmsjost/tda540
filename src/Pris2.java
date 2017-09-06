// Programmet löser in hur mycket en vara kostar per styck, hur många enheter av varan
// som köpts samt beräknar och skriver ut totala priset efter att 10 procents rabatt erhållits om
// totalpriset överstiger 1000 kr.

import javax.swing.*;

public class Pris2 {
    public static void main(String[] args) {
        String indata = JOptionPane.showInputDialog("Ange varans pris i kronor: ");
        double perStyck = Double.parseDouble(indata);

        indata = JOptionPane.showInputDialog("Ange antalet enheter av varan: ");
        int antal = Integer.parseInt(indata);

        double bruttoPris = antal * perStyck;
        double nettoPris;
        double rabatt;
        double rabattFaktor;

        if (bruttoPris > 750)
            rabattFaktor = 0.05;
        else if (bruttoPris > 1500)
            rabattFaktor = 0.1;
        else if (bruttoPris > 3000)
            rabattFaktor = 0.15;
        else
            rabattFaktor = 0;

        rabatt = bruttoPris * rabattFaktor;
        nettoPris = bruttoPris - rabatt;

        String bruttoMessage = "Bruttopris: " + bruttoPris;
        String rabattMessage = "Rabatt: " + rabatt;
        String nettoMessage = "Nettopris: " + nettoPris;

        JOptionPane.showMessageDialog(null,
                bruttoMessage + "\n" +
                rabattMessage + "\n" +
                nettoMessage + "\n"
        );
    }
}
