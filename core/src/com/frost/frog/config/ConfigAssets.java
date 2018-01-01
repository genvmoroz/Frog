package com.frost.frog.config;

import static com.frost.frog.config.ConfigAssets.Element.DEFAULT_ROOT;

public interface ConfigAssets {

    Element MENU_PLAY = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "playOff.png",
            DEFAULT_ROOT  + "buttonMenu/" + "playOff.png"
    );
    Element MENU_EXIT = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "exitOff.png",
            DEFAULT_ROOT  + "buttonMenu/" + "exitOff.png"
    );
    Element MENU_AUDIO = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "volumeOn.png",
            DEFAULT_ROOT  + "buttonMenu/" + "volumeOff.png"
    );
    Element GAME_MENU = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "menuOff.png",
            DEFAULT_ROOT  + "buttonMenu/" + "menuOff.png"
    );
    Element GAME_RESTART = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "restartOff.png",
            DEFAULT_ROOT  + "buttonMenu/" + "restartOff.png"
    );
    Element MENU_DIALOG_YES = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "yesOn.png",
            DEFAULT_ROOT  + "buttonMenu/" + "yesOff.png"
    );
    Element MENU_DIALOG_NO = new Element(
            DEFAULT_ROOT  + "buttonMenu/" + "noOn.png",
            DEFAULT_ROOT  + "buttonMenu/" + "noOff.png"
    );
    Element GAME_FROG = new Element(
            DEFAULT_ROOT  + "object/" + "frog_left.png",
            DEFAULT_ROOT  + "object/" + "frog_right.png"
    );
    Element GAME_CIRCLE = new Element(
            DEFAULT_ROOT  + "object/" + "field.png",
            DEFAULT_ROOT  + "object/" + "field.png"
    );
    Element MENU_BACKGROUND = new Element(
            DEFAULT_ROOT  + "background/" + "backgroundMenu.png",
            DEFAULT_ROOT  + "background/" + "backgroundMenu.png"
    );
    Element GAME_BACKGROUND = new Element(
            DEFAULT_ROOT  + "background/" + "background.png",
            DEFAULT_ROOT  + "background/" + "background.png"
    );
    Element DIALOG_EXIT = new Element(
            DEFAULT_ROOT  + "exit.png",
            DEFAULT_ROOT  + "exit.png"
    );
    Element DIALOG_EXIT_GREEN = new Element(
            DEFAULT_ROOT  + "win.png",
            DEFAULT_ROOT  + "win.png"
    );
    Element LOADING_BACKGROUND = new Element(
            DEFAULT_ROOT  + "loading.jpg",
            DEFAULT_ROOT  + "loading.jpg"
    );
    Element LOADING_LOGOTIP = new Element(
            DEFAULT_ROOT  + "log.jpg",
            DEFAULT_ROOT  + "log.jpg"
    );
    Element GAME_DIALOG_RESTART = new Element(
            DEFAULT_ROOT + "buttonMenu/" + "game_dialog_restart_on.png",
            DEFAULT_ROOT + "buttonMenu/" + "game_dialog_restart_off.png"
    );
    Element MENU_MANUAL = new Element(
            DEFAULT_ROOT + "buttonMenu/" + "manual.png",
            DEFAULT_ROOT + "buttonMenu/" + "manual.png"
    );
    Element FONT = new Element(
            DEFAULT_ROOT + "font/" + "1.fnt",
            DEFAULT_ROOT + "font/" + "1.fnt"
    );

    class Element{

        public static String DEFAULT_ROOT = "default/";

        private String on;
        private String off;

        Element(String s, String t){
            this.on = s;
            this.off = t;
        }
        public String getOn(){
            return on;
        }
        public String getOff(){
            return off;
        }
    }
}

