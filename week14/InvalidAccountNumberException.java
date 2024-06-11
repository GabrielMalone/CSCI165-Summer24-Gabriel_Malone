// Gabriel Malone / CSCI165 / Week 14 / Summer 2024

public class InvalidAccountNumberException extends Exception{
    
    public InvalidAccountNumberException (String customMSg){
        super(customMSg);
        System.out.print(customMSg); 
    }
}
