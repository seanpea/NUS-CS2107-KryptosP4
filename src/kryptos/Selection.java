/**
 * Selection.java
 *
 * This class handles the interaction between the user and the system
 * by displaying the functionality available and forward the user's
 * requests to the engine.
 *
 * @author Sean Pea
 * @version 1.0
 */

package kryptos;

import java.util.Scanner;

public class Selection {
    
    Engine engine;

    public Selection() {
        engine = new Engine();
    }

    public void initKryptosP4(String[] args) {
        Scanner scan = new Scanner(System.in);

        displayWelcomeMessage();
        String choice = "";

        while (!choice.equals("0")) {

            try {
                displayMenu();
                choice = scan.nextLine();
                dispatch(choice);
            } catch (Exception ex) {
                System.err.println("\nERROR: Caught an unexpected exception!");
                //ex.printStackTrace();
                //System.out.println(ex.getMessage());
            }
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("==========================================");
        System.out.println("|               Kryptos P4               |");
        System.out.println("|             Cipher Decoder             |");
        System.out.println("|               Version 1.0              |");
        System.out.println("==========================================");
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("                Main Menu");
        System.out.println("------------------------------------------");
        System.out.println("Cipher");
        System.out.println("   1a. Playfair");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("------------------------------------------");
        System.out.print("Please Enter Your Choice : ");
    }

    private void dispatch(String choice) {
        // To lowercase
        choice = choice.toLowerCase();
        
        // 1a. Decrypt using Playfair Cipher
        if (choice.equalsIgnoreCase("1a")) {
            engine.doPlayfair();
        }
        // 0. Exit
        else if (choice.equalsIgnoreCase("0")) {
            System.out.println("\nTerminating Program...");
        }
        // Invalid Command
        else {
            System.out.println("\nInvalid Command.");
        }
    }
    
}
