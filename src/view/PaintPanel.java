package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Taskbar;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import Controller.PaintListener;
import model.Edge;
import model.Graph;
import model.Vertex;

public class PaintPanel extends JPanel {
	private String typeButtonString = "";
	private static Graph graph;
	private static Vertex selected1;
	private static Vertex selected2;
	private static Edge selectedEdge;
	private boolean directed = false;
	private boolean undirecred = false;
	private String string = "";
	private int[] vertexs;
	private ArrayList<QuadCurve2D> curveArrayList = new ArrayList<QuadCurve2D>();
	private boolean drag = false;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private ArrayList<Ellipse2D> ellipse2ds = new ArrayList<Ellipse2D>();
	private ArrayList<Vertex> vList = new ArrayList<Vertex>();

	public PaintPanel() {
		graph = new Graph();
		PaintListener paintListener = new PaintListener(this);
		this.addMouseListener(paintListener);
		this.addMouseMotionListener(paintListener);
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
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

	public int[] getVertexs() {
		return vertexs;
	}

	public void setVertexs(int[] vertexs) {
		this.vertexs = vertexs;
	}

	public void delVertex(Vertex v) {
		graph.delVertex(v);
	}

	public void fillVertes() {
		vertexs = new int[graph.getVertexs().size() - 1];
		for (int i = 0; i < graph.getVertexs().size(); i++) {
			vertexs[i] = graph.getVertexs().get(i).getIndex();
		}
	}

	public void resetTraved() {
		for (Edge e : graph.getEdges()) {
			e.setTravel(false);
		}
		for (Vertex e : graph.getVertexs()) {
			e.setTravel(false);
		}
	}

	public void resetSelected() {
		for (Vertex vertex : graph.getVertexs()) {
			vertex.setSelected(false);
		}
	}

	public void setTraveled(ArrayList<Integer> result) {
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < graph.getVertexs().size(); j++) {
				if (graph.getVertexs().get(j).getIndex() == result.get(i)) {
					graph.getVertexs().get(j).setTravel(true);
				}
			}

		}

		int index = setTraveledEdge(result);
		System.out.println("" + index);
		int sizeResult = 0;
		while (index < result.size()) {
			for (int i = 0; i < result.size() - 1; i++) {
				if (graph.findEdge(result.get(i), result.get(i + 1)) != null) {
					graph.findEdge(result.get(i), result.get(i + 1)).setTravel(true);
				} else {
					for (int a = i; a > -1; a--) {
						if (graph.findEdge(result.get(a), result.get(i + 1)) != null) {
							graph.findEdge(result.get(a), result.get(i + 1)).setTravel(true);
						}
					}
				}
			}
			result.remove(index);
		}

	}

	public int setTraveledEdge(ArrayList<Integer> result) {
		int index = 0;
		for (int i = 0; i < result.size() - 1; i++) {
			if (graph.findEdge(result.get(i), result.get(i + 1)) != null) {
				index = index + 1;
			}
		}
		return index;
	}

	public ArrayList<QuadCurve2D> getCurveArrayList() {
		return curveArrayList;
	}

	public void setCurveArrayList(ArrayList<QuadCurve2D> curveArrayList) {
		this.curveArrayList = curveArrayList;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<Ellipse2D> getEllipse2ds() {
		return ellipse2ds;
	}

	public void setEllipse2ds(ArrayList<Ellipse2D> ellipse2ds) {
		this.ellipse2ds = ellipse2ds;
	}

	public ArrayList<Vertex> getvList() {
		return vList;
	}

	public void setvList(ArrayList<Vertex> vList) {
		this.vList = vList;
	}

	public static Edge getSelectedEdge() {
		return selectedEdge;
	}

	public static void setSelectedEdge(Edge selectedEdge) {
		PaintPanel.selectedEdge = selectedEdge;
	}

	int i = 0;

//	public void animation() {
//		Timer timer = new Timer();
//		Task task = new Task();
//		timer.schedule(task, 1000);
//	}
//
//	private class Task extends TimerTask {
//		Vertex vertex;
//
//		public Task() {
//		}
//
//		@Override
//		public void run() {
////			for(Vertex vertex:graph.getVertexs()) {
////				if(vertex.isTravel()) {
//			Graphics2D graphics2d = (Graphics2D) getGraphics();
//			graphics2d.setColor(Color.yellow);
//			if(i<6) graphics2d.fill(graph.getVertexs().get(i).getEllipse());
//			i++;
////				}
////			}
//		}
//	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for (Vertex v : graph.getVertexs()) {
			if (v.isTravel()) {
				graphics2d.setColor(Color.YELLOW);
				graphics2d.fill(v.getEllipse());
//				animation();
			} else {
				if (v.isSelected()) {
					graphics2d.setColor(Color.gray);
					graphics2d.fill(v.getEllipse());
				} else {
					graphics2d.setColor(Color.BLACK);
					graphics2d.fill(v.getEllipse());
				}
			}
			Font font = new Font("Arial", Font.BOLD, 15);
			FontMetrics metrics = graphics.getFontMetrics(font);
			graphics.setFont(font);
			graphics.setColor(Color.white);

			int xString = (int) (v.getEllipse().getX() + (v.getEllipse().getWidth() - metrics.stringWidth(string)) / 2)
					- 4;
			int yString = (int) (v.getEllipse().getY() + (v.getEllipse().getHeight() - metrics.getHeight()) / 2) + 14;
			graphics.drawString(v.getIndex() + "", xString, yString);
			graphics2d.setColor(Color.BLACK);
		}
		for (Edge edge : graph.getEdges()) {
			if (edge.getNode1() == edge.getNode2()) {
			} else {
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics2d.setColor(Color.BLACK);
				graphics2d.setStroke(new BasicStroke(3f));
				if (edge.isTravel()) {
					graphics2d.setColor(Color.orange);
				}
				if (isDirected() == true) {
					if (edge.getLine2d() == null) {
					} else {
						double from = angleBetween(edge.getNode1(), edge.getNode2());
						double to = angleBetween(edge.getNode1(), edge.getNode2());
						Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
						Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
						graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
						ArrowHead arrowHead = new ArrowHead();
						AffineTransform affineTransform = AffineTransform.getTranslateInstance(
								pointToPoint2d.getX() - (arrowHead.getBounds().getWidth() / 2d), pointToPoint2d.getY());
						affineTransform.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
						arrowHead.transform(affineTransform);
						graphics2d.draw(arrowHead);
						Font font = new Font("Arial", Font.BOLD, 15);
						FontMetrics metrics = graphics.getFontMetrics(font);
						graphics.setFont(font);
						graphics.setColor(Color.red);
						int xString = (int) (pointFromPoint2d.getX() + ((pointToPoint2d.getX()))
								- metrics.stringWidth(string)) / 2 + 10;

						int yString = (int) (pointFromPoint2d.getY() + (pointToPoint2d.getY()) - metrics.getHeight())
								/ 2 + 3;
						graphics.drawString(edge.getValue() + "", xString, yString);
					}
				}
				if (isUndirecred() == true) {
					double from = angleBetween(edge.getNode1(), edge.getNode2());
					double to = angleBetween(edge.getNode1(), edge.getNode2());
					Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
					Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
					graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
					Font font = new Font("Arial", Font.BOLD, 15);
					FontMetrics metrics = graphics.getFontMetrics(font);
					graphics.setFont(font);
					graphics.setColor(Color.red);
					if (edge.getLine2d() == null) {
					} else {
						int xString = (int) (pointFromPoint2d.getX() + ((pointToPoint2d.getX()))
								- metrics.stringWidth(string)) / 2 + 10;

						int yString = (int) (pointFromPoint2d.getY() + (pointToPoint2d.getY()) - metrics.getHeight())
								/ 2 + 3;
						graphics.drawString(edge.getValue() + "", xString, yString);
					}

				}
			}
			graphics2d.setColor(Color.BLACK);
		}

		for (Edge edge : edges) {
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(3f));
			if (isDirected() == true) {
				double from = angleBetween(edge.getNode1(), edge.getNode2());
				double to = angleBetween(edge.getNode1(), edge.getNode2());
				Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
				Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
				for (QuadCurve2D curve2d : curveArrayList) {
					if (pointFromPoint2d.getX() > pointToPoint2d.getX()) {
						graphics2d.draw(new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
								(pointToPoint2d.getX() + pointFromPoint2d.getX()) / 2 + 5,
								(pointToPoint2d.getX() + pointFromPoint2d.getY()) / 2 + 5, pointToPoint2d.getX(),
								pointToPoint2d.getY()));
						ArrowHead arrowHead = new ArrowHead();
						AffineTransform affineTransform = AffineTransform.getTranslateInstance(
								pointToPoint2d.getX() - (arrowHead.getBounds().getWidth() / 2d), pointToPoint2d.getY());
						affineTransform.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
						arrowHead.transform(affineTransform);
						graphics2d.draw(arrowHead);
						Font font = new Font("Arial", Font.BOLD, 15);
						FontMetrics metrics = graphics.getFontMetrics(font);
						graphics.setFont(font);
						graphics.setColor(Color.red);
						int xString = (int) (pointFromPoint2d.getX() + ((pointToPoint2d.getX()))
								- metrics.stringWidth(string)) / 2 + 10;

						int yString = (int) (pointFromPoint2d.getY() + (pointToPoint2d.getY()) - metrics.getHeight())
								/ 2 + 38;
						graphics.drawString(edge.getValue() + "", xString, yString);
					} else {
						graphics.setColor(Color.black);
						graphics2d.draw(new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
								(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2 + 5,
								(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 5, pointToPoint2d.getX(),
								pointToPoint2d.getY()));
						Font font = new Font("Arial", Font.BOLD, 15);
						FontMetrics metrics = graphics.getFontMetrics(font);
						graphics.setFont(font);
						graphics.setColor(Color.red);
						int xString = (int) (pointFromPoint2d.getX() + ((pointToPoint2d.getX()))
								- metrics.stringWidth(string)) / 2 + 10;

						int yString = (int) (pointFromPoint2d.getY() + (pointToPoint2d.getY()) - metrics.getHeight())
								/ 2 + 38;
						graphics.drawString(edge.getValue() + "", xString, yString);
					}
					graphics2d.setColor(Color.black);
				}
			}
			if (isUndirecred() == true) {
				double from = angleBetween(edge.getNode1(), edge.getNode2());
				double to = angleBetween(edge.getNode1(), edge.getNode2());
				Point2D pointFromPoint2d = getPointOnCircle(edge.getNode1(), from);
				Point2D pointToPoint2d = getPointOnCircle(edge.getNode2(), to - 22);
				graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
				for (QuadCurve2D curve2d : curveArrayList) {
					graphics2d.draw(new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
							(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
							(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 5, pointToPoint2d.getX(),
							pointToPoint2d.getY()));
				}
			}
		}
		for (Vertex v : vList) {
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(1.5f));
			for (Ellipse2D ellipse2d : ellipse2ds) {
				graphics2d.draw(new Ellipse2D.Double((v.getEllipse().getX() - v.getEllipse().getWidth() * 1.0),
						(v.getEllipse().getCenterY() - v.getEllipse().getHeight() / 2 + 15), 50, 25));
				for (Edge edge : graph.getEdges()) {
					Font font = new Font("Arial", Font.BOLD, 15);
					FontMetrics metrics = graphics.getFontMetrics(font);
					graphics.setFont(font);
					graphics.setColor(Color.red);
					int xString = (int) (ellipse2d.getX() + (ellipse2d.getWidth() - metrics.stringWidth(string)) / 2)
							- 4;
					int yString = (int) (ellipse2d.getY() + (ellipse2d.getHeight() - metrics.getHeight()) / 2) + 14;
					graphics.drawString(edge.getValue() + "", xString, yString);
					graphics2d.setColor(Color.BLACK);
				}
			}
		}
	}
}
