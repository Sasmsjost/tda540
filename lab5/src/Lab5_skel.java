import java.util.Scanner;

public class Lab5_skel {

    public static void main(String[] args) {
        // -- Del 1 --

//        doCommandLine();
//        System.out.println(toRobber("Hej på dig"));
//        System.out.println(toPigLatin("Hej på dig"));


        // -- Del 2 --

        rollADice();
        letPlayerRoll();
        letTwoPlayersRollSameDice();
        letPlayerUseDiceCup();
        letPlayerUseDiceCupWithToString();
        findPlayerWithMax();
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
            if(Character.isLetter(character)) {
                if (isVowel(character)) {
                    robberSentence.append(character);
                } else {
                    char lcCharacter = Character.toLowerCase(character);
                    robberSentence.append(character);
                    robberSentence.append('o');
                    robberSentence.append(lcCharacter);
                }
            } else {
                robberSentence.append(character);
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
     * Only supports a single line and will remove excessive whitespace
     */
    public static String toPigLatin(String text) {
        StringBuilder pigLatinSentence = new StringBuilder(text.length());

        Scanner textSplitter = new Scanner(text);
        while(textSplitter.hasNext()) {
            String word = textSplitter.next();
            char firstCharacter = word.charAt(0);
            char lastCharacter = word.charAt(word.length()-1);
            String postFix = " ";

            if(lastCharacter == '.') {
                postFix = ".";
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

            pigLatinSentence.append(postFix);
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
        Dice dice = new Dice(6);
        Player1 player = new Player1("otto", dice);

        rollDiceAndPrintResult(player, 5);
    }
    // 7

    public static void letTwoPlayersRollSameDice() {
        Dice dice = new Dice(6);
        Player1 player1 = new Player1("otto", dice);
        Player1 player2 = new Player1("fia", dice);

        rollDiceAndPrintResult(player1, 5);
        rollDiceAndPrintResult(player2, 5);
    }

    private static void rollDiceAndPrintResult(Player1 player, int count) {
        System.out.println("Player is " + player.getName());
        for (int i = 0; i < count; i++) {
            int roll = player.rollDice();
            System.out.print(roll + " ");
        }
        System.out.println("");
    }

    // 8
    public static void letPlayerUseDiceCup() {
        DiceCup diceCup = createDiceCup(5);
        Player2 player = new Player2("arne", diceCup);

        for(int i = 0; i < 2; i++) {
            int diceRoll = player.rollDice();
            String result = String.format("%s %d", player.getName(), diceRoll);
            System.out.println(result);
        }
    }

    // 9
    // Same as above but toString overridden.
    public static void letPlayerUseDiceCupWithToString() {
        DiceCup diceCup = createDiceCup(5);
        Player2 player = new Player2("arne", diceCup);

        for(int i = 0; i < 2; i++) {
            int diceRoll = player.rollDice();

            System.out.println(diceCup);
            String result = String.format("%s %d", player.getName(), diceRoll);
            System.out.println(result);
        }
    }

    private static DiceCup createDiceCup(int diceCount) {
        Dice[] dices = new Dice[diceCount];
        for(int i = 0; i < dices.length; i++) {
            dices[i] = new Dice(6);
        }

        return new DiceCup(dices);
    }

    // 10
    public static void findPlayerWithMax() {
        Player2[] players = new Player2[5];

        for(int i = 0; i < players.length; i++) {
            String name = String.valueOf(i);
            DiceCup cup = createDiceCup(5);
            Player2 player = new Player2(name, cup);

            players[i] = player;
            player.rollDice();
            System.out.println(player);
        }

        Player2 winningPlayer = players[0];
        for(Player2 player : players) {
            if(player.getLastRoll() > winningPlayer.getLastRoll()) {
                winningPlayer = player;
            }
        }

        String winningMessage = String.format("\n\nWinner: %s", winningPlayer);
        System.out.println(winningMessage);
    }
}
