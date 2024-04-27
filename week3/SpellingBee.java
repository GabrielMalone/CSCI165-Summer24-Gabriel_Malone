// Gabriel Malone / Weeek 3 / CSCI165 / Summer 2024

import java.awt.Color;	// import the Color class
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellingBee {

	boolean solved = false; // prevent user from trying to enter new words once puzzle solved
	int totalUserScore = 0; // keep track of total user score
	// class level ArrayList for all of the words in the dictionary
	ArrayList<String> words = loadWords("EnglishWords.txt");
	// list for the found words
	ArrayList<String> validWords = new ArrayList<String>(); 
	// list for bonus words
	ArrayList<String> beeHiveBonusWords = new ArrayList<String>(); 
   
	private void shuffleBeeString(SpellingBeeGraphics sbg){
		// shuffle beehive word, but center (index 0) needs to remain the same
		String beehiveWord = sbg.getBeehiveLetters();
		// get center character
		char center = beehiveWord.charAt(0);
		// get all letters besides the center character
		String remainingLetters = beehiveWord.substring(1, beehiveWord.length());
		// put the remaining letters into an array
		List<Character> characters = new ArrayList<>(); 
	        for (char c : remainingLetters.toCharArray()) { 
	            	characters.add(c); }
		// shuffle the list
		Collections.shuffle(characters);
		// convert list back to a string
		StringBuilder shuffledString = new StringBuilder(); 
		for (char d : characters) { 
            		shuffledString.append(d); 
       		 } 
		// concatenate the center letter + the remaining shuffled letters
		String shuffled = shuffledString.toString();
		shuffled = center + shuffled;
		// update the beehiveword
		sbg.setBeehiveLetters(shuffled);
   	 } 

	private boolean alreadyInList(String word){
		// check to see if a user has already used this valid word
		// seperate the word and score tuple
		if (validWords.contains(word.toLowerCase()) || beeHiveBonusWords.contains(word.toLowerCase())) return true;
		else return false;
	}

	private boolean allSeven(String word, 
							String beehiveword){
		// iterate through the beehive word and see if all letters
		// in the beehive word are present in the valid word
		// for bonus points 
		for(int i = 0; i < beehiveword.length(); i ++){
			String a = beehiveword.substring(i, i+1).toLowerCase();
			if (! word.toLowerCase().contains(a)) return false;	
		}
		return true;
	}
	 
	private int score(  String word, 
						String beehiveWord, 
						ArrayList<String> beeHiveBonusWords){
		// add score to each valid word
		// one point per letter
		int score = word.length();
		// above code is true for all cases except length of 4
		if (score == 4) score = 1;
		// if word uses all 7 letters give bonus
		if (word.length() > 6  && allSeven(word, beehiveWord)){
			score += 7;
			beeHiveBonusWords.add(word);
		} 
		return score;
	}
		
	private static void addUserWordToDisplay(ArrayList<String> beeHiveBonusWords, 
						 String word, 
						 SpellingBeeGraphics sbg){
		// seperate the word and score tuple
		int index = word.indexOf(" ");
		// if the word contains all the beehive letters and is 7 or more letters
		String splitWord = word.substring(0,index);
		if (beeHiveBonusWords.contains(splitWord)) sbg.addWord(word, Color.BLUE);
		// otherwise print word and score in black
		else sbg.addWord(word);
	}
	
	private static void addWordsToDisplay(ArrayList<String> validWords, 
					      ArrayList<String> beeHiveBonusWords, 
					      SpellingBeeGraphics sbg){
		// iterate through valid word list
		for (String word : validWords){
			// seperate the word and score tuple
			int index = word.indexOf(" ");
			// if the word contains all the beehive letters and is 7 or more letters
			String splitWord = word.substring(0,index);
			// print the word and score in blue
			if (beeHiveBonusWords.contains(splitWord)) sbg.addWord(word, Color.BLUE);
			// otherwise print word and score in black
			else sbg.addWord(word);
		}
	}
	
	public static ArrayList<String> loadWords(String fileName){
		ArrayList<String> words = new ArrayList<String>();             // create an instance of the ArrayList class
		try{
			File file 			= new File(fileName);	// create a File object
			Scanner fileScanner = new Scanner(file);		// Create a scanner for the file object
			String word 		= "";				// string variable to hold a word
			while(fileScanner.hasNext()){				// while the file has more tokens
				word = fileScanner.next();			// grab the next token
				words.add(word);				// add it to the ArrayList
			}
			fileScanner.close();					// close the scanner object  
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Problem opening the file: " + fnfe.getMessage());
		}
		return words;	
	}

	public void run() {

		SpellingBeeGraphics sbg = new SpellingBeeGraphics();

		sbg.addButton("Shuffle", (s) -> shuffleBeeString(sbg));

		sbg.addField("Puzzle", (s) -> puzzleAction(s, sbg));

		sbg.addField("Word",  (s) ->  wordAction(s, sbg));

		sbg.addButton("Solve", (s) -> solveAction(sbg));

		
	}
 
	private void totalWordsScoreDisplay(ArrayList<String> validWords, 
					    int totalScore, 
					    SpellingBeeGraphics sbg ){
		String totalWords = validWords.size() + " words; " + totalScore + " points";
		// display word total
		sbg.showMessage(totalWords);
	}

	private String scoreWord(String word, 
				 String beehiveWord, 
				 ArrayList<String> beeHiveBonusWords){
		// add score to each valid word
		// one point per letter
		int score = score(word, beehiveWord, beeHiveBonusWords);
		String newWordString = word + " (" + score + ") ";
		return newWordString;

	}
	
	private boolean centerLetterCheck(String beehiveword, String word){
		// check to see if any part of the, thus far valid, 
		// word contains the first letter of the beehive word (index 0)
		// get everything the same case regardless of input
		if (word.toLowerCase().contains(beehiveword.toLowerCase().substring(0, 1))) return true;
		else return false;
	}
	
	private boolean onlyBeeHiveLetters(String beehiveWord, String word){
		// iterate through the dictionary word and see if all letters
		// in the dictionary word are present in the beehive word. 
		for(int i = 0; i < word.length(); i ++){
			// get everything the same case, regardless of how inputted
			String a = word.substring(i, i+1).toLowerCase();
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
		int i = 0;               	      	  // outer loop stepper
		int j = 1;               	  	  // inner loop stepper
		while(i < s.length()){	 	  	  // outer while loop head
			char a = s.charAt(i);	 	  // 'a' is the character that will be compared against all other characters ('b') in the word
			while(j < s.length()){    	  // inner loop head 
				char b = s.charAt(j); 	  // all the other characters that are not 'a'. 
				if(a == b) return false;  // if 'a' equals 'b' then not all characters unique // break out of loop if match found, no need to continue	
				else
				j += 1; 		   // if no pairs found, move on. 
					}          	   // end of inner-loop
			j =  i + 2; 			   // reset inner loop to always start at the letter following 'a'
			i += 1;     			   // progress outer-loop ('a' moved to next letter in the word)
			}           			   // end of outer-loop
		return true;				   // return true if all unique
		}               			  		
		
	private boolean realWordCheck(String s){
		boolean valid = false;
		// iterate through dictionary and see if there's a match
		for (String word : words){
			if (s.toLowerCase().equals(word))
			{valid = true; 
			break;}
		}
		return valid;
	}

	private boolean validLetterCheck(String s){
	for(int k = 0; k < s.length(); k++){
		// char to int type conersion
		int check = s.charAt(k);
		// check to see if char is in range of English alphabet chars
		if(check < 65 || check > 122){     
			// if not...
			return false;		  
			} // end if-statement
		} // end of for-loop
	return true;
	}

	private void wordAction(String s, SpellingBeeGraphics sbg) {
		
		boolean validWord = true; // innocent until proven guilty
		String beehiveWord = sbg.getBeehiveLetters(); // get the beehive word
		sbg.clearField("Word"); // clear field after word is entered
		
		// IF PUZZLE ALREADY SOLVED
		if (solved == true){
			validWord = false;
			sbg.showMessage("Puzzle already solved.", Color.GRAY);}
		// LENGTH CHECK (4 min)
		else if (! lengthCheckForScore(s)){
			validWord = false;
			sbg.showMessage("Word must be 4 characters long.", Color.GRAY);}
		// VALID LETTERS CHECK	
		else if (! validLetterCheck(s)){
			validWord = false;
			sbg.showMessage("Word must contain letters only.", Color.GRAY);}
		// LETTERS IN THE BEEHIVE
		else if (! onlyBeeHiveLetters(beehiveWord, s)){
			validWord = false;
			sbg.showMessage("Word must contain letters found only in the beehive.", Color.GRAY);}
		// CENTER LETTER
		else if (! centerLetterCheck(beehiveWord, s)){
			validWord = false;
			sbg.showMessage("Word must contain the center letter.", Color.GRAY);}
		// WORD IN DICTIONARY
		else if (! realWordCheck(s)){
			validWord = false;
			sbg.showMessage("Word must be in the dictionary.", Color.GRAY);}
		// IF NOT ALREADY IN LIST
		else if (alreadyInList(s)){
			validWord = false;
			sbg.showMessage("Word already found.", Color.GRAY);}           
		// IF NO TEST FAILED	
		else if (validWord){
			sbg.showMessage(""); // remove any error messages
			// score each word
			int score = score(s, beehiveWord, beeHiveBonusWords);
			// update round total score
			totalUserScore += score;
			// create string of word and score for display
			String s_score = scoreWord(s, beehiveWord, beeHiveBonusWords);
			// add each word to a new ArrayList
			validWords.add(s);
			// display score and words found
			totalWordsScoreDisplay(validWords, totalUserScore, sbg);
			addUserWordToDisplay(beeHiveBonusWords, s_score, sbg);
		} 
		  
	}     

	private void puzzleAction(String s, SpellingBeeGraphics sbg) {
		
		validWords = new ArrayList<String>(); // clear array
		beeHiveBonusWords = new ArrayList<String>(); // clear array
		totalUserScore = 0; // reset
		sbg.clearWordList(); // clear list when new valid puzzle being solved
		sbg.clearField("Puzzle");
		boolean validWord = true;
		// LENGTH CHECK
		if  (! lengthCheck(s)){
			validWord = false;
			sbg.showMessage("Word must be 7 characters long.", Color.ORANGE); }
		// UNIQUE LETTERS CHECK
		else if (! uniqueCheck(s)){
			validWord = false;
			sbg.showMessage("Word must contain unique letters only.", Color.ORANGE);}
		// VALID LETTERS CHECK	
		else if (! validLetterCheck(s)){
			validWord = false;
			sbg.showMessage("Word must contain letters only.", Color.ORANGE);}
		// IF NO TEST FAILED	
		else if (validWord){
			sbg.setBeehiveLetters(s); // set the puzzle
			sbg.showMessage(""); // remove any error messages
			solved = false; // reset
		} 
	} 
		
	private void solveAction(SpellingBeeGraphics sbg) {
		solved = true; // puzzle solved
		int totalScore = 0; // keep track of total score 
		validWords = new ArrayList<String>(); // clear array
		beeHiveBonusWords = new ArrayList<String>(); // clear array
		sbg.clearWordList(); // clear list when new valid puzzle being solved
		// find all valid words for Puzzle Word
		String beehiveWord = sbg.getBeehiveLetters();
		for(String word : words){
			// innocent until proven guilty
			boolean okayToAdd = true;
			// if word is at least 4 characters long 
			if (! lengthCheckForScore(word)) okayToAdd = false;
			// and contains only letters found in the beehive word
			else if (! onlyBeeHiveLetters(beehiveWord, word)) okayToAdd = false;
			// and contains the first character of the beehiveword
			else if (! centerLetterCheck(beehiveWord, word)) okayToAdd = false;
			// okay to add word 
			else if (okayToAdd) {
				// score each word
				int score = score(word, beehiveWord, beeHiveBonusWords);
				// update round total score
				totalScore += score;
				// create string of word and score
				word = scoreWord(word, beehiveWord, beeHiveBonusWords);
				// add each word to a new ArrayList
				validWords.add(word);
			}
		} 
		// display valid words in app
		addWordsToDisplay(validWords, beeHiveBonusWords, sbg);
		// display total valid words
		totalWordsScoreDisplay(validWords, totalScore, sbg);  
	}

	/* Startup code */
	public static void main(String[] args) { // application starts here
		SpellingBee sb = new SpellingBee();
		sb.run(); // call the run method (defined below)
	}
}
