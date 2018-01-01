package com.frost.frog;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.frost.frog.config.ConfigAssets;

public class ClientAssets {

    private static AssetManager assetManager;

    public static void loadAssetsTexture(ConfigAssets.Element... elements){
        instance();
        for (ConfigAssets.Element element : elements) {
            if (element.getOn() != null)
                assetManager.load((String) element.getOn(), Texture.class);
            if (element.getOff() != null)
                assetManager.load((String) element.getOff(), Texture.class);
        }
        assetManager.finishLoading();
    }
    public static void loadAssetsFont(ConfigAssets.Element... elements){
        instance();
        for (ConfigAssets.Element element : elements) {
            if (element.getOn() != null)
                assetManager.load((String) element.getOn(), BitmapFont.class);
            if (element.getOff() != null)
                assetManager.load((String) element.getOff(), BitmapFont.class);
        }
        assetManager.finishLoading();
    }
    public static AssetManager instance(){
        return (assetManager != null) ? assetManager : (assetManager = new AssetManager());
    }
    public static void dispose(){
        assetManager.clear();
        assetManager.dispose();
        assetManager.finishLoading();
        assetManager = null;
    }
    public static String toPrint(){

        String names = "";
        for (String st : assetManager.getAssetNames()){
            names += "\n" + st;
        }
        return names;
    }
}
