package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
public class GameView extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private static final int IMAGE_COUNT = 10; //If adding image need to increase by 1
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
	
	public GameView(Node[][] maze,Node goal) throws Exception
	{
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	
	public void setCurrentRow(int row) 
	{
		if (row < cellpadding)
		{
			currentRow = cellpadding;
		}
		else if (row > (maze.length - 1) - cellpadding)
		{
			currentRow = (maze.length - 1) - cellpadding;
		}
		else
		{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) 
	{
		if (col < cellpadding)
		{
			currentCol = cellpadding;
		}
		else if (col > (maze[currentRow].length - 1) - cellpadding)
		{
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}
		else
		{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) 
        {
        	for (int col = 0; col < cellspan; col++)
        	{  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = 'X';
       		
        		if (zoomOut)
        		{
        			ch = maze[row][col].getState();
        			if (row == currentRow && col == currentCol)
        			{
        				g2.setColor(Color.YELLOW);
        				g2.fillRect(x1, y1, size,size);
        				continue;
        			}
        			
        			
        		
        			
        			
        		}
        		else
        		{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getState();
        		}
        		
        		
        		if (ch == 'X')
        		{        			
        			imageIndex = 0;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.black);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'W')
        		{
        			imageIndex = 1;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.green);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        			
        		}
        		else if (ch == '?')
        		{
        			imageIndex = 2;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.green);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'B')
        		{
        			imageIndex = 3;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.green);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'H')
        		{
        			imageIndex = 4;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.green);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'I')//I for Ifrit
        		{
        			
        			
        			
        			imageIndex = 9;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.red);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == ' ')//Need to add state for empty tile if nothing do this
        		{
        			imageIndex = 7;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.gray);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'G')//G for Goal paints Goal Node black
        		{
        			imageIndex = 8;;
        			if(zoomOut)
        			{
        				g2.setColor(Color.white);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        				
        			}
        		}
        		else if (ch == 'E')
        		{
        			imageIndex = enemy_state;;       			
        		}else
        		{
        			imageIndex = -1;
        		}
        		
        		if (imageIndex >= 0)
        		{
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}
        		else
        		{
        			//imageIndex = 7;
        			g2.setColor(Color.LIGHT_GRAY);
        			g2.fillRect(x1, y1, size, size);
        		}      		
        	}
        }
	}
	
	public void toggleZoom()
	{
		zoomOut = !zoomOut;	
		
		
	}

	public void actionPerformed(ActionEvent e) 
	{	
		if (enemy_state < 0 || enemy_state == 5)
		{
			enemy_state = 6;
		}
		else
		{
			enemy_state = 5;
		}
		this.repaint();
	}
	
	private void init() throws Exception
	{
		//Add images here
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