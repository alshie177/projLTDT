package Controller;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Edge;
import model.Vertex;
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
		isFocus = false;
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
						paintPanel.getSelected1().setSelected(true);
						paintPanel.repaint();
						System.out.println("s1 A");
						isFocus = false;
						return;
					} else {
						if (paintPanel.getSelected1() != paintPanel.getSelected2()) {
							paintPanel.setSelected2(paintPanel.getGraph().getVertexs().get(i));
							paintPanel.getSelected2().setSelected(true);
							paintPanel.repaint();
							System.out.println("s2 A");
							if (paintPanel.isUndirecred() == true
									&& paintPanel.getSelected1() == paintPanel.getGraph().getVertexs().get(i)
									&& paintPanel.getSelected2() == paintPanel.getGraph().getVertexs().get(i)) {
								Ellipse2D ellipse2d = new Ellipse2D.Double(
										(paintPanel.getSelected2().getEllipse().getX()
												- paintPanel.getSelected2().getEllipse().getWidth() * 1.5),
										(paintPanel.getSelected2().getEllipse().getCenterY()
												- paintPanel.getSelected2().getEllipse().getHeight() / 2 + 5),
										30, 15);
								String valueString = (String) JOptionPane.showInputDialog(null, "Nhập giá trị cho Cạnh",
										"Vui lòng nhập giá trị cho cạnh", JOptionPane.QUESTION_MESSAGE, null, null,
										"1");
								int value;
								try {
									value = Integer.parseInt(valueString);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
									paintPanel.setSelected1(null);
									paintPanel.setSelected2(null);
									isFocus = false;
									paintPanel.setTypeButtonString("");
									paintPanel.repaint();
									return;
								}
								paintPanel.getGraph().addUnderectedEdge(paintPanel.getSelected1(),
										paintPanel.getSelected2(), null, value);
								paintPanel.getEllipse2ds().add(ellipse2d);
								paintPanel.getvList().add(paintPanel.getSelected1());
								paintPanel.resetSelected();
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								isFocus = false;
								indexFocus = 0;
								paintPanel.setTypeButtonString("");
								System.out.println("Edge is available");
								paintPanel.repaint();
								break;
							}
							if (paintPanel.isUndirecred() == true && paintPanel.getSelected1().isExistEdge() == true
									&& paintPanel.getSelected2().isExistEdge() == true
									&& (paintPanel.getGraph().getMtkArrayList()
											.get(paintPanel.getSelected1().getIndex())
											.get(paintPanel.getSelected2().getIndex()) != 0)) {
								paintPanel.getEdges()
										.add(new Edge(paintPanel.getSelected1(), paintPanel.getSelected2(), null, 1));
								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());
								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());

								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
										to - 22);
								QuadCurve2D curve2d = new QuadCurve2D.Double(pointFromPoint2d.getX(),
										pointFromPoint2d.getY(), (pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
										(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2 + 15,
										pointToPoint2d.getX(), pointToPoint2d.getY());
								paintPanel.getCurveArrayList().add(curve2d);
								paintPanel.resetSelected();
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								isFocus = false;
								paintPanel.setTypeButtonString("");
								paintPanel.repaint();
								break;
							}
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
								String valueString = (String) JOptionPane.showInputDialog(null, "Nhập giá trị cho Cạnh",
										"Vui lòng nhập giá trị cho cạnh", JOptionPane.QUESTION_MESSAGE, null, null,
										"1");
								int value;
								try {
									value = Integer.parseInt(valueString);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
									paintPanel.resetSelected();
									paintPanel.setSelected1(null);
									paintPanel.setSelected2(null);
									isFocus = false;
									paintPanel.setTypeButtonString("");
									paintPanel.repaint();
									return;
								}
								paintPanel.getGraph().addUnderectedEdge(paintPanel.getSelected1(),
										paintPanel.getSelected2(), line2d, value);
								for (Vertex vertex : paintPanel.getGraph().getVertexs()) {
									if (vertex == paintPanel.getSelected1()) {
										vertex.setExistEdge(true);
										System.out.println(vertex.isExistEdge());
									}
									if (vertex == paintPanel.getSelected2()) {
										vertex.setExistEdge(true);
										System.out.println(vertex.isExistEdge());
									}
								}
								paintPanel.resetSelected();
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
							if (paintPanel.isDirected() == true
									&& paintPanel.getSelected1() == paintPanel.getGraph().getVertexs().get(i)
									&& paintPanel.getSelected2() == paintPanel.getGraph().getVertexs().get(i)) {
								JOptionPane.showMessageDialog(paintPanel, "Giả Đồ Thị không thuộc Đồ Thị Có Hướng",
										"Error", JOptionPane.ERROR_MESSAGE);
								paintPanel.setTypeButtonString("");
								paintPanel.resetSelected();
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								break;
							}
							if (paintPanel.isDirected() == true && paintPanel.getSelected1().isExistEdge() == true
									&& paintPanel.getSelected2().isExistEdge() == true
									&& (paintPanel.getGraph().getMtkArrayList()
											.get(paintPanel.getSelected1().getIndex())
											.get(paintPanel.getSelected2().getIndex()) != 0
											|| paintPanel.getGraph().getMtkArrayList()
													.get(paintPanel.getSelected2().getIndex())
													.get(paintPanel.getSelected1().getIndex()) != 0)) {
								if (paintPanel.getGraph().getMtkArrayList().get(paintPanel.getSelected1().getIndex())
										.get(paintPanel.getSelected2().getIndex()) == 0) {
									String valueString = (String) JOptionPane.showInputDialog(null,
											"Nhập giá trị cho Cạnh", "Vui lòng nhập giá trị cho cạnh",
											JOptionPane.QUESTION_MESSAGE, null, null, "1");
									int value;
									try {
										value = Integer.parseInt(valueString);
									} catch (Exception ex) {
										JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
										paintPanel.setSelected1(null);
										paintPanel.setSelected2(null);
										isFocus = false;
										paintPanel.setTypeButtonString("");
										paintPanel.repaint();
										return;
									}
									paintPanel.getEdges().add(new Edge(paintPanel.getSelected1(),
											paintPanel.getSelected2(), null, value));
									paintPanel.getGraph().addDerectedEdge(paintPanel.getSelected1(),
											paintPanel.getSelected2(), null, value);
								}
								paintPanel.getEdges()
										.add(new Edge(paintPanel.getSelected1(), paintPanel.getSelected2(), null, 1));
								double from = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());
								double to = paintPanel.angleBetween(paintPanel.getSelected1(),
										paintPanel.getSelected2());

								Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected1(), from);
								Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getSelected2(),
										to - 22);
								QuadCurve2D curve2d = new QuadCurve2D.Double(pointFromPoint2d.getX(),
										pointFromPoint2d.getY(), (pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2,
										(pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2, pointToPoint2d.getX(),
										pointToPoint2d.getY());
								paintPanel.getCurveArrayList().add(curve2d);
								paintPanel.resetSelected();
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								isFocus = false;
								paintPanel.setTypeButtonString("");
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
								String valueString = (String) JOptionPane.showInputDialog(null, "Nhập giá trị cho Cạnh",
										"Vui lòng nhập giá trị cho cạnh", JOptionPane.QUESTION_MESSAGE, null, null,
										"1");
								int value;
								try {
									value = Integer.parseInt(valueString);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
									paintPanel.resetSelected();
									paintPanel.setSelected1(null);
									paintPanel.setSelected2(null);
									isFocus = false;
									paintPanel.setTypeButtonString("");
									paintPanel.repaint();
									return;
								}
								paintPanel.getGraph().addDerectedEdge(paintPanel.getSelected1(),
										paintPanel.getSelected2(), line2d, value);
								for (Vertex vertex : paintPanel.getGraph().getVertexs()) {
									if (vertex == paintPanel.getSelected1()) {
										vertex.setExistEdge(true);
										System.out.println(vertex.isExistEdge());
									}
									if (vertex == paintPanel.getSelected2()) {
										vertex.setExistEdge(true);
										System.out.println(vertex.isExistEdge());
									}
								}
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
								paintPanel.resetSelected();
								paintPanel.setSelected1(null);
								paintPanel.setSelected2(null);
								break;
							}
						}
					}
				}
			}
			paintPanel.resetSelected();
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
		case "dFS": {
			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().contains(e.getX(), e.getY())) {
					if (paintPanel.getSelected1() == null) {
						paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
						paintPanel.resetTraved();
						paintPanel.repaint();
						ArrayList<Integer> result = paintPanel.getGraph().dfs(paintPanel.getSelected1().getIndex());
						paintPanel.setTraveled(result);
						paintPanel.repaint();
						paintPanel.setSelected1(null);
						paintPanel.setTypeButtonString("");
						break;
					}
				}

			}
			break;
		}
		case "dijkstra": {
			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().contains(e.getX(), e.getY())) {
					if (paintPanel.getSelected1() == null) {
						paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
//						System.out.println("1 "+paintPanel.getGraph().getVertexs().get(i).getIndex());
						paintPanel.getSelected1().setSelected(true);
						paintPanel.repaint();
						break;
					} else {
						if (paintPanel.getSelected1() != paintPanel.getSelected2()) {
							paintPanel.setSelected2(paintPanel.getGraph().getVertexs().get(i));
//							System.out.println("2 "+paintPanel.getGraph().getVertexs().get(i).getIndex());
							paintPanel.getSelected2().setSelected(true);
							paintPanel.repaint();
							ArrayList<Integer> result = paintPanel.getGraph().dijkstra(paintPanel.getSelected1(),
									paintPanel.getSelected2());
							System.out.println(result.toString());
							paintPanel.setTraveled(result);
							paintPanel.repaint();
							paintPanel.getSelected1().setSelected(false);
							paintPanel.getSelected2().setSelected(false);
							paintPanel.setSelected1(null);
							paintPanel.setSelected2(null);
							paintPanel.setTypeButtonString("");

						}

					}
					break;
				}

			}
			break;
		}

		case "bFS": {
			for (int i = 0; i < paintPanel.getGraph().getVertexs().size(); i++) {
				if (paintPanel.getGraph().getVertexs().get(i).getEllipse().contains(e.getX(), e.getY())) {
					if (paintPanel.getSelected1() == null) {
						paintPanel.setSelected1(paintPanel.getGraph().getVertexs().get(i));
						paintPanel.resetTraved();
						paintPanel.repaint();
						ArrayList<Integer> result = paintPanel.getGraph().bfs(paintPanel.getSelected1().getIndex());
						paintPanel.setTraveled(result);
						paintPanel.repaint();
						paintPanel.setSelected1(null);
						paintPanel.setTypeButtonString("");
						break;
					}
				}

			}
			break;
		}
		case "delEdge": {
			for (int i = 0; i < paintPanel.getGraph().getEdges().size(); i++) {
				if (paintPanel.getGraph().getEdges().get(i).getLine2d() == null) {
				} else {
					if (paintPanel.getGraph().getEdges().get(i).getLine2d().intersects(e.getX(), e.getY(), 30, 30)) {
						paintPanel.setSelectedEdge(paintPanel.getGraph().getEdges().get(i));
						if (paintPanel.isDirected()) {
							paintPanel.getGraph().delDirectedEdge(paintPanel.getSelectedEdge());
							paintPanel.repaint();
							paintPanel.setSelectedEdge(null);
							paintPanel.setTypeButtonString("");
							break;
						}
						if (paintPanel.isUndirecred()) {
							paintPanel.getGraph().delUndirectedEdge(paintPanel.getSelectedEdge());
							paintPanel.repaint();
							paintPanel.setSelectedEdge(null);
							paintPanel.setTypeButtonString("");
							break;
						}
					}
				}
			}
			for (int i = 0; i < paintPanel.getEdges().size(); i++) {
				for (int j = 0; j < paintPanel.getCurveArrayList().size(); j++) {
					if (paintPanel.getCurveArrayList().get(j).getBounds().intersects(e.getX(), e.getY(), 30, 30)) {
						if (paintPanel.isDirected()) {
							if (paintPanel.getGraph().getMtkArrayList()
									.get(paintPanel.getEdges().get(i).getNode1().getIndex())
									.get(paintPanel.getEdges().get(i).getNode2().getIndex()) != 0) {
								paintPanel.getCurveArrayList().remove(paintPanel.getCurveArrayList().get(j));
								paintPanel.getEdges().remove(paintPanel.getEdges().get(i));
								paintPanel.getGraph().delDirectedEdge(paintPanel.getEdges().get(i));
								paintPanel.getEdges().get(i).getNode1().setExistEdge(false);
								paintPanel.getEdges().get(i).getNode2().setExistEdge(false);
								paintPanel.repaint();
								paintPanel.setTypeButtonString("");
								break;
							} else {
								paintPanel.getCurveArrayList().remove(paintPanel.getCurveArrayList().get(j));
								paintPanel.getEdges().remove(paintPanel.getEdges().get(i));
								paintPanel.getEdges().get(i).getNode1().setExistEdge(false);
								paintPanel.getEdges().get(i).getNode2().setExistEdge(false);
								paintPanel.repaint();
								paintPanel.setTypeButtonString("");
								break;
							}
						}
						if (paintPanel.isUndirecred()) {
							paintPanel.getCurveArrayList().remove(paintPanel.getCurveArrayList().get(j));
							paintPanel.getEdges().remove(paintPanel.getEdges().get(i));
							for (Edge edge : paintPanel.getEdges()) {
								if (edge == paintPanel.getEdges().get(i)) {
									edge.getNode1().setExistEdge(false);
									edge.getNode2().setExistEdge(false);
								}
							}
							paintPanel.getEdges().get(i).getNode1().setExistEdge(false);
							paintPanel.getEdges().get(i).getNode2().setExistEdge(false);
							paintPanel.repaint();
							paintPanel.setTypeButtonString("");
							break;
						}
					}
				}
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
			for (Vertex v : paintPanel.getGraph().getVertexs()) {
				if (v.getIndex() == vertexFocus.getIndex()) {
					Ellipse2D ell = new Ellipse2D.Double(e.getX() - 25, e.getY() - 25, 50, 50);
					v.setEllipse(ell);
					paintPanel.repaint();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
