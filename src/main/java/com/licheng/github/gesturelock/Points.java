package com.licheng.github.gesturelock;

/**
 * Created by licheng on 23/9/15.
 */
public class Points {
    private float x;
    private float y;

    private int status = 0;

    public static int NORMAL=0;
    public static int PRESSED=1;
    public static int ERROR=2;

    public Points(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float distance(Points p){
        float distance = (float) Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y));
        return distance;
    }
}
