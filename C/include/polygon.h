#ifndef POLYGON_H
#define POLYGON_H

#include "point.h"

typedef struct {
    Point* vertices;
    int count;
} Polygon;

Polygon polygon_create(Point* vertices, int count);
double polygon_perimeter(Polygon p);
double polygon_area(Polygon p);

#endif // POLYGON_H
