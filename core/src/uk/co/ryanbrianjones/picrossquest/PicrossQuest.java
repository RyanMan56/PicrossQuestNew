package uk.co.ryanbrianjones.picrossquest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import uk.co.ryanbrianjones.picrossquest.screens.GameScreen;

public class PicrossQuest extends Game {
	private GameScreen gameScreen;
	private AssetManager assetManager;

	@Override
	public void create () {
		assetManager = new AssetManager();

		assetManager.load("Happy.png", Texture.class);
		assetManager.load("HappyTemplate.png", Texture.class);
		assetManager.load("Square.png", Texture.class);
		assetManager.load("Cross.png", Texture.class);
		assetManager.load("Background.png", Texture.class);
		assetManager.load("Glint.png", Texture.class);
		assetManager.load("EndGlint.png", Texture.class);
		assetManager.load("EndGlintSide.png", Texture.class);
		assetManager.load("BattleCat.png", Texture.class);
		assetManager.load("BattleCatTemplate.png", Texture.class);
		assetManager.load("PenButton.png", Texture.class);
		assetManager.load("PenButtonSelected.png", Texture.class);
		assetManager.load("CrossButton.png", Texture.class);
		assetManager.load("CrossButtonSelected.png", Texture.class);
		assetManager.load("Ryan.png", Texture.class);
		assetManager.load("RyanTemplate.png", Texture.class);
		assetManager.load("Main.png", Texture.class);
		assetManager.load("MainTemplate.png", Texture.class);
		assetManager.load("Serpa.png", Texture.class);
		assetManager.load("SerpaTemplate.png", Texture.class);
		assetManager.load("MainWalkAnim.png", Texture.class);
//		assetManager.load("Floor.png", Texture.class);
//		assetManager.load("FloorAesthetic.png", Texture.class);
//		assetManager.load("Dirt.png", Texture.class);

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("Test.tmx", TiledMap.class);
		assetManager.load("House.tmx", TiledMap.class);
	}

	@Override
	public void render () {
		super.render();
		if(assetManager.update()){
			gameScreen = new GameScreen(this, assetManager, "Test.tmx");
//			this.setScreen(gameScreen);
		}
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
	}
}
