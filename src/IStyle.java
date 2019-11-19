import java.awt.*;


/**
 * Specify the color for the application
 *  1: start vertex
 *  2:
 */
public interface IStyle {
    Color green = new Color(0, 213, 92); // start vertex
    Color dark = new Color(48, 48, 48); // wall
    Color lightGreen1 = new Color(132, 255, 138); // current vertex
    Color lightGreen2 = new Color(228, 255, 171); // visited vertex
    Color lightOrange = new Color(255, 233, 178); // found path
    Color red = new Color(245, 59, 87); // target vertex
//    Color lightOrange = new Color(140, 230, 250); // current vertex
    Color lightDark = new Color(120, 120, 120, 80); // outer grid
    Color lightWhite = new Color(232, 232, 232);
}
