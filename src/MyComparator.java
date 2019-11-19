import java.util.Comparator;

public class MyComparator {

    /**
     * Compare the G cost between 2 vertices.
     *
     * @return value
     */
    public static Comparator<Vertex> compare_G() {
        return (v1, v2) -> {
            if (v1.getG() < v2.getG()) {
                return -1;
            } else if (v1.getG() > v2.getG()) {
                return 1;
            } else {
                return 0;
            }
        };
    }

    /**
     * Compare the F cost between 2 vertices.
     *
     * @return value
     */
    public static Comparator<Vertex> compare_F() {
        return (v1, v2) -> {
            if (v1.getF() < v2.getF()) {
                return -1;
            } else if (v1.getF() > v2.getF()) {
                return 1;
            } else {
                return 0;
            }
        };
    }
}
