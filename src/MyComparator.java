import java.util.Comparator;

public class MyComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex v1, Vertex v2) {
        if (v1.getG() < v2.getG()){
            return -1;
        }else if(v1.getG() > v2.getG()){
            return 1;
        }else{
            return 0;
        }
    }
}
