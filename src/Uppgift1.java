import javax.swing.*;

public class Uppgift1{
    public static void main (String[] args) {
        String indata = JOptionPane.showInputDialog("Ange din längd: ");
        int length = Integer.parseInt(indata);
        indata = JOptionPane.showInputDialog("Ange din vikt: ");
        int weight =  Integer.parseInt(indata);
        indata = JOptionPane.showInputDialog("Ange din ålder: ");
        int age =  Integer.parseInt(indata);
        int svar = JOptionPane.showConfirmDialog(null, "är du kvinna?");
        double calories;
        if (svar == 2)
            JOptionPane.showMessageDialog(null, "Du avgav inget ärligt svar!");
        else {
            if (svar == 0)
                calories = 447.7 + 3.1 * length + 9.3 * weight - 4.3 * age;
            else
                calories = 88.4 + 4.8 * length + 13.4 * weight - 5.7 * age;
            JOptionPane.showMessageDialog(null, "Din kaloriförbrukning i vila är "
                    + (int) calories + " kilokalorier per dygn.");
        }
    }
}

