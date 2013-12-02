package edu.ntua.dblab.hecataeus.graph.visual;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

import edu.ntua.dblab.hecataeus.HecataeusViewer;
import edu.ntua.dblab.hecataeus.graph.evolution.NodeType;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.scoring.BarycenterScorer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.Distance;
import edu.uci.ics.jung.graph.Hypergraph;

public class VisualConcentricCircleLayout extends AbstractLayout<VisualNode,VisualEdge> {

	private double relationRadius;
	private double viewRadius;
	private double queryRadius;
	private double width;
	private double height;
	
	private Graph graph;
	
	private List<VisualNode> queries = new ArrayList<VisualNode>();
	private List<VisualNode> relations = new ArrayList<VisualNode>();
	private List<VisualNode> views = new ArrayList<VisualNode>();
	private List<VisualNode> vertices = new ArrayList<VisualNode>();
	private List<VisualNode> nodes;
	
	/**
	 * Creates an instance for the specified graph.
	 * gets querries, views, relations from graph
	 */
	@SuppressWarnings("unchecked")
	public VisualConcentricCircleLayout(Graph g) {
		super(g);
		this.graph = g;
		nodes = new ArrayList<VisualNode>((Collection<? extends VisualNode>) g.getVertices());
	//	scorer();
		for(VisualNode v : nodes){
			if(v.getType() == NodeType.NODE_TYPE_QUERY){
				queries.add(v);
			}
			else if(v.getType() == NodeType.NODE_TYPE_RELATION){
				relations.add(v);
			}
			else if(v.getType() == NodeType.NODE_TYPE_VIEW){
				views.add(v);
			}
			else{
				vertices.add(v);
			}
		}
	}

	/**
	 * Returns the radius of the relation circle.
	 */
	public double getRelationRadius() {
		return relationRadius;
	}

	/**
	 * Returns the radius of the view circle.
	 */
	public double getViewRadius() {
		return viewRadius;
	}
	/**
	 * Returns the radius of the query circle.
	 */
	public double getQueryRadius() {
		return queryRadius;
	}


	public void scorer(){

		BetweennessCentrality beetwc = new BetweennessCentrality(graph);
		
		//BarycenterScorer bc = new BarycenterScorer((Graph)graph);
		Double[] dist = null;
		int i = 0;
		for(Object v : graph.getVertices()){
			//dist[i] = bc.getVertexScore(v);
			beetwc.getVertexScore(v);
			VisualNode vn = (VisualNode)v; 
			beetwc.getEdgeScore(vn._inEdges.get(0));
			//System.out.println(dist);
			i++;
		}
		
	}
	
	public void setRelationVertexOrder(Comparator<VisualNode> comparator){
		if (relations == null){
			for(VisualNode v : nodes){
				if(v.getType() == NodeType.NODE_TYPE_RELATION)
					relations.add(v);
			}
		}
		Collections.sort((List)relations, comparator);
	}
	
	
	public void setViewVertexOrder(Comparator<VisualNode> comparator){
		if (views == null){
			for(VisualNode v : nodes){
				if(v.getType() == NodeType.NODE_TYPE_VIEW)
					views.add(v);
			}
		}
		Collections.sort((List)views, comparator);
	}
	
	
	public void setQueryVertexOrder(Comparator<VisualNode> comparator){
		if (queries == null){
			for(VisualNode v : nodes){
				if(v.getType() == NodeType.NODE_TYPE_QUERY)
					queries.add(v);
			}
		}
		Collections.sort((List)queries, comparator);
	}
	
	
	public void reset() {
		initialize();
	}

	public void initialize() {
		Dimension d = getSize();
		
		if (d != null) {
			height = d.getHeight();
			width = d.getWidth();

			List<Integer> sizes = new ArrayList<Integer>();
			sizes.add(queries.size());
			sizes.add(relations.size());
			sizes.add(views.size());

			Collections.sort(sizes);
			
			/*
			 * 
			 * calculate every radius
			 * 
			 */
			
			double tempRad = 0;
			if (relationRadius <= 0) {
//				relationRadius = 0.45 * (height < width ? height/3 : width/3);
//				relationRadius = 0.45 * (height < width ? height : width);
				
				if(relations.size() == sizes.get(0)){           // ta relations einai ta ligotera
					relationRadius = 0.45 * (height < width ? height*sizes.get(0)/sizes.get(1) : width*sizes.get(0)/sizes.get(1));
				//	tempRad = relationRadius;
				}else if(relations.size() == sizes.get(1)){     //ta relations einai ta mesaia
					relationRadius = 0.45 * (height < width ? height*sizes.get(1)/sizes.get(2) : width*sizes.get(1)/sizes.get(2));
				//	relationRadius+=tempRad;
				//	tempRad = relationRadius;
				}else{    // ta relations einai ta perissotera
					relationRadius = 0.45 * (height < width ? height : width)+100;
				//	relationRadius+=tempRad;
				}
			}
			
			if (viewRadius <= 0) {
//				viewRadius = 0.45 * (height < width ? height/2 : width/2);
//				viewRadius = 0.45 * (height < width ? height*2 : width*2);
//				viewRadius = 0.45 * (height < width ? height*sizes.get(1)/sizes.get(2) : width*sizes.get(1)/sizes.get(2));
				
				if(views.size() == sizes.get(0)){           // ta views einai ta ligotera
					viewRadius = 0.45 * (height < width ? height*sizes.get(0)/sizes.get(1) : width*sizes.get(0)/sizes.get(1));
				//	tempRad = viewRadius;
				}else if(views.size() == sizes.get(1)){     //ta views einai ta mesaia
					viewRadius = 0.45 * (height < width ? height*sizes.get(1)/sizes.get(2) : width*sizes.get(1)/sizes.get(2));
				//	viewRadius+=tempRad;
				//	tempRad = viewRadius;
				}else{    // ta views einai ta perissotera
					viewRadius = 0.45 * (height < width ? height : width)+100;
				//	viewRadius+=tempRad;
				}
			}
			
			if (queryRadius <= 0) {
//				queryRadius = 0.45 * (height < width ? height : width);
//				queryRadius = 0.45 * (height < width ? height*3 : width*3);
//				queryRadius = 0.45 * (height < width ? height*sizes.get(0)/sizes.get(1) : width*sizes.get(0)/sizes.get(1));
				
				if(queries.size() == sizes.get(0)){           // ta queries einai ta ligotera
					queryRadius = 0.45 * (height < width ? height*sizes.get(0)/sizes.get(1) : width*sizes.get(0)/sizes.get(1));
				//	tempRad = queryRadius;
				}else if(queries.size() == sizes.get(1)){     //ta queries einai ta mesaia
					queryRadius = 0.45 * (height < width ? height*sizes.get(1)/sizes.get(2) : width*sizes.get(1)/sizes.get(2));
				//	queryRadius+=tempRad;
				//	tempRad = queryRadius;
				}else{    // ta queries einai ta perissotera
					queryRadius = 0.45 * (height < width ? height : width)+100;
				//	queryRadius+=tempRad;
				}
			}
			
			drawCircles(relations, relationRadius);
			drawCircles(views, viewRadius);
			drawCircles(queries, queryRadius);
			
			

		}
	}
	
	/**
	 * 
	 * @param nodes
	 * @param radius
	 * calulate postions on a cicle for each node
	 * 
	 */
	
	
	
	protected void drawCircles(List<VisualNode> nodes, double radius){
		int cnt = 0;
		for(VisualNode n :  nodes){
			Point2D coord = transform(n);
			double angle = (2*Math.PI*cnt)/nodes.size();
			coord.setLocation(Math.cos(angle) * radius + width/2 , Math.sin(angle) * radius + height/2);
			n.setLocation(coord);
			dosomething(Math.cos(angle) * radius + width/2 , Math.sin(angle) * radius + height/2, n, 0);
			cnt++;
			HecataeusViewer.getActiveViewer().getRenderContext().setVertexFillPaintTransformer(new VisualClusteredNodeColor(n, HecataeusViewer.getActiveViewer().getPickedVertexState()));
			HecataeusViewer.getActiveViewer().repaint();
		}
		
	}

	/**
	 * 
	 * 
	 * @param x  x coordinate of inner circle
	 * @param y  y coord of inner circle
	 * @param node
	 * @param mode
	 */
	
	
	protected void dosomething(double x, double y, VisualNode node, int mode){
		int a = 0;

		ArrayList<VisualNode> sem = new ArrayList<VisualNode>(FindSem(node));
				
		for (VisualNode v : sem){
			Point2D coord = transform(v);
			double angle = (2 * Math.PI * a) / sem.size();
			if(mode == 0){
				v.setLocation(coord);
				coord.setLocation(Math.cos(angle) * 40 +x, Math.sin(angle) * 40+ y);
			}
			else{
				v.setLocation(coord);
				coord.setLocation(Math.cos(angle) * 65 +x, Math.sin(angle) * 65+ y);
			}
			dosomething(x, y, (VisualNode)v,1);
			a++;
		}
	}
	
	protected ArrayList<VisualNode> FindSem(VisualNode node){
		ArrayList<VisualNode> sem = new ArrayList<VisualNode>();
		List<VisualEdge> outE = new ArrayList<VisualEdge>(node._outEdges);

		for(VisualEdge edgeIndx : outE){
			if(edgeIndx.getToNode()!=null){
				if(edgeIndx.getToNode().getType() != NodeType.NODE_TYPE_QUERY && edgeIndx.getToNode().getType() != NodeType.NODE_TYPE_VIEW && edgeIndx.getToNode().getType() != NodeType.NODE_TYPE_RELATION){
					sem.add(edgeIndx.getToNode());
				}
			}
		}
		
		return sem;
	}
	
	protected double SemRadius(List<VisualNode> nodes, double r){
		double R = 0.45 * (r/5) * nodes.size();
		return R;
	}
}