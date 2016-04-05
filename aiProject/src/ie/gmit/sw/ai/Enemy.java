package ie.gmit.sw.ai;

import java.util.TimerTask;

import ie.gmit.sw.ai.runner.GameRunner;
import ie.gmit.sw.ai.searches.*;

public class Enemy extends TimerTask {
	private Node currentNode;
	private Node newNode;
	private Node goalNode;
	private Node[][] globMaze;

	private TraversatorSuper traversator;

	private double health;

	private int moveDur;

	public int getMoveDur() {
		return moveDur;
	}

	public Enemy(Node currentNode, Node goalNode, Node[][] globMaze) {
		super();
		this.currentNode = currentNode;
		this.goalNode = goalNode;
		this.globMaze = globMaze;

		moveDur = 500;
		GameRunner.printPos("Enemy", goalNode);
		traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
		// generate positions
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public void move() {
		// get new node
		newNode = traversator.getPosition();
		if (newNode != null) {
			// set current node to new node
			globMaze[currentNode.getRow()][currentNode.getCol()].setHasEnemy(false);
			globMaze[currentNode.getRow()][currentNode.getCol()].setEnemy(null);

			globMaze[newNode.getRow()][newNode.getCol()].setHasEnemy(true);
			globMaze[newNode.getRow()][newNode.getCol()].setEnemy(this);

			// set new node to blank
			currentNode = newNode;
			newNode = null;

			System.out.println("New Pos: row: " + currentNode.getRow() + ",col: " + currentNode.getCol());
		} else {
			System.out.println("No positions to pop.");
			traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
		}
	}

	@Override
	public void run() {
		move();
	}

}
