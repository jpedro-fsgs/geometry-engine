package geo.operations;

import geo.model.Circle;
import geo.model.Line;
import geo.model.Point;
import geo.model.Polygon;
import geo.model.Rectangle;
import geo.model.Triangle;

import java.util.List;

/**
 * Módulo de operações geométricas: interseções, inclusão e distâncias.
 *
 * <p>Esta classe é a tradução fiel do "módulo" {@code operations.c}. Optamos
 * por mantê-la como uma classe utilitária de métodos {@code static} para tornar
 * explícito um ponto de comparação interessante entre os paradigmas:</p>
 *
 * <ul>
 *   <li><b>Área/Perímetro</b> (em {@code geo.model.Shape}) usam
 *       <b>polimorfismo dinâmico</b>: o método é escolhido em tempo de execução
 *       pelo tipo real do objeto (<i>late binding</i>).</li>
 *   <li><b>Interseções entre formas diferentes</b> são intrinsecamente
 *       binárias (dependem de DOIS tipos). Aqui usamos <b>sobrecarga de
 *       métodos</b> (<i>overloading</i>), que é resolvida em tempo de
 *       <b>compilação</b> (<i>early binding</i>). É o mesmo dilema que o C
 *       resolve com a cadeia de {@code if (type==...)} no {@code main}; a
 *       diferença é que aqui o compilador garante a correspondência de tipos.</li>
 * </ul>
 *
 * <p>Todas as fórmulas seguem {@code docs/MATH.md} e produzem exatamente os
 * mesmos resultados numéricos da versão C.</p>
 */
public final class Operations {

    private Operations() {
        // Classe utilitária: não deve ser instanciada.
    }

    // --- AUXILIARES ---

    /** Orientação de três pontos (produto vetorial 2D): 0=colinear, 1=horário, 2=anti-horário. */
    private static int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                   - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    private static boolean onSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX())
            && q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }

    // --- MESMA FORMA ---

    public static boolean lineIntersects(Line l1, Line l2) {
        Point p1 = l1.getP1(), q1 = l1.getP2();
        Point p2 = l2.getP1(), q2 = l2.getP2();

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) return true;

        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }

    public static boolean circleIntersects(Circle c1, Circle c2) {
        return c1.getCenter().distanceTo(c2.getCenter()) <= (c1.getRadius() + c2.getRadius());
    }

    public static boolean rectangleIntersects(Rectangle r1, Rectangle r2) {
        if (r1.getMax().getX() < r2.getMin().getX() || r1.getMin().getX() > r2.getMax().getX()) return false;
        if (r1.getMax().getY() < r2.getMin().getY() || r1.getMin().getY() > r2.getMax().getY()) return false;
        return true;
    }

    // --- FORMAS DIFERENTES ---

    public static boolean pointInPolygon(Point p, Polygon poly) {
        boolean inside = false;
        List<Point> v = poly.getVertices();
        int n = v.size();
        for (int i = 0; i < n; i++) {
            Point p1 = v.get(i);
            Point p2 = v.get((i + 1) % n);
            if (((p1.getY() > p.getY()) != (p2.getY() > p.getY()))
                && (p.getX() < (p2.getX() - p1.getX()) * (p.getY() - p1.getY())
                        / (p2.getY() - p1.getY()) + p1.getX())) {
                inside = !inside;
            }
        }
        return inside;
    }

    public static boolean pointInCircle(Point p, Circle c) {
        return p.distanceTo(c.getCenter()) <= c.getRadius();
    }

    public static boolean pointInRectangle(Point p, Rectangle r) {
        return p.getX() >= r.getMin().getX() && p.getX() <= r.getMax().getX()
            && p.getY() >= r.getMin().getY() && p.getY() <= r.getMax().getY();
    }

    public static boolean pointInTriangle(Point p, Triangle t) {
        // Se Area(P,P1,P2) + Area(P,P2,P3) + Area(P,P3,P1) == Area(P1,P2,P3), está dentro.
        Triangle t1 = new Triangle(p, t.getP1(), t.getP2());
        Triangle t2 = new Triangle(p, t.getP2(), t.getP3());
        Triangle t3 = new Triangle(p, t.getP3(), t.getP1());
        double totalArea = t.area();
        double sumAreas = t1.area() + t2.area() + t3.area();
        return Math.abs(totalArea - sumAreas) < 0.001;
    }

    public static boolean circleIntersectsRectangle(Circle c, Rectangle r) {
        double closestX = Math.max(r.getMin().getX(), Math.min(c.getCenter().getX(), r.getMax().getX()));
        double closestY = Math.max(r.getMin().getY(), Math.min(c.getCenter().getY(), r.getMax().getY()));
        Point closest = new Point(closestX, closestY);
        return c.getCenter().distanceTo(closest) <= c.getRadius();
    }

    public static boolean lineIntersectsCircle(Line l, Circle c) {
        return distancePointToLine(c.getCenter(), l) <= c.getRadius();
    }

    public static boolean lineIntersectsRectangle(Line l, Rectangle r) {
        if (pointInRectangle(l.getP1(), r) || pointInRectangle(l.getP2(), r)) return true;

        Point min = r.getMin(), max = r.getMax();
        Line[] edges = {
            new Line(new Point(min.getX(), min.getY()), new Point(max.getX(), min.getY())),
            new Line(new Point(max.getX(), min.getY()), new Point(max.getX(), max.getY())),
            new Line(new Point(max.getX(), max.getY()), new Point(min.getX(), max.getY())),
            new Line(new Point(min.getX(), max.getY()), new Point(min.getX(), min.getY()))
        };
        for (Line edge : edges) {
            if (lineIntersects(l, edge)) return true;
        }
        return false;
    }

    public static boolean lineIntersectsPolygon(Line l, Polygon poly) {
        if (pointInPolygon(l.getP1(), poly) || pointInPolygon(l.getP2(), poly)) return true;
        List<Point> v = poly.getVertices();
        int n = v.size();
        for (int i = 0; i < n; i++) {
            Line edge = new Line(v.get(i), v.get((i + 1) % n));
            if (lineIntersects(l, edge)) return true;
        }
        return false;
    }

    public static boolean polygonIntersectsCircle(Polygon poly, Circle c) {
        List<Point> v = poly.getVertices();
        int n = v.size();
        for (Point vertex : v) {
            if (pointInCircle(vertex, c)) return true;
        }
        for (int i = 0; i < n; i++) {
            Line edge = new Line(v.get(i), v.get((i + 1) % n));
            if (lineIntersectsCircle(edge, c)) return true;
        }
        return pointInPolygon(c.getCenter(), poly);
    }

    public static boolean polygonIntersectsRectangle(Polygon poly, Rectangle r) {
        List<Point> v = poly.getVertices();
        int n = v.size();
        for (Point vertex : v) {
            if (pointInRectangle(vertex, r)) return true;
        }
        for (int i = 0; i < n; i++) {
            Line edge = new Line(v.get(i), v.get((i + 1) % n));
            if (lineIntersectsRectangle(edge, r)) return true;
        }
        return pointInPolygon(r.getMin(), poly);
    }

    public static boolean lineIntersectsTriangle(Line l, Triangle t) {
        if (pointInTriangle(l.getP1(), t) || pointInTriangle(l.getP2(), t)) return true;
        Line[] edges = {
            new Line(t.getP1(), t.getP2()),
            new Line(t.getP2(), t.getP3()),
            new Line(t.getP3(), t.getP1())
        };
        for (Line edge : edges) {
            if (lineIntersects(l, edge)) return true;
        }
        return false;
    }

    public static boolean circleIntersectsTriangle(Circle c, Triangle t) {
        if (pointInCircle(t.getP1(), c) || pointInCircle(t.getP2(), c) || pointInCircle(t.getP3(), c)) return true;
        if (pointInTriangle(c.getCenter(), t)) return true;
        Line[] edges = {
            new Line(t.getP1(), t.getP2()),
            new Line(t.getP2(), t.getP3()),
            new Line(t.getP3(), t.getP1())
        };
        for (Line edge : edges) {
            if (lineIntersectsCircle(edge, c)) return true;
        }
        return false;
    }

    public static boolean rectangleIntersectsTriangle(Rectangle r, Triangle t) {
        if (pointInRectangle(t.getP1(), r) || pointInRectangle(t.getP2(), r) || pointInRectangle(t.getP3(), r)) return true;
        if (pointInTriangle(r.getMin(), t)) return true; // Simplificação: checa um canto
        Line[] edges = {
            new Line(t.getP1(), t.getP2()),
            new Line(t.getP2(), t.getP3()),
            new Line(t.getP3(), t.getP1())
        };
        for (Line edge : edges) {
            if (lineIntersectsRectangle(edge, r)) return true;
        }
        return false;
    }

    // --- DISTÂNCIAS ---

    public static double distancePointToLine(Point p, Line l) {
        double dx = l.getP2().getX() - l.getP1().getX();
        double dy = l.getP2().getY() - l.getP1().getY();

        if (dx == 0 && dy == 0) return p.distanceTo(l.getP1());

        double t = ((p.getX() - l.getP1().getX()) * dx + (p.getY() - l.getP1().getY()) * dy)
                 / (dx * dx + dy * dy);

        if (t < 0) return p.distanceTo(l.getP1());
        if (t > 1) return p.distanceTo(l.getP2());

        Point projection = new Point(l.getP1().getX() + t * dx, l.getP1().getY() + t * dy);
        return p.distanceTo(projection);
    }

    public static double distancePointToCircle(Point p, Circle c) {
        double dist = p.distanceTo(c.getCenter());
        return Math.abs(dist - c.getRadius());
    }

    public static double distancePointToRectangle(Point p, Rectangle r) {
        double dx = Math.max(r.getMin().getX() - p.getX(), Math.max(0.0, p.getX() - r.getMax().getX()));
        double dy = Math.max(r.getMin().getY() - p.getY(), Math.max(0.0, p.getY() - r.getMax().getY()));
        return Math.sqrt(dx * dx + dy * dy);
    }
}
