import matplotlib.pyplot as plt
import matplotlib.patches as patches

def load_shapes(filename="shapes.txt"):
    """
    Carrega as formas geométricas de um arquivo texto.
    """
    points = []
    lines = []
    polygons = []
    circles = []
    rectangles = []
    triangles = []
    
    try:
        with open(filename, 'r') as f:
            for line in f:
                parts = line.strip().split()
                if not parts:
                    continue
                
                type_ = parts[0]
                if type_ == 'POINT':
                    points.append((float(parts[1]), float(parts[2])))
                elif type_ == 'LINE':
                    lines.append((
                        (float(parts[1]), float(parts[2])),
                        (float(parts[3]), float(parts[4]))
                    ))
                elif type_ == 'POLYGON':
                    count = int(parts[1])
                    coords = [float(x) for x in parts[2:]]
                    vertices = []
                    for i in range(0, count * 2, 2):
                        vertices.append((coords[i], coords[i+1]))
                    polygons.append(vertices)
                elif type_ == 'CIRCLE':
                    circles.append(((float(parts[1]), float(parts[2])), float(parts[3])))
                elif type_ == 'RECTANGLE':
                    rectangles.append(((float(parts[1]), float(parts[2])), (float(parts[3]), float(parts[4]))))
                elif type_ == 'TRIANGLE':
                    triangles.append([
                        (float(parts[1]), float(parts[2])),
                        (float(parts[3]), float(parts[4])),
                        (float(parts[5]), float(parts[6]))
                    ])
    except FileNotFoundError:
        print(f"Erro: Arquivo '{filename}' não encontrado.")
    except Exception as e:
        print(f"Erro ao ler arquivo: {e}")
                
    return points, lines, polygons, circles, rectangles, triangles

def visualize_geo_engine():
    points, lines, polygons, circles, rectangles, triangles = load_shapes("shapes.txt")
    
    if not any([points, lines, polygons, circles, rectangles, triangles]):
        print("Nenhuma forma encontrada para visualizar.")
        return

    fig, ax = plt.subplots(figsize=(10, 8))
    
    # 1. Plotar os Polígonos
    for i, poly in enumerate(polygons):
        poly_x = [v[0] for v in poly] + [poly[0][0]]
        poly_y = [v[1] for v in poly] + [poly[0][1]]
        ax.fill(poly_x, poly_y, alpha=0.3, label=f'Polígono {i+1}')
        ax.plot(poly_x, poly_y, marker='o', linestyle='-', linewidth=2)

    # 2. Plotar as Linhas
    for i, line in enumerate(lines):
        p1, p2 = line
        ax.plot([p1[0], p2[0]], [p1[1], p2[1]], linestyle='--', 
                 marker='x', label=f'Linha {i+1}')

    # 3. Plotar os Pontos
    for i, p in enumerate(points):
        ax.plot(p[0], p[1], marker='D', label=f'Ponto {i+1}')
        ax.text(p[0], p[1], f' ({p[0]}, {p[1]})', verticalalignment='bottom')

    # 4. Plotar os Círculos
    for i, (center, radius) in enumerate(circles):
        circle_patch = patches.Circle(center, radius, fill=True, alpha=0.2, color='green', label=f'Círculo {i+1}')
        ax.add_patch(circle_patch)
        # Desenhar a borda separadamente para melhor visibilidade
        ax.plot(center[0], center[1], 'go') # centro
        circle_edge = patches.Circle(center, radius, fill=False, color='green', linewidth=2)
        ax.add_patch(circle_edge)

    # 5. Plotar os Retângulos
    for i, (p_min, p_max) in enumerate(rectangles):
        width = p_max[0] - p_min[0]
        height = p_max[1] - p_min[1]
        rect_patch = patches.Rectangle(p_min, width, height, fill=True, alpha=0.2, color='orange', label=f'Retângulo {i+1}')
        ax.add_patch(rect_patch)
        rect_edge = patches.Rectangle(p_min, width, height, fill=False, color='orange', linewidth=2)
        ax.add_patch(rect_edge)

    # 6. Plotar os Triângulos
    for i, tri in enumerate(triangles):
        tri_x = [v[0] for v in tri] + [tri[0][0]]
        tri_y = [v[1] for v in tri] + [tri[0][1]]
        ax.fill(tri_x, tri_y, alpha=0.3, color='purple', label=f'Triângulo {i+1}')
        ax.plot(tri_x, tri_y, marker='v', linestyle='-', color='purple', linewidth=2)

    ax.set_title("Visualização Dinâmica do Motor Geométrico", fontsize=14)
    ax.set_xlabel("Eixo X")
    ax.set_ylabel("Eixo Y")
    ax.grid(True, linestyle=':', alpha=0.7)
    ax.axhline(0, color='black', linewidth=0.5)
    ax.axvline(0, color='black', linewidth=0.5)
    
    # Gerar legenda sem duplicatas de labels
    handles, labels = ax.get_legend_handles_labels()
    by_label = dict(zip(labels, handles))
    ax.legend(by_label.values(), by_label.keys())
    
    ax.set_aspect('equal')
    print("Exibindo visualização...")
    plt.show()

if __name__ == "__main__":
    try:
        visualize_geo_engine()
    except ImportError:
        print("Erro: Matplotlib não encontrado. Instale com 'pip install matplotlib'.")
