package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
		if(paintPanel.isUndirecred()!= paintPanel.isDirected()) {
			toolBarPanel.setEnable();
		}
	}
}
