// Programmet beräknar hypotenusan av en rätvinklig triangel
// vars båda katetrar är 3.0 respektive 4.0

public class Triangel {
    public static void main(String[] arg) {
        double kateter1, kateter2, hypotenusa;
        kateter1 = 3.0;
        kateter2 = 4.0;
        hypotenusa = Math.sqrt(Math.pow(kateter1, 2) + Math.pow(kateter2, 2));
        System.out.println("En rätvinklig triangel med sidorna " + kateter1 + " och " + kateter2);
        System.out.println("har hypotenusan " + hypotenusa);
    }
}
