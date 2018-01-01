package com.frost.frog.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;

public abstract class ModuleDialog {

    public Vector2 position;
    public int width, height;
    public Texture background;
    public MyScreen parent;

    protected ModuleDialog dialog = null;

    ModuleDialog(int x, int y, int w, int h, MyScreen p){
        width = w;
        height = h;
        parent = p;
        position = new Vector2(x, y);
        background = Main.assetManager.get(ConfigAssets.DIALOG_EXIT_GREEN.getOn());
    }
    public void setInput(InputAdapter input){
        Gdx.input.setInputProcessor(input);
    }
    public void render(SpriteBatch batch){
        if (dialog != null){
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f);
            batch.draw(background, 0, 0, 800, 480);
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.8f);
            dialog.render(batch);
        }
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }
}
