package model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class Graph {
	private ArrayList<Vertex> vertexs;
	private ArrayList<Edge> edges;
	private ArrayList<ArrayList<Integer>> mtkArrayList;
	private ArrayList<ArrayList<Integer>> mtkData;
	ArrayList<Integer> arrDel = new ArrayList<>();

	public Graph() {
		vertexs = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		mtkArrayList = new ArrayList<ArrayList<Integer>>();
		mtkData = new ArrayList<ArrayList<Integer>>();
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

			mtkData.add(new ArrayList<Integer>());
			mtkData.get(0).add(0);

			vertexs.add(new Vertex(mtkArrayList.size() - 1, new ArrayList<Vertex>(), el, false));
		} else {
			for (int i = 0; i < mtkArrayList.size(); i++) {
				mtkArrayList.get(i).add(0);
			}

			for (int i = 0; i < mtkData.size(); i++) {
				mtkData.get(i).add(0);
			}

			ArrayList<Integer> dongMoIntegers = new ArrayList<Integer>();
			for (int i = 0; i < mtkArrayList.size() + 1; i++) {
				dongMoIntegers.add(0);

			}
			ArrayList<Integer> dongMoIntegers2 = new ArrayList<Integer>();
			for (int i = 0; i < mtkArrayList.size() + 1; i++) {
				dongMoIntegers2.add(0);

			}
			mtkData.add(dongMoIntegers2);
			mtkArrayList.add(dongMoIntegers);

			vertexs.add(new Vertex(mtkArrayList.size() - 1, new ArrayList<Vertex>(), el, false));
		}

//		showMtk(mtkArrayList);
//		showMtk(mtkData);
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
		delEdgeInMtk(index);
		System.out.println("arr del");
		showMtk(mtkArrayList);
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

	public void delEdgeInMtk(int index) {

		arrDel.add(index);
		for (int i = 0; i < mtkArrayList.size(); i++) {
			mtkArrayList.get(i).set(index, 0);
			mtkArrayList.get(index).set(i, 0);
		}

		for (int i = 0; i < mtkArrayList.size(); i++) {
			for (int j = 0; j < mtkArrayList.size(); j++) {
				if (checkDel(arrDel, i) && checkDel(arrDel, j)) {
					mtkArrayList.get(i).set(j, mtkData.get(i).get(j));
				}
			}
		}
	}

	public boolean checkDel(ArrayList<Integer> arr, int index) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) == index) {
				return false;
			}
		}
		return true;
	}

	public void showMtk(ArrayList<ArrayList<Integer>> mtk) {
		System.out.println("Mtk");
		for (int i = 0; i < mtk.size(); i++) {
			for (int j = 0; j < mtk.get(i).size(); j++) {
				System.out.print(mtk.get(i).get(j) + " ");
			}
			System.out.println();
		}
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

	private ArrayList<Boolean> fillMark() {
		ArrayList<Boolean> isMarked = new ArrayList<>();
		for (int i = 0; i < mtkArrayList.size(); i++) {
			isMarked.add(false);
		}
		return isMarked;
	}

	private ArrayList<ArrayList<Integer>> fillEdgeTo() {
		ArrayList<ArrayList<Integer>> edgeTo = new ArrayList<>();
		for (int i = 0; i < mtkArrayList.size(); i++) {
			edgeTo.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < edgeTo.size(); i++) {
			for (int j = 0; j < edgeTo.size(); j++) {
				if (ke(i, j)) {
					edgeTo.get(i).add(j);
				}
			}
		}
		return edgeTo;
	}

	public Edge findEdge(int index1, int index2) {
		for (Edge e : edges) {
			if (e.getNode1().getIndex() == index1 && e.getNode2().getIndex() == index2
					|| e.getNode2().getIndex() == index1 && e.getNode1().getIndex() == index2) {
				return e;
			}
		}
		return null;
	}

	public void addDerectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d, int value) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, value);

		mtkData.get(diemdau.index).set(diemcuoi.index, value);

		edges.add(new Edge(diemdau, diemcuoi, line2d, value));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
		showMtk(mtkArrayList);
	}

	public void addUnderectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d, int value) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, value);
		mtkArrayList.get(diemcuoi.index).set(diemdau.index, value);

		mtkData.get(diemdau.index).set(diemcuoi.index, value);
		mtkData.get(diemcuoi.index).set(diemdau.index, value);

		edges.add(new Edge(diemdau, diemcuoi, line2d, value));
		diemdau.dsKe.add(diemcuoi);
		diemcuoi.dsKe.add(diemdau);
		showMtk(mtkArrayList);
	}

	public void newFile() {
		mtkArrayList.removeAll(mtkArrayList);
		vertexs.removeAll(vertexs);
		edges.removeAll(edges);
		mtkData.removeAll(mtkData);
	}

//	public static int[] removeTheElement(int[] arr, int index) {
//
//		if (arr == null || index < 0 || index >= arr.length) {
//
//			return arr;
//		}
//
//		int[] anotherArray = new int[arr.length - 1];
//
//		for (int i = 0, k = 0; i < arr.length; i++) {
//
//			if (i == index) {
//				continue;
//			}
//
//			anotherArray[k++] = arr[i];
//		}
//
//		return anotherArray;
//	}

//	public ArrayList<Integer> dijkstra(Vertex src, Vertex end) {
//		ArrayList<Integer> rArrayList = new ArrayList<>();
//		ArrayList<Integer> lArrayList = new ArrayList<>();
//		ArrayList<Integer> pArrayList = new ArrayList<>();
//
//		for (int i = 0; i < vertexs.size(); i++) {
//			rArrayList.add(0);
//			lArrayList.add(Integer.MAX_VALUE);
//			pArrayList.add(-1);
//		}
//
//		for (int i = 0; i < vertexs.size(); i++) {
//			rArrayList.set(i, i);
//		}
//		lArrayList.set(src.getIndex(), 0);
//		pArrayList.set(0, src.index);
//
//		int value;
//		while (rArrayList.size() != 0) {
//
//			for (int i = 0; i < rArrayList.size(); i++) {
//				
//			}
//			value = Collections.min(lArrayList);
//			if (value == end.getIndex()) {
//				rArrayList.remove(value);
//				break;
//			}
//			rArrayList.remove(value);
//			ArrayList<Integer> dskeArrayList = new ArrayList<>();
//			for (int i = 0; i < vertexs.size(); i++) {
//				if (mtkArrayList.get(value).get(i) != 0) {
//					dskeArrayList.add(i);
//				} else {
//					if (mtkArrayList.get(i).get(value) != 0) {
//						dskeArrayList.add(i);
//					}
//				}
//			}
//			ArrayList<Integer> datArrayList = new ArrayList<>();
//			for (int i = 0; i < rArrayList.size(); i++) {
//				if (rArrayList.contains(dskeArrayList.get(i))) {
//					datArrayList.add(dskeArrayList.get(i));
//				}
//			}
//			for (Integer i : datArrayList) {
//				if (lArrayList.get(i) > lArrayList.get(value) + mtkArrayList.get(value).get(i)) {
//					lArrayList.set(i, lArrayList.get(value) + mtkArrayList.get(value).get(i));
//					pArrayList.set(i, value);
//				}
//			}
//		}
//		return pArrayList;
//	}
//
//	private ArrayList<Integer> getdsKe(int vertex) {
//		ArrayList<Integer> resArrayList = new ArrayList<>();
//		for (int i = 0; i < vertexs.size(); i++) {
//			if (vertexs.get(i).getIndex() == vertex) {
//				System.out.println(vertexs.get(i).getDsKe().toString());
//				for (Vertex v : vertexs.get(i).getDsKe()) {
//					resArrayList.add(v.getIndex());
//				}
//			}
//		}
//		return resArrayList;
//	}

	public static void main(String[] args) {
		Ellipse2D ellipse2d = new Ellipse2D.Double(1, 1, 11, 11);
		Vertex v1 = new Vertex(0, new ArrayList<>(), ellipse2d, false);
		Vertex v2 = new Vertex(1, new ArrayList<>(), ellipse2d, false);
		Vertex v3 = new Vertex(2, new ArrayList<>(), ellipse2d, false);
		Vertex v4 = new Vertex(3, new ArrayList<>(), ellipse2d, false);

		Graph g = new Graph();
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addUnderectedEdge(v1, v2, null, 1);
		g.addUnderectedEdge(v1, v3, null, 5);
		g.addUnderectedEdge(v1, v4, null, 7);
		g.addUnderectedEdge(v2, v3, null, 3);
		g.addUnderectedEdge(v2, v4, null, 2);
//		g.addUnderectedEdge(v3, v4, null, 1);
		ArrayList<Integer> a1 = new ArrayList<>();
		a1.add(1);
		a1.add(1);
		a1.add(1);
		a1.add(0);
		a1.add(0);
//		g.showEdge();
//		g.showMtk(g.mtkArrayList);
//		g.delVertex(v1);
//		System.out.println("Column "+g.findColumn(a1));
//		g.showEdge();
//		g.showMtk(g.mtkArrayList);
//		g.delVertex(v2);
////		g.showEdge();
//		g.showMtk(g.mtkArrayList);
//		g.delVertex(v3);
////		g.showEdge();
//		g.showMtk(g.mtkArrayList);
//		g.bfs(2);
		System.out.println("-----");
		g.dijkstra(v1, v3);
		System.out.println(g.dijkstra(v1, v3).toString());
	}
}
