import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Node {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        left = null;
        right = null;
    }
}

class NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        return n1.freq - n2.freq;
    }
}

public class code {

    private static Node buildHuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node newNode = new Node('-', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;

             pq.add(newNode);
        }

         return pq.poll();
    }

     private static void generateCodes(Node root, String code, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, code);
        }

        generateCodes(root.left, code + "0", huffmanCode);

        generateCodes(root.right, code + "1", huffmanCode);
    }

    private static Map<Character, Integer> calculateFrequency(String text) {
        Map<Character, Integer> freqMap = new HashMap<>();

        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        return freqMap;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("C://Users//AMAN BISHT//Desktop//college//new_dev/Huffman coding//chapter1.txt");
            return;
        }

        String inputFilePath = args[0];
        StringBuilder text = new StringBuilder();

        // Read the file content
        try (Scanner scanner = new Scanner(new File("C://Users//AMAN BISHT//Desktop//college//new_dev/Huffman coding//chapter1.txt"))) {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + "C://Users//AMAN BISHT//Desktop//college//new_dev/Huffman coding//chapter1.txt");
            return;
        }

        Map<Character, Integer> freqMap = calculateFrequency(text.toString());

        Node root = buildHuffmanTree(freqMap);

        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(root, "", huffmanCode);

        System.out.println("Huffman Codes: " + huffmanCode);

        StringBuilder encodedString = new StringBuilder();
        for (char ch : text.toString().toCharArray()) {
            encodedString.append(huffmanCode.get(ch));
        }

        System.out.println("Encoded String: " + encodedString.toString());
    }
}
