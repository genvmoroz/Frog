package com.frost.frog.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.frost.frog.ClientAssets;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;
import com.frost.frog.gui.Button;
import com.frost.frog.objects.Field;
import com.frost.frog.objects.Fields;
import com.frost.frog.objects.Frog;

public class Game extends MyScreen {

    private float scale = 0;
    private float alpha = 0;

    public Fields fields;
    private Frog frog;
    public float scaleFields;
    private int score = 0;
    private Button menuButton;
    private Button restartButton;
    private BitmapFont bitmapFont;

    public Field field;

    public Game(Main main) {
        super(main);
        field = new Field(100, 100, 10);
        background = ClientAssets.instance().get(ConfigAssets.GAME_BACKGROUND.getOn());
        scaleFields = (int)(camera.viewportHeight/20.5);
        fields = new Fields(
                ((int)((camera.viewportWidth - camera.viewportHeight) / 2 - scaleFields)) + (int)scaleFields * 6,
                (int)scaleFields, (int)scaleFields
        );

        frog = new Frog(0, 0, (int)scaleFields, this, fields);
        menuButton = new Button(
                Main.assetManager.get(ConfigAssets.GAME_MENU.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.GAME_MENU.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        addScreen(new Exit(100, 20, 600, 420, getGame()));
                    }
                }
        );
        menuButton.setSize(
                (int)(camera.viewportHeight * 0.2f),
                (int)(camera.viewportHeight * 0.2f)
        );
        restartButton = new Button(
                Main.assetManager.get(ConfigAssets.GAME_RESTART.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.GAME_RESTART.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        Game.this.restartGame();
                    }
                }
        );
        restartButton.setSize(
                (int)(camera.viewportHeight * 0.2f),
                (int)(camera.viewportHeight * 0.2f)
        );

        bitmapFont = Main.bitmapFont;
//        bitmapFont.getData().scale(2);
        setInput(input = new Input(this, restartButton, menuButton));
    }

    public Game getGame(){
        return this;
    }

    private class Input extends Menu.Input {

        private boolean buttonTouch = false;

        Input(MyScreen screen, Button... bs) {
            super(screen, bs);
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (!Frog.readyFrog){
                if (fields.collision(Gdx.input.getX(),Gdx.input.getY())){
                    buttonTouch = true;
                    field = Fields.field;
                }
                else buttonTouch = false;
            }
            for (Button b : buttons){
                b.setTouchDown(Gdx.input.getX(),Gdx.input.getY());
            }
            return super.touchDown(screenX, screenY, pointer, button);
        }

        private Field field;

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            if (!Frog.readyFrog && !frog.animated) {
                if (fields.collision(Gdx.input.getX(), Gdx.input.getY())) {
                    if (field.equals(Fields.field)) {
                        if (buttonTouch) {
                            fields.collision(Gdx.input.getX(), Gdx.input.getY(), "remove");
                            score++;
                        }
                    }
                }
                buttonTouch = false;
            }
            for (Button b : buttons){
                b.touchUp(Gdx.input.getX(),Gdx.input.getY());
            }
            return super.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK){
                addScreen(new Exit(100, 20, 600, 420, parent));

            }
            return super.keyDown(keycode);
        }
    }
    public void render(float delta){
        super.render(delta);

        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        menuButton.render(batch, (int)(camera.viewportHeight * 0.08f),
                (int)(camera.viewportHeight * (2 / 3f * 0.7f)));
        restartButton.render(batch,  (int)(camera.viewportHeight * 0.08f),
                (int)(camera.viewportHeight * (2.2f/3f)));
        fields.render(batch);
        frog.render(batch);

        bitmapFont.draw(
                batch, score + "",
                camera.viewportWidth * 0.07f,
                camera.viewportHeight * 0.35f
        );
        if (dialog != null) {
            scale += 2.3;
            alpha += scale;
            if ((alpha/255) >= 0.8){
                alpha = 0.8f * 255;
            }
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, (alpha/255));
            batch.draw(shadow, 0, 0, 800, 480);
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, (alpha/255) + 0.2f);
            dialog.render(batch);
        }else {
            scale = 0;
            alpha = 0;
        }
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }
    public void win(){
        addScreen(new DialogWin(100, 20, 600, 420, this, score));
        Frog.readyFrog = false;
        if (score < Menu.SCORE){
            Menu.PREF.putInteger("score", score);
            Menu.PREF.flush();
        }
        fields.removeAll();
    }
    public void lose(){
        addScreen(new DialogLose(100, 20, 600, 420, this, score));
        Frog.readyFrog = false;
        frog.indexX = 99999;
    }
    public void yes(){
        Loading loading = new Loading(main, "menu");
        main.setScreen(loading);
    }
    public void restartGame(){
        Loading loading = new Loading(main, "game");
        main.setScreen(loading);
    }
}
