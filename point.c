#include "point.h"
#include <math.h>

Point point_create(double x, double y) {
    Point p = {x, y};
    return p;
}

double point_distance(Point p1, Point p2) {
    return sqrt(pow(p2.x - p1.x, 2) + pow(p2.y - p1.y, 2));
}
