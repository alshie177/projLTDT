package model;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Set;

public class Graph {
	private ArrayList<Vertex> vertexs;
	private ArrayList<Edge> edges;
	private ArrayList<ArrayList<Integer>> mtkArrayList;

	public Graph() {
		vertexs = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		mtkArrayList = new ArrayList<ArrayList<Integer>>();
	}

	public ArrayList<Vertex> getVertexs() {
		return vertexs;
	}

	public void setVertexs(ArrayList<Vertex> vertexs) {
		this.vertexs = vertexs;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public ArrayList<ArrayList<Integer>> getMtkArrayList() {
		return mtkArrayList;
	}

	public void setMtkArrayList(ArrayList<ArrayList<Integer>> mtkArrayList) {
		this.mtkArrayList = mtkArrayList;
	}

	public void addVertex(Ellipse el) {
		if (mtkArrayList.size() == 0) {
			mtkArrayList.add(new ArrayList<Integer>());
			mtkArrayList.get(0).add(0);
			vertexs.add(new Vertex( mtkArrayList.size() - 1, new ArrayList<Vertex>(), el));
			return;
		}
		for (int i = 0; i < mtkArrayList.size(); i++) {
			mtkArrayList.get(i).add(0);
		}
		ArrayList<Integer> dongMoIntegers = new ArrayList<Integer>();
		for (int i = 0; i < mtkArrayList.size() + 1; i++) {
			dongMoIntegers.add(0);
			vertexs.add(new Vertex( mtkArrayList.size() - 1, new ArrayList<Vertex>(), el));
		}
		mtkArrayList.add(dongMoIntegers);
	}

	public void addDerectedEdge(Vertex diemdau, Vertex diemcuoi) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, 1);
		edges.add(new Edge(diemdau, diemcuoi));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
	}

	public void addUnderectedEdge(Vertex diemdau, Vertex diemcuoi) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, 1);
		mtkArrayList.get(diemcuoi.index).set(diemdau.index, 1);
		edges.add(new Edge(diemdau, diemcuoi));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
	}

	public void delVertex(Vertex vertex, Ellipse el) {
		ArrayList<ArrayList<Integer>> cloneArrayList = (ArrayList<ArrayList<Integer>>) mtkArrayList.clone();
		ArrayList<ArrayList<Integer>> resArrayList = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		int tmpX = -1;
		int tmpY = -1;
		for (int c = 0; c < mtkArrayList.size() - 1; c++) {
			if (mtkArrayList.size() == 0) {
				resArrayList.add(new ArrayList<Integer>());
				resArrayList.get(0).add(0);
				vertexs.add(new Vertex( resArrayList.size() - 1, new ArrayList<Vertex>(), el));
				return;
			}
			for (int i = 0; i < resArrayList.size(); i++) {
				resArrayList.get(i).add(0);
			}
			ArrayList<Integer> dongMoIntegers = new ArrayList<Integer>();
			for (int i = 0; i < resArrayList.size() + 1; i++) {
				dongMoIntegers.add(0);
				vertexs.add(new Vertex( resArrayList.size() - 1, new ArrayList<Vertex>(), el));
			}
			resArrayList.add(dongMoIntegers);
		}
		for (int i = 0; i < mtkArrayList.size(); i++) {
			tmpX++;
			if (i == vertex.getNameInteger()) {
				tmpX--;
			}
			tmpY = -1;
			for (int j = 0; j < mtkArrayList.size(); j++) {
				tmpY++;
				if (j == vertex.getNameInteger()) {
					tmpY--;
				}
				if (i != vertex.getNameInteger() && j != vertex.getNameInteger()) {
					count++;
					int n = cloneArrayList.get(i).get(j);
					resArrayList.get(tmpX).set(tmpY, n);
				}
			}
		}
		mtkArrayList = resArrayList;
	}
	
	public void delDirectedsEdge(Edge edge) {
		mtkArrayList.get(edge.getNode1().index).set(edge.getNode2().index, 0);
		edges.remove(new Edge(edge.getNode1(), edge.getNode2()));
		edge.getNode1().dsKe.remove(edge.getNode2());
		edge.getNode2().dsKe.remove(edge.getNode1());
	}

	public void delUndirectedsEdge(Edge edge) {
		mtkArrayList.get(edge.getNode1().index).set(edge.getNode2().index, 0);
		mtkArrayList.get(edge.getNode2().index).set(edge.getNode1().index, 0);
		edges.remove(new Edge(edge.getNode1(), edge.getNode2()));
		edge.getNode1().dsKe.remove(edge.getNode2());
		edge.getNode2().dsKe.remove(edge.getNode1());
	}
}
