package uppgift13;

import java.util.Scanner;

public class EasterDate {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.println("Please enter the year you would like to know when easter was: ");
            if (input.hasNextInt()) {
                String userInput = input.nextLine();

                // Discard junk data, only parses the first date the user enters
                Scanner inputParser = new Scanner(userInput);
                int year = inputParser.nextInt();

                if (year >= 1900 && year <= 2099) {
                    String date = getEasterDate(year);
                    System.out.println(year + " easter was on the " + date);
                } else {
                    System.out.println("Only the years between 1900 and 2099 are valid");
                }
            } else {
                input.nextLine();
                System.out.println("The input was not a valid year");

            }
        }
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
            // 'd' is negative
            return (31 + d) + " mars";
        }
    }
}
