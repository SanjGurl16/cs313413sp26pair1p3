package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle) COMPLETED

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        int minX =Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for(Shape s : g.getShapes()) {
            Location b = s.accept(this);
            Rectangle r = (Rectangle) b.getShape();

            int x1 = b.getX();
            int y1 = b.getY();
            int x2 = b.getX() + r.getWidth();
            int y2 = b.getY() + r.getHeight();

            if(x1 < minX) minX = x1;
            if(y1 < minY) minY = y1;
            if(x2 > maxX) maxX = x2;
            if(y2 > maxY) maxY = y2;
        }
        return new Location(minX, minY, new Rectangle(maxX - minX,
                maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        final Location inner = l.getShape().accept(this);
        return new Location(l.getX() + inner.getX(),
                l.getY() + inner.getY(), inner.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point pt : s.getPoints()) {
            int x = pt.getX();
            int y = pt.getY();

            if(x < minX) minX = x;
            if(y < minY) minY = y;
            if(x > maxX) maxX = x;
            if(y > maxY) maxY = y;
        }
        return new Location( minX, minY, new Rectangle(maxX- minX,
                maxY - minY));
    }
}
