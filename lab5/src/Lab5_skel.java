import java.util.Scanner;

public class Lab5_skel {

    public static void main(String[] args) {
        // -- Del 1 --

        doCommandLine();
        System.out.println(toRobber("Hej på dig"));
        System.out.println(toPigLatin("Hej på dig"));


        // -- Del 2 --

        //rollADice();
        //letPlayerRoll();
        //letTwoPlayersRollSameDice();
        //letPlayerUseDiceCup();
        //findPlayerWithMax();
    }

    // ---------- Del 1 ---------------

    // 1 and 4
    public static void doCommandLine() {
        Scanner input = new Scanner(System.in);

        System.out.println("Press (r) to translate to the robber language; (p) to translate to pig latin; (q) to quit");
        while (input.hasNextLine()) {
            String character = input.nextLine();
            switch (character) {
                case "r":
                    System.out.print("Input text > ");
                    String rInput = input.nextLine();

                    String robberSentence = toRobber(rInput);
                    System.out.println(robberSentence);
                    break;
                case "p":
                    System.out.print("Input text > ");
                    String pInput = input.nextLine();

                    String pigLatinSentence = toPigLatin(pInput);
                    System.out.println(pigLatinSentence);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid menu command: " + character);
                    break;
            }
        }
    }

    // 2
    public static String toRobber(String text) {
        StringBuilder robberSentence = new StringBuilder(text.length());

        for(int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if(character == ' ' || character == '\n') {
                robberSentence.append(character);
            } else if(isVowel(character)) {
                robberSentence.append(character);
            } else {
                robberSentence.append(character);
                robberSentence.append('o');
                robberSentence.append(String.valueOf(character).toLowerCase());
            }
        }

        return robberSentence.toString();
    }

    // 3
    private final static char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö'};
    public static boolean isVowel(char ch) {
        char normalizedCh = Character.toLowerCase(ch);
        for(char vowel: vowels) {
            if(normalizedCh == vowel) {
                return true;
            }
        }
        return false;  // Just for now
    }

    /**
     * Only supports single line and will remove excessive whitespace
     */
    public static String toPigLatin(String text) {
        StringBuilder pigLatinSentence = new StringBuilder(text.length());

        Scanner textSplitter = new Scanner(text);
        while(textSplitter.hasNext()) {
            String word = textSplitter.next();
            char firstCharacter = word.charAt(0);
            char lastCharacter = word.charAt(word.length()-1);
            String endingChar = " ";

            if(lastCharacter == '.') {
                endingChar = ".";
                word = word.substring(0, word.length() -1);
            }

            if(isVowel(firstCharacter)) {
                pigLatinSentence.append(word);
                pigLatinSentence.append("way");
            } else {
                String consonants = getFirstConsonants(word);
                String rest = word.substring(consonants.length());
                String newWord = rest + consonants;
                String newWordWithCasing = ensureCasing(newWord, word);

                pigLatinSentence.append(newWordWithCasing);
                pigLatinSentence.append("ay");
            }

            pigLatinSentence.append(endingChar);
        }

        return pigLatinSentence.toString();
    }

    private static String ensureCasing(String newText, String originalText) {
        char firstOriginalCharacter = originalText.charAt(0);
        boolean shouldUppercase = Character.isUpperCase(firstOriginalCharacter);

        if(shouldUppercase) {
            char firstUppercased = Character.toUpperCase(newText.charAt(0));
            String newTextExceptFirst = newText.substring(1);
            return firstUppercased + newTextExceptFirst;
        } else {
            return newText;
        }
    }

    private static String getFirstConsonants(String text) {
        String consonants = "";
        for(int i = 0; i < text.length(); i++) {
            char nthChar = text.charAt(i);
            boolean isConsonant = !isVowel(nthChar);
            if(isConsonant){
                char nthCharLowercase = Character.toLowerCase(nthChar);
                consonants += nthCharLowercase;
            } else {
                break;
            }
        }

        return consonants;
    }


    // ---------- Del 2 ---------------

    // 5
    public static void rollADice() {
        Dice dice = new Dice(6);
        for(int i = 0; i < 100; i++){
            int value = dice.roll();
            System.out.print(value + " ");
        }
        System.out.println("");
    }

    // 6
    public static void letPlayerRoll() {
        Dice dice1 = new Dice(6);
        Player1 player = new Player1("otto", dice1);

        System.out.println("Player is " + player.getName());
        for (int i = 0; i < 5; i++) {
            int roll = player.rollDice();
            System.out.print(roll + " ");
        }
        System.out.println("");
    }

    // 7
    public static void letTwoPlayersRollSameDice() {
    }

    // 8
    public static void letPlayerUseDiceCup() {
    }

    // 9
    // Same as above but toString overridden.

    // 10
    public static void findPlayerWithMax() {
    }
}
