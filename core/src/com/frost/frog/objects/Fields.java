package com.frost.frog.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Fields {

    public static Field field;

    private Field[][] fields;
    public Array<Integer[]> fieldZero = new Array<Integer[]>();
    private int radius;
    private int x, y;

    public Fields(int x, int y, int radius){
        for (Integer[] i : fieldZero){
            System.out.println("x:" + i[0] + "  |  y:" + i[1]);
        }
        fields = new Field[11][11];
        int xn = x;
        int yn = y;
        this.x = x;
        this.y = y;
        this.radius = radius;
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                fields[i][j] = new Field(xn, yn, this.radius);
                fields[i][j].indexX = i;
                fields[i][j].indexY = j;
                xn += radius * 2 + 2;
            }
            yn += radius * 2 - radius/4 + 2;
            if (i % 2 == 0)
                xn = this.x - radius;
            else xn = this.x;
        }
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++) {
                if (i == 0 || i == fields.length - 1){
                    fields[i][j].setTop();
                    fields[i][j].cost = 9999;
                }
                if (j == 0 || j == fields.length - 1){
                    fields[i][j].setTop();
                    fields[i][j].cost = 9999;
                }
            }
        }
        fieldZero.clear();
        while (fieldZero.size < 8){
            int indexX = index();
            int indexY = index();
            if (!isCopyIndex(indexX, indexY)){
                fields[indexX][indexY] = null;
            }
        }

        fieldZero.clear();
    }
    private boolean isCopyIndex(int x, int y){
        for (Integer[] i : fieldZero){
            if (i[0] == x && i[1] == y)
                return true;
        }
        fieldZero.add(new Integer[]{
                x, y
        });
        return false;
    }
    private int index(){
        int i = MathUtils.random(1, 9);
        return i == 5 ? index() : i;
    }
    public void resetCoins(){
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j] != null && !fields[i][j].trash) fields[i][j].cost = 9999;
            }
        }
    }
    public void render(SpriteBatch batch){
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if (fields[i][j] != null && !fields[i][j].trash) {
                    fields[i][j].render(batch);
                }
            }
        }
    }
    public boolean collision(int x, int y){
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if (fields[i][j] != null && !fields[i][j].trash && fields[i][j].collision(x, y)) {
                    field = fields[i][j];
                    return true;
                }
            }
        }
        return false;
    }
    public boolean collision(int x, int y, String txt){
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if (fields[i][j] != null && !fields[i][j].trash && fields[i][j].exist && fields[i][j].collision(x, y)) {
                    remove(i, j);
                    return true;
                }
            }
        }
        return false;
    }
    public void removeAll(){
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                if (fields[i][j] != null && !fields[i][j].trash)
                    fields[i][j].remove();
            }
        }
    }
    public void remove(int i, int j){
        fields[i][j].remove();
    }
    public Field[][] getFields(){
        return fields;
    }
}
