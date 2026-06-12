# Motor de Cálculos Geométricos — Versão Java

Implementação **orientada a objetos** do mesmo motor geométrico escrito em C
(pasta `src/`). Esta versão demonstra **abstração de dados, encapsulamento,
herança e polimorfismo dinâmico** (Tema 3), produzindo **exatamente a mesma
saída** da versão C.

## Arquitetura e estrutura de pastas

O código segue o layout convencional do ecossistema Java (`src/main/java`) e é
organizado em **camadas**, cada uma em seu próprio pacote, separando
responsabilidades:

```
Java/
├── Makefile                       # build/run da versão Java
├── README.md
└── src/
    └── main/
        └── java/
            └── geo/
                ├── model/         # CAMADA DE DOMÍNIO — as formas (dados + comportamento)
                │   ├── Shape.java      (classe abstrata base / TAD)
                │   ├── Point.java
                │   ├── Line.java
                │   ├── Polygon.java
                │   ├── Circle.java
                │   ├── Rectangle.java
                │   └── Triangle.java
                ├── operations/    # CAMADA DE OPERAÇÕES — interseções, inclusão, distâncias
                │   └── Operations.java
                └── app/           # CAMADA DE APLICAÇÃO — entrada/IO e orquestração
                    └── Main.java
```

| Pacote            | Responsabilidade                                                        |
|-------------------|-------------------------------------------------------------------------|
| `geo.model`       | Entidades geométricas. `Shape` é o TAD base; as demais herdam dele.     |
| `geo.operations`  | Algoritmos que combinam formas (interseção, inclusão, distância).        |
| `geo.app`         | Ponto de entrada: lê `shapes.txt` e executa os testes dinâmicos.        |

A divisão reflete a **regra de dependência**: `app` depende de `operations` e de
`model`; `operations` depende de `model`; `model` não depende de ninguém. O
domínio é o núcleo estável e os detalhes (IO) ficam na borda.

## Como compilar e executar

Requer **JDK 11+** (usa `java.util.List.copyOf`). Os comandos lêem o
`shapes.txt` da **raiz do projeto**.

### Opção 1 — via Makefile Raiz (Recomendada)
A partir da raiz do repositório:
```bash
make run-java
```

### Opção 2 — via Makefile local
```bash
make -C Java run      # compila e executa (a partir da raiz)
make -C Java          # apenas compila (gera Java/out/)
make -C Java clean    # remove os .class
```

### Opção 3 — javac/java direto (a partir da raiz do projeto)

```bash
# Compila todas as fontes para Java/out
javac -d Java/out $(find Java/src -name '*.java')

# Executa (lê shapes.txt da raiz)
java -cp Java/out geo.app.Main
```

Opcionalmente, é possível indicar outro arquivo de entrada:

```bash
java -cp Java/out geo.app.Main caminho/para/outras_formas.txt
```

## Verificando que a saída é idêntica à versão C

```bash
make                                          # compila a versão C -> ./geo_engine
make -C Java                                  # compila a versão Java -> Java/out

./geo_engine                 > /tmp/c_out.txt
java -cp Java/out geo.app.Main > /tmp/java_out.txt

diff /tmp/c_out.txt /tmp/java_out.txt && echo "Saídas idênticas"
```
