package com.frost.frog.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;

public abstract class MyScreen implements Screen {

    public OrthographicCamera camera = com.frost.frog.Main.camera;
    protected ModuleDialog dialog = null;

    public com.frost.frog.Main main;
    public Texture background;
    public Texture shadow;
    public SpriteBatch batch;
    public InputAdapter input = null;

    public static float X_SCALE = 800 / (float)Gdx.graphics.getWidth();
    public static float Y_SCALE = 480 / (float)Gdx.graphics.getHeight();

    public MyScreen(Main m){
        main = m;
        batch = m.batch;
        shadow = Main.assetManager.get(ConfigAssets.DIALOG_EXIT.getOn());
    }
    public void removeScreen(){
        if (dialog == null)
            throw new NullPointerException("Dialog is not exciting.");
        dialog = null;
        setInput(input);
    }
    public void addScreen(ModuleDialog dialog){
        this.dialog = dialog;
    }
    public void setInput(InputAdapter input){
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    public void yes(){

    }
}
