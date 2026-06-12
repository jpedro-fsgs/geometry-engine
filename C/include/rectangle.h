#ifndef RECTANGLE_H
#define RECTANGLE_H

#include "point.h"

typedef struct {
    Point min_pt;
    Point max_pt;
} Rectangle;

Rectangle rectangle_create(Point min_pt, Point max_pt);
double rectangle_perimeter(Rectangle r);
double rectangle_area(Rectangle r);

#endif // RECTANGLE_H
