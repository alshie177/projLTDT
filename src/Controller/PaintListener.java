package Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Edge;
import model.Graph;
import model.Vertex;
import view.ArrowHead;
import view.PaintPanel;

public class PaintListener implements MouseListener {
	private PaintPanel paintPanel;
	private Font font;

	public PaintListener(PaintPanel paintPanel) {
		super();
		this.paintPanel = paintPanel;
		this.font = new Font("Arial", font.BOLD, 15);
		paintPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent event) {
				Component srComponent = (Component) event.getSource();
				srComponent.requestFocus();
			}
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (paintPanel.getTypeButtonString()) {
		case "addVertex": {
			Ellipse2D ellipse = new Ellipse2D.Double(e.getX(), e.getY(), 50, 50);
			paintPanel.getGraph().addVertex(ellipse);
			System.out.println("2");
			paintPanel.setTypeButtonString("");
			paintPanel.repaint();
			break;
		}
		case "addEdge": {
			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().intersects(e.getX(), e.getY(), 50, 50)) {
					System.out.println("Started");
					if (paintPanel.getSelected1() == null) {
						paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
						System.out.println("s1 A");
						return;
					} else {
						if (paintPanel.getSelected1() != paintPanel.getSelected2()) {
							paintPanel.setSelected2(paintPanel.getGraph().getVertexs().get(i));
							System.out.println("s2 A");
							if (paintPanel.isUndirecred() == true) {
								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());
								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());

								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
										to - 22);
								Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);
								paintPanel.getGraph().addUnderectedEdge(paintPanel.getSelected1(),
										paintPanel.getSelected2(), line2d);
								for (Edge edge : paintPanel.getGraph().getEdges()) {
									if (edge.getNode1() == paintPanel.getSelected1()
											&& paintPanel.isExistEdge() == true) {
										if (edge.getNode2() == paintPanel.getSelected2()) {
											QuadCurve2D curve2d = new QuadCurve2D.Double(pointFromPoint2d.getX(),
													pointFromPoint2d.getY(),
													(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
													(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 50,
													pointToPoint2d.getX(), pointToPoint2d.getY());
											paintPanel.getCurveArrayList().add(curve2d);
										}
									}
								}
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								paintPanel.setTypeButtonString("");
								System.out.println("Edge is available");
								paintPanel.repaint();
								paintPanel.setExistEdge(true);
								break;
							}
							if (paintPanel.isDirected() == true) {
								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());
								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());

								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
										to - 22);
								Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);
								paintPanel.getGraph().addDerectedEdge(paintPanel.getSelected1(),
										paintPanel.getSelected2(), line2d);
								for (Edge edge : paintPanel.getGraph().getEdges()) {
									if (edge.getNode1() == paintPanel.getSelected1()
											&& paintPanel.isExistEdge() == true) {
										if (edge.getNode2() == paintPanel.getSelected2()) {
											QuadCurve2D curve2d = new QuadCurve2D.Double(pointFromPoint2d.getX(),
													pointFromPoint2d.getY(),
													(pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
													(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 50,
													pointToPoint2d.getX(), pointToPoint2d.getY());
											paintPanel.getCurveArrayList().add(curve2d);
										}
									}
								}
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								paintPanel.setTypeButtonString("");
								System.out.println("Edge is available");
								paintPanel.repaint();
								paintPanel.setExistEdge(true);
								break;
							}
							if (paintPanel.isDirected() == false && paintPanel.isUndirecred() == false
									&& paintPanel.getSelected1() != null && paintPanel.getSelected2() != null) {
								JOptionPane.showMessageDialog(paintPanel, "Type of Edge is not Check!!!", "Error",
										JOptionPane.ERROR_MESSAGE);
								System.out.println("Type of Edge is not Check!!!");
								paintPanel.setTypeButtonString("");
								paintPanel.setSelected1(null);
								break;
							}
						}
					}
				}
			}
			break;
		}
//		case "delVertex": {
//			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
//				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().intersects(e.getX(), e.getY(), 20, 20)) {
//					paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
//					paintPanel.getGraph().getVertexs().remove(paintPanel.getSelected1());
//					paintPanel.repaint();
//				}
//				paintPanel.setSelected1(null);
//				paintPanel.setTypeButtonString("");
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
