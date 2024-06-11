// Gabriel Malone / CSCI165 / Week 14 / Summer 2024

public class OverdraftException extends Exception{
    
    public OverdraftException (String customMSg){
        super(customMSg);
        System.out.print(customMSg); 
    }
}