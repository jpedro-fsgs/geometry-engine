package geo.model;

/**
 * Tipo abstrato de dado (TAD) base de toda forma geométrica.
 *
 * <p>Este é o coração do contraste com a versão em C. Em C, o "polimorfismo" é
 * simulado manualmente no {@code main.c} através de um <i>tagged union</i>:</p>
 *
 * <pre>
 *   typedef enum { T_POINT, T_LINE, ... } ShapeType;
 *   typedef struct { ShapeType type; void* data; } ShapeContainer;
 * </pre>
 *
 * <p>O programador precisa carregar uma etiqueta ({@code type}) e um ponteiro
 * genérico ({@code void*}) e, a cada operação, fazer um {@code switch}/cadeia de
 * {@code if} sobre o tipo, com <i>casts</i> manuais ({@code (Circle*)data}). Não
 * há verificação do compilador: passar o ponteiro errado é um erro silencioso.</p>
 *
 * <p>Em Java usamos <b>herança</b> e <b>polimorfismo dinâmico</b> (Sebesta,
 * Cap. 11 e 12): cada subclasse fornece sua própria implementação de
 * {@link #area()} e {@link #perimeter()}, e a JVM escolhe o método correto em
 * tempo de execução (<i>late binding</i>). Não há etiqueta nem cast: o tipo é
 * intrínseco ao objeto.</p>
 */
public abstract class Shape {

    /** Área da forma (0 para formas degeneradas como ponto e linha). */
    public abstract double area();

    /** Perímetro/comprimento da forma. */
    public abstract double perimeter();

    /** Nome do tipo, usado para mensagens e depuração. */
    public abstract String typeName();

    // ---- Autodescrição polimórfica ----

    /** Descrição resumida para mensagens (ex: "Circulo (5.0,5.0 r=3.0)"). */
    public abstract String describe();

    // ---- Double Dispatch: despacho primário ----

    /**
     * Testa interseção/inclusão com qualquer outra {@code Shape}.
     *
     * <p>Cada subclasse implementa este método como
     * {@code return other.intersectWith(this);}, iniciando o segundo despacho
     * dinâmico que resolve o tipo real do parâmetro {@code other}.</p>
     */
    public abstract boolean intersects(Shape other);

    // ---- Double Dispatch: despachos secundários ----

    /** Interseção quando a outra forma é um {@link Point}. */
    public abstract boolean intersectWith(Point p);

    /** Interseção quando a outra forma é uma {@link Line}. */
    public abstract boolean intersectWith(Line l);

    /** Interseção quando a outra forma é um {@link Circle}. */
    public abstract boolean intersectWith(Circle c);

    /** Interseção quando a outra forma é um {@link Rectangle}. */
    public abstract boolean intersectWith(Rectangle r);

    /** Interseção quando a outra forma é um {@link Triangle}. */
    public abstract boolean intersectWith(Triangle t);

    /** Interseção quando a outra forma é um {@link Polygon}. */
    public abstract boolean intersectWith(Polygon poly);
}
