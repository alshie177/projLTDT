package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Frame extends JFrame {

	public Frame() {
		this.init();
	}

	private void init() {

		this.setTitle("Graph theory");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(870, 500));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		PaintPanel paint = new PaintPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		paint.setBorder(blackline);
		ToolBarPanel toolBarPanel = new ToolBarPanel(paint);

		this.setLayout(new BorderLayout());
		this.add(toolBarPanel, BorderLayout.NORTH);
		this.add(paint, BorderLayout.CENTER);
		this.add(new Panel(), BorderLayout.EAST);
		this.add(new Panel(), BorderLayout.SOUTH);
		this.add(new Panel(), BorderLayout.WEST);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new Frame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}