package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;

public class RecursiveDFSTraversator extends Traversator {
	
	public RecursiveDFSTraversator(Node currentNode, Node goal, Node[][] maze) {
		super(currentNode, goal, maze);

		traverse();
	}
	
	public void traverse() {
		dfs(node);
	}
	
	private void dfs(Node node){
		positions.add(node);
		
		if (!keepRunning) return;
		
		node.setVisited(true);	
		visitCount++;
		
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
				dfs(children[i]);
			}
		}
	}
}
