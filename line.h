#ifndef LINE_H
#define LINE_H

#include "point.h"

typedef struct {
    Point p1;
    Point p2;
} Line;

Line line_create(Point p1, Point p2);
double line_length(Line l);

#endif // LINE_H
