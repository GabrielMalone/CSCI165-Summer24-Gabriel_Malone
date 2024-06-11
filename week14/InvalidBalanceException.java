// Gabriel Malone / CSCI165 / Week 14 / Summer 2024

public class InvalidBalanceException extends Exception{
    
    public InvalidBalanceException (String customMSg){
        super(customMSg);
        System.out.print(customMSg); 
    }
}