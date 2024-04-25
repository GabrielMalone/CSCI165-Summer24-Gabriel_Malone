// Gabriel Malone  / Week 2 / CSCI165 Summer 2024
import java.lang.Math;

public class Primitives {
    public static void main(String[] args){
        
        if (args.length != 3) {
			System.out.println("This program requires three command-line arguments (integers).");
			return;
		}
        
        int myNewInt = Integer.parseInt(args[0]);
		int dividendInt = Integer.parseInt(args[1]);
        int divisorInt = Integer.parseInt(args[2]);

        // 1 - Define and initialize variables of each of the Java primitive types.
        
        System.out.println("\n#1\n");
            // Numerical

            int    myInt       = 12345678;
            double myDouble    = 3.14d;
            byte   myMaxByte   = 127;
            short  myShortMin  = -32768;
            long   myLong      = 999999999999999L;
            float  myFloat     = 12345.12345F;
            
            // Categorical

            boolean myBool     = true;

            // Literal Char

            char myChar        = 'G';

            // Numerical Char

            char myNumbericChar = 10001;

            // Print each value with a descriptive method using printf.

            System.out.printf("An integer is:      %,d%n", myInt);
            System.out.printf("A  double is:       %f%n", myDouble);
            System.out.printf("A  byte is:         %,d%n", myMaxByte);
            System.out.printf("A  short is:        %,d%n", myShortMin);
            System.out.printf("A  long is:         %,d%n", myLong);
            System.out.printf("A  float is:        %,f%n", myFloat);
            System.out.printf("A  boolean is:      %b%n", myBool);
            System.out.printf("A  literal char is: %c%n", myChar);
            System.out.printf("A  numeric char is: %c%n", myNumbericChar);

        // 2 - Perform a series of implicit widening casts and explicit narrowing casts. Be sure to include narrowing casts that result in value manipulation. Print the values. Include comments that describe what is happening with the values when they are cast from type to type, especially values that are changed by a narrowing cast.
        
        System.out.println("\n#2\n");
        
        // implicit widening casting
        
            // converting an integer to a float via automagic
            // the integer is being converted to a decimal (here 1 to 1.0)
            
            double a = myInt;
            System.out.printf("* converting an integer (%,d) to a float (%,f) via automagic%n", myInt, a);
            
            // converting a string to an integer via automagic
            // the unicode is being converted to an integer
           
            int b = myNumbericChar;
            System.out.printf("* converting a string (%c) to an integer (%,d) via automagic%n", myNumbericChar, b);
            
            // converting a long integer to a float via automagic
            // formatted with scientific notation
            
            float c = myLong;
            System.out.printf("* converting a long integer (%,d) to a float in scientific notation (%5.2e) via automagic%n", myLong, c);

        // excplicit narrowing casting
            
            // converting a 64bit float to an 8 bit int.
            // results in a truncated integer 
            
            byte d = (byte)myDouble;
            System.out.printf("* converting a float integer (%,f) to a byte (%d) via explicit narrowing%n", myDouble, d);

            // converting a 64bit digit integer (long) to a 16bit integer (short)
            // results in a truncated integer and loses accuracy, too.
            
            short e = (short)myLong;
            System.out.printf("* converting a long integer (%,d) to a short integer (%,d) via explicit narrowing - loss in accuracy%n", myLong, e);

            // converting the ascii code from a literal char to a a 16 bit integer
            // no loss in accuracy since the ascii code within range of short

            short f = (short)myChar;
            System.out.printf("* converting a char (%c) to a short integer (%,d) via explicit narrowing - no loss in accuracy%n", myChar, f);

            // converting the ascii code from a ascii char to an 8 bit integer
            // loss in accuracy since the ascii code not within range of bye (max val 127)

            byte g = (byte)myNumbericChar;
            System.out.printf("* converting a char (%c) to a byte integer (%d) via explicit narrowing - loss in accuracy%n", myNumbericChar, g);
        
        // 3. Create two variables of type int. Assign these variables the maximum and minimum values of this data type. Use the MIN_VALUE and MAX_VALUE defined constants in the Integer class. Each primitive type has a corresponding class type. Research the Integer API for this.

        System.out.println("\n#3\n");

            int max_int = Integer.MAX_VALUE;
            int min_int = Integer.MIN_VALUE;
            
            System.out.printf("The maximum value for an int is  %,d%n", max_int); 
            System.out.printf("The minimum value for an int is %,d%n", min_int);  

        // 4. Repeat the process described in step 3, except with the type long. Also show the difference between Integer.MAX_VALUE and Long.MAX_VALUE.

        System.out.println("\n#4\n");
            
            long max_long = Long.MAX_VALUE;
            long min_long = Long.MIN_VALUE;

            System.out.printf("The maximum value for a long is  %,d%n", max_long);
            System.out.printf("The minimum value for a long is %,d%n", min_long);
            System.out.printf("Compare the max value for long (%,d) vs the max value for int (%,d)%n", max_long, max_int);
        
        // 5. Using a command line argument enter an integer when you run the program. You will need to convert the value to an int. Because Strings are objects, you cannot use type casting; you have to call a method. Check out the parseInt method in the Integer class.

        System.out.println("\n#5\n");
            
        // Display the square, cube, and fourth power. Display with descriptive messages. Research the Math class and use the pow method for each calculation. Use a loop if you’d like.

            for (int i = 2 ; i < 5; i++) {
                double mathedInt = Math.pow(myNewInt, i);
                String mathedString = "raised to the " + i + " is: ";
                System.out.printf("%,d %-15s %,.0f%n", myNewInt, mathedString, mathedInt);
                
            }
        
        // 6. Add the ability to process two more command line arguments. Enter an integer dividend and divisor. Compute floor division and floor modulus. Use the operators (/ and %) and the floor methods from the Math class. Look this up in the API. Print the result with a descriptive message using printf
      
        System.out.println("\n#6\n");
            
            // division 
            
            double quotient = (double) dividendInt / divisorInt;
            
            // floored result

            double flooredQuotient = Math.floor(quotient);
            
            // modulus division
           
            double remainder = (double) dividendInt % divisorInt;

            // floored remainder
            
            double flooredRemainder = Math.floor(remainder);
            
            // print results

            System.out.printf("%s divided by %s is %.0f (floored)%n", dividendInt, divisorInt, flooredQuotient);
            System.out.printf("%s divided by %s leaves %.0f remaining%n", dividendInt, divisorInt, flooredRemainder);
    }       
}
