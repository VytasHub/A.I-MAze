package ie.gmit.sw.ai;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

import ie.gmit.sw.ai.runner.GameRunner;

public class EnemyCtrl {
	private Node goalNode;
	private Node[][] globMaze;
	private LinkedList<Enemy> enemies;
	private Timer timer;

	public EnemyCtrl(Node goalNode, Node[][] globMaze) {
		enemies = new LinkedList<Enemy>();
		this.goalNode = goalNode;
		this.globMaze = globMaze;

		timer = new Timer();
	}

	public void createEnemy() {
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

		Node currentNode = new Node(row, col);
		
		GameRunner.printPos("EnemyCtrl", goalNode);
		Enemy enemy = new Enemy(currentNode, goalNode, globMaze);
		
		timer.schedule(enemy, enemy.getMoveDur(), enemy.getMoveDur());
	}

}
