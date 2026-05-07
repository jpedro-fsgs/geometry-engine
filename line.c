#include "line.h"

Line line_create(Point p1, Point p2) {
    Line l = {p1, p2};
    return l;
}

double line_length(Line l) {
    return point_distance(l.p1, l.p2);
}
