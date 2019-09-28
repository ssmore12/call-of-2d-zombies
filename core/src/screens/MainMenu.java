package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import main.CodGame;

public class MainMenu implements Screen {
	
	private static final int EXIT_BUTTON_HEIGHT = 75;
	private static final int EXIT_BUTTON_WIDTH = 300;
	private static final int EXIT_BUTTON_X = CodGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
	private static final int EXIT_BUTTON_Y = 200;
	
	private static final int PLAY_BUTTON_HEIGHT = 75;	
	private static final int PLAY_BUTTON_WIDTH = 300;
	private static final int PLAY_BUTTON_X = CodGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;	
	private static final int PLAY_BUTTON_Y = EXIT_BUTTON_Y + 120;
	
	CodGame game;
	
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture title;
	Texture logo;
	
	/**
	 * @param g - new game
	 * 
	 * creates main menu screen and initalizes all instance varaibles
	 */
	public MainMenu (CodGame g) {
		game = g;
		exitButtonActive = new Texture("exitActive.png");
		exitButtonInactive = new Texture("exitInactive.png");
		playButtonActive = new Texture("playActive.png");
		playButtonInactive = new Texture("playInactive.png");
		title = new Texture("title.png");
		logo = new Texture("logo.png");
	}
	
	/**
	 * shows the screen
	 */
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
//		System.out.println(CodGame.WIDTH / 2 - 650 / 2);
		game.batch.draw(title, CodGame.WIDTH / 2 - 650 / 2, 550);
		game.batch.draw(logo, 10, 10);
		if (Gdx.input.getX() < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH && Gdx.input.getX() > EXIT_BUTTON_X && Gdx.input.getY() < CodGame.HEIGHT - EXIT_BUTTON_Y && Gdx.input.getY() > CodGame.HEIGHT - EXIT_BUTTON_Y - EXIT_BUTTON_HEIGHT) {
			game.batch.draw(exitButtonActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()) {
				Gdx.app.exit();
			}
		} else {
			game.batch.draw(exitButtonInactive, CodGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}
		
		
		if (Gdx.input.getX() < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH && Gdx.input.getX() > PLAY_BUTTON_X && Gdx.input.getY() < CodGame.HEIGHT - PLAY_BUTTON_Y && Gdx.input.getY() > CodGame.HEIGHT - PLAY_BUTTON_Y - PLAY_BUTTON_HEIGHT) {
			game.batch.draw(playButtonActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()) {
				this.dispose();
				game.setScreen(new GameScreen(game));
			}
		} else {
			game.batch.draw(playButtonInactive, CodGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2, PLAY_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}
		
		game.batch.end();		
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
	 * pause the game
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * resume the game
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * hide the main menu
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * dispose all the features of the texture
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}