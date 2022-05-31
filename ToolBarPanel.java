package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import Controller.GListenter;

public class ToolBarPanel extends JPanel {
	private PaintPanel paint;
	private JButton openButton, saveButton, buttonNew;
	private JButton addVertexButton, addEdgeButton, delVertexButton, delEdgeButton, moveButton;
	private JButton dFSButton, bFSButton, dijstraButton;
	private JToolBar toolBar;
	private JRadioButton directedButton;
	private JRadioButton undirectedButton;
	private ButtonGroup gr = new ButtonGroup();

	public ToolBarPanel(PaintPanel paint) {
		this.paint = paint;
		this.init();
	}

	private void init() {

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		ActionListener actionListener = new GListenter(this, paint);

		// File ToolBar

		openButton = new JButton();
		openButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("add-file.png"))));

		saveButton = new JButton();
		saveButton.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("Save-icon.png"))));

		buttonNew = new JButton();
		buttonNew.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("New-file-icon.png"))));

		JPanel filePanel = new JPanel();
		JToolBar fileToolBar = new JToolBar();
		filePanel.add(buttonNew);
		filePanel.add(openButton);
		filePanel.add(saveButton);
		fileToolBar.add(filePanel);

		// Graph ToolBar

		addVertexButton = new JButton();
		addVertexButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("plus.png"))));
		addVertexButton.addActionListener(actionListener);
		addVertexButton.setActionCommand("addVertex");

		addEdgeButton = new JButton();
		addEdgeButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("row.png"))));
		addEdgeButton.addActionListener(actionListener);
		addEdgeButton.setActionCommand("addEdge");

		delVertexButton = new JButton();
		delVertexButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("delnode.png"))));
		delVertexButton.addActionListener(actionListener);
		delVertexButton.setActionCommand("delVertex");

		delEdgeButton = new JButton();
		delEdgeButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("delrow.png"))));
		delEdgeButton.addActionListener(actionListener);
		delEdgeButton.setActionCommand("delEdge");

		moveButton = new JButton();
		moveButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("move.png"))));

		JPanel graphPanel = new JPanel();
		JToolBar graphToolBar = new JToolBar();
		graphPanel.add(addVertexButton);
		graphPanel.add(addEdgeButton);
		graphPanel.add(delVertexButton);
		graphPanel.add(delEdgeButton);
		graphPanel.add(moveButton);
		graphToolBar.add(graphPanel);

		// Search ToolBar
		dFSButton = new JButton();
		dFSButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("DFS.png"))));

		bFSButton = new JButton();
		bFSButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("BFS.png"))));

		dijstraButton = new JButton();
		dijstraButton.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(ToolBarPanel.class.getResource("dijkstra.png"))));

		JPanel searchPanel = new JPanel();
		JToolBar searchToolBar = new JToolBar();
		searchPanel.add(dFSButton);
		searchPanel.add(bFSButton);
		searchPanel.add(dijstraButton);
		searchToolBar.add(searchPanel);

		toolBar = new JToolBar();

		undirectedButton = new JRadioButton("Undirected");
		undirectedButton.setForeground(Color.BLACK);
//		undirectedButton.setSelected(true);
		undirectedButton.setFont(new Font("Rockwell", Font.BOLD, 23));
		undirectedButton.setFocusable(false);
		undirectedButton.addActionListener(actionListener);
		undirectedButton.setActionCommand("Directed");
		toolBar.add(undirectedButton);

		directedButton = new JRadioButton("Directed");
		directedButton.setForeground(Color.BLACK);
//		directedButton.setSelected(true);
		directedButton.setFont(new Font("Rockwell", Font.BOLD, 23));
		directedButton.setFocusable(false);
		directedButton.addActionListener(actionListener);
		directedButton.setActionCommand("Directed");
		toolBar.add(directedButton);

		openButton.setFocusPainted(false);
		saveButton.setFocusPainted(false);
		buttonNew.setFocusPainted(false);
		addEdgeButton.setFocusPainted(false);
		addVertexButton.setFocusPainted(false);
		delEdgeButton.setFocusPainted(false);
		delVertexButton.setFocusPainted(false);
		moveButton.setFocusPainted(false);
		dFSButton.setFocusPainted(false);
		bFSButton.setFocusPainted(false);
		dijstraButton.setFocusPainted(false);

		gr.add(undirectedButton);
		gr.add(directedButton);

		this.add(fileToolBar);
		this.add(graphToolBar);
		this.add(searchToolBar);
		this.add(toolBar);

		openButton.setActionCommand("open");
		saveButton.setActionCommand("save");
		buttonNew.setActionCommand("buttonNew");
		moveButton.setActionCommand("move");
		dFSButton.setActionCommand("dFS");
		bFSButton.setActionCommand("bFS");
		dijstraButton.setActionCommand("dijstra");
		directedButton.setActionCommand("directed");
		undirectedButton.setActionCommand("undirected");

	}

	public void setEnable() {
		java.util.Enumeration<javax.swing.AbstractButton> enumeration = gr.getElements();
		while (enumeration.hasMoreElements()) {
			enumeration.nextElement().setEnabled(false);
		}
	}
}
