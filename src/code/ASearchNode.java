
package code;

public class ASearchNode implements Comparable<ASearchNode> {

	Node node;
	ASearchNode parent;
	Segment edge;
	double cost = 0;
	double heuristic = 0;

	public ASearchNode(Node node, ASearchNode parent, Node destination) {
		this.node = node;
		this.parent = parent;
		this.setEdge();
		if (parent == null)
			this.cost = 0;
		else
			this.cost = this.edge.length + this.parent.cost;
		this.heuristic = this.node.location.distance(destination.location);
	}

	public void setEdge() {
		if (this.node == null || this.parent == null)
			return;
		for (Segment s : this.node.segments) {
			if (this.parent.node.segments.contains(s)) {
				this.edge = s;
				return;
			}
		}
	}

	@Override
	public int compareTo(ASearchNode other) {
		double costNode1 = this.cost + this.heuristic;
		double costNode2 = other.cost + other.heuristic;
		if (costNode1 > costNode2)
			return 1;
		else if (costNode1 < costNode2)
			return -1;
		return 0;
	}
}
