package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.runner.GameRunner;

import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class BeamTraversator extends Traversator{
	private int beamWidth= 1; 
	
	public BeamTraversator(Node currentNode, Node goal, Node[][] maze,  int beamWidth){
		super(currentNode, goal, maze);
		this.beamWidth = beamWidth;
		
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
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
//		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}

			Node[] children = node.children(maze);
			Collections.sort(Arrays.asList(children),(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));
			
			int bound = 0;
			if (children.length < beamWidth){
				bound = children.length;
			}else{
				bound = beamWidth;
			}
			
			for (int i = 0; i < bound; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
		}
	}
}
