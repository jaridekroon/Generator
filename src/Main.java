import java.util.ArrayList;
import java.util.Random;

public class Main {
    ArrayList<DoublePoint> intersections;
    ArrayList<Segment> segments;

    public Main() {
        intersections = new ArrayList<>();
        segments = new ArrayList<>();
    }

    public ArrayList<Segment> generate(int n, int min, int max) {
        intersections.clear();
        segments.clear();

        for(int i = 0; i < n; i++) {
            addSegment(min, max);
        }

        return segments;
    }

    public void addSegment(int min, int max) {
        Random rand = new Random();
        int x1,y1,x2,y2;
        Segment s;
        do {
            x1 = rand.nextInt(max - min) + min;
            y1 = rand.nextInt(max - min) + min;
            x2 = rand.nextInt(max - min) + min;
            y2 = rand.nextInt(max - min) + min;
            s = new Segment(new Point(x1,y1),new Point(x2,y2));
        } while(y1 == y2 || !uniqueIntersections(s) || duplicateSegment(s));
        // no horizontal segment, intersection points should be unique and no duplicate segments

        this.segments.add(s);
    }

    public boolean duplicateSegment(Segment s) {
        for(Segment i : segments) {
            if(s.equals(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean uniqueIntersections(Segment s) {
        ArrayList<DoublePoint> temp = new ArrayList<>();
        for(int i = 0; i < segments.size(); i++) {
            try{
                DoublePoint p = segments.get(i).intersection(s);
                if(p != null) {
                    temp.add(p);
                }
            }
            catch(Exception e) {
                //undesired intersection, return false such that new segment will be generated
                return false;
            }
        }

        for(DoublePoint i : intersections) {
            for(DoublePoint j : temp) {
                if(i.equals(j)) {
                    //intersection point not unique
                    return false;
                }
            }
        }
        // add all new intersection points
        intersections.addAll(temp);
        return true;
    }

    public void printList(ArrayList<Segment> list) {
        for(Segment s : list) {
            System.out.println(s.getFirst().getX() + " " + s.getFirst().getY() + " " + s.getSecond().getX() + " " + s.getSecond().getY());
        }
        System.out.println();
        System.out.println("Nr of intersections: " + intersections.size());
    }

    public static void main(String[] args) {
        Main m = new Main();
        int max = 50;
        int min = 0;
        int n = 10;

        ArrayList<Segment> list = m.generate(n, min, max); //generate input list
        m.printList(list); //print to console

        SVGVisualizer v = new SVGVisualizer();
        v.draw(list,"test",max); //create svg
    }
}
