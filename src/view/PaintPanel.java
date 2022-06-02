package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.PaintListener;
import model.AShape;
import model.Edge;
import model.Graph;
import model.Vertex;

public class PaintPanel extends JPanel {
	private String typeButtonString = "";
	static Graph graph;
	static Vertex selected1, selected2;
	private boolean directed = false;
	private boolean undirecred = false;
	private Edge selectEdge;
	private ArrayList<AShape> shapes;

	public PaintPanel() {
		shapes = new ArrayList<>();
		this.init();
		graph = new Graph();
		PaintListener paintListener = new PaintListener(graph, this);
		this.addMouseListener(paintListener);
	}

	private void init() {
		JPanel panel = new JPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.white);
	}

	public String getTypeButtonString() {
		return typeButtonString;
	}

	public void setTypeButtonString(String typeButtonString) {
		this.typeButtonString = typeButtonString;
	}

	public boolean isDirected() {
		return directed;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}

	public boolean isUndirecred() {
		return undirecred;
	}

	public void setUndirecred(boolean undirecred) {
		this.undirecred = undirecred;
	}

	public static Graph getGraph() {
		return graph;
	}

	public static void setGraph(Graph graph) {
		PaintPanel.graph = graph;
	}

	public static Vertex getSelected1() {
		return selected1;
	}

	public static void setSelected1(Vertex selected1) {
		PaintPanel.selected1 = selected1;
	}

	public static Vertex getSelected2() {
		return selected2;
	}

	public static void setSelected2(Vertex selected2) {
		PaintPanel.selected2 = selected2;
	}

	public Edge getSelectEdge() {
		return selectEdge;
	}

	public void setSelectEdge(Edge selectEdge) {
		this.selectEdge = selectEdge;
	}

	public Point2D center(Rectangle2D boundsRectangle2d) {
		return new Point2D.Double(boundsRectangle2d.getCenterX(), boundsRectangle2d.getCenterY());
	}

	public double angleBetween(Vertex from, Vertex to) {
		return angleBetween(center(from.getEllipse().getEllipse2d().getBounds2D()), center(to.getEllipse().getEllipse2d().getBounds2D()));
	}

	public double angleBetween(Point2D from, Point2D to) {
		double x = from.getX();
		double y = from.getY();
		double deltaX = to.getX() - x;
		double deltaY = to.getY() - y;

		double rotation = -Math.atan2(deltaX, deltaY);
		rotation = Math.toRadians(Math.toDegrees(rotation) + 180);
		return rotation;
	}

	public Point2D getPointOnCircle(Vertex shape, double radians) {
		Rectangle2D bounds = shape.getEllipse().getEllipse2d().getBounds();
//		Point2D point = new Point2D.Double(bounds.getX(), bounds.getY()-20);
		Point2D point = center(bounds);
		return getPointOnCircle(point, radians, Math.max(bounds.getWidth(), bounds.getHeight()) / 2d);
	}

	public Point2D getPointOnCircle(Point2D center, double radians, double radius) {

		double x = center.getX();
		double y = center.getY();

		radians = radians - Math.toRadians(90.0); // 0 becomes th?e top
		// Calculate the outter point of the line
		double xPosy = Math.round((float) (x + Math.cos(radians) * radius));
		double yPosy = Math.round((float) (y + Math.sin(radians) * radius));

		return new Point2D.Double(xPosy, yPosy);
	}

	public ArrayList<AShape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<AShape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for (AShape shape : shapes) {
			System.out.println("1");
			shape.drawShape(graphics);
		}
	}
}
