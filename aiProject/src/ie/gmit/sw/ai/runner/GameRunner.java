package ie.gmit.sw.ai.runner;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.searches.*;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class GameRunner implements KeyListener {
	private static final int MAZE_DIMENSION = 100;
	private Node[][] maze;
	private Node goal;
	private GameView view;
	private int currentRow;
	private int currentCol;
	private EnemyCtrl enemyCtrl;
	private Player player;
	private boolean gameover = false;
	
	private int bookLvl = 0;

	public GameRunner() throws Exception {

		// MazeGeneratorFactory factory = MazeGeneratorFactory.getInstance();
		// MazeGenerator generator =
		// factory.getMazeGenerator(MazeGenerator.GeneratorAlgorithm.RecursiveBacktracker,
		// 100, 100);

		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		// goal = model[10][10];

		maze = m.getMaze();
		view = new GameView(maze, goal);
		view.setBounds(99, 5, 800, 800);// Originally
		
		windowsInit();
		player = new Player();
		placePlayer();

		enemyCtrl = new EnemyCtrl(new Node(currentRow, currentCol), maze);
		enemyCtrl.createEnemies();
		
		this.goal = m.getGoalNode();
		Random random = new Random();
		bookLvl = random.nextInt(4);
	}

	public static void printPos(String prefix, Node printNode) {
		System.out.println(prefix + ",row: " + printNode.getRow() + ", col: " + printNode.getCol());
	}

	private void windowsInit() {
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
		view.setPreferredSize(d);
		view.setMinimumSize(d);
		view.setMaximumSize(d);

		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setBounds(10, 11, 85, 591);
		f.getContentPane().add(panel);
		f.getContentPane().add(view);
		f.setSize(1000, 1000);
		f.setLocation(100, 100);
		// f.pack();//Bad guy packs it all together
		f.setVisible(true);
		// f.add(header);

		// JPanel header = new JPanel();
		// header.setLocation(0,0);
		// header.setSize(1, 1);
		// header.setBackground(Color.white);
		// header.setVisible(true);
		// view.add(header);
	}

	private void placePlayer() {
		Random random = new Random();
		currentRow = random.nextInt(MAZE_DIMENSION - 4) + 2; // (int)
																// (MAZE_DIMENSION
																// *
																// Math.random());
		currentCol = random.nextInt(MAZE_DIMENSION - 4) + 2; // (int)
																// (MAZE_DIMENSION
																// *
																// Math.random());
		maze[currentRow][currentCol].setState('E');
		updateView();
	}

	// private void placeEnemies() {
	// // BruteForceTraversator bft = new BruteForceTraversator(true);
	// // Traversator t = new DepthLimitedDFSTraversator(model.length*3);
	// // Traversator rw = new RandomWalk();
	// Traversator rw = new IDDFSTraversator();
	// // Traversator t = new RecursiveDFSTraversator();
	// rw.traverse(maze, maze[5][5], view);
	// }

	private void updateView() {
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}

	public void keyPressed(KeyEvent e) {

		if (!gameover) {

			if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
				if (isValidMove(currentRow, currentCol + 1))
					currentCol++;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
				if (isValidMove(currentRow, currentCol - 1))
					currentCol--;
			} else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
				if (isValidMove(currentRow - 1, currentCol))
					currentRow--;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
				if (isValidMove(currentRow + 1, currentCol))
					currentRow++;
			} else if (e.getKeyCode() == KeyEvent.VK_Z) {
				view.toggleZoom();
			} else {
				return;
			}
		}

		updateView();

		fuzzyFight();
		
		checkTile();
	}
	
	private void checkTile(){
//		System.out.println("State: " + maze[currentRow][currentCol].getState());
		if (maze[currentRow][currentCol].isBookOnTile()) {
			Book book = new Book(new Node(currentRow, currentCol), goal, maze, bookLvl);
			bookLvl++;
			maze[currentRow][currentCol].setBookOnTile(false);
		}
	}

	private void fuzzyFight() {
		if (maze[currentRow][currentCol].isHasEnemy()) {
			// Load from 'FCL' file
			String fileName = "fcl/strenght.fcl";
			FIS fis = FIS.load(fileName, true);

			// Error while loading?
			if (fis == null) {
				System.err.println("Can't load file: '" + fileName + "'");
				return;
			}

			FunctionBlock functionBlock = fis.getFunctionBlock("outcome");
			// Show
			// JFuzzyChart.get().chart(functionBlock);//Prints Charts
			// Set inputs
			Random random = new Random();

			Enemy enemy = maze[currentRow][currentCol].getEnemy();

			// PLAYER SCORE
			fis.setVariable("health", player.getHealth() / 10);
			int luck = random.nextInt(10);
			fis.setVariable("luck", luck);
			// Evaluate
			fis.evaluate();
			// Show output variable's chart
			Variable outcome = functionBlock.getVariable("result");
			int damage = (int) outcome.getValue();
			System.out.println("Player dealt " + damage);
			enemy.decHealth(damage);
			// JFuzzyChart.get().chart(outcome, outcome.getDefuzzifier(),
			// true);// Prints last chart

			// ENEMY SCORE
			fis.setVariable("health", enemy.getHealth() / 10);
			luck = random.nextInt(10);
			fis.setVariable("luck", luck);
			// Evaluate
			fis.evaluate();
			// Show output variable's chart
			outcome = functionBlock.getVariable("result");
			damage = (int) outcome.getValue();
			System.out.println("Enemy dealt " + damage);
			player.decHealth(damage);

			// AFTER FIGHT
			System.out.println("Player health: " + player.getHealth());
			System.out.println("Enemy health: " + enemy.getHealth());
			
			if (player.getHealth() <= 0) {
				player.kill();
				gameover = true;
			}
			
			if (enemy.getHealth() <= 0) {
				enemy.kill();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	} // Ignore

	public void keyTyped(KeyEvent e) {
	} // Ignore

	private boolean isValidMove(int r, int c) {
		// Allows player to collect colectables
		if (r <= maze.length - 1 && c <= maze[r].length - 1 && maze[r][c].getState() == ' '
				|| maze[r][c].getState() == '?' || maze[r][c].getState() == 'B' || maze[r][c].getState() == 'H'
				|| maze[r][c].getState() == 'W'|| maze[r][c].getState() == 'G') {
			maze[currentRow][currentCol].setVisited(true);
			// System.out.println("Node set "+
			// model[currentRow][currentCol].isVisitedByPlayer());
			maze[currentRow][currentCol].setState(' ');
			maze[r][c].setState('E');

			return true;
		} else {
			return false; // Can't move
		}
	}

	public static void main(String[] args) throws Exception {
		new GameRunner();
	}
}