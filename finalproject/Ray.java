// Gabriel Malone / CSCI165 / Final Project / Summer 2024

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Ray {

    public Ray(){}

    public void drawMarches(ArrayList<March> marches, Graphics2D g){
        for (March march : marches){
            march.drawObject(g);
        }
    }

}
