#ifndef POINT_H
#define POINT_H

typedef struct {
    double x;
    double y;
} Point;

Point point_create(double x, double y);
double point_distance(Point p1, Point p2);

#endif // POINT_H
