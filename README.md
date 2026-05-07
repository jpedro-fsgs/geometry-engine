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

Cálculos incluídos: área, perímetro, comprimento e distâncias.

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
   gcc -o geo_engine.exe main.c point.c line.c polygon.c circle.c rectangle.c triangle.c -lm
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
- `circle.h/c`, `rectangle.h/c`, `triangle.h/c`: Módulos dedicados.
- `main.c`: Parser e execução principal.
- `visualize.py`: Script de visualização.
- `Makefile`: Script de compilação.
