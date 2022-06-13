package model;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Vertex {
	Integer index;
	ArrayList<Vertex> dsKe = new ArrayList<Vertex>();
	Ellipse2D ellipse;
	boolean isTravel = false;
	boolean existEdge;
	boolean selected = false;

	public Vertex(Integer index, ArrayList<Vertex> dsKe, Ellipse2D ellipse, boolean existEgde) {
		this.index = index;
		this.dsKe = dsKe;
		this.ellipse = ellipse;
		this.existEdge = existEgde;
	}

	public boolean isExistEdge() {
		return existEdge;
	}

	public void setExistEdge(boolean existEdge) {
		this.existEdge = existEdge;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public ArrayList<Vertex> getDsKe() {
		return dsKe;
	}

	public void setDsKe(ArrayList<Vertex> dsKe) {
		this.dsKe = dsKe;
	}

	public Ellipse2D getEllipse() {
		return ellipse;
	}

	public void setEllipse(Ellipse2D ellipse) {
		this.ellipse = ellipse;
	}

	public boolean isTravel() {
		return isTravel;
	}

	public void setTravel(boolean isTravel) {
		this.isTravel = isTravel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
