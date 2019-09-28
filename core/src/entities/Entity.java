package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import main.AssetHandler;
import main.CodGame;
import map.Tile;
import map.TileMap;

public abstract class Entity extends Sprite implements Disposable {

	private Animation myAnimation;

	private float sTime;

	protected float dif = 4;

	protected Rectangle border;

	protected Vector2 myDelta;

	protected float mySpeed;
	
	/**
	 * Creates a Entity object with no parameters or implementation
	 */
	public Entity() {
		
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
	 * Creates a Entity object with parameters
	 * Sets origin in the middle of the plane and of x and y
	 * Initializes instance variables
	 */
	public Entity(int animId, float x, float y, int w, int h) {
		setAnimation(AssetHandler.getAnimation(animId));

		setOrigin(w / 2, h / 2);

		setX(x);
		setY(y);

		border = new Rectangle();

		sTime = 0f;

		myDelta = new Vector2(0, 0);

		mySpeed = 0;
	}

	/**
	 * Updates the variable sTime with the change in time from current frame to last frame in seconds
	 */
	public void updateAnim() {
		sTime += Gdx.graphics.getDeltaTime();
	}

	/**
	 * Return width of the region of the animation
	 * 
	 * @return width of region
	 */
	public float getWidth() {
		return ((TextureRegion) myAnimation.getKeyFrame(sTime)).getRegionWidth();
	}

	/**
	 * Return length of the region of the animation
	 * 
	 * @return height of region
	 */
	public float getHeight() {
		return ((TextureRegion) myAnimation.getKeyFrame(sTime)).getRegionHeight();
	}

	/**
	 * Return the Rectangle
	 * 
	 * @return instance varaible border
	 */
	public Rectangle getBorder() {
		return border;
	}

	/**
	 * @param deltaTime - speed of movement of character
	 * 
	 * Abstract method to update movement of character
	 * 
	 * @return boolean value of if updated
	 */
	public abstract boolean update(float deltaTime);

	/**
	 * @param batch - object that is being drawn
	 * 
	 * SpriteBatch object is being drawn
	 */
	public void draw(SpriteBatch batch) {
		batch.draw(getCurrentFrame(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1f, 1f,
				getRotation());
	}

	/**
	 * Returning the current frame of the animated object
	 * 
	 * @return current frame of object
	 */
	public TextureRegion getCurrentFrame() {
		return (TextureRegion) myAnimation.getKeyFrame(sTime, true);
	}

	/**
	 * Updating the border of the rectangle with the value of dif
	 */
	protected void updateBorder() {
		border.x = getX() + dif;
		border.y = getY() + dif;

		border.width = getWidth() - dif * 2;
		border.height = getWidth() - dif * 2;
	}
	
	/**
	 * @param  r - rectangle that is being checked for overlapping with the instance 
	 * 			   rectangle
	 * 
	 * After updating the border, this method checks for if there are two rectangles overlapping one another 
	 * and returns true or false
	 * 
	 * @return boolean value for overlapping
	 */
	public boolean isOverlaping(Rectangle r) {

		boolean overlapping = true;

		updateBorder();

		if ((int) border.y + border.height <= r.y) {
			overlapping = false;
		}
		if ((int) border.y >= r.y + r.height) {
			overlapping = false;
		}
		if ((int) border.x + border.width <= r.x) {
			overlapping = false;
		}
		if ((int) border.x >= (r.x + r.width)) {
			overlapping = false;
		}

		return overlapping;
	}
	
	/**
	 * @param r - rectangle that is being checked for collisions
	 * 
	 * The method returns a boolean value for if the rectangle collides with a bound. 
	 * If they do, the border is updated
	 * 
	 * @return boolean value for if collision with bounds occurs
	 */
	public boolean collideBounds(Rectangle r) {
		boolean collided = false;

		updateBorder();

		if (isOverlaping(r)) {
			collided = true;

			setX(getX() - myDelta.x);
			setY(getY() - myDelta.y);
			updateBorder();

			float xBefore = border.x;
			float yBefore = border.y;

			setX(getX() + myDelta.x);

			updateBorder();
			if (isOverlaping(r)) {
				if (xBefore > r.x) {
					setX(r.x + r.width - getDif());
				}
				if (xBefore < r.x) {
					setX(r.x - border.width - getDif());
				}
				updateBorder();
			}

			setY(getY() + myDelta.y);
			updateBorder();
			if (isOverlaping(r)) {
				if (yBefore < r.y) {
					setY(r.y - border.height - getDif());
				}
				if (yBefore > r.y) {
					setY(r.y + r.height - getDif());
				}
				updateBorder();
			}
		}

		return collided;
	}
	/**
	 *  @param map - map that is being checked for collisions
	 * 
	 * The method returns a boolean value for if the rectangle collides with a bound. 
	 * If they do, the border is update
	 * 
	 * @return boolean value for if collision with bounds occurs
	 */
	public boolean collideBounds(TileMap map) {
		boolean collided = false;
		
		setX(getX() - myDelta.x);
		setY(getY() - myDelta.y);
		
		updateBorder();
		float xBefore = border.x;
		float yBefore = border.y;
		
		setX(getX() + myDelta.x);
		updateBorder();
		for (int i = 0; i < map.getI(); i++) {
			for (int j = 0; j < map.getJ(); j++) {
				Tile tile = map.getTile(i, j);
				if (tile != null) {
					if (isOverlaping(tile.getBorder()) && tile.isObstacle()) {
						collided = true;
						if (xBefore > tile.getX()) {
							setX(tile.getBorder().x + tile.getBorder().width - getDif());
						}
						else if (xBefore < tile.getX()) {
							setX(tile.getBorder().x - border.width - getDif());
						}
						updateBorder();
					}
				}
			}
		}
		
		setY(getY() + myDelta.y);
		updateBorder();
		for (int i = 0; i < map.getI(); i++) {
			for (int j = 0; j < map.getJ(); j++) {
				Tile tile = map.getTile(i, j);
				if (tile != null) {
					if (isOverlaping(tile.getBorder()) && tile.isObstacle()) {
						collided = true;
						if (yBefore > tile.getY()) {
							setY(tile.getBorder().y + tile.getBorder().height - getDif());
						}
						else if (yBefore < tile.getY()) {
							setY(tile.getBorder().y - border.height - getDif());
						}
						updateBorder();
					}
				}
			}
		}
		
		return collided;
	}

	/**
	 * returns value of dif
	 * 
	 * @return instance variable dif
	 */
	public float getDif() {
		return dif;
	}
	
	/**
	 * Disposes everything related to the texture of the sprite
	 */
	@Override
	public void dispose() {
		getTexture().dispose();
	}

	/**
	 * @param anim - Animation object to be used for setting
	 * 
	 * Sets animation value to the parameter
	 */
	public void setAnimation(Animation anim) {
		myAnimation = anim;
	}
	
	/**
	 * returns the Animation object
	 * 
	 * @return instance varaible myAnimation
	 */
	public Animation getAnimation() {
		return myAnimation;
	}

}