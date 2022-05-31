package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Set;

public class Graph {
	private ArrayList<Vertex> vertexs;
	private ArrayList<Edge> edges;
	private ArrayList<ArrayList<Integer>> mtkArrayList;
	ArrayList<Edge> nonDel, edgeDel;

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

	public void addVertex(Ellipse2D el) {
		if (mtkArrayList.size() == 0) {
			mtkArrayList.add(new ArrayList<Integer>());
			mtkArrayList.get(0).add(0);
			vertexs.add(new Vertex(mtkArrayList.size() - 1, mtkArrayList.size() - 1, new ArrayList<Vertex>(), el));
			return;
		}
		for (int i = 0; i < mtkArrayList.size(); i++) {
			mtkArrayList.get(i).add(0);
		}
		ArrayList<Integer> dongMoIntegers = new ArrayList<Integer>();
		for (int i = 0; i < mtkArrayList.size() + 1; i++) {
			dongMoIntegers.add(0);

		}
		mtkArrayList.add(dongMoIntegers);
		vertexs.add(new Vertex(mtkArrayList.size() - 1, mtkArrayList.size() - 1, new ArrayList<Vertex>(), el));

	}

	public void addDerectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, 1);
		edges.add(new Edge(diemdau, diemcuoi, line2d));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
	}

	public void addUnderectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, 1);
		mtkArrayList.get(diemcuoi.index).set(diemdau.index, 1);
		edges.add(new Edge(diemdau, diemcuoi, line2d));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
	}

	public void showVertex() {
		System.out.println("List Vertex");
		for (int i = 0; i < vertexs.size(); i++) {
			System.out.println(vertexs.size());
			Ellipse2D v = vertexs.get(i).getEllipse();
			System.out.print(vertexs.get(i).getIndex() + " " + v.getX() + " " + v.getY());
		}
		System.out.println();
	}

	public void showEdge() {
		System.out.println("List Edge " +edges.size());
		for (int i = 0; i < edges.size(); i++) {
			double x1 = edges.get(i).getNode1().getEllipse().getX();
			double y1 = edges.get(i).getNode1().getEllipse().getY();
			double x2 = edges.get(i).getNode2().getEllipse().getX();
			double y2 = edges.get(i).getNode2().getEllipse().getY();
			int name1 = edges.get(i).getNode1().getNameVeretex();
			int name2 = edges.get(i).getNode2().getNameVeretex();
			int index1 = edges.get(i).getNode1().getIndex();
			int index2 = edges.get(i).getNode2().getIndex();
			System.out.println("From: " + x1 + ", " + y1 + " " + index1 + " to: " + x2 + ", " + y2 + " " + index2);
		}
		System.out.println();
	}

	public void delVertex(Vertex v) {
		edgeDel = new ArrayList<>();
		vertexs.remove(v);
		int index = v.getIndex();
		for (int i =0; i < edges.size(); i++) {
			if (edges.get(i).getNode1().getIndex() == index || edges.get(i).getNode2().getIndex() == index) {
				edgeDel.add(edges.get(i));
			
			}
		}
		for (int i = 0; i< edgeDel.size(); i++) {
			edges.remove(edgeDel.get(i));
		}
	}
	
//	public void delDirectedsEdge(Edge edge) { 
//		mtkArrayList.get(edge.getNode1().index).set(edge.getNode2().index, 0);
//		edges.remove(edge);
//		edge.getNode1().dsKe.remove(edge.getNode2());
//		edge.getNode2().dsKe.remove(edge.getNode1());
//		System.out.println("dellllll");
//	}

//	public void delUndirectedsEdge(Edge edge) {
//		mtkArrayList.get(edge.getNode1().index).set(edge.getNode2().index, 0);
//		mtkArrayList.get(edge.getNode2().index).set(edge.getNode1().index, 0);
//		edge.getNode1().dsKe.remove(edge.getNode2());
//		edges.remove(edge);
//		edge.getNode2().dsKe.remove(edge.getNode1());
//		System.out.println("dellllll");
//
//	}
	public static void main(String[] args) {
		Ellipse2D ellipse2d = new Ellipse2D.Double(1, 1, 11, 11);
		Vertex v1 = new Vertex(0, 0, new ArrayList<>(), ellipse2d);
		Vertex v2 = new Vertex(1, 1, new ArrayList<>(), ellipse2d);
		Vertex v3 = new Vertex(2, 2, new ArrayList<>(), ellipse2d);
		Vertex v4 = new Vertex(3, 3, new ArrayList<>(), ellipse2d);
		
		Graph g = new Graph();
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addUnderectedEdge(v1, v2, null);
		g.addUnderectedEdge(v1, v3, null);
		g.addUnderectedEdge(v1, v4, null);
		g.addUnderectedEdge(v2, v3, null);
		g.addUnderectedEdge(v2, v4, null);
		g.addUnderectedEdge(v3, v4, null);
		
		g.showEdge();
		g.delVertex(v1);
		g.showEdge();
		g.delVertex(v2);
		g.showEdge();
		g.delVertex(v3);
		g.showEdge();
	}
}
