package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Ellipse extends AShape {
	private int nameVertex = 0;
	private Ellipse2D el;
	private double x1, y1;

	public Ellipse(double x, double y) {
		super(x, y);
	}

	public int getNameVertex() {
		return nameVertex;
	}

	public void setNameVertex(int nameVertex) {
		this.nameVertex = nameVertex;
	}

	public Ellipse2D getEllipse2d() {
		return el;
	}

	public void setEllipse2d(Ellipse2D el) {
		this.el = el;
	}

	@Override
	public void drawShape(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		g2.setColor(Color.black);
//		el = new Ellipse2D.Double(x, y, 50, 50);
//		g2.fill(el);
//
//		Font font = new Font("Arial", Font.BOLD, 15);
//		FontMetrics metrics = g.getFontMetrics(font);
//		g.setFont(font);
//		g.setColor(Color.white);
//		String string = nameVertex + 1 + "";
//		int x = (int) (el.getX() + (el.getWidth() - metrics.stringWidth(string)) / 2);
//		int y = (int) (el.getY() + (el.getHeight() - metrics.getHeight()) / 2) + 14;
//		g.drawString(string, x, y);
		x1 = x;
		y1 = y;
		g.setColor(Color.black);
		g.fillOval((int) x1, (int) y1, 50, 50);
		System.out.println("3");
	}
}
