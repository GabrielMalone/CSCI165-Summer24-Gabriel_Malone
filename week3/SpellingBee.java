/*
 * File: SpellingBee.java
 * ----------------------
 * This program contains the starter file for the SpellingBee application.
 * 
 * Your work will all go here
 * 
 * BE SURE TO CHANGE THIS COMMENT WHEN YOU COMPLETE YOUR SOLUTION.
 * 
 * to run this app, at the terminal, type:
 * 	javac SpellingBee.java
 * 	java SpellingBee
 */

import java.awt.Color;	// import the Color class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class SpellingBee {
    
    public void run() {
        // create a new SpellingBeeGraphics object. Use this to call methods to add fields and buttons
        SpellingBeeGraphics sbg = new SpellingBeeGraphics();

        /*
         * 	WORKFLOW:
         *  ========
         * 1. Add a field or button depending on the need: sbg.addField or sbg.addButton
         * 2. Define the action that should be taken when the field or button is used
         * 3. The action will then be defined as a method with the same name
         * 4. Add the action to the field or button
         * 
         * Below are two examples of how to add a field and a button
         */

        // add a new text field with the name "Puzzle" and the action "puzzleAction"
        // "puzzleAction" should be defined as a method that takes a string as a parameter
        sbg.addField("Puzzle", (s) -> puzzleAction(s, sbg));
    
        // add a new Button with the name "Solve" and the action "solveAction"
        // "solveAction" should be defined as a method that takes no parameters
        sbg.addButton("Solve", (s) -> solveAction(sbg));
    }

    // define the puzzleAction method that will execute when 
    // you hit "ENTER" after clicking in the "Puzzle" field
    // The contents of the field will be automatically passed to the method as a string
    
    
    private boolean centerLetterCheck(String beehiveword, String word){
        // check to see if any part of the, thus far valid, 
        // word contains the first letter of the beehive word (index 0)
        if (word.contains(beehiveword.toLowerCase().substring(0, 1))) return true;
        else return false;
    }

    private boolean onlyBeeHiveLetters(String beehiveWord, String word){
        // iterate through the dictionary word and see if all letters
        // in the dictionary word are present in the beehive word. 
        for(int i = 0; i < word.length(); i ++){
            String a = word.substring(i, i+1);
            if (! beehiveWord.toLowerCase().contains(a)) return false;	
            }
        return true;
        }
    
    private boolean lengthCheck(String s) {
        // check length of puzzle word
        if (s.length() != 7) return false;
        else return true;
        }

    private boolean lengthCheckForScore(String s) {
        // check length of valid words
        if (s.length() < 4) return false;
        else return true;
        }
    
    private boolean uniqueCheck(String s){		
        int i = 0;               	      	// outer loop stepper
        int j = 1;               	  	    // inner loop stepper
        while(i < s.length()){	 	  		// outer while loop head
            char a = s.charAt(i);	 		// 'a' is the character that will be compared against all other characters ('b') in the word
            while(j < s.length()){    		// inner loop head 
                char b = s.charAt(j); 		// all the other characters that are not 'a'. 
                if(a == b) return false;	// if 'a' equals 'b' then not all characters unique // break out of loop if match found, no need to continue	
                else
                j += 1; 		  			// if no pairs found, move on. 
                    }       			    // end of inner-loop
            j =  i + 2; 			  		// reset inner loop to always start at the letter following 'a'
            i += 1;     			  		// progress outer-loop ('a' moved to next letter in the word)
            }           			    	// end of outer-loop
        return true;
        }               			  		
        
    private boolean validLetterCheck(String s){
        for(int k = 0; k < s.length(); k++){
            // char to int type conersion
            int check = s.charAt(k);
            if(check < 65 || check > 122){ // check to see if char is in range of English alphabet chars
                return false;		   // if not...
                } // end if-statement
            } // end of for-loop
        return true;
        }

    private void puzzleAction(String s, SpellingBeeGraphics sbg) {
        
        boolean validWord = true;
        // LENGTH CHECK
        if  (! lengthCheck(s)){
            validWord = false;
            sbg.showMessage("Word must be 7 characters long.", Color.RED); }
        // UNIQUE LETTERS CHECK
        else if (! uniqueCheck(s)){
            validWord = false;
            sbg.showMessage("Word must contain unique letters only.", Color.ORANGE);}
        // VALID LETTERS CHECK	
        else if (! validLetterCheck(s)){
            validWord = false;
            sbg.showMessage("Word must contain letters only.", Color.GRAY);}
        // IF NO TEST FAILED	
        else if (validWord){
            sbg.setBeehiveLetters(s); // set the puzzle
            sbg.showMessage("", Color.RED); // remove any error messages
            } 
        } 
        
    private void solveAction(SpellingBeeGraphics sbg) {
        sbg.clearWordList(); // clear list when new valid puzzle being solved
        String beehiveWord = sbg.getBeehiveLetters();
        
        try{
            FileReader fileReader = new FileReader("lab/EnglishWords.txt");
            Scanner    dictionary = new Scanner(fileReader);
            while(dictionary.hasNextLine()){
                boolean okayToAdd = true;
                String word = dictionary.nextLine();
                // if word is at least 4 characters long 
                if (! lengthCheckForScore(word)) okayToAdd = false;
                // and contains only letters found in the beehive word
                else if (! onlyBeeHiveLetters(beehiveWord, word)) okayToAdd = false;
                // and contains the first character of the beehiveword
                else if (! centerLetterCheck(beehiveWord, word)) okayToAdd = false;
                // add the word to the list 
                else if (okayToAdd) sbg.addWord(word);
                }
            dictionary.close();
            }
        catch(FileNotFoundException fnf){
            System.out.println("ERROR: FILE NOT FOUND!");
            }		
        }

    /* Constants */
    // The name of the file containing the puzzle dictionary
    private static final String ENGLISH_DICTIONARY = "EnglishWords.txt";

    /* Private instance variables */
    private SpellingBeeGraphics sbg;	// no instance created yet, just scoped the reference variable

    /* Startup code */
    public static void main(String[] args) {	// application starts here
        SpellingBee sb = new SpellingBee();
        sb.run();// call the run method (defined below)
    }
}
