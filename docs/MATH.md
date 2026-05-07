# Documentação Matemática do Motor Geométrico

Este documento detalha os princípios matemáticos e algoritmos utilizados para os cálculos e detecção de colisões neste motor.

## 1. Distâncias

### Distância Euclidiana (Ponto a Ponto)
A base para quase todos os cálculos. Usamos o Teorema de Pitágoras:
$$d = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}$$

### Distância Ponto a Linha (Segmento)
Para calcular a menor distância entre um ponto $P$ e um segmento $AB$:
1. Projetamos o ponto $P$ na reta infinita que contém $AB$ usando produto escalar.
2. O fator de projeção $t$ nos diz onde a projeção cai:
   - Se $t \le 0$, o ponto mais próximo é $A$.
   - Se $t \ge 1$, o ponto mais próximo é $B$.
   - Se $0 < t < 1$, o ponto mais próximo é a projeção ortogonal no segmento.

### Distância Ponto a Retângulo (AABB)
Calculamos a distância separadamente nos eixos X e Y:
- $dx = \max(min.x - p.x, 0, p.x - max.x)$
- $dy = \max(min.y - p.y, 0, p.y - max.y)$
- $distancia = \sqrt{dx^2 + dy^2}$

### Distância Ponto a Círculo
Calculamos a distância do ponto ao centro do círculo e subtraímos o raio:
$$d = | \sqrt{(x_p - x_c)^2 + (y_p - y_c)^2} - r |$$

---

## 2. Áreas e Perímetros

### Polígonos (Fórmula de Shoelace)
Para um polígono com vértices $(x_i, y_i)$, a área é:
$$Area = \frac{1}{2} | \sum_{i=0}^{n-1} (x_i y_{i+1} - x_{i+1} y_i) |$$
Isso funciona para qualquer polígono simples (não auto-intersectante).

### Triângulos (Fórmula de Heron)
Calculamos a área a partir do semi-perímetro $s$ e dos lados $a, b, c$:
$$s = \frac{a+b+c}{2}$$
$$Area = \sqrt{s(s-a)(s-b)(s-c)}$$

### Círculos
- **Área:** $A = \pi r^2$
- **Perímetro:** $P = 2 \pi r$

---

## 3. Inclusão (Ponto dentro de Forma)

### Ponto em Polígono (Raycasting)
Traçamos um raio horizontal partindo do ponto para a direita.
- Se o raio cruzar um número **ímpar** de arestas, o ponto está **dentro**.
- Se for **par**, está **fora**.

### Ponto em Triângulo (Soma de Áreas)
Se um ponto $P$ está dentro do triângulo $ABC$, então a soma das áreas dos sub-triângulos $PAB$, $PBC$ e $PCA$ deve ser exatamente igual à área de $ABC$.

### Ponto em Círculo
Verificamos se a distância do ponto ao centro é menor ou igual ao raio:
$$\sqrt{(x_p - x_c)^2 + (y_p - y_c)^2} \le r$$

### Ponto em Retângulo (AABB)
Verificamos se as coordenadas do ponto estão entre os limites do retângulo:
$$(min.x \le p.x \le max.x) \text{ AND } (min.y \le p.y \le max.y)$$

---

## 4. Intersecções

### Linha x Linha (Orientação)
Usamos o conceito de orientação de três pontos (Produto Vetorial 2D). Dois segmentos $AB$ e $CD$ se cruzam se:
- $C$ e $D$ têm orientações diferentes em relação a $AB$.
- **E** $A$ e $B$ têm orientações diferentes em relação a $CD$.

### Círculo x Círculo
Dois círculos se intersectam se a distância entre seus centros for menor ou igual à soma de seus raios:
$$dist(C_1, C_2) \le r_1 + r_2$$

### Retângulo x Retângulo (AABB)
Dois retângulos alinhados aos eixos se intersectam se não houver separação em nenhum dos eixos:
$$(r1.max.x \ge r2.min.x) \text{ AND } (r1.min.x \le r2.max.x) \text{ AND } (r1.max.y \ge r2.min.y) \text{ AND } (r1.min.y \le r2.max.y)$$

### Círculo x Retângulo
Encontramos o ponto no retângulo que está mais próximo do centro do círculo. Se a distância entre esse ponto e o centro for menor ou igual ao raio, há intersecção.

### Forma A x Forma B (Abordagem Geral)
Para formas como Polígono x Círculo ou Linha x Retângulo, usamos uma combinação de:
1. Verificar se algum vértice da Forma A está dentro da Forma B.
2. Verificar se qualquer aresta da Forma A intersecta qualquer aresta da Forma B.
