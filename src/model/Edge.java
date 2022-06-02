package model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Edge {
	private Vertex node1;
	private Vertex node2;
	private Line2D line2d;

	public Edge(Vertex node1, Vertex node2) {
		super();
		this.node1 = node1;
		this.node2 = node2;
	}

	public Vertex getNode1() {
		return node1;
	}

	public void setNode1(Vertex node1) {
		this.node1 = node1;
	}

	public Vertex getNode2() {
		return node2;
	}

	public void setNode2(Vertex node2) {
		this.node2 = node2;
	}

	private Point2D center(Rectangle2D boundsRectangle2d) {
		return new Point2D.Double(boundsRectangle2d.getCenterX(), boundsRectangle2d.getCenterY());
	}

	private double angleBetween(Vertex from, Vertex to) {
		return angleBetween(center(from.getEllipse().getEllipse2d().getBounds2D()), center(to.getEllipse().getEllipse2d().getBounds2D()));
	}

	private double angleBetween(Point2D from, Point2D to) {
		double x = from.getX();
		double y = from.getY();
		double deltaX = to.getX() - x;
		double deltaY = to.getY() - y;

		double rotation = -Math.atan2(deltaX, deltaY);
		rotation = Math.toRadians(Math.toDegrees(rotation) + 180);
		return rotation;
	}

	private Point2D getPointOnCircle(Vertex shape, double radians) {
		Rectangle2D bounds = shape.getEllipse().getEllipse2d().getBounds();
//		Point2D point = new Point2D.Double(bounds.getX(), bounds.getY()-20);
		Point2D point = center(bounds);
		return getPointOnCircle(point, radians, Math.max(bounds.getWidth(), bounds.getHeight()) / 2d);
	}

	private Point2D getPointOnCircle(Point2D center, double radians, double radius) {

		double x = center.getX();
		double y = center.getY();

		radians = radians - Math.toRadians(90.0); // 0 becomes th?e top
		// Calculate the outter point of the line
		double xPosy = Math.round((float) (x + Math.cos(radians) * radius));
		double yPosy = Math.round((float) (y + Math.sin(radians) * radius));

		return new Point2D.Double(xPosy, yPosy);
	}

	public Line2D getLine2d() {

		double from = angleBetween(node1, node2);
		double to = angleBetween(node1, node2);

		Point2D pointFromPoint2d = getPointOnCircle(node1, from);
		Point2D pointToPoint2d = getPointOnCircle(node2, to - 22);
		this.line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);
		return line2d;
	}
}
