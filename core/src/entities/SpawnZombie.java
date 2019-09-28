package entities;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import main.AssetHandler;

public class SpawnZombie {
	Vector2[] locations;
	
	Random selector;
	
	/**
	 * Constructor the intializes all instance variables
	 * locations array is null
	 */
	public SpawnZombie() {
		locations = null;
		
		selector = new Random();
	}
	
	/**
	 * @param spots - array used to initalize location
	 * 
	 * Constructor the intializes all instance variables
	 * locations array is initalized by parameter
	 */
	public SpawnZombie(Vector2[] spots) {
		locations = spots;
		
		selector = new Random();
	}
	
	/**
	 * creates a Zombie and enters it into the field
	 * 
	 * @return spawned Zombie
	 */
	public Zombie spawn() {
		Zombie z = null;
		if (locations != null) {
			int n = selector.nextInt(locations.length);
			z = new Zombie((int) locations[n].x, (int) locations[n].y, 211, 157);
		}
		return z;
	}
	
	/**
	 * returns the locations array
	 * 
	 * @return instance varaible locations
	 */
	public Vector2[] getSpots() {
		return locations;
	}
	
	/**
	 * @param spots - array used to initalize locations
	 * 
	 * sets the locations array to parameter spots
	 */
	public void setSpots(Vector2[] spots) {
		this.locations = spots;
	}
}