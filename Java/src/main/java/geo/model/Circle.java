package geo.model;

import geo.operations.Operations;

import java.util.Locale;

/**
 * Círculo definido por centro e raio (matemática exata).
 *
 * <p>Espelha {@code circle.h}. As fórmulas {@code area = πr²} e
 * {@code perímetro = 2πr} viram métodos polimórficos: ao iterar uma lista de
 * {@link Shape}, a chamada {@code shape.area()} resolve dinamicamente para
 * esta implementação quando o objeto é um {@code Circle}.</p>
 */
public final class Circle extends Shape {

    private final Point center;
    private final double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2.0 * Math.PI * radius;
    }

    @Override
    public String typeName() {
        return "CIRCLE";
    }

    // ---- Double Dispatch ----

    @Override
    public String describe() {
        return String.format(Locale.US, "Circulo (%.1f,%.1f r=%.1f)",
                center.getX(), center.getY(), radius);
    }

    @Override
    public boolean intersects(Shape other) {
        return other.intersectWith(this);
    }

    @Override
    public boolean intersectWith(Point p) {
        return Operations.pointInCircle(p, this);
    }

    @Override
    public boolean intersectWith(Line l) {
        return Operations.lineIntersectsCircle(l, this);
    }

    @Override
    public boolean intersectWith(Circle c) {
        return Operations.circleIntersects(this, c);
    }

    @Override
    public boolean intersectWith(Rectangle r) {
        return Operations.circleIntersectsRectangle(this, r);
    }

    @Override
    public boolean intersectWith(Triangle t) {
        return Operations.circleIntersectsTriangle(this, t);
    }

    @Override
    public boolean intersectWith(Polygon poly) {
        return Operations.polygonIntersectsCircle(poly, this);
    }
}

