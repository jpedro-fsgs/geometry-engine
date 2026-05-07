#include "rectangle.h"
#include <math.h>

Rectangle rectangle_create(Point min_pt, Point max_pt) {
    Rectangle r = {min_pt, max_pt};
    return r;
}

double rectangle_perimeter(Rectangle r) {
    double width = fabs(r.max_pt.x - r.min_pt.x);
    double height = fabs(r.max_pt.y - r.min_pt.y);
    return 2.0 * (width + height);
}

double rectangle_area(Rectangle r) {
    double width = fabs(r.max_pt.x - r.min_pt.x);
    double height = fabs(r.max_pt.y - r.min_pt.y);
    return width * height;
}
