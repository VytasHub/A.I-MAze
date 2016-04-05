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
	private int enemyNum;

	public EnemyCtrl(Node goalNode, Node[][] globMaze) {
		enemies = new LinkedList<Enemy>();
		this.goalNode = goalNode;
		this.globMaze = globMaze;

		timer = new Timer();
		
		enemyNum = 10; // MAZE_DIM / 4
	}
	
	public void createEnemies(){
		
		for (int i = 0; i < enemyNum; i++) {
			enemies.add(new Enemy(randPos(), randPos(), globMaze, i % Enemy.MAX_EVIL));
		}
		
		for (Enemy enemy : enemies) {
			timer.schedule(enemy, enemy.getMoveDur(), enemy.getMoveDur());
		}
	}
	

//	public void createEnemy() {
//		Node currentNode = randPos();
//		
//		GameRunner.printPos("EnemyCtrl", goalNode);
//		Enemy enemy = new Enemy(currentNode, goalNode, globMaze);
//		
//		timer.schedule(enemy, enemy.getMoveDur(), enemy.getMoveDur());
//	}
	
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

}
