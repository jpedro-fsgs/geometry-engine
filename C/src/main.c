#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "point.h"
#include "line.h"
#include "polygon.h"
#include "circle.h"
#include "rectangle.h"
#include "triangle.h"
#include "operations.h"

#define MAX_SHAPES 100

typedef enum { T_POINT, T_LINE, T_POLY, T_CIRCLE, T_RECT, T_TRI } ShapeType;

typedef struct {
    ShapeType type;
    void* data;
} ShapeContainer;

int main(int argc, char *argv[]) {
    const char *filename = (argc > 1) ? argv[1] : "shapes.txt";
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Error opening file");
        return 1;
    }

    ShapeContainer shapes[MAX_SHAPES];
    int shape_count = 0;

    char type_str[32];
    printf("--- Lendo e Processando Formas ---\n");
    while (fscanf(file, "%31s", type_str) != EOF && shape_count < MAX_SHAPES) {
        if (strcmp(type_str, "POINT") == 0) {
            double x, y;
            fscanf(file, "%lf %lf", &x, &y);
            Point* p = malloc(sizeof(Point));
            *p = point_create(x, y);
            shapes[shape_count++] = (ShapeContainer){T_POINT, p};
            printf("Read POINT: (%.2f, %.2f)\n", p->x, p->y);
        } else if (strcmp(type_str, "LINE") == 0) {
            double x1, y1, x2, y2;
            fscanf(file, "%lf %lf %lf %lf", &x1, &y1, &x2, &y2);
            Line* l = malloc(sizeof(Line));
            *l = line_create(point_create(x1, y1), point_create(x2, y2));
            shapes[shape_count++] = (ShapeContainer){T_LINE, l};
            printf("Read LINE: length = %.2f\n", line_length(*l));
        } else if (strcmp(type_str, "POLYGON") == 0) {
            int count;
            fscanf(file, "%d", &count);
            Point* vertices = malloc(count * sizeof(Point));
            for (int i = 0; i < count; i++) {
                double x, y;
                fscanf(file, "%lf %lf", &x, &y);
                vertices[i] = point_create(x, y);
            }
            Polygon* poly = malloc(sizeof(Polygon));
            *poly = polygon_create(vertices, count);
            shapes[shape_count++] = (ShapeContainer){T_POLY, poly};
            printf("Read POLYGON (%d vertices): Area = %.2f\n", count, polygon_area(*poly));
        } else if (strcmp(type_str, "CIRCLE") == 0) {
            double x, y, r;
            fscanf(file, "%lf %lf %lf", &x, &y, &r);
            Circle* c = malloc(sizeof(Circle));
            *c = circle_create(point_create(x, y), r);
            shapes[shape_count++] = (ShapeContainer){T_CIRCLE, c};
            printf("Read CIRCLE: (%.2f, %.2f) r=%.2f\n", c->center.x, c->center.y, c->radius);
        } else if (strcmp(type_str, "RECTANGLE") == 0) {
            double x1, y1, x2, y2;
            fscanf(file, "%lf %lf %lf %lf", &x1, &y1, &x2, &y2);
            Rectangle* rect = malloc(sizeof(Rectangle));
            *rect = rectangle_create(point_create(x1, y1), point_create(x2, y2));
            shapes[shape_count++] = (ShapeContainer){T_RECT, rect};
            printf("Read RECTANGLE: Area = %.2f\n", rectangle_area(*rect));
        } else if (strcmp(type_str, "TRIANGLE") == 0) {
            double x1, y1, x2, y2, x3, y3;
            fscanf(file, "%lf %lf %lf %lf %lf %lf", &x1, &y1, &x2, &y2, &x3, &y3);
            Triangle* t = malloc(sizeof(Triangle));
            *t = triangle_create(point_create(x1, y1), point_create(x2, y2), point_create(x3, y3));
            shapes[shape_count++] = (ShapeContainer){T_TRI, t};
            printf("Read TRIANGLE: Area = %.2f\n", triangle_area(*t));
        }
    }
    fclose(file);

    printf("\n--- Testes de Operações Dinâmicas ---\n");
    for (int i = 0; i < shape_count; i++) {
        for (int j = 0; j < shape_count; j++) {
            if (i == j) continue; // Não testar contra si mesmo

            ShapeContainer s1 = shapes[i];
            ShapeContainer s2 = shapes[j];

            // 1. PONTO em outras formas
            if (s1.type == T_POINT) {
                Point p = *(Point*)s1.data;
                if (s2.type == T_CIRCLE) {
                    if (point_in_circle(p, *(Circle*)s2.data))
                        printf("Ponto (%.1f,%.1f) esta DENTRO do Circulo r=%.1f\n", p.x, p.y, ((Circle*)s2.data)->radius);
                } else if (s2.type == T_RECT) {
                    if (point_in_rectangle(p, *(Rectangle*)s2.data))
                        printf("Ponto (%.1f,%.1f) esta DENTRO do Retangulo\n", p.x, p.y);
                } else if (s2.type == T_POLY) {
                    if (point_in_polygon(p, *(Polygon*)s2.data))
                        printf("Ponto (%.1f,%.1f) esta DENTRO do Poligono\n", p.x, p.y);
                } else if (s2.type == T_TRI) {
                    if (point_in_triangle(p, *(Triangle*)s2.data))
                        printf("Ponto (%.1f,%.1f) esta DENTRO do Triangulo\n", p.x, p.y);
                } else if (s2.type == T_LINE) {
                    double dist = distance_point_to_line(p, *(Line*)s2.data);
                    if (dist < 0.1) // Se estiver bem perto, consideramos que toca
                        printf("Ponto (%.1f,%.1f) TOCA a Linha (dist=%.2f)\n", p.x, p.y, dist);
                }
            }
            
            // 2. LINHA em outras formas
            if (s1.type == T_LINE) {
                Line l1 = *(Line*)s1.data;
                if (s2.type == T_LINE) {
                    if (i < j && line_intersects(l1, *(Line*)s2.data))
                        printf("Linha INTERSECTA outra Linha\n");
                } else if (s2.type == T_CIRCLE) {
                    if (line_intersects_circle(l1, *(Circle*)s2.data))
                        printf("Linha INTERSECTA Circulo em (%.1f,%.1f)\n", ((Circle*)s2.data)->center.x, ((Circle*)s2.data)->center.y);
                } else if (s2.type == T_RECT) {
                    if (line_intersects_rectangle(l1, *(Rectangle*)s2.data))
                        printf("Linha INTERSECTA Retangulo\n");
                } else if (s2.type == T_POLY) {
                    if (line_intersects_polygon(l1, *(Polygon*)s2.data))
                        printf("Linha INTERSECTA Poligono\n");
                }
            }

            // 3. CIRCULO em outras formas
            if (s1.type == T_CIRCLE) {
                Circle c1 = *(Circle*)s1.data;
                if (s2.type == T_CIRCLE) {
                    if (i < j && circle_intersects(c1, *(Circle*)s2.data))
                        printf("Circulo em (%.1f,%.1f) INTERSECTA outro Circulo\n", c1.center.x, c1.center.y);
                } else if (s2.type == T_RECT) {
                    if (circle_intersects_rectangle(c1, *(Rectangle*)s2.data))
                        printf("Circulo r=%.1f INTERSECTA Retangulo\n", c1.radius);
                } else if (s2.type == T_TRI) {
                    if (circle_intersects_triangle(c1, *(Triangle*)s2.data))
                        printf("Circulo r=%.1f INTERSECTA Triangulo\n", c1.radius);
                }
            }

            // 4. RETANGULO em outras formas
            if (s1.type == T_RECT) {
                Rectangle r1 = *(Rectangle*)s1.data;
                if (s2.type == T_RECT) {
                    if (i < j && rectangle_intersects(r1, *(Rectangle*)s2.data))
                        printf("Retangulo INTERSECTA outro Retangulo\n");
                } else if (s2.type == T_TRI) {
                    if (rectangle_intersects_triangle(r1, *(Triangle*)s2.data))
                        printf("Retangulo INTERSECTA Triangulo\n");
                }
            }

            // 5. POLIGONO em outras formas
            if (s1.type == T_POLY) {
                Polygon p1 = *(Polygon*)s1.data;
                if (s2.type == T_CIRCLE) {
                    if (polygon_intersects_circle(p1, *(Circle*)s2.data))
                        printf("Poligono INTERSECTA Circulo r=%.1f\n", ((Circle*)s2.data)->radius);
                } else if (s2.type == T_RECT) {
                    if (polygon_intersects_rectangle(p1, *(Rectangle*)s2.data))
                        printf("Poligono INTERSECTA Retangulo\n");
                }
            }
        }
    }

    // Limpeza
    for (int i = 0; i < shape_count; i++) {
        if (shapes[i].type == T_POLY) free(((Polygon*)shapes[i].data)->vertices);
        free(shapes[i].data);
    }

    return 0;
}
