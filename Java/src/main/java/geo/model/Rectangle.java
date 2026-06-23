package geo.model;

import geo.operations.Operations;

/**
 * Retângulo alinhado aos eixos (AABB), definido pelos cantos mínimo e máximo.
 *
 * <p>Espelha {@code rectangle.h}. Os getters {@code getMin}/{@code getMax}
 * expõem os cantos sem permitir alteração — o invariante "min ≤ max" fica
 * protegido pelo encapsulamento.</p>
 */
public final class Rectangle extends Shape {

    private final Point min;
    private final Point max;

    public Rectangle(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    public Point getMin() {
        return min;
    }

    public Point getMax() {
        return max;
    }

    private double width() {
        return Math.abs(max.getX() - min.getX());
    }

    private double height() {
        return Math.abs(max.getY() - min.getY());
    }

    @Override
    public double area() {
        return width() * height();
    }

    @Override
    public double perimeter() {
        return 2.0 * (width() + height());
    }

    @Override
    public String typeName() {
        return "RECTANGLE";
    }

    // ---- Double Dispatch ----

    @Override
    public String describe() {
        return "Retangulo";
    }

    @Override
    public boolean intersects(Shape other) {
        return other.intersectWith(this);
    }

    @Override
    public boolean intersectWith(Point p) {
        return Operations.pointInRectangle(p, this);
    }

    @Override
    public boolean intersectWith(Line l) {
        return Operations.lineIntersectsRectangle(l, this);
    }

    @Override
    public boolean intersectWith(Circle c) {
        return Operations.circleIntersectsRectangle(c, this);
    }

    @Override
    public boolean intersectWith(Rectangle r) {
        return Operations.rectangleIntersects(this, r);
    }

    @Override
    public boolean intersectWith(Triangle t) {
        return Operations.rectangleIntersectsTriangle(this, t);
    }

    @Override
    public boolean intersectWith(Polygon poly) {
        return Operations.polygonIntersectsRectangle(poly, this);
    }
}

