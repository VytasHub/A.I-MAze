package ie.gmit.sw.ai;

public class Player {
	private int health;

	public Player() {
		health = 100;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void incHealth(int incAmount) {
		health += incAmount;
	}
	
	public void decHealth(int decAmount) {
		health -= decAmount;
	}
	
	public void kill(){
		System.out.println("GAME OVER");
	}
}
