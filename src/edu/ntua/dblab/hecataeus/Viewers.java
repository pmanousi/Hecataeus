/**
 * 
 */
package edu.ntua.dblab.hecataeus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.ntua.dblab.hecataeus.graph.visual.VisualEdge;
import edu.ntua.dblab.hecataeus.graph.visual.VisualEdgeColor;
import edu.ntua.dblab.hecataeus.graph.visual.VisualEdgeDrawColor;
import edu.ntua.dblab.hecataeus.graph.visual.VisualEdgeLabel;
import edu.ntua.dblab.hecataeus.graph.visual.VisualEdgeToolTips;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNode;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeColor;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeFont;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeIcon;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeLabel;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeShape;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeToolTips;
import edu.ntua.dblab.hecataeus.graph.visual.VisualNodeVisible;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.LayoutLensShapePickSupport;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author eva
 *
 */
public class Viewers {
	public static int cnt = 0;
	protected Transformer<VisualNode, Point2D> locationTransformer;
	protected VisualizationViewer<VisualNode, VisualEdge> vv;
	protected HecataeusViewer viewer;
	
	protected VisualizationViewer<VisualNode, VisualEdge> SetViewers(Layout<VisualNode, VisualEdge> layout, HecataeusViewer viewer) {
		
		this.viewer = viewer;
		vv = new VisualizationViewer<VisualNode, VisualEdge>(layout);
		Dimension prefferedSize = Toolkit.getDefaultToolkit().getScreenSize();
		vv = new VisualizationViewer<VisualNode, VisualEdge>(layout);
		vv.setSize(new Dimension((int)prefferedSize.getWidth()/2,(int)prefferedSize.getHeight()/2));
		vv.setBackground(Color.white);
		vv.setPickSupport(new LayoutLensShapePickSupport<VisualNode, VisualEdge>(vv));
		
		vv.setVertexToolTipTransformer(new VisualNodeToolTips());
		vv.setEdgeToolTipTransformer(new VisualEdgeToolTips());
		// the labels of the Vertices
		vv.getRenderContext().setVertexLabelTransformer(new VisualNodeLabel());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO); 

		vv.getRenderContext().setEdgeStrokeTransformer(new ConstantTransformer(new BasicStroke(0.1f)));
		// the fonts of the vertices
		vv.getRenderContext().setVertexFontTransformer(new VisualNodeFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13)));         //node size
		//the shape of the edges
		vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());  //quad 
		// the labels of the Edges
		vv.getRenderContext().setEdgeLabelTransformer(new VisualEdgeLabel());
		// call the setVertexPaintFunction to paint the nodes
		vv.getRenderContext().setVertexFillPaintTransformer(new VisualNodeColor(vv.getPickedVertexState()));
		
		//set edge color
		vv.getRenderContext().setEdgeDrawPaintTransformer(new VisualEdgeDrawColor(vv.getPickedEdgeState()));
		// call the setEdgePaintFunction to paint the edges
		vv.getRenderContext().setEdgeFillPaintTransformer( new VisualEdgeColor(vv.getPickedEdgeState()));
		// call the setVertexShapeFunction to set the shape of the nodes
		vv.getRenderContext().setVertexShapeTransformer(new VisualNodeShape());
		// call the setNodeVisible to set the shape of the nodes according to
		vv.getRenderContext().setVertexIncludePredicate(new VisualNodeVisible());
		vv.getRenderContext().setVertexIconTransformer(new VisualNodeIcon());
		

		vv.getRenderContext().getMultiLayerTransformer().addChangeListener(vv);
		HecataeusModalGraphMouse gm = new HecataeusModalGraphMouse();
		gm.HecataeusViewerPM(viewer);
		vv.setGraphMouse(gm);
		vv.addGraphMouseListener(gm);
		
		return vv;

	}

}
