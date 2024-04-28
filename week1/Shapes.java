// Gabriel Malone / CS165 / Week 1 Shapes / Summer 2024

public class Shapes {
    public static void main(String[] args){
        // strings
        String line1 = "*****   *****   * * * * *";
        String line2 = "****    *   *     * * *"  ;
        String line3 = "***     *   *      * *"   ;
        String line4 = "**      *   *     * * *"  ;
        String line5 = "*       *****   * * * * *";
        // put the string vars into an array
        String [] stringArray = {line1, line2, line3, line4, line5};
        // loop through the array and print
        for(String line : stringArray){
            System.out.println(line);
        }
    }
}
