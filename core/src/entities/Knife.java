package entities;

import com.badlogic.gdx.graphics.g2d.Animation;

import items.Weapon;
import main.AssetHandler;

public class Knife extends Weapon {
	
	static int myAnimationID;
	static int myFireRate = 30;
	static int myDamage = 75;
	static int myRange = 50;
	static int myBulletSpeed = 0;
	static boolean isMelee = true;
	
	/**
	 * Constructor to initialze knife and its hit animation
	 * @param myAnimationID - knife move animation ID
	 */
	public Knife(int myAnimationID) {
		super(myAnimationID, myFireRate, myDamage, myRange, myBulletSpeed, isMelee);
		// TODO Auto-generated constructor stub
		
		hitAnimation = AssetHandler.createAnimation("knifeHit.png", 15, 0, 0, 110, 100);
	}

}
