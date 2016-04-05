package ie.gmit.sw.ai;

import ie.gmit.sw.ai.searches.*;

public class Enemy extends Thread {
	private Node currentNode;
	private Node goalNode;
	private Node[][] globMaze;
	
	private TraversatorSuper traversator;
	
	private double health;
	
	public Enemy(Node currentNode, Node goalNode, Node[][] globMaze) {
		super();
		this.currentNode = currentNode;
		this.goalNode = goalNode;
		this.globMaze = globMaze;
		
		traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	@Override
	public void run() {
		super.run();
		// generate positions
		traversator.traverse();
	}
	
}
