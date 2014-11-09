package MIQ;

import java.util.ArrayList;

public class Node {

	String itemname =null;
	int count ;
	Node parent = null;
	ArrayList<Node> childs = new ArrayList<Node>();
    Node nodeLink = null;
    int max=0;
    int nodeUtility=0;
    Node getChild(String name) {
		
		for (Node child : this.childs) {
		
			if (child.itemname.equals(name)) {
				
				return child;
			}
		}
		
		return null;
	}
}
