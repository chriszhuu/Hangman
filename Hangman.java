import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    private String[] dictionary;
    private int wordLength;
    private String[] currentWord;
    private String letter;
    private int guessesRemaining;
    private ArrayList<String> guessedLetters = new ArrayList<>();
    private ArrayList<String> candidateWords = new ArrayList<>();

    public void reveal() {
        /* 1) identify matching letters in each candidate word
           2) record index of matching letters */
        String[] tally = new String[candidateWords.size()];
        int x = 0;
        /* iterate over every candidate word */
        for (String s : candidateWords) {
            tally[x] = "";
            int count = 0;
            /* which letters in the word match user guess?
               how many matches in a word?
               all these info are concatenated into a string
               each word has a corresponding info-string in "tally" array */
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == letter.charAt(0)) {
                    tally[x] = tally[x] + i;
                }
                count++;
            }
            tally[x] = tally[x] + count;
            x++;
        }

        /* 1) group words based on matching letter index combinations
           2) count the size of each group */
        ArrayList<String> seen = new ArrayList<>();
        int[] count = new int[candidateWords.size()];
        seen.add(tally[0]);
        int index = 0;
        for (
                int m = 0;
                m < tally.length; m++) {
            if (m == 0 || !seen.contains(tally[m])) {
                seen.add(tally[m]);
            } else {
                continue;
            }
            for (int n = m; n < tally.length; n++) {
                if (tally[m].equals(tally[n])) {
                    count[index]++;
                }
            }
            index++;
        }

        /* identify the largest group */
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > max) {
                max = count[i];
                maxIndex = i;
            }
        }

        /* select words within the group and delete the rest,
           update candidateWords */
        String chosen = seen.get(maxIndex);
        ArrayList<String> filtered = new ArrayList<>();
        for (int i = 0; i < tally.length; i++){
            if (tally[i].equals(chosen)){
                filtered.add(candidateWords.get(i));
            }
        }
        candidateWords = filtered;
        if (chosen.length() > 1){
            for (int i = 0; i < chosen.length() - 1; i++){

                currentWord[chosen.charAt(i)-'0'] = letter + " ";
                System.out.println("Your guess matches letter at index "+ chosen.charAt(i));
            }
        }
        printState();
        if (candidateWords.size() == 1){
            System.out.println("\n************ Congratulations! You figured out the word! ************");
        }
        if (guessesRemaining == 0){
            System.out.println("\n************ :( You've used up all guesses, the word is " + candidateWords.get(0)+ " ************");
        }

    }



    public Hangman() {
        System.out.println("Welcome to hangman!");
        dictionary = new Words().getWords();
        enterWordLength();
        generateList(wordLength);
        while (candidateWords.size() == 0) {
            enterWordLength();
            generateList(wordLength);
            System.out.println(">>> Entered length corresponds to no word in the dictionary, please enter a different length:");
        }
        enterNumGuesses();
        currentWord = new String[wordLength];
        for (int i = 0; i < wordLength; i++) {
            currentWord[i] = "_ ";
        }
        System.out.println("We're all set, the game begins!");
    }

    private void enterWordLength() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Step 1: enter a word length between 1 and 29 inclusive:");
        boolean b = false;
        while (!b) {
            wordLength = sc.nextInt();
            if (wordLength < 1 || wordLength > 29) {
                System.out.println(">>> Word length needs to be between 1 and 29. Please re-enter:");
                continue;
            }
            b = true;
        }
    }

    private void enterNumGuesses() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Step 2: enter the number of guesses you want to have:");
        boolean b = false;
        while (!b) {
            guessesRemaining = sc.nextInt();
            if (guessesRemaining < 1) {
                System.out.println(">>> Number of guesses needs to be greater than 0. Please re-enter:");
                continue;
            }
            b = true;
        }
    }

    private void generateList(int wordLength) {
        for (String word : dictionary) {
            if (word.length() == wordLength) {
                candidateWords.add(word);
            }
        }
    }


    public void guess() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nGuess a letter:");
        boolean b = false;
        while (!b) {
            letter = sc.next().toLowerCase();
            if (letter.length() != 1 || letter.charAt(0) < 'a' || letter.charAt(0) > 'z') {
                System.out.println("Please re-enter. One letter between A and Z only:");
                continue;
            }
            if (guessedLetters.contains(letter)) {
                System.out.println("You have already guessed this letter. Please guess another one:");
                continue;
            }
            b = true;
        }
        guessedLetters.add(letter);
        guessesRemaining--;
    }


    public void play() {
        while (true){
            if (guessesRemaining <= 0 || candidateWords.size() <= 1) {
                return;
            }
            guess();
            reveal();
        }
    }

    public static void main(String[] args) {
        Hangman round1 = new Hangman();
        round1.play();

    }




    public void printState() {
        System.out.println("\n>>> The word is currently");
        for (String s : currentWord) {
            System.out.print(s);
        }
        System.out.println("\n>>> You have " + guessesRemaining + " guesses remaining.");
        System.out.println(">>> You have already tried these letters");
        for (String s : guessedLetters) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
