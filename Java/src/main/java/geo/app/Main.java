package geo.app;

import geo.model.Circle;
import geo.model.Line;
import geo.model.Point;
import geo.model.Polygon;
import geo.model.Rectangle;
import geo.model.Shape;
import geo.model.Triangle;
import geo.operations.Operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Ponto de entrada do motor geométrico em Java.
 *
 * <p>É a contraparte de {@code src/main.c}. Lê o mesmo arquivo
 * {@code shapes.txt} (na raiz do projeto) e produz <b>exatamente a mesma
 * saída</b> da versão C, permitindo a comparação direta entre os dois
 * paradigmas exigida pelo Tema 3 do projeto.</p>
 *
 * <p>Esta classe vive na camada {@code geo.app} (entrada/IO), separada da
 * camada de domínio ({@code geo.model}) e da camada de operações
 * ({@code geo.operations}). Diferenças de paradigma evidentes aqui:</p>
 * <ul>
 *   <li>O C mantém um array de {@code ShapeContainer} (enum + {@code void*}) e
 *       precisa de {@code malloc}/{@code free} explícitos. Em Java usamos uma
 *       {@code List<Shape>} polimórfica; a memória é gerenciada pelo GC.</li>
 *   <li>O laço de testes dinâmicos usa {@code instanceof} (verificação de tipo
 *       em runtime) no lugar do {@code switch} sobre a etiqueta {@code type}.</li>
 * </ul>
 */
public final class Main {

    private static final int MAX_SHAPES = 100;

    /** Locale fixo em US para garantir o ponto decimal, igual ao printf do C. */
    private static final Locale US = Locale.US;

    private Main() {
        // Classe de entrada: não deve ser instanciada.
    }

    public static void main(String[] args) {
        // Permite indicar o caminho do arquivo; por padrão "shapes.txt" (raiz).
        String path = (args.length > 0) ? args[0] : "shapes.txt";

        List<Shape> shapes = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            scanner.useLocale(US); // nextDouble usa ponto, não vírgula
            System.out.println("--- Lendo e Processando Formas ---");
            readShapes(scanner, shapes);
        } catch (FileNotFoundException e) {
            System.err.println("Error opening " + path);
            System.exit(1);
        }

        runDynamicTests(shapes);
    }

    /**
     * Lê tokens do arquivo. Assim como o {@code fscanf} do C, tokens que não
     * correspondem a uma palavra-chave de forma (ex.: linhas de comentário
     * iniciadas por {@code #}) são simplesmente ignorados.
     */
    private static void readShapes(Scanner scanner, List<Shape> shapes) {
        while (scanner.hasNext() && shapes.size() < MAX_SHAPES) {
            String type = scanner.next();
            switch (type) {
                case "POINT": {
                    Point p = new Point(scanner.nextDouble(), scanner.nextDouble());
                    shapes.add(p);
                    System.out.printf(US, "Read POINT: (%.2f, %.2f)%n", p.getX(), p.getY());
                    break;
                }
                case "LINE": {
                    Point a = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Point b = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Line l = new Line(a, b);
                    shapes.add(l);
                    System.out.printf(US, "Read LINE: length = %.2f%n", l.length());
                    break;
                }
                case "POLYGON": {
                    int count = scanner.nextInt();
                    List<Point> vertices = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
                        vertices.add(new Point(scanner.nextDouble(), scanner.nextDouble()));
                    }
                    Polygon poly = new Polygon(vertices);
                    shapes.add(poly);
                    System.out.printf(US, "Read POLYGON (%d vertices): Area = %.2f%n", count, poly.area());
                    break;
                }
                case "CIRCLE": {
                    Point center = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Circle c = new Circle(center, scanner.nextDouble());
                    shapes.add(c);
                    System.out.printf(US, "Read CIRCLE: (%.2f, %.2f) r=%.2f%n",
                            c.getCenter().getX(), c.getCenter().getY(), c.getRadius());
                    break;
                }
                case "RECTANGLE": {
                    Point min = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Point max = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Rectangle rect = new Rectangle(min, max);
                    shapes.add(rect);
                    System.out.printf(US, "Read RECTANGLE: Area = %.2f%n", rect.area());
                    break;
                }
                case "TRIANGLE": {
                    Point a = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Point b = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Point cc = new Point(scanner.nextDouble(), scanner.nextDouble());
                    Triangle t = new Triangle(a, b, cc);
                    shapes.add(t);
                    System.out.printf(US, "Read TRIANGLE: Area = %.2f%n", t.area());
                    break;
                }
                default:
                    // Token desconhecido (comentário, etc.): ignora, como o fscanf do C.
                    break;
            }
        }
    }

    /**
     * Reproduz o duplo laço de testes de {@code main.c}. A ordem de iteração
     * (i, j) e as guardas {@code i < j} para formas simétricas são mantidas
     * idênticas para que a saída coincida linha a linha com a versão C.
     */
    private static void runDynamicTests(List<Shape> shapes) {
        System.out.println();
        System.out.println("--- Testes de Operações Dinâmicas ---");

        int n = shapes.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue; // Não testar contra si mesmo

                Shape s1 = shapes.get(i);
                Shape s2 = shapes.get(j);

                // 1. PONTO em outras formas
                if (s1 instanceof Point) {
                    Point p = (Point) s1;
                    if (s2 instanceof Circle) {
                        Circle c = (Circle) s2;
                        if (Operations.pointInCircle(p, c)) {
                            System.out.printf(US, "Ponto (%.1f,%.1f) esta DENTRO do Circulo r=%.1f%n",
                                    p.getX(), p.getY(), c.getRadius());
                        }
                    } else if (s2 instanceof Rectangle) {
                        if (Operations.pointInRectangle(p, (Rectangle) s2)) {
                            System.out.printf(US, "Ponto (%.1f,%.1f) esta DENTRO do Retangulo%n",
                                    p.getX(), p.getY());
                        }
                    } else if (s2 instanceof Polygon) {
                        if (Operations.pointInPolygon(p, (Polygon) s2)) {
                            System.out.printf(US, "Ponto (%.1f,%.1f) esta DENTRO do Poligono%n",
                                    p.getX(), p.getY());
                        }
                    } else if (s2 instanceof Triangle) {
                        if (Operations.pointInTriangle(p, (Triangle) s2)) {
                            System.out.printf(US, "Ponto (%.1f,%.1f) esta DENTRO do Triangulo%n",
                                    p.getX(), p.getY());
                        }
                    } else if (s2 instanceof Line) {
                        double dist = Operations.distancePointToLine(p, (Line) s2);
                        if (dist < 0.1) {
                            System.out.printf(US, "Ponto (%.1f,%.1f) TOCA a Linha (dist=%.2f)%n",
                                    p.getX(), p.getY(), dist);
                        }
                    }
                }

                // 2. LINHA em outras formas
                if (s1 instanceof Line) {
                    Line l1 = (Line) s1;
                    if (s2 instanceof Line) {
                        if (i < j && Operations.lineIntersects(l1, (Line) s2)) {
                            System.out.println("Linha INTERSECTA outra Linha");
                        }
                    } else if (s2 instanceof Circle) {
                        Circle c = (Circle) s2;
                        if (Operations.lineIntersectsCircle(l1, c)) {
                            System.out.printf(US, "Linha INTERSECTA Circulo em (%.1f,%.1f)%n",
                                    c.getCenter().getX(), c.getCenter().getY());
                        }
                    } else if (s2 instanceof Rectangle) {
                        if (Operations.lineIntersectsRectangle(l1, (Rectangle) s2)) {
                            System.out.println("Linha INTERSECTA Retangulo");
                        }
                    } else if (s2 instanceof Polygon) {
                        if (Operations.lineIntersectsPolygon(l1, (Polygon) s2)) {
                            System.out.println("Linha INTERSECTA Poligono");
                        }
                    }
                }

                // 3. CIRCULO em outras formas
                if (s1 instanceof Circle) {
                    Circle c1 = (Circle) s1;
                    if (s2 instanceof Circle) {
                        if (i < j && Operations.circleIntersects(c1, (Circle) s2)) {
                            System.out.printf(US, "Circulo em (%.1f,%.1f) INTERSECTA outro Circulo%n",
                                    c1.getCenter().getX(), c1.getCenter().getY());
                        }
                    } else if (s2 instanceof Rectangle) {
                        if (Operations.circleIntersectsRectangle(c1, (Rectangle) s2)) {
                            System.out.printf(US, "Circulo r=%.1f INTERSECTA Retangulo%n", c1.getRadius());
                        }
                    } else if (s2 instanceof Triangle) {
                        if (Operations.circleIntersectsTriangle(c1, (Triangle) s2)) {
                            System.out.printf(US, "Circulo r=%.1f INTERSECTA Triangulo%n", c1.getRadius());
                        }
                    }
                }

                // 4. RETANGULO em outras formas
                if (s1 instanceof Rectangle) {
                    Rectangle r1 = (Rectangle) s1;
                    if (s2 instanceof Rectangle) {
                        if (i < j && Operations.rectangleIntersects(r1, (Rectangle) s2)) {
                            System.out.println("Retangulo INTERSECTA outro Retangulo");
                        }
                    } else if (s2 instanceof Triangle) {
                        if (Operations.rectangleIntersectsTriangle(r1, (Triangle) s2)) {
                            System.out.println("Retangulo INTERSECTA Triangulo");
                        }
                    }
                }

                // 5. POLIGONO em outras formas
                if (s1 instanceof Polygon) {
                    Polygon p1 = (Polygon) s1;
                    if (s2 instanceof Circle) {
                        Circle c = (Circle) s2;
                        if (Operations.polygonIntersectsCircle(p1, c)) {
                            System.out.printf(US, "Poligono INTERSECTA Circulo r=%.1f%n", c.getRadius());
                        }
                    } else if (s2 instanceof Rectangle) {
                        if (Operations.polygonIntersectsRectangle(p1, (Rectangle) s2)) {
                            System.out.println("Poligono INTERSECTA Retangulo");
                        }
                    }
                }
            }
        }
    }
}
