package geo.model;

/**
 * Segmento de reta definido por dois pontos.
 *
 * <p>Equivalente ao {@code struct Line} de {@code line.h}. A diferença de
 * paradigma: em C, as funções {@code line_create}/{@code line_length} são
 * livres dentro do "módulo" {@code line.c} e operam sobre o struct passado por
 * valor. Em Java, o comportamento ({@link #length()}) vive <i>dentro</i> do
 * objeto, junto dos dados — é a unificação de dados e operações que caracteriza
 * a abstração orientada a objetos.</p>
 */
public final class Line extends Shape {

    private final Point p1;
    private final Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public double length() {
        return p1.distanceTo(p2);
    }

    @Override
    public double area() {
        return 0.0;
    }

    @Override
    public double perimeter() {
        return length();
    }

    @Override
    public String typeName() {
        return "LINE";
    }
}
