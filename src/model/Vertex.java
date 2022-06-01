package model;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Vertex {
	Integer nameVeretex;
	Integer index;
	ArrayList<Vertex> dsKe = new ArrayList<Vertex>();
	Ellipse2D ellipse;
	boolean isTravel = false;

	public Vertex(Integer nameVeretex, Integer index, ArrayList<Vertex> dsKe, Ellipse2D ellipse) {
		this.nameVeretex = nameVeretex;
		this.index = index;
		this.dsKe = dsKe;
		this.ellipse = ellipse;
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

	public Integer getNameVeretex() {
		return nameVeretex;
	}

	public void setNameVeretex(Integer nameVeretex) {
		this.nameVeretex = nameVeretex;
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

}
