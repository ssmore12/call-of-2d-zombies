package entities;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import main.CodGame;
import main.SinCos;
import map.TileMap;

public class Zombie extends Entity{
	int myDamage;
	Vector2 myObjective;
	long myLastDamaged;
	int[] placesX = new int[200];
	int[] placesY = new int[200];
	
	float myCosine;
	float mySine;
	TileMap myMap;
	float myHP;
	public static int myAnimationID;
	boolean chaseObj;
	
	/**
	 * creates Zombie with Entity constructer
	 */
	public Zombie() {
		super(myAnimationID, 0, 0, 60, 60);
	}
	
	/**
	 * @param aminId - index of animation
	 * 
	 * @param x - value of x coordinate
	 * 
	 * @param y - value of y coordinate
	 * 
	 * @param w - value of the width of the plane
	 * 
	 * @param h - value of the height of the plane
	 * 
	 * Creates a Zombie object with parameters
	 * Uses super constructer from Entity class
	 * Initializes instance variables
	 */
	public Zombie(int x, int y, int width, int height) {
		super(myAnimationID, x, y, width, height);
		dif = 4;
		updateBorder();
		myObjective = new Vector2(0, 0);
		myDamage = 1;
		mySpeed = 150;
		myHP = 100;
		myLastDamaged = 0;
		chaseObj = true;
		
		for (int i = 0; i <= 99; i++) {
			placesX[i] = i - 99;
			placesY[i] = i - 99;
		}
		
		for (int i = 100; i <= 199; i++) {
			placesX[i] = i + CodGame.WIDTH - 100;
			placesY[i] = i + CodGame.HEIGHT - 100;
		}
	}
	
	/**
	 * initializes Zombie variables
	 */
	public void init(TileMap m) {
		dif = 4;
		updateBorder();
		myObjective = new Vector2(0, 0);
		myDamage = 1;
		mySpeed = 150;
		myHP = 100;
		myMap = m;
		myLastDamaged = 0;
		float x;
		float y;
		
		Random rand = new Random();
		do {
			x = rand.nextInt(myMap.getJ() - 2) * 60;
			y = rand.nextInt(myMap.getI() - 2) * -60;
		} while (myMap.getTileOf(x, y) == null || myMap.getTileOf(x, y).isObstacle());

		setX(x);
		setY(y);
	}
	
	/**
	 * @param deltaTime - speed of movement of character
	 * 
	 * Method to update movement of character
	 * 
	 * @return boolean value of if updated
	 */
	public boolean update(float deltaTime) {
		myDelta.x = 0;
		myDelta.y = 0;
		
		if (this.myHP <= 0)
			return false;

		if (myObjective != null && chaseObj) {
			
			Vector2 dir = new Vector2(myObjective.x - getX(), myObjective.y - getY());
			dir.rotate90(-1);
			
			this.setRotation(dir.angle());
			
			mySine = SinCos.getSine((int)getRotation());
			myCosine = SinCos.getCosine((int)getRotation());
			
			
			myDelta.x = -mySine * mySpeed * deltaTime;
			myDelta.y = myCosine * mySpeed * deltaTime;
			
			setX(getX() + myDelta.x);
			setY(getY() + myDelta.y);
		}
		
		if(myDelta.x != 0 || myDelta.y != 0)
			updateAnim();
		
		updateBorder();
		
		return true;
	}
	
	/**
	 * @param x - value of x
	 * 
	 * @param y - value of y
	 * 
	 * set variables of Vector2 object x and y to the parameters
	 */
	public void setObjective(float x, float y) {
		if (myMap != null) {
			myObjective.x = x;
			myObjective.y = y;
		}
	}
	
	/**
	 * @param obj - Vector2 object
	 * 
	 * set instance variable to obj
	 */
	public void setObjective(Vector2 obj) {
		if (obj != null)
			setObjective(obj.x, obj.y);
		else 
			myObjective = obj;
	}
	
	/**
	 * @param m - TileMap object
	 * 
	 * set the map instance variable to the paramter
	 */
	public void setMap(TileMap m) {
		myMap = m;
	}
	
	/**
	 * @param d - damage caused to object
	 * 
	 * updates hp of character after damage
	 */
	public void damage(int d) {
		float dTime = Gdx.graphics.getDeltaTime();
		if ((System.nanoTime() - myLastDamaged) / 1000000 > 200)
		{
			myLastDamaged = System.nanoTime();
		}
		this.myHP -= d;
	}

	/**
	 * returns myDamage instance variable
	 * 
	 * @return myDamage
	 */
	public int getDamage() {
		return myDamage;
	}
	
	/**
	 * checks if character is alive
	 * 
	 * @return boolean to check character alive or not
	 */
	public boolean alive() {
		return myHP > 0;
	}
	
	/**
	 * returns cosine varaible
	 * 
	 * @return cosine
	 */
	public float getCosine() {
		return myCosine;
	}
	
	/**
	 * returns sine variable
	 * 
	 * @return sine
	 */
	public float getSine() {
		return mySine;
	}
	/**
	 * makes the zombie stop chasing player
	 */
	public void forceStop() {
		chaseObj = false;
	}
	/**
	 * makes the zombie start chasing player
	 */
	public void forceStart() {
		chaseObj = true;
	}
}
