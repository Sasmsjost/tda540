package uppgift5;

import javax.swing.*;

public class Triangel2 {
    public static void main(String[] arg) {
        double kateter1, kateter2, hypotenusa;

        String katet1In = JOptionPane.showInputDialog("Ange katet 1");
        String katet2In = JOptionPane.showInputDialog("Ange katet 2");

        kateter1 = Double.parseDouble(katet1In);
        kateter2 = Double.parseDouble(katet2In);

        hypotenusa = Math.sqrt(Math.pow(kateter1, 2) + Math.pow(kateter2, 2));
        JOptionPane.showMessageDialog(null, "Hypotenusan är " + hypotenusa + " lång");
    }
}
