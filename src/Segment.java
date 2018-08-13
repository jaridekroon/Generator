import java.security.InvalidParameterException;

public class Segment {
    private Point first;
    private Point second;

    public Segment(Point first, Point second){
        this.first = first;
        this.second = second;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        Segment s = (Segment) o;
        return (this.first == s.getFirst() && this.second == s.getSecond())
                || (this.first == s.getSecond() && this.second == s.getFirst());
    }

    public DoublePoint intersection(Segment s) throws InvalidParameterException{
        int x1 = this.first.getX();
        int y1 = this.first.getY();
        int x2 = this.second.getX();
        int y2 = this.second.getY();
        int x3 = s.getFirst().getX();
        int y3 = s.getFirst().getY();
        int x4 = s.getSecond().getX();
        int y4 = s.getSecond().getY();

        //if both lines vertical and x1 != x3, no intersection
        if(x1 == x2 && x3 == x4) {
            if (x1 != x3) {
                // no intersection
                return null;
            } else if ((Math.min(y1,y2) < y3 && y3 < Math.max(y1,y2)) || (Math.min(y1,y2) < y4 && y4 < Math.max(y1,y2))
                    || (Math.min(y3,y4) < y1 && y1 < Math.max(y3,y4)) || (Math.min(y3,y4) < y2 && y2 < Math.max(y3,y4))){
                // segments overlap, throw exception to catch (we do not want these types of intersections)
                throw new InvalidParameterException("Both vertical with overlap.");
            }
        }

        //first line vertical
        if(x1 == x2) {
            //y2 = a2*x + b2
            double a2 = (y4 - y3)/(x4-x3);
            double b2 = y3 - a2*x3;
            //fill in x1
            double in = a2*x1 + b2;
            //check of intersection within both line segments
            if(Math.min(x3,x4) < x1 && x1 < Math.max(x3,x4)) {
                //intersection point!
                return new DoublePoint(x1,in);
            } else {
                // no intersection
                return null;
            }
        }

        //second line vertical
        if(x3 == x4) {
            //y1 = a1*x + b1
            double a1 = (y2 - y1)/(x2-x1);
            double b1 = y1 - a1*x1;
            //fill in x3
            double in = a1*x3 + b1;
            //check of intersection within both line segments
            if(Math.min(x1,x2) < x3 && x3 < Math.max(x1,x2)) {
                //intersection point!
                return new DoublePoint(x3,in);
            } else {
                //no intersection point
                return null;
            }
        }

        //neither vertical
        double a1 = (y2-y1)/(x2-x1);
        double b1 = y1 - a1*x1;
        double a2 = (y4-y3)/(x4-x3);
        double b2 = y3 - a2*x3;
        if((y2-y1)*(x4-x3) == (x2-x1)*(y4-y3)) {
            //same slope
            if(b1 == b2) {
                //same intercept
                if ((Math.min(x1,x2) < x3 && x3 < Math.max(x1,x2)) || (Math.min(x1,x2) < x4 && x4 < Math.max(x1,x2))
                        || (Math.min(x3,x4) < x1 && x1 < Math.max(x3,x4)) || (Math.min(x3,x4) < x2 && x2 < Math.max(x3,x4))) {
                    //overlap, throw exception to catch (we do not want these types of intersections)
                    throw new InvalidParameterException("Same slope same intercept with overlap.");
                } else {
                    //no intersection
                    return null;
                }
            } else {
                //no intersection
                return null;
            }
        }

        //intersection point solution to y = a1*x + b1 and y = a2*x + b2
        double x0 = -(b1-b2)/(a1-a2);
        if(Math.min(x1, x2) < x0 && x0 < Math.max(x1, x2) && Math.min(x3, x4) < x0 && x0 < Math.max(x3, x4)) {
            //intersection point!
            return new DoublePoint(x0,a1*x0+b1);
        }
        //no intersection
        return null;
    }
}
