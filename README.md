# Motor de Cálculos Geométricos

Um motor modular em C para cálculos geométricos 2D, com suporte a visualização em Python.

**[Documentação Matemática (MATH.md)](docs/MATH.md)**

## Funcionalidades

O motor suporta as seguintes entidades geométricas:
- **Pontos (POINT):** Coordenadas X e Y.
- **Linhas (LINE):** Definidas por dois pontos.
- **Polígonos (POLYGON):** Sequência de vértices (array dinâmico).
- **Círculos (CIRCLE):** Centro e raio (matemática exata).
- **Retângulos (RECTANGLE):** Alinhados aos eixos (AABB).
- **Triângulos (TRIANGLE):** Definidos por três pontos.

### Operações Suportadas
- **Cálculos Básicos:** Área, perímetro, comprimento e distâncias.
- **Interseções:** Verificação de colisões entre Círculos, Linhas e Retângulos.
- **Inclusão:** Verificação de ponto dentro de Círculo, Retângulo e Polígono (via Raycasting).

## Polimorfismo e Gerenciamento de Formas

O motor implementa um conceito de **polimorfismo em C** para permitir que diferentes tipos de formas coexistam em uma mesma lista e interajam entre si. Isso é feito através de duas estruturas principais no `src/main.c`:

- **`ShapeType` (Enum):** Uma etiqueta que identifica o tipo real da forma (ex: `T_CIRCLE`, `T_POLY`).
- **`ShapeContainer` (Struct):** Um "envelope" genérico que contém o tipo e um ponteiro `void* data` para a estrutura real.

Este design permite que o motor percorra uma lista de formas variadas e decida em tempo de execução qual algoritmo de intersecção aplicar, tornando o sistema altamente extensível e capaz de realizar testes dinâmicos entre quaisquer pares de objetos.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:
- `include/`: Arquivos de cabeçalho (`.h`).
- `src/`: Arquivos fonte em C (`.c`).
- `shapes.txt`: Arquivo de entrada na raiz.
- `docs/`: Documentação detalhada (`MATH.md`).
- `scripts/`: Scripts utilitários (visualização em Python).
- `build/`: Pasta gerada para arquivos objeto (`.o`).

## Como Usar (C)

### No Linux / macOS
1. Abra o terminal na pasta raiz do projeto.
2. Compile usando o comando:
   ```bash
   make
   ```
3. Execute o motor:
   ```bash
   ./geo_engine
   ```

### No Windows
1. Abra o CMD ou PowerShell na pasta do projeto.
2. Compile manualmente com o comando:
   ```bash
   gcc -o geo_engine.exe src/*.c -Iinclude -lm
   ```
3. Execute o motor:
   ```bash
   .\geo_engine.exe
   ```

---

## Como Usar (Java)

A versão em Java fica na pasta `Java/` e produz **exatamente a mesma saída** da versão em C. Todos os comandos devem ser executados na **pasta raiz do projeto** (onde está o `shapes.txt`).

> Requisito: **JDK 11 ou superior** instalado (`java -version`).

### No Linux / macOS
1. Abra o terminal na pasta raiz do projeto.
2. Compile e execute usando o comando:
   ```bash
   make -C Java run
   ```
3. Para apenas compilar (gera `Java/out/`):
   ```bash
   make -C Java
   ```

Alternativa sem `make`:
```bash
javac -d Java/out $(find Java/src -name '*.java')
java -cp Java/out geo.app.Main
```

### No Windows
1. Abra o CMD ou PowerShell na pasta raiz do projeto.
2. Compile manualmente com o comando:
   ```bash
   javac -d Java\out Java\src\main\java\geo\model\*.java Java\src\main\java\geo\operations\*.java Java\src\main\java\geo\app\*.java
   ```
3. Execute o motor:
   ```bash
   java -cp Java\out geo.app.Main
   ```

---

## Como Usar (Python)

O script de visualização permite ver as formas definidas no arquivo `shapes.txt`.

### Requisitos
- Python 3 instalado.
- Biblioteca Matplotlib instalada: `pip install matplotlib`

### Execução
```bash
python visualize.py
```

## Formato do Arquivo `shapes.txt`

Cada linha representa uma forma geométrica. Exemplos:
- `POINT 0.0 0.0`
- `LINE 0.0 0.0 3.0 4.0`
- `POLYGON 4 0.0 0.0 3.0 0.0 3.0 4.0 0.0 4.0`
- `CIRCLE 5.0 5.0 2.0`
- `RECTANGLE 1.0 1.0 4.0 3.0`
- `TRIANGLE 6.0 1.0 9.0 1.0 7.5 4.0`
