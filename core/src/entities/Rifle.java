package entities;

import items.Weapon;

public class Rifle extends Weapon {

	static int myAnimationID;
	static int myFireRate = 130;
	static int myDamage = 10;
	static int myRange = 1000;
	static int myBulletSpeed = 1000;
	static boolean isMelee = false;

	/**
	 * Constructor to initialze knife
	 * @param myAnimationID - pistol move animation ID
	 */
	public Rifle(int myAnimationID) {
		super(myAnimationID, myFireRate, myDamage, myRange, myBulletSpeed, isMelee);
		// TODO Auto-generated constructor stub
	}
}
