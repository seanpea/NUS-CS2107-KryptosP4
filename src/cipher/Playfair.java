/**
 * Playfair.java
 *
 * This class provides the encrytion and decryption function
 * using the Playfair Cipher.
 *
 * @author Sean Pea
 * @version 1.0
 */
package cipher;

import helper.Point2D;

public class Playfair extends Cipher<String> {

    /*
     * Instance Variables
     */
    private char keyPair1;
    private char keyPair2;
    private char filler;
    private char[][] keyTable = {{'A', 'B', 'C', 'D', 'E'},
        {'F', 'G', 'H', 'I', 'K'},
        {'L', 'M', 'N', 'O', 'P'},
        {'Q', 'R', 'S', 'T', 'U'},
        {'V', 'W', 'X', 'Y', 'Z'}};

    /*
     * Constructor
     */
    public Playfair(char keyPair1, char keyPair2,
            char filler, String key,
            StringBuffer plainText,
            StringBuffer cipherText) {
        // Call Superclass Constructor
        super(plainText, cipherText, key);
        // Initialize Variables
        this.keyPair1 = keyPair1;
        this.keyPair2 = keyPair2;
        this.filler = filler;
    }
    
    /*
     * Public Methods
     */
    public void init() {
        // Normalize Key
        normalizeKey();
        // Initialize Key Table
        initKeyTable();
    }

    /**
     * pre-condition: plain text cannot be null
     *
     * post-condition: set the variable cipherText with cipher text
     *
     * Description:
     *
     * This method encrypt a plain text
     *
     * Rules:
     *
     * (1) If both letters are the same (or only one letter is left), add a
     * FILLER after the first letter
     *
     * (2) If the letters appear on the same row of the key table, replace them
     * with the letters to their immediate right respectively (wrapping around
     * to the left side of the row)
     *
     * (3) If the letters appear on the same column of the key table, replace
     * them with the letters immediately below respectively (wrapping around to
     * the top side of the column
     *
     * (4) If the letters are not on the same row or column, replace them with
     * the letters on the same row respectively but at the other pair of corners
     * of the rectangle defined by the original pair
     *
     * The order is important – the first letter of the encrypted pair is the
     * one that lies on the same row as the first letter of the plaintext pair.
     */
    @Override
    public void encrypt() {
        // Duplicate Plain Text
        StringBuilder plain = new StringBuilder(plainText);
        // Convert to Pairs
        char[][] plainPair;
        if (plain.length() % 2 == 0) {
            plainPair = new char[(plain.length() / 2)][2];
            for (int i = plain.length() - 2; i >= 0; i -= 2) {
                plainPair[i / 2][0] = plain.charAt(i);
                plainPair[i / 2][1] = plain.charAt(i + 1);
            }
        } else {
            plainPair = new char[(plain.length() / 2 + 1)][2];
            for (int i = plain.length() - 1; i >= 0; i -= 2) {
                plainPair[i / 2][0] = plain.charAt(i);
                if (i != plain.length() - 1) {
                    plainPair[i / 2][1] = plain.charAt(i + 1);
                }
            }
        }
        // Add Intermediate Filler(s)
        plain = new StringBuilder();
        for (int i = 0; i < plainPair.length; i++) {
            plain.append(plainPair[i][0]);
            if ((plainPair[i][0] == keyPair1 && plainPair[i][1] == keyPair2)
                    || (plainPair[i][0] == keyPair2 && plainPair[i][1] == keyPair1)
                    || (plainPair[i][0] == plainPair[i][1])) {
                plain.append(filler);
            }
            if (i < (plainPair.length - 1) || plainPair[i][1] != '\u0000') {
                plain.append(plainPair[i][1]);
            }
        }

        // Add Trailing Filler
        if (plain.length() % 2 != 0) {
            plain.append(filler);
        }

        /*
         * For Error Checking
         *
        System.out.println(plain.length());
        System.out.println(plain);
         * 
         */
        
        // Encrypt
        cipherText = new StringBuffer();

        Point2D pt1, pt2;

        for (int i = 0; i < plain.length(); i += 2) {
            pt1 = searchKeyTable(plain.charAt(i));
            pt2 = searchKeyTable(plain.charAt(i + 1));

            // Rule 2: Same Row
            if (pt1.getX() == pt2.getX()) {
                cipherText.append(keyTable[pt1.getX()][(pt1.getY() + 1) % 5]);
                cipherText.append(keyTable[pt2.getX()][(pt2.getY() + 1) % 5]);
            } // Rule 3: Same Column
            else if (pt1.getY() == pt2.getY()) {
                cipherText.append(keyTable[(pt1.getX() + 1) % 5][pt1.getY()]);
                cipherText.append(keyTable[(pt2.getX() + 1) % 5][pt2.getY()]);
            } // Rule 4: Different Row and Column
            else {
                cipherText.append(keyTable[pt1.getX()][pt2.getY()]);
                cipherText.append(keyTable[pt2.getX()][pt1.getY()]);
            }
        }

        /*
         * For Error Checking
         *
        System.out.println(cipherText.length());
        System.out.println(cipherText);
         * 
         */

    }

    /**
     * pre-condition: cipher text cannot be null
     *
     * post-condition: set the variable plainText with plain text
     *
     * Description:
     *
     * This method decrypt a cipher text
     *
     * Rules:
     *
     * (1) If the letters appear on the same row of the key table, replace them
     * with the letters to their immediate left respectively (wrapping around to
     * the right side of the row)
     *
     * (2) If the letters appear on the same column of the key table, replace
     * them with the letters immediately above respectively (wrapping around to
     * the bottom side of the column
     *
     * (3) If the letters are not on the same row or column, replace them with
     * the letters on the same row respectively but at the other pair of corners
     * of the rectangle defined by the original pair
     *
     * The order is important – the first letter of the encrypted pair is the
     * one that lies on the same row as the first letter of the plaintext pair.
     */
    @Override
    public void decrypt() {
        // Decrypt
        StringBuilder decryptedText = new StringBuilder();

        Point2D pt1, pt2;

        for (int i = 0; i < cipherText.length(); i += 2) {
            pt1 = searchKeyTable(cipherText.charAt(i));
            pt2 = searchKeyTable(cipherText.charAt(i + 1));

            // Rule 1: Same Row
            if (pt1.getX() == pt2.getX()) {
                decryptedText.append(keyTable[pt1.getX()][(pt1.getY() + 4) % 5]);
                decryptedText.append(keyTable[pt2.getX()][(pt2.getY() + 4) % 5]);
            } // Rule 2: Same Column
            else if (pt1.getY() == pt2.getY()) {
                decryptedText.append(keyTable[(pt1.getX() + 4) % 5][pt1.getY()]);
                decryptedText.append(keyTable[(pt2.getX() + 4) % 5][pt2.getY()]);
            } // Rule 3: Different Row and Column
            else {
                decryptedText.append(keyTable[pt1.getX()][pt2.getY()]);
                decryptedText.append(keyTable[pt2.getX()][pt1.getY()]);
            }
        }
        
        plainText = new StringBuffer(decryptedText);
        /*
         * For Error Checking
         *
        System.out.println(plainText.length());
        System.out.println(plainText);

        System.out.println(decryptedText.length());
        System.out.println(decryptedText);
         * 
         */
    }
    
    public String printKeyTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyTable.length; i++) {
            for (int j = 0; j < keyTable.length; j++) {
                sb.append(" ");
                sb.append(keyTable[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /*
     * Private Methods
     */
    private void normalizeKey() {
        // Convert to Upper Case
        key = key.toUpperCase();
        // Convert to String Builder
        StringBuilder sb = new StringBuilder(key);
        // Loop to remove duplicates
        for (int i = 0; i < sb.length(); i++) {
            String s = "" + sb.charAt(i);
            int index;
            do {
                index = sb.indexOf(s, (i + 1));
                if (index != -1) {
                    sb.deleteCharAt(index);
                }
            } while (index != -1);
        }
        
        /*
         * For Error Checking
         *
        System.out.println(sb);
         * 
         */
        
        // Return Result
        key = sb.toString();
    }

    private void initKeyTable() {
        // Create the rest of the Key
        StringBuilder chars;
        // Remove Key Pair 2 from Orginal Key
        String c = "" + keyPair2;
        int index = key.indexOf(c);
        if (index != -1) {
            chars = new StringBuilder(key);
            chars.deleteCharAt(index);
            key = chars.toString();
        }
        // Remove Key Pair 2 from Remaining Key
        chars = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        index = chars.indexOf(c);
        chars.deleteCharAt(index);
        // Remove Key from Remaining Key
        for (int i = 0; i < key.length(); i++) {
            c = "" + key.charAt(i);
            index = chars.indexOf(c);
            if (index != -1) {
                chars.deleteCharAt(index);
            }
        }
        // Populate Key Table
        int count = 0, count2 = 0;
        for (int i = 0; i < keyTable.length; i++) {
            for (int j = 0; j < keyTable.length; j++) {
                if (count < key.length()) {
                    keyTable[i][j] = key.charAt(count++);
                } else {
                    keyTable[i][j] = chars.charAt(count2++);
                }
            }
        }
    }

    private Point2D searchKeyTable(char c) {
        if (c == keyPair2) {
            c = keyPair1;
        }
        for (int i = 0; i < keyTable.length; i++) {
            for (int j = 0; j < keyTable.length; j++) {
                if (keyTable[i][j] == c) {
                    return new Point2D(i, j);
                }
            }
        }
        return null;
    }

    /*
     * Accessor and Mutator Methods
     */
    public char getKeyPair1() {
        return keyPair1;
    }

    public void setKeyPair1(char keyPair1) {
        this.keyPair1 = keyPair1;
    }

    public char getKeyPair2() {
        return keyPair2;
    }

    public void setKeyPair2(char keyPair2) {
        this.keyPair2 = keyPair2;
    }

    public char getFiller() {
        return filler;
    }

    public void setFiller(char filler) {
        this.filler = filler;
    }

    public char[][] getKeyTable() {
        return keyTable;
    }

    public void setKeyTable(char[][] keyTable) {
        this.keyTable = keyTable;
    }

    @Override
    public String toString() {
        super.toString();
        return "Keypair : " + keyPair1 + "/" + keyPair2
                + "\nFiller : " + filler
                + "\nKey Table :\n" + printKeyTable();
    }
}
