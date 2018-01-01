package com.frost.frog.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;
import com.frost.frog.gui.Button;

public class DialogWin extends ModuleDialog {

    private Button restart, menu;
    private BitmapFont bitmapFontW;
    private int score;

    public DialogWin(int x, int y, int w, int h, MyScreen p, int score) {
        super(x, y, w, h, p);
        restart = new Button(
                Main.assetManager.get(ConfigAssets.GAME_DIALOG_RESTART.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.GAME_DIALOG_RESTART.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        ((Game) DialogWin.this.parent).restartGame();
                    }
                }

        );
        menu = new Button(
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_YES.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_DIALOG_YES.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        parent.yes();
                    }
                }
        );
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default/font/AdobeHebrew-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 52;
        parameter.color = Color.YELLOW;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        bitmapFontW = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        setInput(new Input(parent, restart, menu));
        this.score = score;
    }
    class Input extends Menu.Input{

        private Game game;

        Input(MyScreen screen, Button... bs) {
            super(screen, bs);
            game = (Game)screen;
        }
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK){
                game.removeScreen();
            }
            return super.keyDown(keycode);
        }
    }

    public void render(SpriteBatch batch) {
        OrthographicCamera camera = parent.camera;

        batch.draw(background, position.x, position.y, width, height);
        menu.render(batch, (int)(position.x + width - 206), (int)(position.y + 10),
                width/3, width/3/4);

        restart.render(batch, (int)(position.x + 6), (int)(position.y + 10),
                width/3, width/3/4);
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.9f);
        bitmapFontW.draw(
                batch, "You win!",
                camera.viewportWidth * 0.37f,
                camera.viewportHeight * 0.7f
        );
        bitmapFontW.draw(
                batch, "Score: " + score,
                camera.viewportWidth * 0.37f,
                camera.viewportHeight * 0.55f
        );
    }
}
