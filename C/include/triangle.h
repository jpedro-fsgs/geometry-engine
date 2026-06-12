#ifndef TRIANGLE_H
#define TRIANGLE_H

#include "point.h"

typedef struct {
    Point p1, p2, p3;
} Triangle;

Triangle triangle_create(Point p1, Point p2, Point p3);
double triangle_perimeter(Triangle t);
double triangle_area(Triangle t);

#endif // TRIANGLE_H
