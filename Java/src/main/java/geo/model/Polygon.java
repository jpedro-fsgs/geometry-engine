package geo.model;

import java.util.Collections;
import java.util.List;

/**
 * Polígono simples definido por uma sequência de vértices.
 *
 * <p>Em C ({@code polygon.h}) o polígono guarda {@code Point* vertices} e um
 * {@code int count} — um array alocado manualmente com {@code malloc}, que
 * precisa ser liberado com {@code free} no fim do {@code main} (gerência
 * manual de memória). Em Java, usamos uma {@link List} imutável; o
 * <i>Garbage Collector</i> cuida do ciclo de vida, eliminando a classe de bugs
 * de <i>dangling pointer</i>/<i>memory leak</i> da versão C.</p>
 */
public final class Polygon extends Shape {

    private final List<Point> vertices;

    public Polygon(List<Point> vertices) {
        // Cópia defensiva + imutabilidade: ninguém externo altera os vértices.
        this.vertices = Collections.unmodifiableList(List.copyOf(vertices));
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public int getCount() {
        return vertices.size();
    }

    @Override
    public double perimeter() {
        double perimeter = 0.0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point a = vertices.get(i);
            Point b = vertices.get((i + 1) % n);
            perimeter += a.distanceTo(b);
        }
        return perimeter;
    }

    /**
     * Área pela Fórmula de Shoelace (mesma variação usada em {@code polygon.c}
     * para reproduzir exatamente o mesmo resultado numérico).
     */
    @Override
    public double area() {
        double area = 0.0;
        int n = vertices.size();
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            Point vi = vertices.get(i);
            Point vj = vertices.get(j);
            area += (vj.getX() + vi.getX()) * (vj.getY() - vi.getY());
            j = i;
        }
        return Math.abs(area / 2.0);
    }

    @Override
    public String typeName() {
        return "POLYGON";
    }
}
