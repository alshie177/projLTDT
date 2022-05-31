package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
			paintPanel.setTypeButtonString("addVertex");
		}
		if (inputString.equals("addEdge")) {
			paintPanel.setTypeButtonString("addEdge");
		}
		if (inputString.equals("delVertex")) {
			paintPanel.setTypeButtonString("delVertex");
		}
		if (inputString.equals("delEdge")) {
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
			String start = JOptionPane.showInputDialog("Nhập điểm bắt đầu");
			String end = JOptionPane.showInputDialog("Nhập điểm kết thúc");
			int Start,End;
			try {
				Start = Integer.parseInt(start);
				End = Integer.parseInt(end);
			}
			catch(Exception ex ){
				JOptionPane.showMessageDialog(paintPanel,"Vui lòng nhập đúng định dạng");
				return;
			}
			paintPanel.getGraph().dfs(Start, End);
			
			
		}
		if (inputString.equals("bFS")) {
			String start = JOptionPane.showInputDialog("Nhập điểm bắt đầu");
			String end = JOptionPane.showInputDialog("Nhập điểm kết thúc");
			int Start,End;
			try {
				Start = Integer.parseInt(start);
				End = Integer.parseInt(end);
			}
			catch(Exception ex ){
				JOptionPane.showMessageDialog(paintPanel,"Vui lòng nhập đúng định dạng");
				return;
			}
			paintPanel.getGraph().bfs(Start, End);
		}
	}
}
