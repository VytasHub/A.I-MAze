package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.runner.GameRunner;

import java.util.*;

public class BestFirstTraversator extends Traversator {

	public BestFirstTraversator(Node currentNode, Node goal, Node[][] maze) {
		super(currentNode, goal, maze);
		
		traverse();
	}

	public void traverse() {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);

		long time = System.currentTimeMillis();
		int visitCount = 0;

		while (!queue.isEmpty()) {
			node = queue.poll();
	
			positions.add(node);
			node.setVisited(true);
			visitCount++;

			if (node.isGoalNode()) {
				time = System.currentTimeMillis() - time; // Stop the clock
				// TraversatorStats.printStats(node, time, visitCount);
				
				break;
			}

			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()) {
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}

			// Sort the whole queue. Effectively a priority queue, first in,
			// best out
			Collections.sort(queue, (Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));
		}

	}
}