package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;
import java.util.Arrays;

/**
 * A special case of a group consisting only of Points.
 *
 */
public final class Polygon extends Group {

    private final List<Point> points;

    public Polygon(final Point... points) {
        super(points);
        this.points = Arrays.asList(points);
    }

    @SuppressWarnings("unchecked")
    public List<Point> getPoints() {
        return (List<Point>) getShapes();
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        // TODO your job
        return v.onPolygon(this);
    }
}
