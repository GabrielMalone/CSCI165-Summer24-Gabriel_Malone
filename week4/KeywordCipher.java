// Gabriel Malone / CS165 / Week 4 / Summer 2024

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class KeywordCipher {

    private static void writeToFile(String enciphered_string, String file_name){
        
        try{
            File output_file = new File(file_name);
            PrintWriter writer = new PrintWriter(output_file);
            writer.println(enciphered_string);
            writer.close();

        }catch(IOException ioe){
            System.out.printf("Could not write to %s%n.", file_name);
            System.exit(0);
        }
    }
    
    private static String arrayToString(ArrayList<String> new_string_array){
        
        // didn't want to write a new open file function
        String string_from_file = "";
        for ( String s : new_string_array){
            string_from_file += s.toUpperCase();
        }
        return string_from_file;
    }
    
    private static String prepareKeyWord(String keyWord){
        
        // new keyword initialize
        String uniqueKeyWord = "";
        // put characters from keyword into an array
        char[] charArray = keyWord.toCharArray();
        // iterate through the character array
        for(char c : charArray){
            // if the new string does not have the current character add it
            // only add alphabet characters
            if  (! uniqueKeyWord.contains(Character.toString(c)) 
                && Character.isAlphabetic(c)) 
                // add characters that pass the check
                uniqueKeyWord += c;
        } // for loop end
        return uniqueKeyWord.toUpperCase();
    }
    
    private static String generateCipherAlphabet(String uniqueKeyWord){
    
        // array to hold the keyword characters
        char[] charArray = uniqueKeyWord.toCharArray();
    
        String newCipherAlphabet = "";
        // A - Z string
        String rot13CharSet = ROT.rotCharacterSet(13);
        // iterate through array and start off new alphabet cipher with the keyword
        for(char c : charArray){
            newCipherAlphabet += c;
        }
        // attach the alphabet to the keyword
        newCipherAlphabet += rot13CharSet;
        
        // remove any duplicate letters
        String uniqueCipher = "";
        char[] cipherArray = newCipherAlphabet.toCharArray();
        // iterate through the characters of the keyword + alphabet
        for (char d : cipherArray){
            // skip any duplicates in making the new string
            if ( ! uniqueCipher.contains(Character.toString(d))) uniqueCipher += d ;
        }
        return uniqueCipher;
    }
    
    private static String encipher(String uniqueCipher, String stringToEncipher){
    
        String encipheredString = "";
        
        for (int index = 0 ; index < stringToEncipher.length() ; index ++){
            if (stringToEncipher.charAt(index) == 32) encipheredString += "";
            else{
                int digit = stringToEncipher.charAt(index) - 65;
                char symbol = uniqueCipher.charAt(digit);
                encipheredString += String.valueOf(symbol);
            }    
        }
        return encipheredString;
    }

    private static String decipher(String stringToDecipher, String keywordString, String uniqueCipher) {
        
        // 1:1 substition only
        // get char index for the character in the encrypted string from the unique cipher
        // get the decrepted character with that index from the A-Z string

        String rot13String = ROT.rotCharacterSet(13);
        String decipheredString = "";
        char symbol = 0;

        for (int index = 0 ; index < stringToDecipher.length() ; index ++){
            if (stringToDecipher.charAt(index) == 32) decipheredString += "";
            else {
                char x = stringToDecipher.charAt(index);
                int cryptValue = uniqueCipher.indexOf(x);
                symbol = rot13String.charAt(cryptValue);
                decipheredString += String.valueOf(symbol);
                }   
            }
         return decipheredString;
    }
    
    private static String encipherColumned(String encipheredString){
        
        // create columned string
        String encipheredStringColumns = "";
        int remainder_after_split = encipheredString.length() % 5;
        // iterate through the ciphered string
        for (int index = 0 ; index < encipheredString.length() - 5; index += 5){
            // add 5 letters at a time + a space
            encipheredStringColumns += encipheredString.substring(index, index + 5) + " ";
        }
        encipheredStringColumns += encipheredString.substring(encipheredString.length() - remainder_after_split);
    
        return encipheredStringColumns;
    }

    public static void main(String[] args) {
        
        // test area
        /* 
        String fileName      = "string_encrypted.txt";
        String encodeDecode  = "d";
        String keywordString = "kryptos";
        */
        
        // need to provide filename.txt , d/e for decrypt / encrypt, and a keyword for the cipher.
        if (args.length != 3){                                      
            System.out.println("Need to provide 3 arguments. Goodbye.");
            System.exit(0); 
        }
        else if ( ! (args[1].equals("d") || args[1].equals("e")) ){
            System.out.println("Ivalid argument. (d)ecrypt or (e)ncrypt only. Goodbye.");
            System.exit(0); 
        }
        
        else if (! args[2].matches("^[a-zA-Z]*$")){
            System.out.println("Ivalid cipher Key.");
            System.exit(0);
        }
        
        
        String fileName      = args[0];
        String encodeDecode  = args[1];
        String keywordString = args[2];
        

        ArrayList<String> new_string_array = ROT.openFile(fileName);
        // convert String array to one string
        String string_from_file  = arrayToString(new_string_array);
        // prepare the keyword
        String uniqueKeyWord     = prepareKeyWord(keywordString);
        // generate the unique cipher
        String uniqueCipher      = generateCipherAlphabet(uniqueKeyWord);
        // encrypt string from file
        if (encodeDecode.equals("e")){
            String encipheredString  = encipher(uniqueCipher, string_from_file);
            String encipheredStringColumns = encipherColumned(encipheredString);
            // created encoded file name
            String encoded_file_name = ROT.newFileNameCreator(fileName, encodeDecode);
            writeToFile(encipheredStringColumns, encoded_file_name);
        }
        if (encodeDecode.equals("d")){
            String decipheredString  = decipher(string_from_file, keywordString, uniqueCipher);
            String encipheredStringColumns = encipherColumned(decipheredString);
            // created encoded file name
            String decoded_file_name = ROT.newFileNameCreator(fileName, encodeDecode);
            writeToFile(encipheredStringColumns, decoded_file_name);
        }
        // write enciphered string to file
    }
}





