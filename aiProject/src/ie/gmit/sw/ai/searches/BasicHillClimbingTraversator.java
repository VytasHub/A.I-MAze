package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.runner.GameRunner;

public class BasicHillClimbingTraversator extends Traversator {

	public BasicHillClimbingTraversator(Node currentNode, Node goal, Node[][] maze) {
		super(currentNode, goal, maze);
		
		traverse();
	}

	public void traverse() {
		long time = System.currentTimeMillis();
		int visitCount = 0;

		Node next = null;
		while (node != null) {

			node.setVisited(true);
			visitCount++;

			if (node.isGoalNode()) {
				time = System.currentTimeMillis() - time; // Stop the clock
				// TraversatorStats.printStats(node, time, visitCount);
				break;
			}

			Node[] children = node.children(maze);
			int fnext = Integer.MAX_VALUE;
			for (int i = 0; i < children.length; i++) {
				if (children[i].getHeuristic(goal) < fnext) {
					next = children[i];
					fnext = next.getHeuristic(goal);
				}
			}

			if (fnext >= node.getHeuristic(goal)) {
				// System.out.println("Cannot improve on current node " +
				// node.toString() + " \nh(n)="
				// + node.getHeuristic(goal) + " = Local Optimum...");
				break;
			}
			node = next;
			positions.add(node);
			next = null;
		}
	}

}