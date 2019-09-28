package map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import entities.Entity;
import main.AssetHandler;

public class Tile extends Entity {
	private boolean isObstacle;

	/**
	 * @param regId - index of region
	 * 
	 * @param dx - x coordinate of border
	 * 
	 * @param dy - y cooridnate of border
	 * 
	 * @param width - width of border
	 * 
	 * @param height - height of border
	 * 
	 * @param isOb - boolean to see if it is an obstacle
	 * 
	 * Constructor initalizes instance varaibles
	 * 
	 */
	public Tile(int regId, float dx, float dy, int width, int height, boolean isOb) {
		isObstacle = isOb;
		dif = 0;

		setRegion(AssetHandler.getRegion(regId));

		border = new Rectangle(dx, dy, width, height);
		setX(dx);
		setY(dy);
	}

	/**
	 * returns region width
	 * 
	 * @return region width
	 */
	@Override
	public float getWidth() {
		return getRegionWidth();
	}

	/**
	 * returns region length
	 * 
	 * @return region length
	 */
	@Override
	public float getHeight() {
		return getRegionHeight();
	}

	/**
	 * @param deltaTime - speed of movement of character
	 * 
	 * Abstract method to update movement of character
	 * 
	 * @return boolean value of if updated
	 */
	@Override
	public boolean update(float deltaTime) {
		return true;
	}
	/**
	 * @param batch - object that is being drawn
	 * 
	 * SpriteBatch object is being drawn
	 */
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight(), getRegionX(),
				getRegionY(), getRegionWidth(), getRegionHeight(), false, false);
	}
	/**
	 * @param ext - rectangle that is being checked for collisions
	 * 
	 * The method returns a boolean value for if the rectangle collides with a bound. 
	 * If they do, the border is updated
	 * 
	 * @return boolean value for if collision with bounds occurs
	 */
	@Override
	public boolean collideBounds(Rectangle ext) {
		boolean coll = isOverlaping(ext);

		return coll;
	}

	/**
	 * returns if tile is an obstacle
	 * 
	 * @return if it is an obstacle
	 */
	public boolean isObstacle() {
		return isObstacle;
	}

}