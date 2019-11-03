import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HandleListener implements MouseListener {
    private int mouseX = 0;
    private int mouseY = 0;
    public HandleListener() {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println(e.getSource());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
