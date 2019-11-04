import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Grid extends JPanel implements MouseListener {
    private final int DIMENSION = 30;
    private final int ROWS = 460 / DIMENSION; // height
    private final int COLS = 660 / DIMENSION; // width
    private final Node[][] grids;
    public Grid(){
        grids = new Node[COLS][ROWS];
        init();
        addMouseListener(this);
    }

    public void init(){
        for(int i = 0; i < grids.length; i++){
            for(int j = 0; j < grids[i].length; j++){
                grids[i][j] = new Node(i, j);
            }
        }
    }

    public void paint(Graphics g){
        for(int i = 0; i < grids.length; i++){
            for(int j = 0; j < grids[i].length; j++){
                if(grids[i][j].getColor() == -1){
                    g.setColor(Color.WHITE);
                }else if(grids[i][j].getColor() == 0){
                    g.setColor(Color.GREEN);
                }else{
                    g.setColor(Color.RED);
                }
                g.fillRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);
                g.setColor(Color.BLACK);
                g.drawRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / DIMENSION;
        int y = e.getY() / DIMENSION;
        grids[x][y].setColor(0);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
