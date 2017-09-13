package uppgift13;

import java.util.Scanner;

public class EasterDate {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // When the application exists, print "shutting down!" to the user
        Runtime.getRuntime().addShutdownHook(new Thread(()->System.out.println("\nShutting down!")));

        // Loop until crlt-d (EOF) is hit, crlt-c will also quit the program but in a more forceful manner
        do {
            System.out.print("Please enter the year you would like to know when easter was: ");
            if (input.hasNextInt()) {
                int year = input.nextInt();

                // Read the rest of the input as junk data
                input.next();

                if (year >= 1900 && year <= 2099) {
                    String date = getEasterDate(year);
                    System.out.println(year + " easter was on the " + date);
                } else {
                    System.out.println("Only the years between 1900 and 2099 are valid");
                }
            } else {
                // Read the rest of the input as junk data
                input.next();
                System.out.println("The input was not a valid year");

            }
        } while(input.hasNextLine());
    }

    private static String getEasterDate(int year) {
        // The original formula can be found in the course material
        int n = year - 1900;
        int a = n % 19;
        int b = (7 * a + 1) / 19;
        int m = (11 * a + 4 - b) % 29;
        int q = n / 4;
        int w = (n + q + 31 - m) % 7;
        int d = 25 - m - w;

        if(d > 0) {
            return d + " april";
        } else {
            return (31 + d) + " mars";
        }
    }
}
