package geo.model;

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
}
