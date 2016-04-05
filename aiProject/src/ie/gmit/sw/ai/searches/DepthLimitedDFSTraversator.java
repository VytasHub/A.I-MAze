package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;

import java.awt.Component;
public class DepthLimitedDFSTraversator extends Traversator{
	private int limit;
	
	public DepthLimitedDFSTraversator(Node currentNode, Node goal, Node[][] maze, int limit){
		super(currentNode, goal, maze);
		this.limit = limit;
		
		traverse();
	}
	
	public void traverse() {
		
		dfs(node, 1);
		
		if (keepRunning){
			System.out.println("Failed to find goal node within a depth of " + limit);
		}
	}
	
	private void dfs(Node node, int depth){
		positions.add(node);
		if (!keepRunning || depth > limit) return;
		
		node.setVisited(true);	
		
		if (node.isGoalNode()){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount);
	        keepRunning = false;
			return;
		}
		
		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i], depth + 1);
			}
		}
	}
}