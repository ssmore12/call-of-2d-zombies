package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenu;

public class CodGame extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	
	public SpriteBatch batch;
	
	/**
	 * initalizes screen and batch
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}

	/**
	 * renders the game
	 */
	@Override
	public void render() {	
		super.render();
	}

	/**
	 * disposes all features of batch's texture
	 */
	@Override
	public void dispose() {
		
	}
}
