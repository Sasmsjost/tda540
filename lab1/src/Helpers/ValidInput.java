package Helpers;

public class ValidInput {
    public static void ensure(String input) {
        if (input == null || input.isEmpty()){
            System.out.println("Cancel pressed or input was none, exiting");
            System.exit(0);
        }
    }
}
