package ie.gmit.sw.ai.searches;

import java.util.LinkedList;

import ie.gmit.sw.ai.Node;

public abstract class TraversatorSuper {
	protected Node node;
	protected Node goal;
	protected Node[][] maze;
	protected LinkedList<Node> positions;

	public TraversatorSuper(Node currentNode, Node goal, Node[][] maze) {
		this.node = new Node(currentNode.getRow(), currentNode.getCol());
		this.node = new Node(goal.getRow(), goal.getCol());
		this.maze = new Node[maze.length][maze[0].length];
		cloneMaze(maze);
		positions = new LinkedList<Node>();
	}

	private void cloneMaze(Node[][] maze) {
		init();
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze.length; col++) {
				this.maze[row][col].setState(maze[row][col].getState());
			}
		}
	}
	
	private void init() {
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				this.maze[row][col] = new Node(row, col);
				this.maze[row][col].setState('X');
			}
		}
	}

	protected LinkedList<Node> getPositions() {
		return positions;
	}

	protected Node getPosition() {
		return positions.pop();
	}

	public abstract void traverse();

}
