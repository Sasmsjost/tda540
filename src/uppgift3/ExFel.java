package uppgift3;

import javax.swing.*;

public class ExFel {
    public static void main(String[] arg) {
        Float tal1, tal2;
        double tal3;
        String indata = JOptionPane.showInputDialog("Ange forsta talet");
        tal1 = Float.parseFloat(indata);
        indata = JOptionPane.showInputDialog("Ange andra talet");
        tal2 = Float.parseFloat(indata);
        tal3 = tal1 / tal2;
        JOptionPane.showMessageDialog(null, "Resultatet blev " + tal3);
    }
}
