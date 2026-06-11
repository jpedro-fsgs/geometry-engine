package geo.model;

/**
 * Triângulo definido por três pontos.
 *
 * <p>Espelha {@code triangle.h}. A área usa a Fórmula de Heron (a partir dos
 * três lados), idêntica à de {@code triangle.c}.</p>
 */
public final class Triangle extends Shape {

    private final Point p1;
    private final Point p2;
    private final Point p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    @Override
    public double perimeter() {
        return p1.distanceTo(p2) + p2.distanceTo(p3) + p3.distanceTo(p1);
    }

    /** Área pela Fórmula de Heron (semi-perímetro). */
    @Override
    public double area() {
        double a = p1.distanceTo(p2);
        double b = p2.distanceTo(p3);
        double c = p3.distanceTo(p1);
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public String typeName() {
        return "TRIANGLE";
    }
}
