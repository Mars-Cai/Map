package code;

import java.util.Collection;
import java.util.HashSet;

/**
 * Road represents ... a road ... in our graph, which is some metadata and a
 * collection of Segments. We have lots of information about Roads, but don't
 * use much of it.
 *
 * @author tony
 */
public class Road {
	public final int roadID;
	public final String name, city;
	public final Collection<Segment> components;
	public double length;
	public boolean notforcar = false;
	public boolean oneway = false;
	public Road(int roadID, int type, String label, String city, int oneway,
			int speed, int roadclass, int notforcar, int notforpede,
			int notforbicy) {
		this.roadID = roadID;
		this.city = city;
		this.name = label;
		this.components = new HashSet<Segment>();
		this.notforcar = notforcar == 1 ? true : false;
		this.oneway = oneway == 1 ? true : false;
	}

	public void addSegment(Segment seg) {
		components.add(seg);
	}

	public double roadLength() {
		this.length = 0;
		for(Segment s :this.components)
			this.length+=s.length;
		return this.length;
	}
}

// code for COMP261 assignments