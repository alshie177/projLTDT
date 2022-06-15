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
	private ArrayList<Integer> arrDel = new ArrayList<>();

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

	public void travel(ArrayList<Integer> arrayList) {

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
		diemcuoi.getDsKe().add(diemdau);
		showMtk(mtkArrayList);
	}

	public void addUnderectedEdge(Vertex diemdau, Vertex diemcuoi, Line2D line2d, int value) {
		mtkArrayList.get(diemdau.index).set(diemcuoi.index, value);
		mtkArrayList.get(diemcuoi.index).set(diemdau.index, value);

		mtkData.get(diemdau.index).set(diemcuoi.index, value);
		mtkData.get(diemcuoi.index).set(diemdau.index, value);

		edges.add(new Edge(diemdau, diemcuoi, line2d, value));
		diemdau.getDsKe().add(diemcuoi);
		diemcuoi.getDsKe().add(diemdau);
		showMtk(mtkArrayList);
	}

	public void newFile() {
		mtkArrayList.removeAll(mtkArrayList);
		vertexs.removeAll(vertexs);
		edges.removeAll(edges);
		mtkData.removeAll(mtkData);
	}

	public void delUndirectedEdge(Edge edge) {
		edges.remove(edge);
		mtkArrayList.get(edge.getNode1().getIndex()).set(edge.getNode2().getIndex(), 0);
		mtkArrayList.get(edge.getNode2().getIndex()).set(edge.getNode1().getIndex(), 0);

		mtkData.get(edge.getNode1().getIndex()).set(edge.getNode2().getIndex(), 0);
		mtkData.get(edge.getNode2().getIndex()).set(edge.getNode1().getIndex(), 0);

		edge.getNode1().getDsKe().remove(edge.getNode2());
		edge.getNode2().getDsKe().remove(edge.getNode1());
	}

	public void delDirectedEdge(Edge edge) {
		edges.remove(edge);
		mtkData.get(edge.getNode1().getIndex()).set(edge.getNode2().getIndex(), 0);
		mtkArrayList.get(edge.getNode1().getIndex()).set(edge.getNode2().getIndex(), 0);
		edge.getNode2().getDsKe().remove(edge.getNode1());
		showMtk(mtkArrayList);
	}

	private ArrayList<Integer> shortDistantList(Vertex src) {
		ArrayList<Integer> rArrayList = new ArrayList<>();
		ArrayList<Integer> lArrayList = new ArrayList<>();
		ArrayList<Integer> pArrayList = new ArrayList<>();
		ArrayList<Boolean> checkArrayList = new ArrayList<>();

		// gán giá trị cho arrl
		for (int i = 0; i < vertexs.size(); i++) {
			rArrayList.add(0);
			lArrayList.add(Integer.MAX_VALUE);
			pArrayList.add(-1);
			checkArrayList.add(true);
		}

		// đặt đỉnh cho R= tập đỉnh
		for (int i = 0; i < vertexs.size(); i++) {
			rArrayList.set(i, i);
		}

		// gán kc bắt đầu là 0
		lArrayList.set(src.getIndex(), 0);

		int value;
		while (rArrayList.size() != 0) {

			// gán value là đỉnh có L[value] nhỏ nhất
			value = minDis(lArrayList, checkArrayList);
			checkArrayList.set(value, false);
//
//			if (value == end.getIndex()) {
//				break;
//			}
			// xóa đỉnh value ra khỏi R
			for (int i = 0; i < rArrayList.size(); i++) {
				if (rArrayList.get(i) == value) {
					rArrayList.remove(i);
				}
			}
			ArrayList<Integer> dskeArrayList = new ArrayList<Integer>();
			for (int i = 0; i < vertexs.size(); i++) {
				if (mtkArrayList.get(value).get(i) != 0) {
					dskeArrayList.add(i);
				} else {
					if (mtkArrayList.get(i).get(value) != 0) {
						dskeArrayList.add(i);
					}
				}
			}
			ArrayList<Integer> datArrayList = new ArrayList<Integer>();
			for (int i = 0; i < rArrayList.size(); i++) {
				for (int j = 0; j < dskeArrayList.size(); j++) {
					if (rArrayList.get(i) == dskeArrayList.get(j)) {
						datArrayList.add(dskeArrayList.get(j));
					}
				}
			}
			for (int i : datArrayList) {
				if (lArrayList.get(i) > lArrayList.get(value) + mtkArrayList.get(value).get(i)) {
					lArrayList.set(i, lArrayList.get(value) + mtkArrayList.get(value).get(i));
					pArrayList.set(i, value);
				}
			}
		}
		return pArrayList;
	}

	private int minDis(ArrayList<Integer> lArrayList, ArrayList<Boolean> checkArrayList) {
		int index = -1;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < lArrayList.size(); i++) {
			if (checkArrayList.get(i) == true && lArrayList.get(i) < min) {
				min = lArrayList.get(i);
				index = i;
			}
		}

		return index;
	}

	public ArrayList<Integer> dijkstra(Vertex srcVertex, Vertex endVertex) {
		ArrayList<Integer> dataArrayList = new ArrayList<Integer>();
		ArrayList<Integer> resArrayList = new ArrayList<Integer>();
		ArrayList<Integer> cloneArrayList = (ArrayList<Integer>) shortDistantList(srcVertex).clone();

		dataArrayList.add(endVertex.getIndex());
		for (int i = cloneArrayList.size() - 1; i > 0;) {
			dataArrayList.add(cloneArrayList.get(i));
			i = cloneArrayList.get(i);
			if (i == srcVertex.getIndex()) {
				break;
			}
		}

		for (int i = dataArrayList.size() - 1; i > -1; i--) {
			resArrayList.add(dataArrayList.get(i));
		}

		System.out.println(resArrayList.toString());
		return resArrayList;
	}

	public static void main(String[] args) {
		Ellipse2D ellipse2d = new Ellipse2D.Double(1, 1, 11, 11);
		Vertex v1 = new Vertex(0, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v2 = new Vertex(1, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v3 = new Vertex(2, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v4 = new Vertex(3, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v5 = new Vertex(4, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v6 = new Vertex(5, new ArrayList<Vertex>(), ellipse2d, false);
		Vertex v7 = new Vertex(6, new ArrayList<Vertex>(), ellipse2d, false);

		Graph g = new Graph();
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addVertex(ellipse2d);
		g.addUnderectedEdge(v1, v2, null, 8);
		g.addUnderectedEdge(v2, v4, null, 1);
		g.addUnderectedEdge(v2, v3, null, 4);
		g.addUnderectedEdge(v3, v6, null, 3);
		g.addUnderectedEdge(v5, v4, null, 5);
		g.addUnderectedEdge(v5, v6, null, 6);
		g.addUnderectedEdge(v5, v7, null, 3);
		g.addUnderectedEdge(v7, v6, null, 5);
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
//		g.dijkstra(v1, v3);
		System.out.println(g.shortDistantList(v1).toString());
		System.out.println("-----");
		System.out.println(g.dijkstra(v2, v7).toString());
//		System.out.println(g.getDskInt(2).toString());
//		System.out.println(v2.getDsKe().toString());
	}
}
