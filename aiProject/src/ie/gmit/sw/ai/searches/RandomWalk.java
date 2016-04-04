package ie.gmit.sw.ai.searches;

import java.awt.Component;

import ie.gmit.sw.ai.*;

public class RandomWalk implements Traversator
{
	public void traverse(Node[][] maze, Node node, Component viewer) 
	{
		
	
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(maze.length, 2) * 5;//Original was 2  but wouldn't always find the goal node so increased number of steps
		System.out.println("Number of steps allowed: " + steps);
	
		System.out.println("visitCount " + visitCount);
		System.out.println("steps " + steps);
		
		boolean complete = false;
		while(visitCount <= steps && node != null)
		{	
			
		
			node.setVisited(true);	
			
			visitCount++;
			
			if (visitCount % 10 == 0) viewer.repaint();
			//node.isGoalNode()
			//node.getState() == 'G'
			if (node.getState() == 'G')
			{
				System.out.println("Goal");
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
		        viewer.repaint();
		        complete = true;
				break;
			}
			
//			try 
//			{ //Simulate processing each expanded node
//			Thread.sleep(1);
//			} 
//			catch (InterruptedException e) 
//			{
//				e.printStackTrace();
//			}
			
			//Pick a random adjacent node
			
        	Node[] children = node.children(maze);
        	node = children[(int)(children.length * Math.random())];
        	
        	
		}
		System.out.println("While Ends");
		
		if (!complete) System.out.println("*** Out of steps....");
	}

	
}

