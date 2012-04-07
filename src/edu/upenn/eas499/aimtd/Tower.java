package edu.upenn.eas499.aimtd;

/**
 * A tower that shoots projectiles at passing monsters within range.
 * By default, towers lock onto Monsters until they leave the shooting range.
 * @author fedenusy
 *
 */
public abstract class Tower {
	
	///// Instance variables /////
	private int _x, _y;
	private int _fireDamage;
	private int _fireSpeed;
	private int _fireRange;
	
	
	///// Constructors /////
	/**
	 * @param x The tower's x-position.
	 * @param y The tower's y-position.
	 * @param fireDamage Damage from a single projectile, in terms of Monster hit points.
	 * @param firingRate Speed at which the tower fires. A speed of 100 means the tower can
	 * fire a single projectile within a call of Coordinator.tick(); a speed of 200 means the
	 * tower can fire off 2 projectiles within a single tick.
	 * @param fireRange The tower's firing radius.
	 */
	public Tower(int x, int y, int fireDamage, int firingRate, int fireRange) {
		_x = x;
		_y = y;
		_fireDamage = fireDamage;
		_fireSpeed = firingRate;
		_fireRange = fireRange;
	}
	
	
	///// Getter methods /////
	public int getFireDamage() { return _fireDamage; }
	public int getFireSpeed() { return _fireSpeed; }
	
	
	///// Public methods /////
	/**
	 * @param x The target's x position.
	 * @param y The target's y position.
	 * @return Whether this tower's projectiles reach the given coordinates.
	 */
	public boolean reaches(int x, int y) {
		return (_fireRange >= Math.sqrt((_x-x)^2-(_y-y)^2));
	}
	
}
