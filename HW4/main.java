import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TrieST<Integer> trie = new TrieST<>();

        int numOfWords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numOfWords; i++) {

            String word = scanner.nextLine();
            trie.put(word, i + 1); // Assuming each word has a unique value, you can modify this as needed
        }

        System.out.println("Enter Operation Code:");

        int operationCode;

        do {

            operationCode = scanner.nextInt();
            scanner.nextLine(); //

            switch (operationCode) {
                case 1:
                    String searchKey = scanner.nextLine();
                    boolean searchResult = trie.search(searchKey) != null;
                    System.out.println(searchResult);
                    System.out.println("Enter Operation Code:");
                    break;
                case 2:
                    trie.countAndPrintWordsWithPrefix();
                     System.out.println("Enter Operation Code:");
                    break;
                case 3:
                    String userChar = scanner.next();

                    trie.reverseFindAndPrint(userChar);
                     System.out.println("Enter Operation Code:");

                    break;
                case 4:
                    trie.shortestUniquePrefix();
                     System.out.println("Enter Operation Code:");

                    break;
                case 5:
                    trie.longestCommonPrefix();
                     System.out.println("Enter Operation Code:");

                    break;
                case 6:
                    trie.exit();

                    break;
                default:
                    System.out.println("Invalid operation code. Please enter a valid code.");
                    break;
            }

        } while (operationCode != 6);

        scanner.close();
    }

}
