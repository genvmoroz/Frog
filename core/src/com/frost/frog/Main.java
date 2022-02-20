package com.frost.frog;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.GdxRuntimeException;

import com.frost.frog.config.ConfigAssets;
import com.frost.frog.state.Loading;

public class Main extends Game {

	public static double alpha = 0;
	public static double scale = 1;
	public boolean setState = false;
	public static boolean attenuation = false;
	public static boolean attenuationOff = true;

	private Screen temp;
	public SpriteBatch batch;

	public static OrthographicCamera camera;
	public static AssetManager assetManager;
	public static BitmapFont bitmapFont;
	public Main main;

	@Override
	public void create () {
		batch = new SpriteBatch();
			assetManager = ClientAssets.instance();
			ClientAssets.loadAssetsTexture(
					ConfigAssets.LOADING_LOGOTIP,
					ConfigAssets.LOADING_BACKGROUND,
					ConfigAssets.LOADING_BACKGROUND,
					ConfigAssets.DIALOG_EXIT,
					ConfigAssets.DIALOG_EXIT_GREEN,
					ConfigAssets.GAME_BACKGROUND,
					ConfigAssets.MENU_BACKGROUND,
					ConfigAssets.GAME_CIRCLE,
					ConfigAssets.GAME_FROG,
					ConfigAssets.MENU_AUDIO,
					ConfigAssets.MENU_PLAY,
					ConfigAssets.MENU_EXIT,
					ConfigAssets.MENU_DIALOG_NO,
					ConfigAssets.MENU_DIALOG_YES,
					ConfigAssets.MENU_MANUAL,
					ConfigAssets.GAME_MENU,
					ConfigAssets.GAME_RESTART,
					ConfigAssets.GAME_DIALOG_RESTART
			);
//		ClientAssets.loadAssetsFont(ConfigAssets.FONT);

		assetManager.load("black.png", Texture.class);
		assetManager.finishLoading();

		// load font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default/font/AdobeHebrew-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 100;
		parameter.color = Color.YELLOW;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 3;
		bitmapFont = generator.generateFont(parameter);// font size 12 pixels
		generator.dispose();

		Gdx.input.setCatchBackKey(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		Loading loading = new Loading(this, "start");
		setScreen(loading);
	}
	@Override
	public void render () {
		if(temp != null){
			batch.setProjectionMatrix(camera.combined);
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();

			super.render();

			if (setState){
				if (attenuationOff){
					scale += 1.5;
					alpha += scale;
					if (alpha >= 200){
						attenuationOff = false;
						scale = 1;
						attenuation = true;
						setState(temp);
					}
				}
				if (attenuation) {
					scale += 1.5;
					alpha -= scale;

					if (alpha <= 1) {
						attenuation = false;
						alpha = 0;
						scale = 1;
						setState = false;
					}
				}
			}
			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, (float)(alpha/255));
			try{
				batch.draw((Texture) assetManager.get("black.png"), 0, 0, 800, 480);
			}catch (GdxRuntimeException e){
				System.err.println(e.getMessage());
			}
			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
			batch.end();
		}
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		temp = screen;
		setState = true;
		attenuationOff = true;
	}
	private void setState(Screen screen){
		if (this.screen != null) this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
	@Override
	public Screen getScreen() {
		return super.getScreen();
	}
}
