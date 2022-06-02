package Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Edge;
import model.Ellipse;
import model.Graph;
import model.Vertex;
import view.ArrowHead;
import view.PaintPanel;

public class PaintListener implements MouseListener {
	private Graph graph;
	private PaintPanel paintPanel;
	private int index = 1;
	private Font font;

	public PaintListener(Graph graph, PaintPanel paintPanel) {
		super();
		this.graph = graph;
		this.paintPanel = paintPanel;
		this.font = new Font("Arial", font.BOLD, 15);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (paintPanel.getTypeButtonString()) {
		case "addVertex": {
			Ellipse ellipse = new Ellipse(e.getX(), e.getY());
			paintPanel.getShapes().add(ellipse);
			paintPanel.repaint();
			paintPanel.setTypeButtonString("");
			System.out.println("2");
			break;
		}
//		case "addEdge": {
//			for (int i = 0; i < graph.getVertexs().size(); i++) {
//				if (graph.getVertexs().get(i).getEllipse2d().contains(e.getX(), e.getY())) {
//					System.out.println("started");
//					if (paintPanel.getSelected1() == null) {
//						paintPanel.setSelected1(graph.getVertexs().get(i));
//						System.out.println("selected1 available");
//						return;
//					} else {
//						if (paintPanel.getSelected1() != paintPanel.getSelected2()) {
//							paintPanel.setSelected2(graph.getVertexs().get(i));
//							System.out.println("selected2 available");
//							if (paintPanel.isUndirecred() == true) {
//								graph.addUnderectedEdge(paintPanel.getSelected1(), paintPanel.getSelected2());
//								Graphics graphics = paintPanel.getGraphics();
//								Graphics2D graphics2d = (Graphics2D) graphics;
//								graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//										RenderingHints.VALUE_ANTIALIAS_ON);
//								graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//										RenderingHints.VALUE_RENDER_QUALITY);
//								graphics2d.setStroke(new BasicStroke(7f));
////								graphics2d.draw(new Line2D.Double(paintPanel.getSelected1().getEllipse2d().getCenterX(),
////										paintPanel.getSelected1().getEllipse2d().getCenterY(),
////										paintPanel.getSelected2().getEllipse2d().getCenterX(),
////										paintPanel.getSelected2().getEllipse2d().getCenterY()));
//
//								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
//										paintPanel.getSelected2());
//								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
//										paintPanel.getSelected2());
//
//								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
//								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
//										to - 22);
//
//								graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
//								graphics2d.dispose();
//								paintPanel.setSelected1(null);
//								paintPanel.setSelected2(null);
//								paintPanel.setTypeButtonString("");
//								System.out.println("Edge is available");
//								break;
//							}
//							if (paintPanel.isDirected() == true) {
//								graph.addDerectedEdge(paintPanel.getSelected1(), paintPanel.getSelected2());
//								Graphics graphics = paintPanel.getGraphics();
//								Graphics2D graphics2d = (Graphics2D) graphics;
//								graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//										RenderingHints.VALUE_ANTIALIAS_ON);
//								graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//										RenderingHints.VALUE_RENDER_QUALITY);
//								graphics2d.setStroke(new BasicStroke(7f));
////								graphics2d.draw(new Line2D.Double(paintPanel.getSelected1().getEllipse2d().getCenterX(),
////										paintPanel.getSelected1().getEllipse2d().getCenterY(),
////										paintPanel.getSelected2().getEllipse2d().getCenterX(),
////										paintPanel.getSelected2().getEllipse2d().getCenterY()));
//
//								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
//										paintPanel.getSelected2());
//								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
//										paintPanel.getSelected2());
//
//								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
//								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
//										to - 22);
//
//								graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
//								graphics2d.setStroke(new BasicStroke(4f));
//								ArrowHead arrowHead = new ArrowHead();
//								AffineTransform affineTransform = AffineTransform.getTranslateInstance(
//										pointToPoint2d.getX() - (arrowHead.getBounds().getWidth() / 2d),
//										pointToPoint2d.getY());
//								affineTransform.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
//								arrowHead.transform(affineTransform);
//								graphics2d.draw(arrowHead);
//								graphics2d.dispose();
//								paintPanel.setSelected1(null);
//								paintPanel.setSelected2(null);
//								paintPanel.setTypeButtonString("");
//								System.out.println("Edge is available");
//								break;
//							}
//
//							if (paintPanel.isDirected() == false && paintPanel.isUndirecred() == false
//									&& paintPanel.getSelected1() != null && paintPanel.getSelected2() != null) {
//								JOptionPane.showMessageDialog(paintPanel, "Type of Edge is not Check!!!", "Error",
//										JOptionPane.ERROR_MESSAGE);
//								System.out.println("Type of Edge is not Check!!!");
//								paintPanel.setTypeButtonString("");
//								paintPanel.setSelected1(null);
//								break;
//							}
//						}
//					}
//				}
//			}
//			break;
//		}
//		case "delVertex": {
//			for (int i = 0; i < graph.getVertexs().size(); i++) {
//				if (graph.getVertexs().get(i).getEllipse2d().contains(e.getX(), e.getY())) {
//					System.out.println("del");
//					paintPanel.setSelected1(graph.getVertexs().get(i));
//					Graphics graphics1 = paintPanel.getGraphics();
//					Graphics2D graphics2d = (Graphics2D) graphics1;
//					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//					graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//					graphics2d.setColor(Color.WHITE);
//					Ellipse2D ellipse2d = paintPanel.getSelected1().getEllipse2d();
//					ellipse2d.setFrame(paintPanel.getSelected1().getEllipse2d().getX() - 5,
//							paintPanel.getSelected1().getEllipse2d().getY() - 2, 60, 60);
//					graphics2d.fill(ellipse2d);
//					if (paintPanel.isUndirecred() == true) {
//						for (Vertex vertex : paintPanel.getSelected1().getDsKe()) {
//							graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//									RenderingHints.VALUE_ANTIALIAS_ON);
//							graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//									RenderingHints.VALUE_RENDER_QUALITY);
//							graphics2d.setStroke(new BasicStroke(15f));
//							graphics2d.setColor(Color.WHITE);
//							double from = paintPanel.angleBetween(paintPanel.getSelected1(), vertex);
//							double to = paintPanel.angleBetween(paintPanel.getSelected1(), vertex);
//
//							Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
//							Point2D pointToPoint2d = paintPanel.getPointOnCircle(vertex, to - 22);
//
//							graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
//							graphics2d.setColor(Color.BLACK);
//							graphics2d.fill(vertex.getEllipse2d());
//							FontMetrics metrics = graphics1.getFontMetrics(font);
//							graphics1.setFont(font);
//							graphics1.setColor(Color.white);
//							String string = vertex.getNameInteger() + 1 + "";
//							int x = (int) (vertex.getEllipse2d().getX()
//									+ (vertex.getEllipse2d().getWidth() - metrics.stringWidth(string)) / 2);
//							int y = (int) (vertex.getEllipse2d().getY()
//									+ (vertex.getEllipse2d().getHeight() - metrics.getHeight()) / 2) + 14;
//							graphics1.drawString(string, x, y);
//							graph.delVertex(paintPanel.getSelected1(), paintPanel.getSelected1().getEllipse2d());
//						}
//						paintPanel.setSelected1(null);
//						break;
//					}
//					if (paintPanel.isDirected() == true) {
//						for (Vertex vertex : paintPanel.getSelected1().getDsKe()) {
//							graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//									RenderingHints.VALUE_ANTIALIAS_ON);
//							graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//									RenderingHints.VALUE_RENDER_QUALITY);
//
//							double from = paintPanel.angleBetween(paintPanel.getSelected1(), vertex);
//							double to = paintPanel.angleBetween(paintPanel.getSelected1(), vertex);
//							Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
//							Point2D pointToPoint2d = paintPanel.getPointOnCircle(vertex, to - 22);
//							graphics2d.draw(new Line2D.Double(pointFromPoint2d, pointToPoint2d));
//
//							graphics2d.setStroke(new BasicStroke(10f));
//							ArrowHead arrowHead = new ArrowHead();
//							AffineTransform affineTransform = AffineTransform.getTranslateInstance(
//									pointToPoint2d.getX() - (arrowHead.getBounds().getWidth() / 2d),
//									pointToPoint2d.getY());
//							affineTransform.rotate(from, arrowHead.getBounds2D().getCenterX(), 0);
//							arrowHead.transform(affineTransform);
//							graphics2d.draw(arrowHead);
//
//							graphics2d.setColor(Color.BLACK);
//							graphics2d.fill(vertex.getEllipse2d());
//							FontMetrics metrics = graphics1.getFontMetrics(font);
//							graphics1.setFont(font);
//							graphics1.setColor(Color.white);
//							String string = vertex.getNameInteger() + 1 + "";
//							int x = (int) (vertex.getEllipse2d().getX()
//									+ (vertex.getEllipse2d().getWidth() - metrics.stringWidth(string)) / 2);
//							int y = (int) (vertex.getEllipse2d().getY()
//									+ (vertex.getEllipse2d().getHeight() - metrics.getHeight()) / 2) + 14;
//							graphics1.drawString(string, x, y);
//							graph.delVertex(paintPanel.getSelected1(), paintPanel.getSelected1().getEllipse2d());
//						}
//					}
//					paintPanel.setSelected1(null);
//					break;
//				}
//				paintPanel.setTypeButtonString("");
//			}
//			break;
//		}
//		case "delEdge": {
//			for (int i = 0; i < graph.getEdges().size(); i++) {
//				if (graph.getEdges().get(i).getLine2d().intersects(e.getX(), e.getY(), 5, 5)) {
//					paintPanel.setSelectEdge(graph.getEdges().get(i));
//					System.out.println("delEdge");
//					paintPanel.setSelected1(paintPanel.getSelectEdge().getNode1());
//					paintPanel.setSelected2(paintPanel.getSelectEdge().getNode2());
//
//					double from = paintPanel.angleBetween(paintPanel.getSelected1(), paintPanel.getSelected2());
//					double to = paintPanel.angleBetween(paintPanel.getSelected1(), paintPanel.getSelected2());
//
//					Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
//					Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(), to - 22);
//					Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);
//					Graphics graphics = paintPanel.getGraphics();
//					Graphics2D graphics2d = (Graphics2D) graphics;
//					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//					graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//					graphics2d.setColor(Color.white);
//					graphics2d.setStroke(new BasicStroke(15f));
//					graphics2d.draw(line2d);
//					graphics2d.setColor(Color.BLACK);
//					graphics2d.fill(paintPanel.getSelected1().getEllipse2d());
//					graphics2d.fill(paintPanel.getSelected2().getEllipse2d());
//					FontMetrics metrics = graphics.getFontMetrics(font);
//					graphics.setFont(font);
//					graphics.setColor(Color.white);
//					String string1 = paintPanel.getSelected1().getNameInteger() + 1 + "";
//					String string2 = paintPanel.getSelected2().getNameInteger() + 1 + "";
//					int x1 = (int) (paintPanel.getSelected1().getEllipse2d().getX()
//							+ (paintPanel.getSelected1().getEllipse2d().getWidth() - metrics.stringWidth(string1)) / 2);
//					int y1 = (int) (paintPanel.getSelected1().getEllipse2d().getY()
//							+ (paintPanel.getSelected1().getEllipse2d().getHeight() - metrics.getHeight()) / 2) + 14;
//					graphics.drawString(string1, x1, y1);
//					int x2 = (int) (paintPanel.getSelected2().getEllipse2d().getX()
//							+ (paintPanel.getSelected2().getEllipse2d().getWidth() - metrics.stringWidth(string1)) / 2);
//					int y2 = (int) (paintPanel.getSelected2().getEllipse2d().getY()
//							+ (paintPanel.getSelected2().getEllipse2d().getHeight() - metrics.getHeight()) / 2) + 14;
//					graphics.drawString(string2, x2, y2);
//					graph.delDirectedsEdge(new Edge(paintPanel.getSelected1(), paintPanel.getSelected2()));
//					paintPanel.setTypeButtonString("");
//					break;
//				}
//			}
//			break;
//		}
		case "":
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
