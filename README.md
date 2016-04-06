# A.I-MAze

##Enemy Algorithms
####Enemy.java 
######RandomWalk
######BruteForceTraversator DFS
######BruteForceTraversator BFS
######RecursiveDFSTraversator
######DepthLimitedDFSTraversator
######IDDFSTraversator
All of these algorithms are implemented each enemy using different algorithm
![](https://github.com/VytasHub/A.I-MAze/blob/master/aiProject/pictures/enemys.png)
```java
private void createTraversator(){
		switch (evilLvl) {
		case 0:	// random walk
			traversator = new RandomWalk(currentNode, goalNode, globMaze);
			System.out.println("Created Random Walk Demon");
			break;
			
		case 1:	// brute DFS
			traversator = new BruteForceTraversator(currentNode, goalNode, globMaze, true);
			System.out.println("Created Brute Force DFS Demon");
			break;
			
		case 2:	// brute BFS
			traversator = new BruteForceTraversator(currentNode, goalNode, globMaze, false);
			System.out.println("Created Brute Force BFS Demon");
			break;
			
		case 3:	// recursive DFS
			traversator = new RecursiveDFSTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created Recursive DFS Demon");
			break;
			
		case 4:	// depth limited DFS
			traversator = new DepthLimitedDFSTraversator(currentNode, goalNode, globMaze, 5);
			System.out.println("Created Depth Limited DFS Demon");
			break;
			
		case 5:// Iterative deepening DFS
			traversator = new IDDFSTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created IDDFS DFS Demon");
			break;

		default:
			break;
		}
	}
```
##Path Algorithms
####Book.java
######BasicHillClimbingTraversator
######SteepestAscentHillClimbingTraversator
######BestFirstTraversator
######BeamTraversator
All of these algorithms are implemented whenever you pick a book (help) random algorithm is picked and the path is painted.
![](https://github.com/VytasHub/A.I-MAze/blob/master/aiProject/pictures/path.png)
```java
private void createTraversator(){
		switch (bookLvl) {
		case 0:	// random walk
			traversator = new BasicHillClimbingTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created Basic Hill Climber Book");
			break;
			
		case 1:
			traversator = new SteepestAscentHillClimbingTraversator(currentNode, goalNode, globMaze);
			System.out.println("CreatedSteepestAscentHillClimbingTraversator Book");
			break;
			
		case 2:
			traversator = new BestFirstTraversator(currentNode, goalNode, globMaze);
			System.out.println("Created BestFirstTraversator Book");
			break;
			
		case 3:
			traversator = new BeamTraversator(currentNode, goalNode, globMaze, 2);
			System.out.println("Created BeamTraversator Book");
			break;

		default:
			System.out.println("Algo error.");
			break;
		}
	}
```
##Fuzzy Logic
####fcl/strength.fcl
##Threads
#### Enemy.java
Enemy extends TimerTask which is a Tread

