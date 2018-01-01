package com.frost.frog.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;
import com.frost.frog.gui.Button;

public class Exit extends ModuleDialog {

    Button yes, no;

    Exit(int x, int y, int w, int h, MyScreen p){

        super(x, y, w, h, p);

        yes = new Button(
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_YES.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_YES.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        parent.yes();
                    }
                }
        );
        no = new Button(
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_NO.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_NO.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        parent.removeScreen();
                    }
                }
        );
        setInput(new Input(parent, yes, no));
    }

    class Input extends Menu.Input{

        Input(MyScreen screen, Button... bs) {
            super(screen, bs);
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK){
                parent.removeScreen();
            }
            return super.keyDown(keycode);
        }
    }
    public void render(SpriteBatch batch) {
        batch.draw(background, position.x, position.y, width, height);
        yes.render(batch, (int)(position.x + width - 206), (int)(position.y + 10),
                width/3, width/3/4);

        no.render(batch, (int)(position.x + 6), (int)(position.y + 10),
                width/3, width/3/4);

        super.render(batch);
    }
}
