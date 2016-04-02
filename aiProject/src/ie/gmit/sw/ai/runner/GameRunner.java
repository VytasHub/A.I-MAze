package ie.gmit.sw.ai.runner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.searches.BruteForceTraversator;


public class GameRunner implements KeyListener
{
	private static final int MAZE_DIMENSION = 100;
	private Node[][] model;
	private Node goal;
	private GameView view;
	private int currentRow;
	private int currentCol;
	
	public GameRunner() throws Exception
	{
		//MazeGeneratorFactory factory = MazeGeneratorFactory.getInstance();
		//MazeGenerator generator = factory.getMazeGenerator(MazeGenerator.GeneratorAlgorithm.RecursiveBacktracker, 100, 100);
		
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		//goal = model[10][10];
		
		model = m.getMaze();
    	view = new GameView(model,goal);
    	
    	//Traversator t = new BruteForceTraversator();
    	//goal = model[10][10];
    	//BruteForceTraversator search = new BruteForceTraversator(true);
    	//search.traverse(model, model[5][5]);
    	
    	//search.traverse(model, model[5][5]);
    	
    	
    	placePlayer();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());//new FlowLayout()
        f.getContentPane().add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        //f.add(header);
        
        
        JPanel header = new JPanel();
    	header.setLocation(0,0);
    	header.setSize(1, 1);
    	header.setBackground(Color.white);
    	header.setVisible(true);
    	view.add(header);
    	
	}
	
	private void placePlayer()
	{   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol].setState('E');
    	updateView(); 		
	}
	
	private void updateView()
	{
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}

    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) 
        {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) 
        {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z)
        {
        	view.toggleZoom();
        }
        else
        {
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int r, int c)
	{
		//Allows player to collect colectables
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getState() == ' ' || model[r][c].getState() == '?'|| model[r][c].getState() == 'B'|| model[r][c].getState() == 'H'|| model[r][c].getState() == 'W'  )
		{
			model[currentRow][currentCol].setState(' ');
			model[r][c].setState('E');
			return true;
		}
		else
		{
			return false; //Can't move
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new GameRunner();
	}
}