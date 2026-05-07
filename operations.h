#ifndef OPERATIONS_H
#define OPERATIONS_H

#include "point.h"
#include "line.h"
#include "polygon.h"
#include "circle.h"
#include "rectangle.h"
#include "triangle.h"

// Interseções entre a mesma forma
int line_intersects(Line l1, Line l2);
int circle_intersects(Circle c1, Circle c2);
int rectangle_intersects(Rectangle r1, Rectangle r2);

// Interseções entre formas diferentes
int point_in_polygon(Point p, Polygon poly);
int point_in_circle(Point p, Circle c);
int point_in_rectangle(Point p, Rectangle r);
int circle_intersects_rectangle(Circle c, Rectangle r);
int line_intersects_circle(Line l, Circle c);

// Distâncias
double distance_point_to_line(Point p, Line l);

#endif // OPERATIONS_H
