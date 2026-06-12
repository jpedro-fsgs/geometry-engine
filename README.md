# Motor de Cálculos Geométricos

Um projeto de motor geométrico 2D modular, implementado em múltiplos paradigmas de programação. Este repositório explora a comparação entre a abordagem **Procedural/Estruturada (Linguagem C)** e a **Orientada a Objetos (Linguagem Java)**, produzindo *exatamente os mesmos resultados matemáticos*. Também conta com um visualizador em **Python**.

**[Documentação Matemática (MATH.md)](docs/MATH.md)**

## Estrutura do Repositório

Para facilitar o estudo e a modularidade, o projeto está dividido em pastas independentes para cada linguagem:

- **`C/`**: Implementação baseada em structs, ponteiros e alocação manual de memória. (Ver [C/README.md](C/README.md))
- **`Java/`**: Implementação baseada em classes, herança, imutabilidade e polimorfismo dinâmico. (Ver [Java/README.md](Java/README.md))
- **`docs/`**: Documentação das fórmulas matemáticas.
- **`visualize.py`**: Script Python para visualização visual das formas.
- **`shapes.txt`**: O arquivo de configuração principal, lido por ambos os motores (C e Java) para instanciar as formas geométricas.

## Funcionalidades e Entidades

O motor suporta:
- **Pontos (POINT):** Coordenadas X e Y.
- **Linhas (LINE):** Segmentos de reta definidos por dois pontos.
- **Polígonos (POLYGON):** Sequência dinâmica de vértices.
- **Círculos (CIRCLE):** Centro e raio (matemática exata).
- **Retângulos (RECTANGLE):** Alinhados aos eixos (AABB).
- **Triângulos (TRIANGLE):** Definidos por três pontos.

As **operações geométricas** incluem o cálculo de distâncias, cálculo de área e perímetro, checagem de interseção (colisão) entre as mais variadas formas, e teste de inclusão (Raycasting e áreas).

## Como Executar

O repositório possui um `Makefile` unificado na raiz para facilitar a execução dos módulos.

### Executando o Módulo C
```bash
make run-c
```

### Executando o Módulo Java
Requisito: JDK 11 ou superior.
```bash
make run-java
```

### Limpando o Projeto
Para apagar os binários e arquivos `.class` de ambas as pastas:
```bash
make clean
```

---

## Formato do Arquivo `shapes.txt`

Cada linha representa uma forma geométrica. É assim que o motor sabe o que carregar. Exemplos:
- `POINT 0.0 0.0`
- `LINE 0.0 0.0 3.0 4.0`
- `POLYGON 4 0.0 0.0 3.0 0.0 3.0 4.0 0.0 4.0`
- `CIRCLE 5.0 5.0 2.0`
- `RECTANGLE 1.0 1.0 4.0 3.0`
- `TRIANGLE 6.0 1.0 9.0 1.0 7.5 4.0`

## Visualização em Python

O repositório acompanha um script que usa `matplotlib` para renderizar o que está no arquivo `shapes.txt`.
**Requisitos:** Python 3 e `matplotlib` (`pip install matplotlib`).

```bash
python visualize.py
```

