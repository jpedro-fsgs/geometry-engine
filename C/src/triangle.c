#include "triangle.h"
#include <math.h>

Triangle triangle_create(Point p1, Point p2, Point p3) {
    Triangle t = {p1, p2, p3};
    return t;
}

double triangle_perimeter(Triangle t) {
    return point_distance(t.p1, t.p2) + 
           point_distance(t.p2, t.p3) + 
           point_distance(t.p3, t.p1);
}

double triangle_area(Triangle t) {
    double a = point_distance(t.p1, t.p2);
    double b = point_distance(t.p2, t.p3);
    double c = point_distance(t.p3, t.p1);
    double s = (a + b + c) / 2.0;
    return sqrt(s * (s - a) * (s - b) * (s - c));
}
