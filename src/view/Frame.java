package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.w3c.dom.css.RGBColor;

public class Frame extends JFrame {
	
	public Frame() {
		this.init();
	}

	private void init() {
		JFrame frame = new JFrame();
		frame.setTitle("Graph theory");
		frame.setMinimumSize(new Dimension(870, 500));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PaintPanel paint = new PaintPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		paint.setBorder(blackline);
		ToolBarPanel toolBarPanel = new ToolBarPanel(paint);
		
		frame.setLayout(new BorderLayout());
		frame.add(toolBarPanel, BorderLayout.NORTH);
		frame.add(paint, BorderLayout.CENTER);
		frame.add(new Panel(),BorderLayout.EAST);
		frame.add(new Panel(),BorderLayout.SOUTH);
		frame.add(new Panel(),BorderLayout.WEST);
		frame.pack();
		frame.setVisible(true);
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
