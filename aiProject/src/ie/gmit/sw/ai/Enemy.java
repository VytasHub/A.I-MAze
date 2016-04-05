package ie.gmit.sw.ai;

import java.util.Random;
import java.util.TimerTask;

import ie.gmit.sw.ai.runner.GameRunner;
import ie.gmit.sw.ai.searches.*;

public class Enemy extends TimerTask {
	private Node currentNode;
	private Node newNode;
	private Node goalNode;
	private Node[][] globMaze;

	private Traversator traversator;

	private double health;
	private int evilLvl = 0;
	public static int MAX_EVIL = 6;

	private int moveDur;

	public int getMoveDur() {
		return moveDur;
	}

	
	
	public int getEvilLvl() {
		return evilLvl;
	}



	public void setEvilLvl(int evilLvl) {
		this.evilLvl = evilLvl;
	}



	public Enemy(Node currentNode, Node goalNode, Node[][] globMaze, int evilLvl) {
		super();
		this.currentNode = currentNode;
		this.goalNode = goalNode;
		this.globMaze = globMaze;
		this.evilLvl = evilLvl;

		moveDur = 666;
//		GameRunner.printPos("Enemy", goalNode);
		createTraversator();
		
//		traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
//		traversator = new BeamTraversator(currentNode,goalNode, globMaze, 2);
//		traversator = new BasicHillClimbingTraversator(currentNode, goalNode), globMaze);
//		traversator = new BruteForceTraversator(currentNode, goalNode, globMaze, false);	// DFS on
		// generate positions
	}
	
	private void createTraversator(){
		switch (evilLvl) {
		case 0:	// random walk
			traversator = new RandomWalk(currentNode, goalNode, globMaze);
			System.out.println("Created Random Walk Demon");
			break;
			
		case 1:	// brute DFS
			traversator = new BruteForceTraversator(currentNode, goalNode, globMaze, true);
			System.out.println("Created Brute Force DFS Demon");
			break;
			
		case 2:	// brute BFS
			traversator = new BruteForceTraversator(currentNode, goalNode, globMaze, false);
			System.out.println("Created Brute Force BFS Demon");
			break;
			
		case 3:	// recursive DFS
			traversator = new RecursiveDFSTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created Recursive DFS Demon");
			break;
			
		case 4:	// depth limited DFS
			traversator = new DepthLimitedDFSTraversator(currentNode, goalNode, globMaze, 5);
			System.out.println("Created Depth Limited DFS Demon");
			break;
			
		case 5:// Iterative deepening DFS
			traversator = new IDDFSTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created IDDFS DFS Demon");
			break;

		default:
			break;
		}
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

//			System.out.println("New Pos: row: " + currentNode.getRow() + ",col: " + currentNode.getCol());
		} else {
			System.out.println("No positions to pop.");
			createTraversator();
		}
	}
	
	private Node randPos(){
		Random random = new Random();
		boolean foundPos = false;
		int row = 0;
		int col = 0;

		do {
			row = random.nextInt(globMaze.length - 4) + 2;
			col = random.nextInt(globMaze.length - 4) + 2;

			if (globMaze[row][col].getState() != 'W') {
				foundPos = true;
			}
		} while (!foundPos);

		return new Node(row, col);
	}

	@Override
	public void run() {
		move();
	}

}
