/**
 * Engine.java
 *
 * This class processes the functionalities provided by KryptosP4.
 *
 * @author Sean Pea
 * @version 1.0
 */

package kryptos;

import cipher.Playfair;
import java.util.Scanner;

public class Engine {

    Scanner scan;

    public Engine() {
        scan = new Scanner(System.in);
    }

    public void doPlayfair() {
        // Variable
        char keyPair1, keyPair2, filler, process;
        String keyPair, key;
        String plainText, cipherText;
        
        // Print Function
        System.out.println("\n1a. Decrypt using Playfair Cipher");
        System.out.println("------------------------------------------");
        
        // Get Plaintext
        System.out.print("Enter Plain Text or Leave Empty for Default : ");
        plainText = scan.nextLine().toUpperCase();
        
        // Get Ciphertext
        System.out.print("Enter Cipher Text or Leave Empty for Default : ");
        cipherText = scan.nextLine().toUpperCase();
        
        // Get Key Pair
        System.out.print("Enter Keypair (e.g. IJ) : ");
        keyPair = scan.nextLine().toUpperCase();
        keyPair1 = keyPair.charAt(0);
        keyPair2 = keyPair.charAt(1);
        
        // Get Filler
        System.out.print("Enter Filler (e.g. X) : ");
        filler = scan.nextLine().toUpperCase().charAt(0);
        
        // Get Key
        System.out.print("Enter Key or Leave Empty for Default : ");
        key = scan.nextLine().toUpperCase();
        
        System.out.println("------------------------------------------");
        System.out.print("Encrypt or Decrypt (e/d) : ");
        process = scan.nextLine().toUpperCase().charAt(0);
        System.out.println();
        
        Playfair pf = new Playfair(keyPair1, keyPair2, filler,
                key, new StringBuffer(plainText), new StringBuffer(cipherText));
        
        pf.init();
        
        if (process == 'E') {
            pf.encrypt();
            System.out.println("Cipher Text : " + pf.getCipherText());
        } else {
            pf.decrypt();
            System.out.println("Plain Text : " + pf.getPlainText());
        }
        
       
        
        
        /*
         * For Error Checking
         *
        System.out.println(pf);
        
        System.out.println(keyPair1);
        System.out.println(keyPair2);
        System.out.println(filler);
        System.out.println(key);
         *
         */

    }
}
