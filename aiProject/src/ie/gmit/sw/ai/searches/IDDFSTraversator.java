package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;

public class IDDFSTraversator extends Traversator{
	private int limit;
	
	public IDDFSTraversator(Node currentNode, Node goal, Node[][] maze) {
		super(currentNode, goal, maze);
		this.limit = 1;
		
		traverse();
	}
	
	public void traverse() {
		while(keepRunning){
			dfs(node, 0, limit);
			
			if (keepRunning){
	      		limit++;       		
	      		unvisit();				
			}
      	}

	}

	private void dfs(Node node, int depth, int limit){
		positions.add(node);
		
		if (visitCount > maxPositions) {
			keepRunning = false;
		}
		
		if (!keepRunning || depth > limit) return;		
		node.setVisited(true);	
		visitCount++;
		//node.getState() == 'G'
		//node.isGoalNode()
		if (node.getState() == 'G'){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount);
	        keepRunning = false;
			return;
		}
		
		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i], depth + 1, limit);
			}
		}
	} 
		
	private void unvisit(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}
}