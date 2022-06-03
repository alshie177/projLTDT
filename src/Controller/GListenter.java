package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.Frame;
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
			String start = JOptionPane.showInputDialog("Nhập điểm bắt đầu");
			int Start;
			try {
				Start = Integer.parseInt(start);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
				return;
			}

			ArrayList<Integer> result = paintPanel.getGraph().dfs(Start);
			paintPanel.setTraveled(result);
			paintPanel.repaint();
		}
		if (inputString.equals("bFS")) {
			paintPanel.resetTraved();
			paintPanel.repaint();
			String start = JOptionPane.showInputDialog("Nhập điểm bắt đầu");
			int Start;
			try {
				Start = Integer.parseInt(start);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(paintPanel, "Vui lòng nhập đúng định dạng");
				return;
			}
			ArrayList<Integer> result = paintPanel.getGraph().bfs(Start);
			paintPanel.setTraveled(result);
			paintPanel.repaint();
		}
	}
}
