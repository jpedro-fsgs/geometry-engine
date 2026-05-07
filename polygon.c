#include "polygon.h"
#include <math.h>
#include <stdlib.h>

Polygon polygon_create(Point* vertices, int count) {
    Polygon p = {vertices, count};
    return p;
}

double polygon_perimeter(Polygon p) {
    double perimeter = 0;
    for (int i = 0; i < p.count; i++) {
        Point p1 = p.vertices[i];
        Point p2 = p.vertices[(i + 1) % p.count];
        perimeter += point_distance(p1, p2);
    }
    return perimeter;
}

double polygon_area(Polygon p) {
    double area = 0.0;
    int j = p.count - 1;

    for (int i = 0; i < p.count; i++) {
        area += (p.vertices[j].x + p.vertices[i].x) * (p.vertices[j].y - p.vertices[i].y);
        j = i;
    }

    return fabs(area / 2.0);
}
