#ifndef CIRCLE_H
#define CIRCLE_H

#include "point.h"

typedef struct {
    Point center;
    double radius;
} Circle;

Circle circle_create(Point center, double radius);
double circle_perimeter(Circle c);
double circle_area(Circle c);

#endif // CIRCLE_H
