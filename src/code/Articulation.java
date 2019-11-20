package code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Articulation {

	public static Set<Node> findArticulationPoints(Collection<Node> nodes) {
		// first, set all nodes' count = MAX_VALUE and initial their neighbours
		for (Node n : nodes) {
			n.count = Integer.MAX_VALUE;
			for (Segment seg : n.segments) {
				if (seg.start == n && seg.end != null)
					n.neighbours.add(seg.end);
				else if (seg.end == n && seg.start != null)
					n.neighbours.add(seg.start);
			}
		}

		Set<Node> unvisitedNodes = new HashSet<>(nodes);
		Set<Node> articulationPoints = new HashSet<>();

		while (!unvisitedNodes.isEmpty()) {
			int numSubtrees = 0;
			Node start = unvisitedNodes.iterator().next();
			start.count = 0;
			for (Node neighbour : start.neighbours) {
				if (neighbour.count == Integer.MAX_VALUE) {
					iterArtPts(neighbour, start, articulationPoints, unvisitedNodes);
					numSubtrees++;
				}
			}

			if (numSubtrees > 1)
				articulationPoints.add(start);

			unvisitedNodes.remove(start);
		}
		// could return empty set
		return articulationPoints;
	}

	private static void iterArtPts(Node firstNode, Node root, Set<Node> artPoints, Set<Node> unvisitedNodes) {
		Stack<IteArtStackElement> stack = new Stack<>();
		stack.push(new IteArtStackElement(firstNode, 1, new IteArtStackElement(root, 0, null)));

		while (!stack.isEmpty()) {
			IteArtStackElement elem = stack.peek();
			Node node = elem.node;
			if (elem.children == null) { // first time
				node.count = elem.reachBack = elem.count;
				elem.children = new ArrayList<>();
				for (Node neighbour : node.neighbours) {
					if (neighbour.nodeID != elem.parent.node.nodeID) {
						elem.children.add(neighbour);
					}
				}
			} else if (!elem.children.isEmpty()) { // children to process
				Node child = elem.children.remove(0);
				if (child.count < Integer.MAX_VALUE)
					elem.reachBack = Math.min(elem.reachBack, child.count);
				else
					stack.push(new IteArtStackElement(child, node.count + 1, elem));
			} else { // last time
				if (node.nodeID != firstNode.nodeID) {
					if (elem.reachBack >= elem.parent.count)
						artPoints.add(elem.parent.node);
					elem.parent.reachBack = Math.min(elem.parent.reachBack, elem.reachBack);
				}
				stack.pop();
				unvisitedNodes.remove(elem.node);
			}
		}
	}

	private static class IteArtStackElement {

		Node node;
		int reachBack;
		IteArtStackElement parent;
		int count;
		List<Node> children;

		public IteArtStackElement(Node node, int count, IteArtStackElement parent) {
			this.node = node;
			this.reachBack = Integer.MAX_VALUE;
			this.parent = parent;
			this.count = count;
			this.children = null;
		}
	}
}