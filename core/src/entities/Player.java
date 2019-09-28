package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import items.Weapon;
import main.AssetHandler;
import main.CodGame;
import main.SinCos;

public class Player extends Entity {
	public static final int PLAYERSIZE = 112;

	private int hp;
	private int ammo;

	public Weapon current;
	private Weapon myKnife;
	private Weapon myPistol;
	private Weapon myRifle;

	private int score;
	private long lastDamaged;
	private long damageDelay;

	private Vector2 velocity = new Vector2();
	float angle;

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
	 * Creates a Player object with parameters
	 * Uses super constructor from Entity class
	 * Initializes instance variables
	 */
	public Player(int animId, float x, float y, int width, int height) {
		super(animId, x, y, width, height);

		dif = 4;

		hp = 100;

		ammo = 1000000;

		myPistol = new Pistol(AssetHandler.createAnimation("pistolMove.png", 20, 0, 0, 75, 101));
		myKnife = new Knife(AssetHandler.createAnimation("knifeMove.png", 20, 0, 0, 75, 101));
		myRifle = new Rifle(AssetHandler.createAnimation("rifleMove.png", 20, 0, 0, 80, 101));
		myKnife.setBorder(border);
		
		current = myKnife;

		mySpeed = 270;

		score = 0;
		lastDamaged = 0;
		damageDelay = 600;
		updateBorder();
	}

	/**
	 * @param batch - object that is being drawn
	 * 
	 * SpriteBatch object is being drawn
	 */
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	/**
	 * @param deltaTime - speed of movement of character
	 * 
	 * Method to update movement of character
	 * 
	 * @return boolean value of if updated
	 */
	public boolean update(float delta) {
		
		float centerX = Gdx.graphics.getWidth() / 2;
		float centerY = Gdx.graphics.getHeight() / 2;
		
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY();
		
		Vector2 direction = new Vector2(mouseX - centerX, CodGame.HEIGHT - mouseY - centerY);
		direction.rotate90(-1);
		setRotation(direction.angle());

		if (Gdx.input.isKeyPressed(Keys.W)) {
			setY(getY() + mySpeed * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			setY(getY() - mySpeed * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			setX(getX() - mySpeed * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			setX(getX() + mySpeed * Gdx.graphics.getDeltaTime());
		}
		
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			current = myRifle;
			this.setAnimation(AssetHandler.getAnimation(current.getAnimationId()));
		}
		
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			current = myPistol;
			this.setAnimation(AssetHandler.getAnimation(current.getAnimationId()));
		}
		
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			current = myKnife;
			this.setAnimation(AssetHandler.getAnimation(current.getAnimationId()));
		}

		if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			updateAnim();
		}
		
		if (Gdx.input.isTouched()) {
			float tX = getX() + (getWidth() / 2);
			float tY = getY() + (getHeight() / 2);

			//tX -= SinCos.getSine((int) getRotation() + 20) * 50;
			//tY += SinCos.getCosine((int) getRotation() + 20) * 23;

			if (!current.isMeele()) {
				if (ammo > 0) {
					ammo--;
					current.shoot(tX, tY, getRotation());
				}
			} else {
				this.setAnimation(AssetHandler.getAnimation(myKnife.hitAnimation));
				updateAnim();
				current.shoot(tX, tY, getRotation());
			}
		}
		return false;
	}

	/**
	 * returns number of ammo left
	 * 
	 * @return ammo left
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * @param amount - added ammo
	 * 
	 * adds more ammo
	 */
	public void addAmmo(int amount) {
		ammo += amount;
	}

	/**
	 * returns amount of hp
	 * 
	 * @return amount of hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 
	 * @param amount - amount of damage
	 * 
	 * @param xDir - float of xDirection
	 * 
	 * @param yDir - float of yDirection
	 * causes damage to the to sprite object
	 */
	public void damage(int amount, float xDir, float yDir) {
		if ((System.nanoTime() - lastDamaged) / 1000000 > damageDelay) {
			lastDamaged = System.nanoTime();

			hp -= amount;
		}
	}

	/**
	 * checks is sprite is alive
	 * 
	 * @return boolean value for if alive
	 */
	public boolean alive() {
		return hp > 0;
	}

	/**
	 * 
	 * @param a - value added to score
	 * 
	 * adds value of a to the score
	 */
	public void addScore(int a) {
		score += a;
	}

	/**
	 * returns the score value
	 * 
	 * @return score value
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param w - Weapon to set to current
	 * 
	 * sets object w to instance variable current
	 */
	public void setWeapon(Weapon w) {
		current = w;
	}

	/**
	 * returns the current variable
	 * 
	 * @return current variable
	 */
	public Weapon getWeapon() {
		return current;
	}

	/**
	 * @param am - add am to hp
	 * 
	 * heals sprite character by increasing hp
	 */
	public void heal(int am) {
		hp += am;
	}

	/**
	 * checks if sprite is damaged
	 * 
	 * @return return boolean to check if player is damaged
	 */
	public boolean isDamaged() {
		return ((System.nanoTime() - lastDamaged) / 1000000 < damageDelay) ? true : false;
	}

	/**
	 * Disposes everything related to the texture of the sprite
	 */
	public void dispose() {
		super.dispose();
	}
}
