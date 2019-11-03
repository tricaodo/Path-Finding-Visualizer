import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {
    private final int DIMENSION = 30;
    private final int ROWS = 460 / DIMENSION; // height
    private final int COLS = 660 / DIMENSION; // width
    private final int[][] grids;
    public Grid(){
        grids = new int[COLS][ROWS];
        HandleListener handleListener = new HandleListener();
        this.addMouseListener(handleListener);
    }

    public void paint(Graphics g){
        for(int i = 0; i < grids.length; i++){
            for(int j = 0; j < grids[i].length; j++){
                g.drawRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);
                g.setColor(Color.BLACK);
            }
        }
    }
}
