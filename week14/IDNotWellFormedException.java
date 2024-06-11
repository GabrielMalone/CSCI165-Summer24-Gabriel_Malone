// Gabriel Malone / CSCI165 / Week 14 / Summer 2024

public class IDNotWellFormedException extends Exception{
    
    public IDNotWellFormedException (String customMSg){
        super(customMSg);
        System.out.print(customMSg);
        
    }
}
