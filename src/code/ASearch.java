package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;


public class ASearch {

    public static List<Segment> findPath(Node start, Node destination) {
        HashSet<Integer> visitedNodes = new HashSet<>();
        PriorityQueue<ASearchNode> fringe = new PriorityQueue<>();

        // enqueue the start node
        fringe.add(new ASearchNode(start, null, destination));

        while (!fringe.isEmpty()) {
            ASearchNode current = fringe.poll();
            visitedNodes.add(current.node.nodeID);
            // if the destination node is dequeued, the path is found
            if (current.node.nodeID == destination.nodeID) {
                List<Segment> path = new ArrayList<>();
                ASearchNode backTracer = current;
                while (backTracer.parent != null){
                    path.add(backTracer.edge);
                    backTracer = backTracer.parent;
                }
                Collections.reverse(path);
                return path;
            }

            Node oneEnd = current.node;
            for (Segment seg : current.node.segments) {
                Node theOtherEnd = null;
    				if (seg.start == oneEnd)
    					theOtherEnd = seg.end;
    				else if (seg.end == oneEnd && !seg.road.oneway)
    					theOtherEnd=seg.start;
                // check if this segment is not for car, if so, don't enqueue this node
                if (seg.road.notforcar) {
                	continue;
                }
                if (theOtherEnd!=null && !visitedNodes.contains(theOtherEnd.nodeID)) { // filter out those visited
                    ASearchNode neighbour = new ASearchNode(theOtherEnd, current, destination);
                    boolean alreadyIn = false;
                    // check if it already in fringe, if so, see if its cost needs to be updated
                    for (ASearchNode n : fringe) {
                        if (n.node.nodeID == theOtherEnd.nodeID) {
                            if (neighbour.cost < n.cost) {
                                n.parent = neighbour.parent;
                                n.setEdge();
                            }
                            alreadyIn = true;
                            break;
                        }
                    }
                    if (!alreadyIn)
                        fringe.add(neighbour);

                }
            }
        }
        return new ArrayList<>();
    }
}