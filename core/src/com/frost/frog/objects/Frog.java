package com.frost.frog.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.frost.frog.Main;
import com.frost.frog.config.ConfigAssets;
import com.frost.frog.state.Game;

public class Frog extends Field {

    private Texture left;
    private Texture right;
    private Fields fields;
    private Field[][] fieldArray;
    private int minMoves = 9999;
    private Field minField;
    private Field fieldToAnim;
    private int x, y;
    private boolean leftMovie;
    private Game game;
    private float speed = 4.0f;

    private boolean stop = false;
    public boolean animated = false;
    public static boolean readyFrog = false;

    public Frog(int x, int y, int height, Game game, Fields fields) {
        super(x, y, height);
        this.fields = fields;
        fieldArray = fields.getFields();
        this.y = 5;
        this.x = 5;
        left = Main.assetManager.get(ConfigAssets.GAME_FROG.getOn());
        right = Main.assetManager.get(ConfigAssets.GAME_FROG.getOff());

        this.game = game;
        try {
            position.set(fieldArray[this.x][this.y].position.x, fieldArray[this.x][this.y].position.y);
            fieldArray[this.y][this.x].lock = true;
        }catch (NullPointerException e){
            game.restartGame();
            e.printStackTrace();
        }
        readyFrog = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (stop) return;
        edge();
        if (left != null || right != null){
            batch.draw(
                    (leftMovie) ? left : right,
                    position.x - 2 - radius,
                    position.y - radius + 2, radius * 2,
                    radius * 2
            );
            animationJump();
        }
        if (readyFrog)update();
    }
    private void edge(){
        if (animated) return;
        if (x == 10 || x == 0 || y == 10 || y == 0){
            game.lose();
            stop = true;
        }
    }
    private void update(){

        fields.resetCoins();
        minMoves = 9999;
        minField = null;
        int[] in = new int[6];
        boolean zero = true;
        start : while (zero) {
            zero = false;
            for (int i = 0; i < in.length; i++) {
                if (in[i] == 0) {
                    zero = true;
                }
            }
            int n = MathUtils.random(1, 6);
            switch (n) {
                case 1: {
                    if (in[0] == 1)
                        continue start;
                    else {
                        movie(x, y, "leftUp");
                        in[0] = 1;
                    }
                    break;
                }
                case 2: {
                    if (in[1] == 2)
                        continue start;
                    else {
                        movie(x, y, "right");
                        in[1] = 2;
                    }
                    break;
                }
                case 3: {
                    if (in[2] == 3)
                        continue start;
                    else {
                        movie(x, y, "rightUp");
                        in[2] = 3;
                    }
                    break;
                }
                case 4: {
                    if (in[3] == 4)
                        continue start;
                    else {
                        movie(x, y, "rightDown");
                        in[3] = 4;
                    }
                    break;
                }
                case 5: {
                    if (in[4] == 5)
                        continue start;
                    else {
                        movie(x, y, "leftDown");
                        in[4] = 5;
                    }
                    break;
                }
                case 6: {
                    if (in[5] == 6)
                        continue start;
                    else {
                        movie(x, y, "left");
                        in[5] = 6;
                    }
                    break;
                }
            }
        }
        try {
            Thread.sleep(10);
            swap(minField.getField());
        }catch (Throwable e){
            game.win();
            trash = true;
        }
        readyFrog = false;
    }
    private void swap(Field field){

        fieldArray[x][y].lock = false;
        int tmpY = y;
        int tmpX = x;
        animated = true;
        x = field.indexX;
        y = field.indexY;
        fieldToAnim = field;
        if (tmpX % 2 == 0){
            leftMovie = (tmpX == x && tmpY > y)
                     || (tmpX > x && tmpY == y)
                     || (tmpX < x && tmpY == y);
        }
        else{
            leftMovie = (tmpX == x && tmpY > y)
                    || (tmpX > x && tmpY > y)
                    || (tmpX < x && tmpY > y);
        }

        fieldArray[x][y].lock = true;
//        position.set(field.position.x, field.position.y);
    }
    private void animationJump(){
        if (fieldToAnim == null) return;

        float scaleX = fieldToAnim.position.x - position.x;
        float scaleY = fieldToAnim.position.y - position.y;
        float dx = 0, dy = 0;
        double gip = Math.sqrt(Math.pow(scaleX, 2) + Math.pow(scaleY, 2));

        dy += scaleY / gip;
        dx += scaleX / gip;

        dx *= speed;
        dy *= speed;

        position.add(Double.isNaN(dx) ? 0 : dx, Double.isNaN(dy) ? 0 : dy);

        if (closeToField(fieldToAnim, 3f)){
            position.set(fieldToAnim.position.x, fieldToAnim.position.y);
            fieldToAnim = null;
            animated = false;
        }
    }
    private boolean closeToField(Field field, float scale){
        return position.x >= field.position.x - scale &&
                position.x <= field.position.x + scale &&
                position.y >= field.position.y - scale &&
                position.y <= field.position.y + scale;

    }
    private void movie(int i, int j, String st){
        Field temp = null;
        int cost = 1;

        if (!fieldArray[i][j].lock && !fieldArray[i][j].trash){

            cost = fieldArray[i][j].cost;
            temp = fieldArray[i][j];

            if (fieldArray[i][j].getTop()){
                if (fieldArray[i][j].cost <= minMoves){
                    minMoves = fieldArray[i][j].cost;
                    minField = fieldArray[i][j];
                }
                return;
            }
            if (st.equals("leftUp")){
                if (--i % 2 == 0)
                    j--;
            }
            if (st.equals("rightUp")){
                if (--i % 2 != 0)
                    j++;
            }
            if (st.equals("left")){
                j--;
            }
            if (st.equals("right")){
                j++;
            }
            if (st.equals("rightDown")){
                if (++i % 2 != 0)
                    j++;
            }
            if (st.equals("leftDown")){
                if (++i % 2 == 0)
                    j--;
            }
            if (i < 0 || i > 10 || j < 0 || j > 10 || fieldArray[i][j] == null || fieldArray[i][j].trash) return;

            if (fieldArray[i][j].cost <= cost) return;

        }if (fieldArray[i][j] != null && fieldArray[i][j].lock){

            if (st.equals("leftUp")){
                if (--i % 2 == 0) {
                    j--;
                }
            }
            if (st.equals("rightUp")){
                if (--i % 2 != 0) {
                    j++;
                }
            }
            if (st.equals("left")){
                j--;
            }
            if (st.equals("right")){
                j++;
            }
            if (st.equals("rightDown")){
                if (++i % 2 != 0) {
                    j++;
                }
            }
            if (st.equals("leftDown")){
                if (++i % 2 == 0) {
                    j--;
                }
            }
            if (i < 0 || i > 10 || j < 0 || j > 10 || fieldArray[i][j] == null || fieldArray[i][j].trash) return;
            if (fieldArray[i][j].cost <= cost) return;
        }

        fieldArray[i][j].cost = cost + 1;
        fieldArray[i][j].field = temp;

        movie(i, j, "right");
        movie(i, j, "leftUp");
        movie(i, j, "leftDown");
        movie(i, j, "left");
        movie(i, j, "rightDown");
        movie(i, j, "rightUp");
    }
    public void dispose(){
        left.dispose();
        right.dispose();
    }
}
