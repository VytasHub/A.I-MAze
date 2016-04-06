package ie.gmit.sw.ai;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

import ie.gmit.sw.ai.runner.GameRunner;
import ie.gmit.sw.ai.searches.*;

public class Book  {
	private Node currentNode;
	private Node newNode;
	private Node goalNode;
	private Node[][] globMaze;
//	private LinkedList<Node> positions;

	private Traversator traversator;

	private double health;
	private int bookLvl = 0;
	public static int MAX_BOOK_LVL = 4;
	private int pathLen = 50;


	public Book(Node currentNode, Node goalNode, Node[][] globMaze, int bookLvl) {
		super();
//		Random random = new Random();
		
		this.currentNode = currentNode;
		this.goalNode = goalNode;
		this.globMaze = globMaze;
		this.bookLvl = bookLvl % MAX_BOOK_LVL; //random.nextInt(MAX_BOOK_LVL);
		
		createTraversator();
//		positions = traversator.getPositions();
		pathPainter();
	}
	
	public void pathPainter(){
		for (int i = 0; i < pathLen; i++) {
			Node pathNode = traversator.getPosition();
//			globMaze[pathNode.getRow()][pathNode.getCol()].setBook(this);
			if (pathNode != null) {
				globMaze[pathNode.getRow()][pathNode.getCol()].setHasPath(true);
			}
			else{
				break;
			}
			
		}
	}
	
	private void createTraversator(){
		switch (bookLvl) {
		case 0:	// random walk
			traversator = new BasicHillClimbingTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created Basic Hill Climber Book");
			break;
			
		case 1:
			traversator = new SteepestAscentHillClimbingTraversator(currentNode, goalNode, globMaze);
			System.out.println("CreatedSteepestAscentHillClimbingTraversator Book");
			break;
			
		case 2:
			traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created BestFirstTraversator Book");
			break;
			
		case 3:
			traversator = new BeamTraversator(currentNode, goalNode, globMaze, 2);
			System.out.println("Created BeamTraversator Book");
			break;

		default:
			System.out.println("Algo error.");
			break;
		}
	}

//	public void move() {
//		// get new node
//		newNode = traversator.getPosition();
//		if (newNode != null) {
//			// set current node to new node
//			globMaze[currentNode.getRow()][currentNode.getCol()].setHasEnemy(false);
//			globMaze[currentNode.getRow()][currentNode.getCol()].setEnemy(null);
//
//			globMaze[newNode.getRow()][newNode.getCol()].setHasEnemy(true);
//			globMaze[newNode.getRow()][newNode.getCol()].setEnemy(this);
//
//			// set new node to blank
//			currentNode = newNode;
//			newNode = null;
//
////			System.out.println("New Pos: row: " + currentNode.getRow() + ",col: " + currentNode.getCol());
//		} else {
//			System.out.println("No positions to pop.");
//			createTraversator();
//		}
//	}
	
//	private Node randPos(){
//		Random random = new Random();
//		boolean foundPos = false;
//		int row = 0;
//		int col = 0;
//
//		do {
//			row = random.nextInt(globMaze.length - 4) + 2;
//			col = random.nextInt(globMaze.length - 4) + 2;
//
//			if (globMaze[row][col].getState() != 'W') {
//				foundPos = true;
//			}
//		} while (!foundPos);
//
//		return new Node(row, col);
//	}

}
