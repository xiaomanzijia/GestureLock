package com.licheng.github.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licheng on 23/9/15.
 */
public class GestureLockView extends View {

    private Points[][] pointsites = new Points[3][3];
    private Paint paint = new Paint();
    private boolean isInit = false;

    private float mouseX;
    private float mouseY;
    private boolean isDraw = false;

    private float circlrR = 0.0f;

    private ArrayList<Points> pointlist = new ArrayList<Points>();
    private ArrayList<Integer> passpointlist = new ArrayList<Integer>();

    private Paint pressPaint = new Paint();
    private Paint errorPaint = new Paint();

    private onDrawFinishedListener listener;

    public GestureLockView(Context context) {
        super(context);
    }

    public GestureLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            init();
        }
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(pointsites[i][j].getStatus()==Points.NORMAL){
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(pointsites[i][j].getX(),pointsites[i][j].getY(),circlrR,paint);
                }
                if(pointsites[i][j].getStatus()==Points.PRESSED){
                    paint.setColor(Color.YELLOW);
                    canvas.drawCircle(pointsites[i][j].getX(),pointsites[i][j].getY(),circlrR,paint);
                }
                if(pointsites[i][j].getStatus()==Points.ERROR){
                    paint.setColor(Color.RED);
                    canvas.drawCircle(pointsites[i][j].getX(),pointsites[i][j].getY(),circlrR,paint);
                }
            }
        }

        if(pointlist.size()>0){
            Points a = pointlist.get(0);
            for(int i = 1;i<pointlist.size();i++){
                Points b = pointlist.get(i);
                drawLine(canvas,a,b);
                a=b;
            }
            if(isDraw){
                drawLine(canvas,a,new Points(mouseX,mouseY));
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        int[] ij;
        int i;
        int j;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                resetPoints();
                ij = getSelectedPoint();
                if(ij!=null){
                    isDraw = true;
                    i = ij[0];
                    j = ij[1];
                    pointsites[i][j].setStatus(Points.PRESSED);
                    pointlist.add(pointsites[i][j]);
                    passpointlist.add(i*3+j);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDraw){
                    ij = getSelectedPoint();
                    if(ij!=null){
                        i = ij[0];
                        j = ij[1];
                        if(!pointlist.contains(pointsites[i][j])){
                            pointsites[i][j].setStatus(Points.PRESSED);
                            pointlist.add(pointsites[i][j]);
                            passpointlist.add(i*3+j);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean valid = false;
                if(listener!=null&&isDraw){
                    valid = listener.onDrawFinished(passpointlist);
                }
                if(!valid){
                    for(Points p : pointlist){
                        p.setStatus(Points.ERROR);
                    }
                }
                isDraw = false;
                break;
        }
        //刷新界面
        this.postInvalidate();
        return true;
    }

    private int[] getSelectedPoint(){
        Points pMouse = new Points(mouseX,mouseY);
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(pointsites[i][j].distance(pMouse)<circlrR){
                    int[] result = new int[2];
                    result[0]=i;
                    result[1]=j;
                    return result;
                }
            }
        }
        return null;
    }

    private void drawLine(Canvas canvas,Points a,Points b){
        if(a.getStatus()==Points.PRESSED){
            canvas.drawLine(a.getX(),a.getY(),b.getX(),b.getY(),pressPaint);
        }
        if(a.getStatus()==Points.ERROR){
            canvas.drawLine(a.getX(),a.getY(),b.getX(),b.getY(),errorPaint);
        }
    }

    private void init() {
        float width = getWidth();
        float height = getHeight();
        float offset = Math.abs(width-height)/2;
        float space = width/4;

        //space应该大于圆直径的2倍
        circlrR = 0.32f * space;

        pointsites[0][0] = new Points(space,offset+space);
        pointsites[0][1] = new Points(2*space,offset+space);
        pointsites[0][2] = new Points(3*space,offset+space);
        pointsites[1][0] = new Points(space,offset+2*space);
        pointsites[1][1] = new Points(2*space,offset+2*space);
        pointsites[1][2] = new Points(3*space,offset+2*space);
        pointsites[2][0] = new Points(space,offset+3*space);
        pointsites[2][1] = new Points(2*space,offset+3*space);
        pointsites[2][2] = new Points(3*space,offset+3*space);


        errorPaint.setColor(Color.RED);
        errorPaint.setStrokeWidth(5);

        pressPaint.setColor(Color.YELLOW);
        pressPaint.setStrokeWidth(5);
        isInit=true;
    }

    public void resetPoints(){
        pointlist.clear();
        passpointlist.clear();
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                pointsites[i][j].setStatus(Points.NORMAL);
            }
        }
        this.postInvalidate();
    }

    public interface onDrawFinishedListener{
        boolean onDrawFinished(List<Integer> passedpointlist);
    }

    public void setOnDrawFinishedListener(onDrawFinishedListener listener){
        this.listener = listener;
    }
}
