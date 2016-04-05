package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.*;

public class RandomWalk extends Traversator {
	public RandomWalk(Node currentNode, Node goal, Node[][] maze) {
		super(currentNode, goal, maze);

		traverse();
	}

	public void traverse() {

		int steps = (int) Math.pow(maze.length, 2) * 5;
		// Original was 2 but wouldn't always find the goal node so increased
		// number of steps
		System.out.println("Number of steps allowed: " + steps);

		System.out.println("visitCount " + visitCount);
		System.out.println("steps " + steps);

		boolean complete = false;
		while (visitCount <= steps && node != null) {

			visitCount++;

			node.setVisited(true);

			if (visitCount % 10 == 0)
				// node.isGoalNode()
				// node.getState() == 'G'
				if (node.getState() == 'G') {
					System.out.println("Goal");
					time = System.currentTimeMillis() - time; // Stop the clock
					TraversatorStats.printStats(node, time, visitCount);
					complete = true;
					break;
				}

			// Pick a random adjacent node

			Node[] children = node.children(maze);
			node = children[(int) (children.length * Math.random())];
			positions.add(node);
		}
		System.out.println("While Ends");

		if (!complete)
			System.out.println("*** Out of steps....");
	}

}
