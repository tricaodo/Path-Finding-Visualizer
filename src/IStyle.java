import java.awt.*;


/**
 * Specify the color for the application
 *  1: start vertex
 *  2:
 */
public interface IStyle {
    Color green = new Color(46, 213, 115); // start vertex
    Color dark = new Color(48, 48, 48); // wall
    Color lightGreen = new Color(132, 255, 138); // visited vertex
    Color lightBlue = new Color(255, 234, 167); // current vertex
    Color lightOrange = new Color(140, 230, 250); // found path
    Color lightDark = new Color(120, 120, 120, 80); // outer grid
    Color lightWhite = new Color(232, 232, 232);
    Color lightPink = new Color(255, 121, 121); // target vertex
    Color lightPurple = new Color(245, 59, 87); // target vertex
}
