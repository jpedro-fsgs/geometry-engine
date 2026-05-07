#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "point.h"
#include "line.h"
#include "polygon.h"
#include "circle.h"
#include "rectangle.h"
#include "triangle.h"

int main() {
    FILE *file = fopen("shapes.txt", "r");
    if (!file) {
        perror("Error opening shapes.txt");
        return 1;
    }

    char type[32];
    while (fscanf(file, "%31s", type) != EOF) {
        if (strcmp(type, "POINT") == 0) {
            double x, y;
            if (fscanf(file, "%lf %lf", &x, &y) == 2) {
                Point p = point_create(x, y);
                printf("Read POINT: (%.2f, %.2f)\n", p.x, p.y);
            }
        } else if (strcmp(type, "LINE") == 0) {
            double x1, y1, x2, y2;
            if (fscanf(file, "%lf %lf %lf %lf", &x1, &y1, &x2, &y2) == 4) {
                Point p1 = point_create(x1, y1);
                Point p2 = point_create(x2, y2);
                Line l = line_create(p1, p2);
                printf("Read LINE: length = %.2f\n", line_length(l));
            }
        } else if (strcmp(type, "POLYGON") == 0) {
            int count;
            if (fscanf(file, "%d", &count) == 1 && count > 0) {
                Point *vertices = malloc(count * sizeof(Point));
                if (vertices == NULL) {
                    fprintf(stderr, "Memory allocation failed for polygon vertices\n");
                    continue;
                }
                for (int i = 0; i < count; i++) {
                    double x, y;
                    if (fscanf(file, "%lf %lf", &x, &y) == 2) {
                        vertices[i] = point_create(x, y);
                    }
                }
                Polygon poly = polygon_create(vertices, count);
                printf("Read POLYGON (%d vertices):\n", count);
                printf("  - Perimeter: %.2f\n", polygon_perimeter(poly));
                printf("  - Area: %.2f\n", polygon_area(poly));
                free(vertices);
            }
        } else if (strcmp(type, "CIRCLE") == 0) {
            double x, y, r;
            if (fscanf(file, "%lf %lf %lf", &x, &y, &r) == 3) {
                Circle c = circle_create(point_create(x, y), r);
                printf("Read CIRCLE: (%.2f, %.2f) r=%.2f\n", c.center.x, c.center.y, c.radius);
                printf("  - Perimeter: %.2f\n", circle_perimeter(c));
                printf("  - Area: %.2f\n", circle_area(c));
            }
        } else if (strcmp(type, "RECTANGLE") == 0) {
            double x1, y1, x2, y2;
            if (fscanf(file, "%lf %lf %lf %lf", &x1, &y1, &x2, &y2) == 4) {
                Rectangle rect = rectangle_create(point_create(x1, y1), point_create(x2, y2));
                printf("Read RECTANGLE: min=(%.2f, %.2f) max=(%.2f, %.2f)\n", rect.min_pt.x, rect.min_pt.y, rect.max_pt.x, rect.max_pt.y);
                printf("  - Perimeter: %.2f\n", rectangle_perimeter(rect));
                printf("  - Area: %.2f\n", rectangle_area(rect));
            }
        } else if (strcmp(type, "TRIANGLE") == 0) {
            double x1, y1, x2, y2, x3, y3;
            if (fscanf(file, "%lf %lf %lf %lf %lf %lf", &x1, &y1, &x2, &y2, &x3, &y3) == 6) {
                Triangle t = triangle_create(point_create(x1, y1), point_create(x2, y2), point_create(x3, y3));
                printf("Read TRIANGLE: p1=(%.2f, %.2f) p2=(%.2f, %.2f) p3=(%.2f, %.2f)\n", t.p1.x, t.p1.y, t.p2.x, t.p2.y, t.p3.x, t.p3.y);
                printf("  - Perimeter: %.2f\n", triangle_perimeter(t));
                printf("  - Area: %.2f\n", triangle_area(t));
            }
        } else {
            char buffer[256];
            fgets(buffer, sizeof(buffer), file);
        }
    }

    fclose(file);
    return 0;
}
