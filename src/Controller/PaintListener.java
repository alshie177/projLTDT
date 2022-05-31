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
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Edge;
import model.Graph;
import model.Vertex;
import view.ArrowHead;
import view.PaintPanel;

public class PaintListener implements MouseListener, MouseMotionListener {
	private PaintPanel paintPanel;
	private Font font;
	boolean isFocus = false;
	int indexFocus = 0;
	Vertex vertexFocus;
	Ellipse2D ellipse;

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
			ellipse = new Ellipse2D.Double(e.getX(), e.getY(), 50, 50);
			paintPanel.getGraph().addVertex(ellipse);
//			System.out.println("2");
			paintPanel.setTypeButtonString("");
			paintPanel.repaint();
//			paintPanel.getGraph().showVertex();
			isFocus = false;
			break;
		}
		case "addEdge": {
			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().contains(e.getX(), e.getY())) {
					System.out.println("Started");
					if (paintPanel.getSelected1() == null) {
						paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
						System.out.println("s1 A");
						isFocus = false;
						return;
					} else {
						if (paintPanel.getSelected1() != paintPanel.getSelected2()) {
							paintPanel.setSelected2(paintPanel.getGraph().getVertexs().get(i));
							System.out.println("s2 A");
							if (paintPanel.isUndirecred() == true) {
								System.out.println("add Edge");
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
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								isFocus = false;
								indexFocus = 0;
								paintPanel.setTypeButtonString("");
								System.out.println("Edge is available");
//								paintPanel.getGraph().showEdge();
								paintPanel.repaint();
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
								isFocus = false;
								indexFocus = 0;
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								paintPanel.setTypeButtonString("");
								System.out.println("Edge is available");
								paintPanel.repaint();
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
		case "delVertex": {
			try {
				if (isFocus && paintPanel.getGraph().getVertexs().size() > 0) {
					paintPanel.delVertex(vertexFocus);
					paintPanel.repaint();
					isFocus = false;
					indexFocus = 0;
					paintPanel.setTypeButtonString("");
				}
			} catch (Exception ex) {
				throw ex;
			}

			break;
		}
		case "":
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
			if (paintPanel.getGraph().getVertexs().get(i).getEllipse().contains(e.getX(), e.getY())) {
				isFocus = true;
				indexFocus = paintPanel.getGraph().getVertexs().get(i).getIndex();
				vertexFocus = paintPanel.getGraph().getVertexs().get(i);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isFocus) {
			Vertex v = paintPanel.getGraph().getVertexs().get(vertexFocus.getIndex());
			Ellipse2D ell = new Ellipse2D.Double(e.getX(), e.getY(), 50, 50);
			v.setEllipse(ell);
			paintPanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
