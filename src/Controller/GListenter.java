package Controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Edge;
import model.Vertex;
import view.PaintPanel;
import view.ToolBarPanel;

public class GListenter implements ActionListener {
	ToolBarPanel toolBarPanel;
	PaintPanel paintPanel;

	public GListenter(ToolBarPanel toolBarPanel, PaintPanel paintPanel) {
		this.toolBarPanel = toolBarPanel;
		this.paintPanel = paintPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String inputString = e.getActionCommand();
		if (inputString.equals("addVertex")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("addVertex");
		}
		if (inputString.equals("addEdge")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("addEdge");
		}
		if (inputString.equals("delVertex")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("delVertex");
		}
		if (inputString.equals("delEdge")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("delEdge");
		}
		if (inputString.equals("directed")) {
			paintPanel.setDirected(true);
		}
		if (inputString.equals("undirected")) {
			paintPanel.setUndirecred(true);
		}
		if (paintPanel.isUndirecred() != paintPanel.isDirected()) {
			toolBarPanel.setEnable();
		}
		if (inputString.equals("dFS")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("dFS");
		}
		if (inputString.equals("bFS")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("bFS");
		}
		if (inputString.equals("dijkstra")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			paintPanel.setTypeButtonString("dijkstra");
		}
		if (inputString.equals("new")) {
			paintPanel.getGraph().newFile();
			paintPanel.getEdges().removeAll(paintPanel.getEdges());
			paintPanel.getCurveArrayList().removeAll(paintPanel.getCurveArrayList());
			paintPanel.resetTraved();
			paintPanel.repaint();
		}
		if (inputString.equals("save")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);
			fc.setFileFilter(filter);
			int returnVal = fc.showSaveDialog(this.paintPanel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.out.println(file.toString());
				String string= file.getAbsolutePath();
				if(string.endsWith(".txt")) {
					save(file.getAbsolutePath());
				}else {
					save(file.getAbsolutePath() + ".txt");
				}
			}
		}
		if (inputString.equals("open")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this.paintPanel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				ArrayList<String> dataArrayList = new ArrayList<String>();
				try {
//					
					dataArrayList = (ArrayList<String>) Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
					System.out.println(dataArrayList.toString());
					for (int i = 0; i < dataArrayList.size() - 1; i++) {
						Random random = new Random();
						Dimension dimension = paintPanel.getMaximumSize();
						int x = random.nextInt(1700);
						int y = random.nextInt(850);
						Ellipse2D ellipse2d = new Ellipse2D.Double(x, y, 50, 50);
						paintPanel.getGraph().getVertexs().add(new Vertex(i, new ArrayList<>(), ellipse2d, false));
						paintPanel.repaint();
					}
					for (String string : dataArrayList) {
						if (string.contains("Directed")) {
							paintPanel.setDirected(true);
							toolBarPanel.getDirectedButton().setSelected(true);
							toolBarPanel.setEnable();
							getNum(dataArrayList);
							addDirectedEdge(paintPanel.getGraph().getMtkArrayList());
							paintPanel.repaint();
						}
						if (string.contains("Undirected")) {
							paintPanel.setUndirecred(true);
							toolBarPanel.getUndirectedButton().setSelected(true);
							toolBarPanel.setEnable();
							getNum(dataArrayList);
							addUndirectedEdge(paintPanel.getGraph().getMtkArrayList());
							paintPanel.repaint();
						}
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public void save(String nameString) {
		try {
			PrintWriter pWriter = new PrintWriter(nameString, "UTF-8");
			String dataString;

			for (int i = 0; i < paintPanel.getGraph().getMtkArrayList().size(); i++) {
				for (int j = 0; j < paintPanel.getGraph().getMtkArrayList().get(i).size(); j++) {
					if (j == paintPanel.getGraph().getMtkArrayList().get(i).size() - 1) {
						dataString = paintPanel.getGraph().getMtkArrayList().get(i).get(j) + "";
						pWriter.print(dataString);
					} else {
						dataString = paintPanel.getGraph().getMtkArrayList().get(i).get(j) + " ";
						pWriter.print(dataString);
					}
				}

				pWriter.println();
			}

			if (paintPanel.isDirected() == true) {
				pWriter.print("Directed");
			} else {
				pWriter.print("Undirected");
			}

			pWriter.flush();
			pWriter.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	public void getNum(ArrayList<String> strings) {
		ArrayList<Integer> lineArrayList = new ArrayList<Integer>();
		String[] lineStrings = new String[strings.size() - 1];
		for (int i = 0; i < strings.size() - 1; i++) {
			int n = 0;
			lineStrings = strings.get(i).split(" ");
			for (int j = 0; j < lineStrings.length; j++) {
				n = Integer.parseInt(lineStrings[j]);
				lineArrayList.add(n);
			}
		}
		for (int k = 0; k < strings.size() - 1; k++) {
			ArrayList<Integer> a1 = new ArrayList<>();
			for (int m = 0; m < strings.size() - 1; m++) {
				a1.add(lineArrayList.get(k * (strings.size() - 1) + m));
			}
			paintPanel.getGraph().getMtkArrayList().add(a1);
			paintPanel.getGraph().getMtkData().add(a1);
		}
		paintPanel.getGraph().showMtk(paintPanel.getGraph().getMtkArrayList());
	}

	public void addUndirectedEdge(ArrayList<ArrayList<Integer>> mtkArrayList) {
		for (int i = 0; i < mtkArrayList.size(); i++) {
			for (int j = 0; j < mtkArrayList.size(); j++) {
				if (mtkArrayList.get(i).get(j) != 0 && mtkArrayList.get(j).get(i) != 0) {
					double from = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j));
					double to = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j));
					Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getGraph().getVertexs().get(i),
							from);
					Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getGraph().getVertexs().get(j),
							to - 22);
					Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);

					Edge edge = new Edge(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j), line2d, mtkArrayList.get(i).get(j));
					paintPanel.getGraph().getEdges().add(edge);
				}
			}
		}
	}

	public void addDirectedEdge(ArrayList<ArrayList<Integer>> mtkArrayList) {
		for (int i = 0; i < mtkArrayList.size(); i++) {
			for (int j = 0; j < mtkArrayList.size(); j++) {
				if (mtkArrayList.get(i).get(j) != 0 && mtkArrayList.get(j).get(i) != 0) {
					double from = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j));
					double to = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j));
					Point2D pointFromPoint2d = paintPanel.getPointOnCircle(paintPanel.getGraph().getVertexs().get(i),
							from);
					Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getGraph().getVertexs().get(j),
							to - 22);
					Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);

					Edge edge2 = new Edge(paintPanel.getGraph().getVertexs().get(i),
							paintPanel.getGraph().getVertexs().get(j), line2d, mtkArrayList.get(i).get(j));
					paintPanel.getGraph().getEdges().add(edge2);
					Edge edge = new Edge(paintPanel.getGraph().getVertexs().get(j),
							paintPanel.getGraph().getVertexs().get(i), null, mtkArrayList.get(j).get(i));
					paintPanel.getEdges().add(edge);
					QuadCurve2D curve2d = new QuadCurve2D.Double(pointFromPoint2d.getX(), pointFromPoint2d.getY(),
							(pointToPoint2d.getX() + pointFromPoint2d.getX()) / 2 + 5,
							(pointToPoint2d.getX() + pointFromPoint2d.getY()) / 2 + 5, pointToPoint2d.getX(),
							pointToPoint2d.getY());
					paintPanel.getCurveArrayList().add(curve2d);

				} else {
					if (mtkArrayList.get(i).get(j) != 0) {
						double from = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
								paintPanel.getGraph().getVertexs().get(j));
						double to = paintPanel.angleBetween(paintPanel.getGraph().getVertexs().get(i),
								paintPanel.getGraph().getVertexs().get(j));
						Point2D pointFromPoint2d = paintPanel
								.getPointOnCircle(paintPanel.getGraph().getVertexs().get(i), from);
						Point2D pointToPoint2d = paintPanel.getPointOnCircle(paintPanel.getGraph().getVertexs().get(j),
								to - 22);
						Line2D line2d = new Line2D.Double(pointFromPoint2d, pointToPoint2d);

						Edge edge = new Edge(paintPanel.getGraph().getVertexs().get(i),
								paintPanel.getGraph().getVertexs().get(j), line2d, mtkArrayList.get(i).get(j));
						paintPanel.getGraph().getEdges().add(edge);
					}
				}

			}
		}
	}
}
