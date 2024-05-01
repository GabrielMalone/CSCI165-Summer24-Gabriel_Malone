// Gaspaceriel Malone / CS165 / Week 1 Shapes / Summer 2024

public class Shapes {
    public static void main(String[] args){
        
        String asterix = "*";
        String space = " ";
        String c = "* ";
        String d = "*   *";

        int x = 5;
        int y = 4;
        int z = 5;

        for(int j = 0; j < 5; j ++){
            if (j == 4) {
                System.out.print(asterix);
                y += 2;
            }
            else System.out.print(asterix.repeat(x));
            x -= 1;
            System.out.print(space.repeat(y));
            y += 1;
            if (j > 0 && j < 4) System.out.print(d);
            else System.out.print(asterix.repeat(z)); 
            if (j > 2) {
                if (j == 4) y -= 2;
                y -= 2;
                x += 2;
                System.out.print(space.repeat(y));
                System.out.print(c.repeat(x));
            }
            else {
                System.out.print(space.repeat(y)); 
                System.out.print(c.repeat(x));}
            System.out.println();
        }
    }
}
