package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

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
		showMtk();
	}

	public void addUnderectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, 1);
		mtkArrayList.get(diemcuoi.index).set(diemdau.index, 1);
		edges.add(new Edge(diemdau, diemcuoi, line2d));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
		showMtk();

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
		System.out.println("List Edge " + edges.size());
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
		vertexs.remove(v);
		int index = v.getIndex();
		delEdge(index);

	}

	public void delEdge(int index) {
		ArrayList<Edge> edgeDel = new ArrayList<>();
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getNode1().getIndex() == index || edges.get(i).getNode2().getIndex() == index) {
				edgeDel.add(edges.get(i));

			}
		}
		for (int i = 0; i < edgeDel.size(); i++) {
			edges.remove(edgeDel.get(i));
		}
	}

	public void showMtk() {
		System.out.println("Mtk");
		for (int i = 0; i < mtkArrayList.size(); i++) {
			for (int j = 0; j < mtkArrayList.get(i).size(); j++) {
				System.out.print(mtkArrayList.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	public boolean arrayCompare(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
		for (int i = 0; i < arr1.size(); i++) {
			if (arr1.get(i) != arr2.get(i))
				return false;
		}
		return true;
	}

	public boolean ke(int x, int y) {
		return mtkArrayList.get(x).get(y) == 1;
	}

	public ArrayList<Integer> dfs(int start) {
		ArrayList<ArrayList<Integer>> edgeTo = fillEdgeTo();
		ArrayList<Boolean> isMarked = fillMark();
		ArrayList<Integer> result = new ArrayList();
		
		Stack<Integer> temp = new Stack<>();
		temp.push(start);
		isMarked.set(start, true);
		while (!temp.isEmpty()) {
			int p = temp.pop();
			result.add(p);
			System.out.print(p);
			for (int i : edgeTo.get(p)) {
				if (!isMarked.get(i)) {
					temp.push(i);
					isMarked.set(i, true);
				}
			}
		}
		System.out.println();
		return result;
	}

	public ArrayList<Integer> bfs(int start) {
		ArrayList<ArrayList<Integer>> edgeTo = fillEdgeTo();
		ArrayList<Boolean> isMarked = fillMark();
		ArrayList<Integer> result = new ArrayList();
		
		LinkedList<Integer> temp = new LinkedList<>();
		temp.add(start);
		isMarked.set(start, true);
		while (!temp.isEmpty()) {
//			System.out.println(temp.peek());
			int p = temp.poll();
			result.add(p);
			System.out.print(p);
			for (int i : edgeTo.get(p)) {
				if (!isMarked.get(i)) {
					temp.add(i);
					isMarked.set(i, true);
				}
			}
		}
		System.out.println();
		return result;
	}
	
	public ArrayList<Boolean> fillMark(){
		ArrayList<Boolean> isMarked = new ArrayList<>();
		for (int i = 0; i < mtkArrayList.size(); i++) {
			isMarked.add(false);
		}
		return isMarked;
	}
	
	public ArrayList<ArrayList<Integer>> fillEdgeTo(){
		ArrayList<ArrayList<Integer>> edgeTo = new ArrayList<>();
		for (int i = 0; i < mtkArrayList.size(); i++) {
			edgeTo.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < edgeTo.size(); i++) {
			for (int j = 0; j< edgeTo.size(); j++) {
				if (ke(i, j)) {
					edgeTo.get(i).add(j);
				}
			}
		}
		return edgeTo;
	}

	public Edge findEdge(int index1, int index2) {
		for (Edge e: edges) {
			if (e.getNode1().getIndex() == index1 && e.getNode2().getIndex()== index2 || 
					e.getNode2().getIndex() == index1 && e.getNode1().getIndex()== index2) {
				return e;
			}
		}
		return null;
	}
	
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
