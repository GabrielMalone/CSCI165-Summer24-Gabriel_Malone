// Gabriel Malone / CSCI165 / Summer 2024

public class Initials {
    public static void main(String[] args) {
        
    // Write Java code that uses a command line argument for the users first and last name. When running the program, enter the name surrounded by quotes so the terminal reads it as a single token, otherwise it will be read as two distinct tokens.

    // get input from command line
    if (args.length > 0){
    // grab the args
    String name = args[0];
        
    // find index of split of first and last name

    int space = name.indexOf(" ");
    
    // create first and last name variables from index info

    String firstName = name.substring(0, space);
    String lastName  = name.substring(space + 1, name.length());
   
    // get first initial from first name index 0
    // get last initial from last name index 0
    
    char firstInitial = firstName.charAt(0);
    char lastInitial  = lastName.charAt(0);

    // casting char to short    

    short firstInitNumeric = (short)firstInitial;
    short lastIniNumeric   = (short)lastInitial;    
    
    // some addition

    int mySum = firstInitNumeric + lastIniNumeric;

    // string concatentation

    String intiails = Character.toString(firstInitial) + Character.toString(lastInitial);

    // print statements
    
    //A.A.
    System.out.printf("%C.%C.%n", firstInitial, lastInitial);
    // A = x, A = x
    System.out.printf("%C = %d, %C = %d%n", firstInitial, firstInitNumeric, lastInitial, lastIniNumeric);
    // x + x = y
    System.out.printf("%d + %d = %d%n", firstInitNumeric, lastIniNumeric, mySum);
    // AA
    System.out.println(intiails.toUpperCase());
    

        }

    }
}
