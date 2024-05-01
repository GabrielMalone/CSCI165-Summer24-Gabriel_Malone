// Gabriel Malone / CS165 / Week 5 / Summer 2024

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
    
    /**
     * Method to find the largest fraction in an array of fractions
     * @param fractions
     * @return
     */
    public static Fraction largestFraction(Fraction[ ] fractions){
        // set up the comparison
        Fraction largestFraction = new Fraction(0);
        // iterate through fractions array
        for (int index = 0 ; index < 100; index ++ ){
            int x = fractions [index].compareTo(largestFraction);
            // if next fraction bigger than than the previous biggest
            if (x == 1) largestFraction = fractions [index];
        } // end for loop
        return largestFraction;
    }

    public static void main(String[] args) {
        

        // 1 -  create an instance from Fraction class
        Fraction myF = new Fraction(4, 12);
        // print fraction
        String originalF = myF.toString();
        System.out.printf("%nOriginal fraction: %s%n", originalF);
        
        // 2 - reduce
        myF.reduce();
        // print results
        String reducedF = myF.toString();
        System.out.printf("%s reduces to %s%n", originalF, reducedF);
        
        // 3 - set new numerator
        myF.setNumerator(4);
        // show results
        System.out.printf("%s's numerator changed to %s%n", reducedF, myF.toString());
        
        // 4 - set new denominator and show results
        String currentFraction = myF.toString();
        myF.setDenominator(6);
        System.out.printf("%s's denominator changed to %s%n", currentFraction, myF.toString());
       
        // 5 - convert fraction to a decimal and show results
        double myFdecimal = myF.toDecimal();
        System.out.printf("%s converted to a decimal is %f%n", myF.toString(), myFdecimal);
        
        // 6 - return denominator and print results
        int denominator = myF.getDenominator();
        System.out.printf("The denominator of %s is %d%n", myF.toString(), denominator);
        
        // 7 - return numerator and print results
        int numerator = myF.getNumerator();
        System.out.printf("The numerator of %s is %d%n", myF.toString(), numerator);
        
        // create another instance with only numerator argument
        Fraction myF2 = new Fraction(15);
        System.out.printf("Fraction with only numerator argument: %s%n",myF2.toString());

        // create another instance with an invalid denominator
        Fraction myF3 = new Fraction(9, 0);
        System.out.printf("Fraction with 0 passed in as denominator: %s%n",myF3.toString());

        // 8 - instances 4 & 5
        Fraction myF4 = new Fraction(3, 15);
        String myF4String = myF4.toString();

        Fraction myF5 = new Fraction(1, 3);
        String myF5String = myF5.toString();

        // 9 - add fractions 
        myF4.add(myF5);
        // output
        System.out.printf("%s + %s = %s%n", myF4String, myF5String, myF4.toString());
        
        // another new instance just 'cause
        Fraction myF6 = new Fraction(1, 3);
        
        // 10 - subtract fractions
        System.out.printf("%s - %s = ", myF4.toString(), myF6.toString());
        myF4.subtract(myF6);
        System.out.printf("%s%n", myF4.toString());
       
        // 11 - multiply fractions
        System.out.printf("%s * %s = ", myF4.toString(), myF6.toString());
        myF4.multiply(myF6);
        System.out.printf("%s%n", myF4.toString());
        
        // 12 - divide fractions
        System.out.printf("%s / %s = ", myF4.toString(), myF6.toString());
        myF4.divide(myF6);
        System.out.printf("%s%n", myF4.toString());

        // 13 - compare fractions
        // new instances for these comparisons
        Fraction myF7 = new Fraction(1, 2);
        Fraction myF8 = new Fraction(2, 4);
        Fraction myF9 = new Fraction(3, 4);
        
        // starting with equals
        if (myF7.equals(myF8)) System.out.printf("%s equals %s%n", myF7.toString(), myF8.toString());
        else{
            System.out.printf("%s does not equal %s%n", myF7.toString(), myF8.toString());
        }
        if (myF7.equals(myF9)) System.out.printf("%s equals %s%n", myF7.toString(), myF9.toString());
        else{
            System.out.printf("%s does not equal %s%n", myF7.toString(), myF9.toString());
        }
        
        // greater than , less than

         int result = myF7.compareTo(myF9);
         
         switch (result) {
            
            case -1:    System.out.printf("%s is less than %s%n",myF7.toString(),myF9.toString());
                        break;
            
            case  0:    System.out.printf("%s equals %s%n",myF7.toString(),myF9.toString());
                        break;

            case  1:    System.out.printf("%s is greater than %s%n",myF7.toString(),myF9.toString());
                        break;
            
            } // end switch 


        // 14 - create and fill fractions array

        Fraction[] fractions = new Fraction[100];

        try{
            // open fractions.txt
            File input_file = new File("fractions.txt");                   
            Scanner file_scanner = new Scanner(input_file); 
            int index = 0;        
            while(file_scanner.hasNext()){  
                // pull all the fractions out of the file one by one                      
                String n = file_scanner.nextLine(); 
                // get the nom and denom from the strings
                int splitMark = n.indexOf(",");
                String nominatorString  = n.substring(0, splitMark);    
                String denominatoString = n.substring(splitMark + 1, n.length());
                // convert to inengers
                int nom = Integer.parseInt(nominatorString);
                int denom = Integer.parseInt(denominatoString);
                // update instances in the fractions array
                fractions [index] = new Fraction(nom, denom);
                index += 1;
            }
            file_scanner.close();                                  

        }catch(IOException ioe){
            System.out.printf("Could not open fractions.txt. Goodbye.%n");
            System.exit(0);
        }

        // 15 - find the largest fraction in the array using compare
        
        Fraction largesFraction = largestFraction(fractions);
        System.out.printf("The largest fraction in the array is: %s%n", largesFraction.toString());

        
    
        




        






    




       

        
        
    }
}