package com.frost.frog.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;
import com.frost.frog.state.Game;
import com.frost.frog.state.MyScreen;

public class Field {

    public Texture icon;

    public Vector2 position;
    public int radius;
    private boolean top = false;
    public int cost = 9999;
    public Field field = null;
    public int indexX, indexY;
    public boolean exist = true;
    public boolean trash = false;
    public boolean lock = false;

    private float alpha = 1f;

    public Field(int x, int y, int radius){
        position = new Vector2(x, y);
        icon = Main.assetManager.get(ConfigAssets.GAME_CIRCLE.getOn());
        this.radius = radius;
    }
    public void render(SpriteBatch batch){

        if (alpha <= 0.05)
            alpha = 0.05f;

        if (alpha <= 0.2){
            trash = true;
            Frog.readyFrog = !Frog.readyFrog;
        }

        if (exist && !trash) {
            batch.draw(icon, position.x - radius, position.y - radius, radius * 2, radius * 2);
        } else {
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha-=0.05f);
            batch.draw(icon, position.x - radius, position.y - radius, radius * 2, radius * 2);
        }
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
    }
    public boolean collision(int x, int y){
        if (lock) return false;
        x = (int)(x * MyScreen.X_SCALE);
        y = (int)(y * MyScreen.Y_SCALE);
        y = 480 - y;
        return (x > position.x - radius && x < (position.x + radius) &&
                y > position.y - radius && y < (position.y + radius));
    }
    public void remove(){
        exist = false;
    }
    public void setTop(){
        top = true;
    }
    public Field getField(){
        return (field == null) ? this : field.getField();
    }
    public boolean getTop(){
        return top;
    }
}
