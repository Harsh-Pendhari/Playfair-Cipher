import java.util.*;

public class Playfair {
    private char[][] keyMatrix = new char[5][5];

    // Build 5x5 key matrix
    private void buildKeyMatrix(String key) {
        String alph = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // J = I
        String data = (key + alph).toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        for (char c : data.toCharArray()) set.add(c);
        Iterator<Character> it = set.iterator();
        for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) keyMatrix[i][j] = it.next();
    }

    private int[] pos(char c) {
        if (c == 'J') c = 'I';
        for (int i = 0; i < 5; i++) for (int j = 0; j < 5; j++) if (keyMatrix[i][j] == c) return new int[]{i,j};
        return null;
    }

    private String processPair(char a, char b, boolean enc) {
        if (a == 'J') a = 'I';
        if (b == 'J') b = 'I';
        int[] p1 = pos(a), p2 = pos(b);
        int r1 = p1[0], c1 = p1[1], r2 = p2[0], c2 = p2[1];
        if (r1 == r2) { // same row
            c1 = (c1 + (enc?1:4)) % 5; c2 = (c2 + (enc?1:4)) % 5;
        } else if (c1 == c2) { // same col
            r1 = (r1 + (enc?1:4)) % 5; r2 = (r2 + (enc?1:4)) % 5;
        } else { // rectangle
            int tmp = c1; c1 = c2; c2 = tmp;
        }
        return ""+keyMatrix[r1][c1]+keyMatrix[r2][c2];
    }

    public String encrypt(String text, String key) {
        buildKeyMatrix(key);
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J","I");
        if (text.length()%2!=0) text += "X";
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<text.length();i+=2) sb.append(processPair(text.charAt(i), text.charAt(i+1), true));
        return sb.toString();
    }

    public String decrypt(String text, String key) {
        buildKeyMatrix(key);
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<text.length();i+=2) sb.append(processPair(text.charAt(i), text.charAt(i+1), false));
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Playfair pf = new Playfair();
        System.out.print("Enter plaintext: ");
        String pt = sc.nextLine();
        System.out.print("Enter key: ");
        String key = sc.nextLine();
        String ct = pf.encrypt(pt,key);
        System.out.println("Encrypted: " + ct);
        System.out.println("Decrypted: " + pf.decrypt(ct,key));
    }
}
