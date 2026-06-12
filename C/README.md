# Motor de Cálculos Geométricos — Versão C

Implementação do motor geométrico utilizando a linguagem C (Tema 1 e 2). Esta versão foca em conceitos de programação procedural, alocação dinâmica de memória, e o uso de structs e ponteiros para simular um comportamento polimórfico elementar.

## Arquitetura e estrutura de pastas

O código em C está organizado da seguinte forma:

```text
C/
├── Makefile       # Script de compilação
├── README.md      # Este arquivo
├── include/       # Arquivos de cabeçalho (.h) definindo as estruturas e assinaturas
└── src/           # Implementação das funções (.c) e o arquivo principal
```

A estrutura de dados utiliza um *tagged union* virtual:
- **`ShapeType` (Enum):** Identifica o tipo da forma geométrica.
- **`ShapeContainer` (Struct):** Um "envelope" contendo o `ShapeType` e um ponteiro genérico (`void* data`) para a estrutura real correspondente (Ponto, Linha, Círculo, etc.).

Isso permite que o arquivo `main.c` crie um array misto de diferentes formas geométricas e, em tempo de execução, decida qual função específica chamar baseada na etiqueta da forma.

## Como compilar e executar

Todos os testes de colisão e cálculos baseiam-se nas entradas definidas no arquivo `shapes.txt` localizado na **raiz do repositório**.

### A partir do Makefile Raiz (Recomendado)
Volte para a raiz do projeto e execute:
```bash
make run-c
```
Isso garante que o binário encontre o arquivo `shapes.txt` automaticamente.

### Diretamente na pasta C
Você pode compilar e rodar estando dentro da pasta `C/`:
```bash
make
./geo_engine ../shapes.txt
```

> **Aviso de Memória:** O código em C aloca dinamicamente os arrays para os vértices do Polígono e as próprias formas geométricas. O `main.c` inclui as chamadas de `free()` correspondentes para evitar *memory leaks*.
