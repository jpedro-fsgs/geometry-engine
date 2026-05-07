#include "operations.h"
#include <math.h>

// --- AUXILIARES ---

static int orientation(Point p, Point q, Point r) {
    double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    if (val == 0) return 0;
    return (val > 0) ? 1 : 2;
}

static int on_segment(Point p, Point q, Point r) {
    if (q.x <= fmax(p.x, r.x) && q.x >= fmin(p.x, r.x) &&
        q.y <= fmax(p.y, r.y) && q.y >= fmin(p.y, r.y))
        return 1;
    return 0;
}

// --- MESMA FORMA ---

int line_intersects(Line l1, Line l2) {
    Point p1 = l1.p1, q1 = l1.p2;
    Point p2 = l2.p1, q2 = l2.p2;

    int o1 = orientation(p1, q1, p2);
    int o2 = orientation(p1, q1, q2);
    int o3 = orientation(p2, q2, p1);
    int o4 = orientation(p2, q2, q1);

    if (o1 != o2 && o3 != o4) return 1;

    if (o1 == 0 && on_segment(p1, p2, q1)) return 1;
    if (o2 == 0 && on_segment(p1, q2, q1)) return 1;
    if (o3 == 0 && on_segment(p2, p1, q2)) return 1;
    if (o4 == 0 && on_segment(p2, q1, q2)) return 1;

    return 0;
}

int circle_intersects(Circle c1, Circle c2) {
    return point_distance(c1.center, c2.center) <= (c1.radius + c2.radius);
}

int rectangle_intersects(Rectangle r1, Rectangle r2) {
    if (r1.max_pt.x < r2.min_pt.x || r1.min_pt.x > r2.max_pt.x) return 0;
    if (r1.max_pt.y < r2.min_pt.y || r1.min_pt.y > r2.max_pt.y) return 0;
    return 1;
}

// --- FORMAS DIFERENTES ---

int point_in_polygon(Point p, Polygon poly) {
    int count = 0;
    for (int i = 0; i < poly.count; i++) {
        Point p1 = poly.vertices[i];
        Point p2 = poly.vertices[(i + 1) % poly.count];
        if (((p1.y > p.y) != (p2.y > p.y)) &&
            (p.x < (p2.x - p1.x) * (p.y - p1.y) / (p2.y - p1.y) + p1.x)) {
            count++;
        }
    }
    return count % 2 == 1;
}

int point_in_circle(Point p, Circle c) {
    return point_distance(p, c.center) <= c.radius;
}

int point_in_rectangle(Point p, Rectangle r) {
    return (p.x >= r.min_pt.x && p.x <= r.max_pt.x &&
            p.y >= r.min_pt.y && p.y <= r.max_pt.y);
}

int circle_intersects_rectangle(Circle c, Rectangle r) {
    double closest_x = fmax(r.min_pt.x, fmin(c.center.x, r.max_pt.x));
    double closest_y = fmax(r.min_pt.y, fmin(c.center.y, r.max_pt.y));
    
    Point closest_pt = {closest_x, closest_y};
    return point_distance(c.center, closest_pt) <= c.radius;
}

int line_intersects_circle(Line l, Circle c) {
    return distance_point_to_line(c.center, l) <= c.radius;
}

// --- DISTÂNCIAS ---

double distance_point_to_line(Point p, Line l) {
    double dx = l.p2.x - l.p1.x;
    double dy = l.p2.y - l.p1.y;
    
    if (dx == 0 && dy == 0) return point_distance(p, l.p1);
    
    double t = ((p.x - l.p1.x) * dx + (p.y - l.p1.y) * dy) / (dx * dx + dy * dy);
    
    if (t < 0) return point_distance(p, l.p1);
    if (t > 1) return point_distance(p, l.p2);
    
    Point projection = {l.p1.x + t * dx, l.p1.y + t * dy};
    return point_distance(p, projection);
}
