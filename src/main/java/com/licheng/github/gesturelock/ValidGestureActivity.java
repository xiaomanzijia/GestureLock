package com.licheng.github.gesturelock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import java.util.List;

/**
 * Created by licheng on 24/9/15.
 */
public class ValidGestureActivity extends Activity {
    private GestureLockView gestureLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validgesture);
        SharedPreferences sp = getSharedPreferences("password",this.MODE_PRIVATE);
        final String password = sp.getString("password", "");

        gestureLockView = (GestureLockView) findViewById(R.id.gestureview);
        gestureLockView.setOnDrawFinishedListener(new GestureLockView.onDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(List<Integer> passedpointlist) {
                StringBuffer sb = new StringBuffer();
                for(Integer i : passedpointlist){
                    sb.append(i);
                }
                if(sb.toString().equals(password)){
                    Toast.makeText(getApplicationContext(),"密码正确",Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }
}
