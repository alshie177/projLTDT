package view;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class ArowHead1 {

	double phi;
	int barb;

	public ArowHead1() {
		phi = Math.toRadians(89);
		barb = 10;
	}

	public void drawArrowHead(Graphics2D g2, Point2D pointToPoint2d, Point2D pointFromPoint2d, Color color) {
		g2.setPaint(color);
		double dy = pointToPoint2d.getY() - pointFromPoint2d.getY();
		double dx = pointToPoint2d.getX() - pointFromPoint2d.getX();
		double theta = Math.atan2(dy, dx);
		// System.out.println("theta = " + Math.toDegrees(theta));
		double x, y, rho = theta + phi + 10;
		for (int j = 0; j < 2; j++) {
			x = pointToPoint2d.getX() - barb * Math.cos(rho);
			y = pointToPoint2d.getY() - barb * Math.sin(rho);
			g2.draw(new Line2D.Double(pointToPoint2d.getX(), pointToPoint2d.getY(), x, y));
			rho = theta - phi - 105;
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public void drawCurve(Graphics2D g2, Point2D pointToPoint2d, Point2D pointFromPoint2d, Color color) {
		g2.setPaint(color);
		double x1 = pointFromPoint2d.getX();
		double y1 = pointFromPoint2d.getY();
		double ctrlx = (pointFromPoint2d.getX() + pointToPoint2d.getX()) / 2;
		double ctrly = (pointFromPoint2d.getX() + pointToPoint2d.getY()) / 2;
		double x2 = pointToPoint2d.getX();
		double y2 = pointToPoint2d.getY();
		for (int i = 0; i < 1; i++) {
			g2.draw(new QuadCurve2D.Double(x1, y1, ctrlx, ctrly, x2, y2));
		}
		double dy = pointToPoint2d.getY() - pointFromPoint2d.getY();
		double dx = pointToPoint2d.getX() - pointFromPoint2d.getX();
		double theta = Math.atan2(dy, dx);
		// System.out.println("theta = " + Math.toDegrees(theta));
		double x, y, rho = theta + phi + 10;
		for (int j = 0; j < 2; j++) {
			x = pointToPoint2d.getX() - barb * Math.cos(rho);
			y = pointToPoint2d.getY() - barb * Math.sin(rho);
			g2.draw(new Line2D.Double(pointToPoint2d.getX(), pointToPoint2d.getY(), x, y));
			rho = theta - phi - 105;
		}
	}
}