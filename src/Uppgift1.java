import javax.swing.*;

public class Uppgift1{
    public static void main (String[] arg) {
        String indata = JOptionPane.showInputDialog("Ange din l�ngd: ");
        int length = Integer.parseInt(indata);
        indata = JOptionPane.showInputDialog("Ange din vikt: ");
        int weight =  Integer.parseInt(indata);
        indata = JOptionPane.showInputDialog("Ange din �lder: ");
        int age =  Integer.parseInt(indata);
        int svar = JOptionPane.showConfirmDialog(null, "�r du kvinna?");
        double calories;
        if (svar == 2)
            JOptionPane.showMessageDialog(null, "Du avgav inget �rligt svar!");
        else {
            if (svar == 0)
                calories = 447.7 + 3.1 * length + 9.3 * weight - 4.3 * age;
            else
                calories = 88.4 + 4.8 * length + 13.4 * weight - 5.7 * age;
            JOptionPane.showMessageDialog(null, "Din kalorif�rbrukning i vila �r "
                    + (int) calories + " kilokalorier per dygn.");
        }
    }
}

