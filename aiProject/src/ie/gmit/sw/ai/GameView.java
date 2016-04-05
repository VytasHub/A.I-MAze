package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.ai.runner.GameRunner;

public class GameView extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;
	private static final int IMAGE_COUNT = 10; // If adding image need to
												// increase by 1
	private int cellspan = 5;
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;

	public GameView(Node[][] maze, Node goal) throws Exception {
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}

	public void setCurrentRow(int row) {
		if (row < cellpadding) {
			currentRow = cellpadding;
		} else if (row > (maze.length - 1) - cellpadding) {
			currentRow = (maze.length - 1) - cellpadding;
		} else {
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding) {
			currentCol = cellpadding;
		} else if (col > (maze[currentRow].length - 1) - cellpadding) {
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		} else {
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		cellspan = zoomOut ? maze.length : 5;
		final int size = DEFAULT_VIEW_SIZE / cellspan;
		g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		for (int row = 0; row < cellspan; row++) {
			for (int col = 0; col < cellspan; col++) {
				int x1 = col * size;
				int y1 = row * size;

				char tileState = 'X';
				boolean tileHasEnemy = false;

				//
				// if (zoomOut) { // zoomed out
				// ch = maze[row][col].getState();
				// if (row == currentRow && col == currentCol) {
				// g2.setColor(Color.YELLOW);
				// g2.fillRect(x1, y1, size, size);
				//// continue;
				// }
				//
				// } else { // zoomed in
				// ch = maze[currentRow - cellpadding + row][currentCol -
				// cellpadding + col].getState();
				// } // zoomed in else

				// if zoomed out
				if (zoomOut) {
					tileState = maze[row][col].getState();
					tileHasEnemy = maze[row][col].isHasEnemy();
				}
				// if zoomed in
				else {
					tileState = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getState();
					tileHasEnemy = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].isHasEnemy();
				}

				// if (maze[row][col].isHasEnemy() && zoomOut) { // I for Ifrit
				//
				// GameRunner.printPos("Has Enemy on: ", maze[row][col]);
				//
				// if (zoomOut) {
				//
				// // return;
				// }
				// } else if (
				// && !zoomOut) {
				// // GameRunner.printPos("Has Enemy on: ", maze[currentRow -
				// // cellpadding + row][currentCol - cellpadding + col]);
				// imageIndex = 9;
				// }

				// tiles
				if (tileHasEnemy) {
					if (zoomOut) {
//						GameRunner.printPos("Enemy Zoomed out: ", new Node(row, col));
						g2.setColor(Color.RED);
						g2.fillRect(x1, y1, size, size);
					}else{
						imageIndex = 9;
//						GameRunner.printPos("REGULAR VIEW: ", new Node(row, col));
					}
				} else if (row == currentRow && col == currentCol) { // character
																		// (enemy)
					if (zoomOut) {
						g2.setColor(Color.BLUE);
						g2.fillRect(x1, y1, size, size);
					}

				} else if (tileState == 'X') { // wall (which is hedge)
					

					if (zoomOut) {
						g2.setColor(Color.black);
						g2.fillRect(x1, y1, size, size);
						// continue;
					} else{
						imageIndex = 0;
					}
				} else if (tileState == 'W') {
					

					if (zoomOut) {
						g2.setColor(Color.green);
						g2.fillRect(x1, y1, size, size);
						// continue;

					} else{
						imageIndex = 1;
					}

				}
				// else if (maze[row][col].isPathToGoal() == true)
				// {
				// //imageIndex = 9;;
				// if(zoomOut)
				// {
				// g2.setColor(Color.pink);
				// g2.fillRect(x1, y1, size, size);
				// continue;
				//
				// }
				//
				// }

				else if (tileState == '?') {
					imageIndex = 2;

					if (zoomOut) {
						g2.setColor(Color.green);
						g2.fillRect(x1, y1, size, size);
						// continue;

					}
				} else if (tileState == 'B') {
					imageIndex = 3;

					if (zoomOut) {
						g2.setColor(Color.green);
						g2.fillRect(x1, y1, size, size);
						// continue;

					}
				} else if (tileState == 'H') {
					imageIndex = 4;

					if (zoomOut) {
						g2.setColor(Color.green);
						g2.fillRect(x1, y1, size, size);
						// continue;

					}
				}
				// else if (maze[currentRow][currentCol].isVisited())
				// {
				// //imageIndex = 9;;
				// if(zoomOut)
				// {
				// g2.setColor(Color.ORANGE);
				// g2.fillRect(x1, y1, size, size);
				// continue;
				//
				// }
				// }
				// model[currentRow][currentCol].setVisitedByPlayer('V');

				// else if (ch == 'I')// I for Ifrit
				// {
				// // TODO: remove
				// imageIndex = 9;
				//
				// if (zoomOut) {
				// g2.setColor(Color.red);
				// g2.fillRect(x1, y1, size, size);
				// continue;
				// }

				// if (maze[row][col].isHasEnemy()) {
				//
				// }
				// }
				else if (tileState == ' ')// Need to add state for empty tile if
											// nothing do this
				{
					imageIndex = 7;

					if (zoomOut) {
						g2.setColor(Color.gray);
						g2.fillRect(x1, y1, size, size);
						// continue;

					}
				} else if (tileState == 'G')// G for Goal paints Goal Node black
				{
					imageIndex = 8;
					if (zoomOut) {
						g2.setColor(Color.white);
						g2.fillRect(x1, y1, size, size);
						// continue;
					}
				} else if (tileState == 'E') {
					imageIndex = enemy_state;
				} else {
					imageIndex = -1;
				}

				if (!zoomOut) {
					paintTile(g2, size, x1, y1);
				}

//				paintEnemy(g2, size, row, col, x1, y1);
			} // for col
		} // for row
	} // class

	// private void paintEnemy(Graphics2D g2, final int size, int row, int col,
	// int x1, int y1) {
	// // enemy stuff
	// if (zoomOut) {
	// if (maze[row][col].isHasEnemy()) {
	// g2.setColor(Color.RED);
	// g2.fillRect(x1, y1, size, size);
	// }
	// }else{
	// if (maze[currentRow - cellpadding + row][currentCol - cellpadding +
	// col].isHasEnemy()) {
	// imageIndex = 9;
	// }
	// }
	// }

	private void paintTile(Graphics2D g2, final int size, int x1, int y1) {
		if (imageIndex >= 0) { // zoomed in
			g2.drawImage(images[imageIndex], x1, y1, null);
		} else { // zoomed out
			// imageIndex = 7;
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(x1, y1, size, size);
		}
	}

	public void toggleZoom() {
		zoomOut = !zoomOut;

	}

	public void actionPerformed(ActionEvent e) {
		if (enemy_state < 0 || enemy_state == 5) {
			enemy_state = 6;
		} else {
			enemy_state = 5;
		}
		this.repaint();
	}

	private void init() throws Exception {
		// Add images here
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/hedge.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));
		images[2] = ImageIO.read(new java.io.File("resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("resources/empty.png"));
		images[8] = ImageIO.read(new java.io.File("resources/goal.png"));
		images[9] = ImageIO.read(new java.io.File("resources/Ifrit.png"));
	}
}