package ie.gmit.sw.ai.searches;

import ie.gmit.sw.ai.Node;

import java.awt.Component;
public class IDDFSTraversator implements Traversator{
	private Node[][] maze;
	private Component viewer;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
	public void traverse(Node[][] maze, Node start, Component viewer) {
		this.maze = maze;
		this.viewer = viewer;
		int limit = 1;
		
		while(keepRunning){
			dfs(start, 0, limit);
			
			if (keepRunning){
	      		limit++;       		
	      		unvisit();				
			}
      	}

	}

	private void dfs(Node node, int depth, int limit){
		if (!keepRunning || depth > limit) return;		
		node.setVisited(true);	
		visitCount++;
		viewer.repaint();
		
		if (node.isGoalNode()){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount);
	        viewer.repaint();
	        keepRunning = false;
			return;
		}
		
		try { //Simulate processing each expanded node
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
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