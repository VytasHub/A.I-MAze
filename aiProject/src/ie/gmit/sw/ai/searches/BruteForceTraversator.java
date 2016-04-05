package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.runner.GameRunner;
import java.util.*;

public class BruteForceTraversator extends Traversator {
	private boolean dfs = true;

	
	public BruteForceTraversator(Node currentNode, Node goal, Node[][] maze, boolean depthFirst){
		super(currentNode, goal, maze);
		this.dfs = depthFirst;
		
		traverse();
	}
	
	public void traverse() {
        //Start the clock
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		Deque<Node> queue = new LinkedList<Node>();
		queue.offer(node);
		
		while (!queue.isEmpty() && visitCount < maxPositions){
//			System.out.println("Visit Count: " + visitCount);
			node = queue.poll();
			positions.add(node);
			
//			GameRunner.printPos("Brute: ", node);
			node.setVisited(true);
			visitCount++;
			
			if (node.isGoalNode())
			{
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					if (dfs){
						queue.addFirst(children[i]);
					}else{
						queue.addLast(children[i]);
					}
				}									
			}
			
			
		}
	}
}
