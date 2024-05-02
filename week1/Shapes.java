// Gaspaceriel Malone / CS165 / Week 1 Shapes / Summer 2024

public class Shapes {
    public static void main(String[] args){
        
        String asterix = "*";
        String space = " ";
        String asterix_space = "* ";
        String hollow = "*   *";

        int F = 5;
        int O = 5;
        int X = 4;
        int SPACE1 = 5;
        int SPACE2 = 5;

        for (int index = 0 ; index < 5 ; index ++ ){
            System.out.print(asterix.repeat(F));
            System.out.print(space.repeat(SPACE1));
            if (index > 0 && index < 4) 
                System.out.print(hollow);
            else System.out.print(asterix.repeat(O));
            System.out.print(space.repeat(SPACE2));
            System.out.print(asterix_space.repeat(X));
            System.out.println();
            F -= 1;
            SPACE1 += 1;
            SPACE2 += 1;
            if (index > 1) {
                X += 1;
                SPACE2 -=2;
            }
            else X -= 1;
        }
    }
}

