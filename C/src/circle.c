#include "circle.h"
#include <math.h>

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

Circle circle_create(Point center, double radius) {
    Circle c = {center, radius};
    return c;
}

double circle_perimeter(Circle c) {
    return 2.0 * M_PI * c.radius;
}

double circle_area(Circle c) {
    return M_PI * c.radius * c.radius;
}
