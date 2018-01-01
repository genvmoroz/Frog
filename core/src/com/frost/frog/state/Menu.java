package com.frost.frog.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.frost.frog.ClientAssets;
import com.frost.frog.gui.Button;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;

public class Menu extends MyScreen {

    static Preferences PREF = null;
    static Music audio = null;
    static boolean musicPlaying = true;
    static int SCORE;

    public static BitmapFont bitmapFont;

    private float scale = 0;
    private float alpha = 0;

    private Button startGame;
    private Button exitGame;
    private Button volume;
    private Button manual;

    public Menu(Main m) {
        super(m);

        if (audio == null){
            audio = Gdx.audio.newMusic(
            Gdx.files.internal(ConfigAssets.Element.DEFAULT_ROOT + "/rainbows.mp3"));
            audio.setLooping(true);
            audio.setVolume(.5f);
            audio.play();
        }
        background = Main.assetManager.get(ConfigAssets.MENU_BACKGROUND.getOn());
        if (PREF == null)
            PREF = Gdx.app.getPreferences("MinScale");
        SCORE = PREF.getInteger("scale", 1000);

        this.startGame = new Button(
                Main.assetManager.get(ConfigAssets.MENU_PLAY.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_PLAY.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        Loading loading;
                        loading = new Loading(main, "game");
                        main.setScreen(loading);
                    }
                }
        );
        this.exitGame = new Button(
                Main.assetManager.get(ConfigAssets.MENU_EXIT.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_EXIT.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        addScreen(new Exit(100, 20, 600, 420, getMenu()));
                    }
                }
        );
        this.manual = new Button(
                Main.assetManager.get(ConfigAssets.MENU_MANUAL.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_MANUAL.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        System.out.println("Manual");
                    }
                }
        );
        this.volume = new Button(
                Main.assetManager.get(ConfigAssets.MENU_AUDIO.getOn(), Texture.class),
                Main.assetManager.get(ConfigAssets.MENU_AUDIO.getOff(), Texture.class),
                new Button.ButtonTask() {
                    @Override
                    public void task() {
                        if (musicPlaying) {
                            audio.pause();
                            volume.setActive(0);
                            musicPlaying = false;
                        } else {
                            audio.play();
                            volume.setActive(1);
                            musicPlaying = true;
                        }
                    }
                }
        );
        if (musicPlaying)
            volume.setActive(1);
        setInput(input = new Input(this, startGame, exitGame, volume, manual));
    }
    public Menu getMenu(){
        return this;
    }

    public static class Input extends InputAdapter{

        MyScreen parent;
        Button[] buttons;

        Input(MyScreen screen, Button... bs){
            parent = screen;
            buttons = bs;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {



            for (Button b : buttons){
                b.setTouchDown(Gdx.input.getX(),Gdx.input.getY());
            }
            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACK){
                parent.addScreen(new Exit(100, 20, 600, 420, parent));
            }
            return super.keyDown(keycode);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            for (Button b : buttons){
                b.touchUp(Gdx.input.getX(),Gdx.input.getY());
            }
            return super.touchUp(screenX, screenY, pointer, button);
        }
    }
    @Override
    public void render(float delta) {
            batch.draw(
                    background,
                    0, 0,
                    camera.viewportWidth, camera.viewportHeight
            );
        startGame.render(batch,
                (int)(camera.viewportWidth * 0.8f),
                (int)(camera.viewportHeight * 0.6f),
                150, 150);
        exitGame.render(batch,
                (int)(camera.viewportWidth * 0.8f),
                (int)(camera.viewportHeight * 0.2f),
                150, 150);
        volume.render(batch,
                (int)(camera.viewportWidth * 0.0f),
                (int)(camera.viewportHeight * 0.85f),
                70, 70);
        manual.render(batch,
                (int)(camera.viewportWidth * 0.0f),
                (int)((camera.viewportHeight * 0.85f) - 70),
                70, 70);
//        bitmapFont.getData().setScale(0.6f, 0.6f);
//        bitmapFont.draw(
//                batch, "Best score: " + SCORE,
//                camera.viewportWidth * 0.33f,camera.viewportHeight * 0.75f
//        );
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
        }
    public void yes(){
        audio.dispose();
        audio = null;
        ClientAssets.dispose();
        Gdx.app.exit();
    }
}
