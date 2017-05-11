package edu.ntua.dblab.hecataeus.graph.visual;

import org.apache.commons.collections15.Transformer;

import edu.ntua.dblab.hecataeus.graph.evolution.EvolutionEvent;
import edu.ntua.dblab.hecataeus.graph.evolution.EvolutionPolicy;
import edu.ntua.dblab.hecataeus.graph.evolution.NodeType;

/**
 * class for setting tooltip on the graph
 */
public final class VisualNodeToolTips implements Transformer<VisualNode,String> {
	public String transform(VisualNode node) {
		if (node.getVisible()) {

			String tooltip = "<html>";
			String cat = node.getType().getCategory().toString();
			tooltip += cat + "  ";
			if (node.getType() == NodeType.NODE_TYPE_QUERY && node.getFile() != null) { //gia na kserw to arxeio pou einai to query
				tooltip += "<br>From file <b>" + node.getFileName() + "</b><br>";
			}
			if ((node.getSQLDefinition() != null) && (!node.getSQLDefinition().isEmpty())) {
				tooltip += "<b>SQL Definition</b><br>";
				String definition = node.getSQLDefinition();
				definition = definition.replace("\n", "<br>");
				tooltip += definition;
				tooltip += "<hr>";
			}
			if (node.getHasPolicies()) {
				for (EvolutionPolicy p : node.getPolicies()) {
					tooltip += "<i>Policy:</i>" + p;
					tooltip += "<br>";
				}
			}
			if (node.getHasEvents()) {
				for (EvolutionEvent e : node.getEvents()) {
					tooltip += "<i>Event:</i> " + e;
					tooltip += "<br>";
				}
			}
			if (node.getType() == NodeType.NODE_TYPE_FILE) { //
				tooltip += "<i>Path: </i>" + node.getPath() + "<br/>"; //added by sgerag
			} //
			else { //
				tooltip += "<i>Line: </i>" + node.getLine() + "<br/>"; //added by sgerag
			}
			tooltip += "<i>Status: </i>" + node.getStatus();
			tooltip += "</html>";
			return tooltip;
		}
		return null;
	}
}