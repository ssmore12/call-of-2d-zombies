package screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Player;
import entities.Zombie;
import entities.ZombieHandler;
import items.Bullet;
import main.AssetHandler;
import main.CodGame;
import main.SinCos;
import map.TileMap;

public class GameScreen implements Screen {	
	OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	SpriteBatch batch;
	BitmapFont font;
	
	Player myPlayer;
	ArrayList<Bullet> myBullets;
	
	ZombieHandler myZombieHandler;
	
	int wave;
	TileMap myMap;
	CodGame game;
	
	/**
	 * @param g - new game
	 * 
	 * initalizes instance varaible
	 */
	public GameScreen(CodGame g) {
		game = g;
	}


	/**
	 * creates all game objects
	 */
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		SinCos.init();
		AssetHandler.initAssetHandler();
		
		Zombie.myAnimationID = AssetHandler.createAnimation("zombie.png", 17, 0, 0, 104, 75);
		Bullet.regId = AssetHandler.createRegion("atlas.png", 256, 0, 6, 13);
		myPlayer = new Player(AssetHandler.createAnimation("knifeMove.png", 20, 0, 0, 75, 101),
				62, -58, 75, 101);
		
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		
		int[][] mapSpec = {
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 1, 1, 1, 2, 2, 2, 2, 1, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};

		myMap = new TileMap(mapSpec);
		myZombieHandler = new ZombieHandler(myMap);
		wave = 1;
	}

	/**
	 * renders all game objects
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.x = myPlayer.getX() + (myPlayer.getWidth() / 2);
		camera.position.y = myPlayer.getY() + (myPlayer.getHeight() / 2);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		if (myPlayer.alive()) {
			if (myZombieHandler.getCount() < wave * 5) {
				myZombieHandler.spawn(); 
			}
		}
		
		if (myZombieHandler.isEmpty()) {
			wave++;
			myZombieHandler.resetCount();
			myZombieHandler.forceSpawn();
		}
		
		for (int j = 0; j < myZombieHandler.myZombies.size(); j++) {
			if (myZombieHandler.myZombies.get(j).alive()) {
				if (myPlayer.alive()) 
					myZombieHandler.myZombies.get(j).setObjective(myPlayer.getBorder().x, myPlayer.getBorder().y);
				else
					myZombieHandler.myZombies.get(j).setObjective(null);
				myZombieHandler.myZombies.get(j).update(delta);
			}
			else {
				
				myZombieHandler.remove(myZombieHandler.myZombies.get(j));
				myPlayer.addScore(10);
			}
		}
		
		if (myPlayer.alive()) {
			myPlayer.update(delta);
		}
		myBullets = myPlayer.getWeapon().getBullets();
		myPlayer.getWeapon().update(myZombieHandler.myZombies, delta);
		
		
		
		
		for (int j = 0; j < myZombieHandler.myZombies.size(); j++) {
			if (myZombieHandler.myZombies.get(j).alive()) {
				myMap.collideBounds(myZombieHandler.myZombies.get(j));
			}
		}
		for (int i = 0; i < myBullets.size(); i++) {
			if (myBullets.get(i).collideBounds(myMap)) {
				myBullets.remove(i);
			}
		}
		
		for (int j = 0; j < myZombieHandler.myZombies.size(); j++) {
			if (myZombieHandler.myZombies.get(j).alive()) {
				if (myZombieHandler.myZombies.get(j).isOverlaping(myPlayer.getBorder()) && myPlayer.alive()) {
					myZombieHandler.myZombies.get(j).forceStop();
					myPlayer.damage(1, -myZombieHandler.myZombies.get(j).getSine(), myZombieHandler.myZombies.get(j).getCosine());
				}
				else {
					myZombieHandler.myZombies.get(j).forceStart();
				}
				
				for (int i = 0; i < myBullets.size(); i++) {
					if (myBullets.get(i).collideBounds(myZombieHandler.myZombies.get(j).getBorder()))
						myZombieHandler.myZombies.get(j).damage(myBullets.get(i).getCurrentShooted().getDamage());
				}
			}
		}
		
		
		myMap.collideBounds(myPlayer);

		
		batch.begin();
		myMap.draw(batch);
		
		Iterator itr = (Iterator) myBullets.iterator();
		
		while (itr.hasNext()) {
			Bullet b = (Bullet) itr.next();
			b.draw(batch);
		}
		
		if (myPlayer.isDamaged())
			batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		if (myPlayer.alive())
			myPlayer.draw(batch);
		batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		for (int j = 0; j < myZombieHandler.myZombies.size(); j++) {
			if (myZombieHandler.myZombies.get(j).alive()) {
				myZombieHandler.myZombies.get(j).draw(batch);
			}
		}
		
		font.draw(batch, "Wave: " + wave, camera.position.x + 730, camera.position.y + 420);
		font.draw(batch, "Player:", camera.position.x - 780, camera.position.y + 420);
		font.draw(batch, "Life " + myPlayer.getHp(), camera.position.x - 780, camera.position.y + 390);
		font.draw(batch, "Ammo " + myPlayer.getAmmo(), camera.position.x - 780, camera.position.y + 360);
		font.draw(batch, "Score " + myPlayer.getScore(), camera.position.x - 780, camera.position.y + 330);
		
		font.draw(batch, "Weapon:", camera.position.x - 780, camera.position.y - 360);
		font.draw(batch, "Damage: " + myPlayer.getWeapon().getDamage(), camera.position.x - 780, camera.position.y - 390);
		font.draw(batch, "Rate: " + myPlayer.getWeapon().getFireRate(), camera.position.x - 780, camera.position.y - 420);
		batch.end();
	}

	/**
	 * @param width - resize screen to this width
	 * 
	 * @param height - resize screen to this height
	 * 
	 * resize screen
	 */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * pauses the game
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * resumes the game
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * hides the main menu
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * diposes all the features of the texture
	 */
	@Override
	public void dispose() {
		game.batch.dispose();
	}

}
