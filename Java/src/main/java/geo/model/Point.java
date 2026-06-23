package geo.model;

import geo.operations.Operations;

import java.util.Locale;

/**
 * Ponto 2D imutável.
 *
 * <p>Contraste com C: em {@code point.h} o {@code Point} é um {@code struct}
 * com os campos {@code x} e {@code y} totalmente públicos — qualquer parte do
 * programa pode escrevê-los livremente. Aqui aplicamos <b>encapsulamento</b>:
 * os campos são {@code private final} e só podem ser lidos via getters, o que
 * garante a integridade do dado (um ponto nunca muda depois de criado).</p>
 *
 * <p>{@code Point} também participa da hierarquia {@link Shape} (área e
 * perímetro nulos), permitindo que um ponto conviva na mesma lista polimórfica
 * que as demais formas — equivalente ao {@code T_POINT} do enum em C, porém sem
 * a necessidade de uma "etiqueta" manual.</p>
 */
public final class Point extends Shape {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /** Distância euclidiana até outro ponto (Teorema de Pitágoras). */
    public double distanceTo(Point other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public double area() {
        return 0.0;
    }

    @Override
    public double perimeter() {
        return 0.0;
    }

    @Override
    public String typeName() {
        return "POINT";
    }

    // ---- Double Dispatch ----

    @Override
    public String describe() {
        return String.format(Locale.US, "Ponto (%.1f,%.1f)", x, y);
    }

    @Override
    public boolean intersects(Shape other) {
        return other.intersectWith(this);
    }

    @Override
    public boolean intersectWith(Point p) {
        return false; // Ponto-ponto: sem interseção definida no projeto.
    }

    @Override
    public boolean intersectWith(Line l) {
        return Operations.distancePointToLine(this, l) <= 0.1;
    }

    @Override
    public boolean intersectWith(Circle c) {
        return Operations.pointInCircle(this, c);
    }

    @Override
    public boolean intersectWith(Rectangle r) {
        return Operations.pointInRectangle(this, r);
    }

    @Override
    public boolean intersectWith(Triangle t) {
        return Operations.pointInTriangle(this, t);
    }

    @Override
    public boolean intersectWith(Polygon poly) {
        return Operations.pointInPolygon(this, poly);
    }
}

