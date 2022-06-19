package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Frame extends JFrame {

	public Frame() {
		this.init();
	}

	private void init() {

		this.setTitle("Graph Theory");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1250, 500));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
		URL url = this.getClass().getResource("bannerLTDT.png");
		Image image= Toolkit.getDefaultToolkit().createImage(url);
		image=image.getScaledInstance(256, 256, image.SCALE_SMOOTH);
		this.setIconImage(image);

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