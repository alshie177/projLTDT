package model;

import java.util.ArrayList;

public class Vertex {
	Integer nameVeretex;
	Integer index;
	ArrayList<Vertex> dsKe = new ArrayList<Vertex>();
	Ellipse ellipse;

	public Vertex( Integer index, ArrayList<Vertex> dsKe, Ellipse ellipse) {
		this.nameVeretex = ellipse.getNameVertex();
		this.index = index;
		this.dsKe = dsKe;
		this.ellipse = ellipse;
	}

	public Integer getNameInteger() {
		return nameVeretex;
	}

	public void setNameInteger(Integer nameInteger) {
		this.nameVeretex = nameInteger;
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

	public Ellipse getEllipse() {
		return ellipse;
	}

	public void setEllipse(Ellipse ellipse) {
		this.ellipse = ellipse;
	}

}
