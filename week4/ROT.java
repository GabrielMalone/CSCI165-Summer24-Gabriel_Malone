// Gabriel Malone / CS165 / Week 4 / Summer 2024

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ROT {
    
    // if someone selects ROT13 their input needs to be in caps
    static boolean all_caps = false;
    
    static String rotCharacterSet(int x){
        
        String cipher = "";

        switch (x) {
            
            case 13:    for (char index = 65 ; index <= 90; index ++){
                        cipher += index; // concatenate character to string
                        } // end for-loop
                        break;
            
            case 47:    for (char index = 33 ; index <= 126; index ++){
                        cipher += index; // concatenate character to string
                        } // end for-loop
                        break;

            default:    cipher = "Please enter 13 or 47";
                        break;
            } // end switch 
            return cipher;
        }  // end function 
        
    static ArrayList<String> applyROT(ArrayList<String> new_string_array, int rotType){
        
        // get correct cipher string
        String cipher  = rotCharacterSet(rotType);     
        // blank array for encrpted/decrypted words               
        ArrayList<String> altered_string_array = new ArrayList<>();   
        int cipher_start = cipher.charAt(0);
        if (all_caps) cipher_start = Character.getNumericValue(cipher.charAt(0));
        // iterate through the string array
        for(String s : new_string_array ){                            
            String new_string = "";    
            // initialize new string ^            
            // iterate through each letter in each string                   
            for(int index  = 0 ; index < s.length(); index ++ ){      
                // get decimal value of each char in the string
                char digit = s.charAt(index);
                if (all_caps){ digit = (char)Character.getNumericValue(s.charAt(index));}
                // wrap around algorithm 
                int num = ( digit - cipher_start + rotType) % (rotType * 2);      
                // new char 
                char symbol = cipher.charAt(num);  
                // build new string from the new chars                   
                new_string += String.valueOf(symbol);                 
            }
            altered_string_array.add(new_string);
        }    
    return altered_string_array;
    }

    static ArrayList<String> openFile(String fileName){
        // place to put all of the string data from the file
        ArrayList<String> string_data = new ArrayList<String>();
        
        try{
            
            File input_file = new File(fileName);                   
            Scanner file_scanner = new Scanner(input_file);         
            
            while(file_scanner.hasNext()){  
                // pull all the lines out from the file                        
                String n = file_scanner.nextLine(); 
                // add each line to the array
                string_data.add(n);                                 
            }
            file_scanner.close();                                  

        }catch(IOException ioe){
            System.out.printf("Could not open %s. Goodbye.%n", fileName);
            System.exit(0);
        }
        return string_data;

    }

    public static void writeToFile(ArrayList<String> new_string, String file_name){
        
        try{
            File output_file = new File(file_name);
            PrintWriter writer = new PrintWriter(output_file);
            for( String s : new_string){
                writer.println(s);
            }
            writer.close();
        }catch(IOException ioe){
            System.out.printf("Could not write to %s%n.", file_name);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
      
        if (args.length != 3){                                      
            System.out.println("Need to provide 3 arguments. Goodbye.");
            System.exit(0); 
        }
        else if ( ! (args[1].equals("d") || args[1].equals("e")) ){
            System.out.println("Ivalid argument. Goodbye.");
            System.exit(0); 
        }
        else if (! (args[2].equals("13") || args[2].equals("47"))){
            System.out.println("Ivalid cipher code. Default cipher-13 applied.");
            args[2] = "13";
        }
        
        String fileName      = args[0];
        String encodeDecode  = args[1];
        int    rotType       = Integer.parseInt(args[2]);
        
        // open text file 

        /* 
        String fileName      = "text.txt";
        String encodeDecode  = "e";
        int    
        */
        if (rotType == 13) all_caps = true;

        ArrayList<String> new_string_array = openFile(fileName);

        // if encode selected
        if (encodeDecode.equals("e")){
            // get the encoded String
            ArrayList<String> encoded_string_array = applyROT(new_string_array, rotType);
            // create new file name for the encoded file
            // remove the .txt extension
            int delimeter = fileName.indexOf(".");
            String base_file_name = fileName.substring(0, delimeter);
            // place the _encrypted.txt suffix
            String encoded_file_name = base_file_name + "_encrypted.txt";
            writeToFile(encoded_string_array, encoded_file_name);

        }
        // if decode selected
        else if (encodeDecode.equals("d")){
            ArrayList<String> decoded_string_array = applyROT(new_string_array, rotType);
            // remove '_encrypted.txt' from file name
            int delimeter = fileName.indexOf("_");
            String base_file_name = fileName.substring(0, delimeter);
            // place '.txt' back on file name
            String decoded_file_name = base_file_name + ".txt";
            writeToFile(decoded_string_array, decoded_file_name);
        }
    }
}
