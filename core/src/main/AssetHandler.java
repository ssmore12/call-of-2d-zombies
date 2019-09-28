package main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetHandler {

	private static ArrayList<String> myPaths;
	private static ArrayList<Texture> myTextures;
	private static ArrayList<TextureRegion> myTextureRegions;
	private static ArrayList<Animation> myAnimations;

	/**
	 * initalizes all instance varaibles
	 */
	public static void initAssetHandler() {
		myTextures = new ArrayList<Texture>();
		myTextureRegions = new ArrayList<TextureRegion>();
		myAnimations = new ArrayList<Animation>();
		myPaths = new ArrayList<String>();
	}

	/**
	 * @param path - String contained in array
	 * 
	 * length of String is index in the textures array
	 * 
	 * @return the texture at the index in the myTextures array
	 */
	private static Texture getTexture(String path) {
		Texture t = null;

		if (myPaths.contains(path)) {
			int index = myPaths.indexOf(path);
			if (index >= 0)
				t = myTextures.get(index);
		} else {
			myPaths.add(path);
			myTextures.add(new Texture(Gdx.files.internal(path)));
			t = myTextures.get(myTextures.size() - 1);
		}
		return t;
	}
	
	/**
	 * @param i - index of array
	 * 
	 * returns Animation at that index of array
	 * 
	 * @return Animation
	 */
	public static Animation getAnimation(int i) {
		Animation t = null;

		if (i < myAnimations.size() && i >= 0)
			t = myAnimations.get(i);
		return t;
	}

	/**
	 * @param path - String contained in array
	 * 
	 * @param nFrames - number of frames
	 * 
	 * @param x - value of x coordinate
	 * 
	 * @param y - value of y coordinate
	 * 
	 * @param w - value of width
	 * 
	 * @param h - value of height
	 * 
	 * creates animation at a certain point in region
	 * 
	 * returns index of animation
	 * 
	 * @return index of animation
	 */
	public static int createAnimation(String path, int nFrames, int x, int y, int w, int h) {
		Animation anim;
		Texture aTex = getTexture(path);

		TextureRegion[] temp = new TextureRegion[nFrames];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = new TextureRegion(aTex, x + i * w, y, w, h);
		}
		anim = new Animation(0.10f, temp);

		myAnimations.add(anim);
		return myAnimations.indexOf(anim);
	}

	/**
	 * @param path - String contained in array
	 * 
	 * @param nFrames - number of frames
	 * 
	 * @param x - value of x coordinate
	 * 
	 * @param y - value of y coordinate
	 * 
	 * @param w - value of width
	 * 
	 * @param h - value of height
	 * 
	 * creates region
	 * 
	 * returns index of region
	 * 
	 * @return index of region
	 * 
	 */
	public static int createRegion(String path, int x, int y, int w, int h) {
		TextureRegion region = new TextureRegion(getTexture(path), x, y, w, h);
		myTextureRegions.add(region);
		return myTextureRegions.indexOf(region);
	}

	/**
	 * @param i - index of array
	 * 
	 * returns region at i, index
	 * 
	 * @return texture region at that index
	 */
	public static TextureRegion getRegion(int i) {
		TextureRegion t = null;

		if (i >= 0 && i < myTextureRegions.size())
			t = myTextureRegions.get(i);
		return t;
	}

	/**
	 * disposes of all textures in array
	 */
	public static void cleanUp() {
		for (Texture t : myTextures) {
			t.dispose();
		}
	}

}