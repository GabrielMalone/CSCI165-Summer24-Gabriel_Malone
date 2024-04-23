public class HelloWorld2{
    public static void main(String[] args){
        
        for (String name : args)
            System.out.println("Hello " + name + ". Nice work processing the arguments");
        
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty( "user.dir"));
    } // end of main
} // end of class