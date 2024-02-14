import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.List;

public class TrieST<Value> {

    private static int R = 256; // radix
    private Node root; // root of trie

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public TrieST() {
        this.root = new Node();
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;
        return (Value) x.val;

    }

    private Node get(Node x, String key, int d) {
        // Return value associated with key in the subtrie rooted at x.
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {

        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) { // Change value associated with key if in subtrie rooted at
                                                             // x.
        if (x == null)
            x = new Node();
        if (d == key.length()) {
            x.val = (Integer) val;
            return x;
        }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public Value search(String key) {
        Node x = search(root, key, 0);
        if (x == null)
            return null;
        return (Value) x.val;
    }

    private Node search(Node x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d);
        return search(x.next[c], key, d + 1);
    }

    public void exit() {
        System.out.println("Exiting...");
        // İstenen çıkış işlemleri buraya eklenebilir.
    }

    public void countAndPrintWordsWithPrefix() {
        countAndPrintWordsWithPrefix(root, "");
    }

    private void countAndPrintWordsWithPrefix(Node x, String currentPrefix) {
        if (x == null)
            return;
        if (x.val != null) {
            System.out.println(currentPrefix + ": " + countWordsWithPrefix(currentPrefix));
        }

        for (char c = 0; c < R; c++) {
            String nextPrefix = currentPrefix + c;
            countAndPrintWordsWithPrefix(x.next[c], nextPrefix);
        }
    }

    private int countWordsWithPrefix(String prefix) {
        LinkedList<String> queue = new LinkedList<String>();
        Node x = get(root, prefix, 0);
        collectWithPrefix(x, prefix, queue);

        // Remove the original prefix itself from the queue
        queue.remove(prefix);

        // Count the number of words with the given prefix
        int count = queue.size();
        return count;
    }

    private void collectWithPrefix(Node x, String currentPrefix, Queue<String> q) {
        if (x == null)
            return;
        if (x.val != null)
            q.add(currentPrefix);

        for (char c = 0; c < R; c++) {
            String nextPrefix = currentPrefix + c;
            collectWithPrefix(x.next[c], nextPrefix, q);
        }
    }

    public void reverseFindAndPrint(String suffix) {
        // Create a list to store words with the given suffix
        List<String> wordsWithSuffix = reverseFind(suffix);

        // Sort the collected words lexicographically
        Collections.sort(wordsWithSuffix);

        // Print the sorted words
        for (String word : wordsWithSuffix) {
            System.out.println(word);
        }
    }

    private List<String> reverseFind(String suffix) {
        // Create a list to store words with the given suffix
        List<String> wordsWithSuffix = new ArrayList<>();

        // Traverse the Trie to collect words with the specified suffix
        reverseFind(root, "", suffix, wordsWithSuffix);

        return wordsWithSuffix;
    }

    private void reverseFind(Node x, String currentWord, String suffix, List<String> result) {
        if (x == null) {
            return;
        }

        int d = currentWord.length();

        // Check if the current word ends with the specified suffix
        if (d >= suffix.length() && currentWord.endsWith(suffix) && x.val != null) {
            result.add(currentWord);
        }

        // Traverse all child nodes
        for (char c = 0; c < R; c++) {
            reverseFind(x.next[c], currentWord + c, suffix, result);
        }
    }

    public void shortestUniquePrefix() {
        shortestUniquePrefix(root, "");
    }

    private void shortestUniquePrefix(Node x, String currentPrefix) {
        if (x == null)
            return;

        int countChildren = 0;
        char uniqueChar = 0;

        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                countChildren++;
                uniqueChar = c;
            }
        }

        if (countChildren == 1 && x.val == null) {
            System.out.println(currentPrefix + uniqueChar);
            shortestUniquePrefix(x.next[uniqueChar], currentPrefix + uniqueChar);
        } else if (countChildren > 1 || (countChildren == 1 && x.val != null)) {
            List<String> wordsWithPrefix = getWordsWithPrefix(currentPrefix);
            if (wordsWithPrefix.size() > 1) {
                System.out.println(currentPrefix);
            } else {
                System.out.println(currentPrefix + ": not exists");
            }
        } else {
            for (char c = 0; c < R; c++) {
                if (x.next[c] != null) {
                    shortestUniquePrefix(x.next[c], currentPrefix + c);
                }
            }
        }
    }

    // Yeni fonksiyon getWordsWithPrefix() eklendi
    // Yeni fonksiyon getWordsWithPrefix() içeriği değiştirildi
    private List<String> getWordsWithPrefix(String prefix) {
        LinkedList<String> queue = new LinkedList<>();
        Node x = get(root, prefix, 0);
        collectWithPrefix(x, prefix, queue);

        // Kuyruğun içeriğini kontrol et
        System.out.println("Queue contents: " + queue);

        // Farklı kelimeleri çıkar
        queue.removeIf(word -> !word.startsWith(prefix));

        return queue;
    }

    public void longestCommonPrefix() {
        String result = findLongestCommonPrefix(root);
        if (result.isEmpty()) {
            System.out.println("not exists");
        } else {
            System.out.println(result);
        }
    }

    private String findLongestCommonPrefix(Node x) {
        if (x == null) {
            return "";
        }

        int countChildren = 0;
        char uniqueChar = 0;

        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                countChildren++;
                uniqueChar = c;
            }
        }

        if (countChildren == 1 && x.val == null) {
            return uniqueChar + findLongestCommonPrefix(x.next[uniqueChar]);
        } else if (countChildren > 1 || (countChildren == 1 && x.val != null)) {
            return "";
        }

        // If countChildren == 0, meaning it's a leaf node, return an empty string
        return "";
    }

}
