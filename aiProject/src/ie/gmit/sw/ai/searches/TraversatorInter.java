package ie.gmit.sw.ai.searches;

import java.awt.Component;

import ie.gmit.sw.ai.Node;

public interface TraversatorInter 
{
	public void traverse(Node[][] maze, Node start, Component viewer);
}
