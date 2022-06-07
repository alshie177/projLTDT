package model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Edge {
	private Vertex node1;
	private Vertex node2;
	private Line2D line2d;
	boolean isTravel = false;
	private int value;

	public Edge(Vertex node1, Vertex node2, Line2D line2d, int value) {
		super();
		this.node1 = node1;
		this.node2 = node2;
		this.line2d = line2d;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Line2D getLine2d() {
		return line2d;
	}

	public void setLine2d(Line2D line2d) {
		this.line2d = line2d;
	}

	public Vertex getNode1() {
		return node1;
	}

	public void setNode1(Vertex node1) {
		this.node1 = node1;
	}

	public Vertex getNode2() {
		return node2;
	}

	public void setNode2(Vertex node2) {
		this.node2 = node2;
	}

	public boolean isTravel() {
		return isTravel;
	}

	public void setTravel(boolean isTravel) {
		this.isTravel = isTravel;
	}

}
