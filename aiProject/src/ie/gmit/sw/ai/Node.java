package ie.gmit.sw.ai;

import java.awt.Color;

//import java.awt.Color;



public class Node 
{
	private char state;
	public char getState() 
	{
		return state;
	}

	public void setState(char state) 
	{
		this.state = state;
	}

	private static final int MAX_EXITS = 4;
	public enum NodeType{Wall, Passage};
	public enum NodePassage{North, South, East, West, None};
	private Node parent;
	private Color color = Color.BLACK;
	private NodeType type = NodeType.Wall;
	private NodePassage passage = NodePassage.None;
	public boolean visited =  false;
	public boolean goal;
	private int row = -1;
	private int col = -1;
	private int distance;
	private boolean pathToGoal;
	//private char visitedByPlayer;
	private boolean visitedByPlayer = false;
	
	// enemy stuff
	private Enemy enemy;
	private boolean hasEnemy;

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public boolean isHasEnemy() {
		return hasEnemy;
	}

	public void setHasEnemy(boolean hasEnemy) {
		this.hasEnemy = hasEnemy;
	}

	public boolean isVisitedByPlayer() 
	{
		return visitedByPlayer;
	}

	public void setVisitedByPlayer(boolean visitedByPlayer) 
	{
		this.visitedByPlayer = visitedByPlayer;
	}

	public boolean isPathToGoal() 
	{
		return pathToGoal;
	}

	public void setPathToGoal(boolean pathToGoal) {
		this.pathToGoal = pathToGoal;
	}

	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Node[] children(Node[][] maze){
		
		Node[] children = new Node[MAX_EXITS];
		if (col - 1 >=0 && maze[row][col - 1].getState() != 'W') children[0] = maze[row][col - 1]; //A West edge
		if (col + 1 < maze[row].length && maze[row][col + 1].getState() != 'W') children[1] = maze[row][col + 1]; //An East Edge
		if (row - 1 >= 0 && maze[row-1][col].getState() != 'W') children[2] = maze[row - 1][col]; //A North edge
		if (row + 1 < maze.length && maze[row + 1][col].getState() != 'W') children[3] = maze[row + 1][col]; //An South Edge
	
		int counter = 0;
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) counter++;
		}
		
		Node[] tmp = new Node[counter];
		int index = 0;
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null){
				tmp[index] = children[i];
				index++;
			}
		}

		return tmp;
	}
	
	public NodeType getType() {
		return type;
	}
	
	public void setType(NodeType type) {
		this.type = type;
	}

	public NodePassage getPassage() {
		return passage;
	}

	public void setPassage(NodePassage passage) {
		this.passage = passage;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.LIGHT_GRAY;
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public String toString() 
	{
		if (passage == NodePassage.North)
		{
			return "N ";
		}
		else
		{
			return "W ";
		}
	}
}
