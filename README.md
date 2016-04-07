# A.I-MAze
Project can be started by double clicking on the Executable from within the folder as it contains libraries that executable referencing.

Or Project can be downloaded from GitHub and opened in Eclipse and then running the the GameRunner class.
##Enemy Algorithms
####Enemy.java 
######RandomWalk
######BruteForceTraversator DFS
######BruteForceTraversator BFS
######RecursiveDFSTraversator
######DepthLimitedDFSTraversator
######IDDFSTraversator
All of these algorithms are implemented each enemy using different algorithm.Black tile is wall green collectable and red is enemy.

<br>
<br>
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
All of these algorithms are implemented whenever you pick a book (help) random algorithm is picked and the path is painted marked with a star
<br>
<br>
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
####GameRunner.java
Fuzzy logic is implemented in GameRunner.java in following method displayed below fuzzyFight()
<br>
<br>
```java
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
```
##Threads
#### Enemy.java
Enemy extends TimerTask which is a Tread

