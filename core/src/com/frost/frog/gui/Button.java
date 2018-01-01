package com.frost.frog.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.frost.frog.state.MyScreen;

public class Button {

    public Vector2 position;
    public Texture[] buttons;
    public int height;
    public int width;
    public int enumButton = 0;

    private ButtonTask task;
    private boolean isTouchDown = false;

    public Button(Texture on, Texture off, ButtonTask task){
        this.buttons = new Texture[2];
        buttons[1] = on;
        buttons[0] = off;
        this.position = new Vector2(0, 0);
        width = buttons[0].getWidth();
        height = buttons[0].getHeight();
        enumButton = 0;
        this.task = task;
    }

    public void render(SpriteBatch batch, int x, int y, int width, int height){
        batch.draw(buttons[enumButton], position.x = x, position.y = y, this.width = width, this.height = height);
    }
    public void render(SpriteBatch batch, int x, int y){
        batch.draw(buttons[enumButton], position.x = x, position.y = y, width, height);
    }
    public void setActive(int i){
        enumButton = i;
    }

    public void setTouchDown(int x, int y){
        if (focus(x, y)){
            isTouchDown = true;
            setActive(1);
        }
    }
    private boolean focus(int x, int y){
        x = (int) (x * MyScreen.X_SCALE);
        y = (int) (y * MyScreen.Y_SCALE);
        y = 480 - y;
        return (x >= position.x && x <= (position.x + width) &&
                y >= position.y && y <= (position.y + height));
    }
    public void touchUp(int x, int y){
        if (isTouchDown) {
            setActive(0);
            if (focus(x, y)){
                task.task();
            }
            isTouchDown = false;
        }
    }
    public void setSize(int w, int h){
        width = w;
        height = h;
    }
    public Texture getButton(){ return buttons[enumButton];}
    public boolean getValue(){ return (enumButton == 1);}
    public void dispose(){
        buttons[0].dispose();
        buttons[1].dispose();
    }
    public interface ButtonTask{
        void task();
    }
}
