package entities;

import items.Weapon;

public class Pistol extends Weapon {
	
	static int myAnimationID;
	static int myFireRate = 1000;
	static int myDamage = 50;
	static int myRange = 500;
	static int myBulletSpeed = 500;
	static boolean isMelee = false;

	/**
	 * Constructor to initialze knife
	 * @param myAnimationID - pistol move animation ID
	 */
	public Pistol(int myAnimationID) {
		super(myAnimationID, myFireRate, myDamage, myRange, myBulletSpeed, isMelee);
		// TODO Auto-generated constructor stub
	}

}
