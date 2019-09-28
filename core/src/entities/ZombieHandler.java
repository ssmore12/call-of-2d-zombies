package entities;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Pool;

import map.TileMap;

public class ZombieHandler {
	public ArrayList<Zombie> myZombies;
	private Pool<Zombie> myZombiesPool;
	
	private long lastSpawned;
	private int delay;

	private TileMap myMap;
	private int myCount;
	
	/**
	 * @param m - TileMap object used to initialize instance variable
	 * Constructor that initalizes all instance varaibles
	 */
	public ZombieHandler(TileMap m) {
		
		myZombies = new ArrayList<Zombie>();
		
		myZombiesPool = new Pool<Zombie>() {
			public Zombie newObject() {
				return new Zombie();
			}
		};
		
		lastSpawned = 0;
		
		myMap = m;
		delay = 1500;
	}
	
	/**
	 * spawns zombies
	 * 
	 * @return boolean to see if zombues spawned
	 */
	public boolean spawn() {
		if ((System.nanoTime() - lastSpawned) / 1000000 > delay) {
			
			lastSpawned = System.nanoTime();
			Zombie z = myZombiesPool.obtain();
			
			z.init(myMap);
			myZombies.add(z);
			
			myCount++;
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * checks if ArrayList is empty
	 * 
	 * @return boolean to see if empty
	 */
	public boolean isEmpty() {
		return myZombies.isEmpty();
	}
	
	/**
	 * returns count of zombies
	 * 
	 * @return myCount
	 */
	public int getCount() {
		return myCount;
	}
	
	/**
	 * sets count of zombies to 0
	 */
	public void resetCount() {
		myCount = 0;
	}
	
	/**
	 * @param z - zombie that is to be removed from the ArrayList 
	 * 
	 * removes a zombie from the ArrayList
	 */
	public void remove(Zombie z) {
		myZombiesPool.free(z);
		
		myZombies.remove(z);
	}

	/**
	 * force spawns a couple of zombies
	 */
	public void forceSpawn() {
		lastSpawned = 0;
		spawn();
	}
}
