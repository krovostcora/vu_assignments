import java.util.Scanner;

public class WordCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string: ");
        String inputString = scanner.nextLine();
        System.out.print("Enter the letter: ");
        char letter = scanner.next().charAt(0);
        scanner.close();

        int wordCount = countWordsStartingWithLetter(inputString, letter);
        System.out.println("Number of words starting with '" + letter + "': " + wordCount);
    }

    private static int countWordsStartingWithLetter(String inputString, char letter) {
        int count = 0;
        String[] words = inputString.split("\\s+");

        for (String word : words) {
            if (Character.toLowerCase(word.charAt(0)) == Character.toLowerCase(letter)) {
                count++;
            }
        }

        return count;
    }
}