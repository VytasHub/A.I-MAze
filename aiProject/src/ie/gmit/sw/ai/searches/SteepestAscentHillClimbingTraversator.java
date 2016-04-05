package ie.gmit.sw.ai.searches;

import java.awt.*;
import java.util.*;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.runner.GameRunner;
public class SteepestAscentHillClimbingTraversator extends Traversator {
	
	public SteepestAscentHillClimbingTraversator(Node currentNode, Node goal, Node[][] maze){
		super(currentNode, goal, maze);
		
		traverse();
	}
	
	public void traverse() {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		while(!queue.isEmpty()){
			node = queue.poll();
			positions.add(node);
			visitCount++;
			node.setVisited(true);		
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
//		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			//Sort the children of the current node in order of increasing h(n)
			Node[] children = node.children(maze);
			Collections.sort(Arrays.asList(children),(Node current, Node next) -> next.getHeuristic(goal) - current.getHeuristic(goal));
			
			for (int i = 0; i < children.length; i++) {			
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]); //LIFO
				}
			}
		}
	}
}