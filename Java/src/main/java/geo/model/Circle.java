package geo.model;

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
}
