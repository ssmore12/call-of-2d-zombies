package map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;
import main.AssetHandler;

public class TileMap {
private Tile[][] map;
	
	/**
	 * @param i - row
	 * 
	 * @param j - column
	 * 
	 * initalizes map
	 */
	public TileMap(int i, int j) {
		map = new Tile[i][j];
	}
	
	/**
	 * @param mapSpec - 2D array to intialize map
	 * 
	 * initalizes map and creates region with a wall and grass
	 */
	public TileMap(int[][] mapSpec) {
		int wall = AssetHandler.createRegion("atlas.png", 0, 0, 128, 128);
		int grass = AssetHandler.createRegion("atlas.png", 128, 0, 128, 128);
		map = new Tile[mapSpec.length][mapSpec[0].length];
		for (int i = 0; i < mapSpec.length; i++) {
			for (int j = 0; j < mapSpec[i].length; j++) {
				if (mapSpec[i][j] == 1) {
					map[i][j] = new Tile(wall, 128 * j, -(128 * i), 128, 128, true);
				}
				else if (mapSpec[i][j] == 2) {
					map[i][j] = new Tile(grass, 128 * j, -(128 * i), 128, 128, false);
				}
			}
		}
	}
	
	/**
	 * @param ent - entity being checked for colliding
	 * 
	 * Calls collideBounds from Entity abstract class
	 */
	public void collideBounds(Entity ent) {
		ent.collideBounds(this);
	}
	
	/**
	 * @param batch - object that is being drawn
	 * 
	 * SpriteBatch object is being drawn at every tile in the map
	 */
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] != null) 
						map[i][j].draw(batch);
			}
		}
	}
	
	/**
	 * @param i - row
	 * 
	 * @param j - column
	 * 
	 * returns tile at those indeces
	 * 
	 * @return tile at i , j
	 */
	public Tile getTile(int i, int j) {
		if ( i < 0 || i >= map.length || j < 0 || j > map[0].length)
			return null;
		else 
			return map[i][j];
	}
	
	/**
	 * @param i - row
	 * 
	 * @param j - column
	 * 
	 * @param t - Tile to be set at those indeces
	 * 
	 */
	public void setTile(int i, int j, Tile t) {
		if (i < map.length && j < map[0].length)
			map[i][j] = t;
	}
	
	/**
	 * returns length of rows of map
	 * 
	 * @returns length of rows of map
	 * 
	 */
	public int getI() {
		return map.length;
	}
	
	/**
	 * returns length of columns of map
	 * 
	 * @returns length of columns of map
	 * 
	 */
	public int getJ() {
		return map[0].length;
	}
	
	/**
	 * @param x - x coordinate at which tile is
	 * 
	 * @param y - y coordinate at which tile is
	 * 
	 * returns tile if the width and length of the tile does not exceed the location of the coordinate
	 * 
	 * @return tile object	
	 */
	public Tile getTileOf(float x, float y) {
		Tile tile = null;
		x++;
		y++;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Tile t = this.getTile(i, j);
				if (t != null) {
					if (x >= t.getX() && x <= t.getX() + t.getWidth() && y >= t.getY() && y <= t.getY() + t.getHeight()) {
						tile = t;
					}
				}
			}
		}
		return tile;
	}
	
	/**
	 * @param a - first tile
	 * 
	 * @param b - second tile
	 * 
	 * creates a Tile array and puts the path in which objects will take
	 * 
	 * @return Tile array
	 */
	public Tile[] getPath(Tile a, Tile b) {
		ArrayList<Tile> open = new ArrayList<Tile>();
		ArrayList<Tile> closed = new ArrayList<Tile>();
		
		closed.add(a);
		
		while (a != b) {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					Tile t = this.getTile(i, j);
					if (t != null) {
						if (!closed.contains(t) && !t.isObstacle()) {
							if ((t.getX() == a.getX()) && (t.getY() == a.getY() + 128 || t.getY() == a.getY() - 128)) {
								open.add(t);
							}
							if ((t.getY() == a.getY()) && (t.getX() == a.getX() + 128 || t.getX() == a.getX() - 128)) {
								open.add(t);
							}
						}
					}
				}
			}
			
			float lowestF = 0;
			Tile lowestTile = null;

			for (int c = 0; c < open.size(); c++) {
				float h = Math.abs((b.getX() - open.get(c).getX())) + Math.abs((b.getY() - open.get(c).getY()));
				float g = Math.abs((open.get(c).getX() - a.getX())) + Math.abs((open.get(c).getY() - a.getY()));
				
				float f = h + g;
				if (c == 0) {
					lowestF = f;
					lowestTile = open.get(c);
				}
				
				else if (f < lowestF) {
					lowestF = f;
					lowestTile = open.get(c);
				}
			}

			open.remove(lowestTile);
			closed.add(lowestTile);
			
			a = lowestTile;
		}
		
		Tile[] path = new Tile[closed.size()];
		for (int c = 0; c < path.length; c++) {
			path[c] = closed.get(c);
		}
		
		return path;
	}
}