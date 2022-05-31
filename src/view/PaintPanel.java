package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import Controller.PaintListener;
import model.Edge;
import model.Graph;
import model.Vertex;

public class PaintPanel extends JPanel {
	private String typeButtonString = "";
	private static Graph graph;
	private static Vertex selected1;
	private static Vertex selected2;
	private boolean directed = false;
	private boolean undirecred = false;
	private Edge selectEdge;
	private String string = "";
	private ArrayList<QuadCurve2D> curveArrayList = new ArrayList<QuadCurve2D>();
	private boolean existEdge = false;

	public PaintPanel() {
		graph = new Graph();
		PaintListener paintListener = new PaintListener(this);
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

	public boolean isExistEdge() {
		return existEdge;
	}

	public void setExistEdge(boolean existEdge) {
		this.existEdge = existEdge;
	}

	public ArrayList<QuadCurve2D> getCurveArrayList() {
		return curveArrayList;
	}

	public void setCurveArrayList(ArrayList<QuadCurve2D> curveArrayList) {
		this.curveArrayList = curveArrayList;
	}

	public Point2D center(Rectangle2D boundsRectangle2d) {
		return new Point2D.Double(boundsRectangle2d.getCenterX(), boundsRectangle2d.getCenterY());
	}

	public double angleBetween(Vertex from, Vertex to) {
		return angleBetween(center(from.getEllipse().getBounds2D()), center(to.getEllipse().getBounds2D()));
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
		Rectangle2D bounds = shape.getEllipse().getBounds();
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

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for (Vertex v : graph.getVertexs()) {
			double x = v.getEllipse().getX();
			double y = v.getEllipse().getY();
			Ellipse2D el = new Ellipse2D.Double(x, y, 50, 50);
			graphics2d.setColor(Color.BLACK);
			graphics2d.fill(el);

			Font font = new Font("Arial", Font.BOLD, 15);
			FontMetrics metrics = graphics.getFontMetrics(font);
			graphics.setFont(font);
			graphics.setColor(Color.white);

			int xString = (int) (el.getX() + (el.getWidth() - metrics.stringWidth(string)) / 2) - 4;
			int yString = (int) (el.getY() + (el.getHeight() - metrics.getHeight()) / 2) + 14;
			graphics.drawString(v.getNameVeretex() + "", xString, yString);
		}
		for (Edge edge : graph.getEdges()) {
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(7f));
			if (isDirected() == true) {
				double from = angleBetween(edge.getNode1(), edge.getNode2());
				double to = angleBetween(edge.getNode1(), edge.getNode2());
				Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
				Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
				graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
				graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
				graphics2d.setStroke(new BasicStroke(4f));
				ArrowHead arrowHead = new ArrowHead();
				AffineTransform affineTransform = AffineTransform.getTranslateInstance(
						pointToPoint2d.getX() - (arrowHead.getBounds().getWidth() / 2d), pointToPoint2d.getY());
				affineTransform.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
				arrowHead.transform(affineTransform);
				graphics2d.draw(arrowHead);
				if (existEdge == true) {
					for (QuadCurve2D curve2d : curveArrayList) {
						graphics2d.draw(new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
								(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
								(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 40, pointToPoint2d.getX(),
								pointToPoint2d.getY()));
						ArrowHead arrowHead1 = new ArrowHead();
						AffineTransform at1 = AffineTransform
								.getTranslateInstance(curve2d.getX2() - (arrowHead1.getBounds2D().getWidth()-2 / 3d)-15, curve2d.getY2()+22);
						at1.rotate(curve2d.getX2()-0.5, curve2d.getCtrlX()-0.5,-2);
						arrowHead1.transform(at1);
						graphics2d.draw(arrowHead1);
					}
				}
			} else {
				double from = angleBetween(edge.getNode1(), edge.getNode2());
				double to = angleBetween(edge.getNode1(), edge.getNode2());
				Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
				Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
				graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
				if (existEdge == true) {
					for (QuadCurve2D curve2d : curveArrayList) {
						graphics2d.draw(new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
								(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
								(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 40, pointToPoint2d.getX(),
								pointToPoint2d.getY()));
					}
				}
			}
		}
	}
}
