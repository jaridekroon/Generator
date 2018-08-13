public class DoublePoint {
    private double x;
    private double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        double eps = 1e-5;
        DoublePoint p = (DoublePoint) o;
        return Math.abs(this.getX() - p.getX()) < eps && Math.abs(this.getY() - p.getY()) < eps;
    }
}
