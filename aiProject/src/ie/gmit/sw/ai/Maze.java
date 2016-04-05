package ie.gmit.sw.ai;

import java.util.Random;

import ie.gmit.sw.ai.Node;

public class Maze {
	private Node goal;
	private Node[][] maze;
	private Random random;

	public Maze(int rows, int cols) {
		maze = new Node[rows][cols];
		init();
		buildMaze();
		setGoalNode();
		addWallCage();
		
		random = new Random();

		int featureNumber = (int) ((rows * cols) * 0.01);
		addFeature('W', 'X', featureNumber);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
//		addFeature('I', 'X', featureNumber);
	}

	private void init() {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				maze[row][col] = new Node(row, col);
				maze[row][col].setState('X');
			}
		}
	}
	
	private void addWallCage(){
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				if (row < 2 || row >= maze.length - 2) {
					maze[row][col].setState('X');
				}
				
				if (col < 2 || col >= maze.length - 2) {
					maze[row][col].setState('X');
				}
			}
		}
	}

	private void addFeature(char feature, char replace, int number) {
		int counter = 0;
		while (counter < feature) {
//			int row = (int) (maze.length  * Math.random());
//			int col = (int) (maze[0].length * Math.random());
			
			int row = random.nextInt(maze.length - 4) + 2;
			int col =random.nextInt(maze[0].length - 4) + 2;

			if (maze[row][col].getState() == replace) {
				maze[row][col].setState(feature);
				counter++;
			}
		}
	}

	private void buildMaze() {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length - 1; col++) {
				int num = (int) (Math.random() * 10);
				if (num >= 5 && col + 1 < maze[row].length - 1) {
					maze[row][col + 1].setState(' ');
					continue;
				}
				if (row + 1 < maze.length) { // Check south
					maze[row + 1][col].setState(' ');
				}
			}
		}
	}

	public void setGoalNode() {
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length - 4) + 2;
		int randCol = generator.nextInt(maze[0].length - 4) + 2;
		
		maze[randRow][randCol].setGoalNode(true);
		goal = maze[randRow][randCol];
		maze[randRow][randCol].setState('G');

	}

	public Node getGoalNode() {
		return goal;
	}

	public Node[][] getMaze() {
		return this.maze;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1)
					sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}