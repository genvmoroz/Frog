package com.frost.frog.state;

import com.badlogic.gdx.graphics.Texture;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;

public class Loading extends MyScreen {

    public Texture back;
    public Texture logotip;
    float x = 0;
    String state = "menu";

    private MyScreen screen;

    public Loading(Main main, String state) {
        super(main);
        setInput(null);
        this.state = state;
        back = Main.assetManager.get(ConfigAssets.LOADING_BACKGROUND.getOn());
        logotip = Main.assetManager.get(ConfigAssets.LOADING_LOGOTIP.getOn());
    }

    @Override
    public void render(float delta) {

        batch.draw(state.equals("start") ? logotip : back, 0, 0, camera.viewportWidth, camera.viewportHeight);

        if (state.equals("start")) {
            if (Main.assetManager.update() && (x += delta) >= 3.5f) {
                screen = new Menu(main);
                main.setScreen(screen);
            }
        }else if ((x += delta) >= 0.5f) {

            if (state.equals("menu")) {
                screen = new Menu(main);
                main.setScreen(screen);
            }
            if (state.equals("game")) {
                screen = new Game(main);
                main.setScreen(screen);
            }
        }
    }
}
