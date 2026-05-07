# Motor de Cálculos Geométricos

Um motor modular em C para cálculos geométricos 2D, com suporte a visualização em Python.

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

O motor implementa um conceito de **polimorfismo em C** para permitir que diferentes tipos de formas coexistam em uma mesma lista e interajam entre si. Isso é feito através de duas estruturas principais no `main.c`:

- **`ShapeType` (Enum):** Uma etiqueta que identifica o tipo real da forma (ex: `T_CIRCLE`, `T_POLY`).
- **`ShapeContainer` (Struct):** Um "envelope" genérico que contém o tipo e um ponteiro `void* data` para a estrutura real.

Este design permite que o motor percorra uma lista de formas variadas e decida em tempo de execução qual algoritmo de intersecção aplicar, tornando o sistema altamente extensível.

## Como Usar (C)

### No Linux / macOS
O projeto utiliza um `Makefile` para automatizar a compilação.
1. Abra o terminal na pasta do projeto.
2. Compile usando o comando:
   ```bash
   make
   ```
3. Execute o motor:
   ```bash
   ./geo_engine
   ```

### No Windows
Você precisará de um compilador C (como o **MinGW** ou **GCC via MSYS2**).
1. Abra o CMD ou PowerShell na pasta do projeto.
2. Se você tiver o `make` instalado (ex: via MinGW), basta rodar `make`.
3. Caso contrário, compile manualmente com o comando:
   ```bash
   gcc -o geo_engine.exe main.c point.c line.c polygon.c circle.c rectangle.c triangle.c operations.c -lm
   ```
4. Execute o motor:
   ```bash
   .\geo_engine.exe
   ```

---

## Como Usar (Python)

O script `visualize.py` permite visualizar as formas definidas no arquivo `shapes.txt`.

### Requisitos
- Python 3 instalado.
- Biblioteca Matplotlib instalada:
  ```bash
  pip install matplotlib
  ```

### Execução
- **Linux/macOS:** `python3 visualize.py`
- **Windows:** `python visualize.py` ou `py visualize.py`

## Formato do Arquivo `shapes.txt`

Você pode definir a cena geométrica editando o arquivo `shapes.txt`. Cada linha representa uma forma:

- **Ponto:** `POINT <x> <y>`
- **Linha:** `LINE <x1> <y1> <x2> <y2>`
- **Polígono:** `POLYGON <n_vertices> <x1> <y1> ... <xn> <yn>`
- **Círculo:** `CIRCLE <x_centro> <y_centro> <raio>`
- **Retângulo:** `RECTANGLE <x_min> <y_min> <x_max> <y_max>`
- **Triângulo:** `TRIANGLE <x1> <y1> <x2> <y2> <x3> <y3>`

## Estrutura do Projeto

- `point.h/c`, `line.h/c`, `polygon.h/c`: Módulos básicos.
- `circle.h/c`, `rectangle.h/c`, `triangle.h/c`: Módulos dedicados para formas específicas.
- `operations.h/c`: Módulo central de cálculos de intersecção, inclusão e distâncias mistas.
- `main.c`: Parser principal e motor de testes dinâmicos.
- `visualize.py`: Script de visualização gráfica.
- `Makefile`: Script de automação de build.
