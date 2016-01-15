package com.licheng.github.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by licheng on 23/9/15.
 */
public class GestureLockActivity extends Activity{
    private GestureLockView gestureLockView;
    private Button btn_reset,btn_save;
    private List<Integer> passlist = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturelocker);
        gestureLockView = (GestureLockView) findViewById(R.id.gesture);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_save = (Button) findViewById(R.id.btn_save);

        gestureLockView.setOnDrawFinishedListener(new GestureLockView.onDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(List<Integer> passedpointlist) {
                if(passedpointlist.size() < 3){
                    Toast.makeText(getApplicationContext(),"密码不能少于3个点",Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    passlist = passedpointlist;
                    return true;
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestureLockView.resetPoints();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passlist!=null){
                    StringBuilder sb = new StringBuilder();
                    for(Integer i:passlist){
                        sb.append(i);
                    }
                    SharedPreferences sp = GestureLockActivity.this.getSharedPreferences("password", GestureLockActivity.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password",sb.toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"密码"+sb.toString()+"保存成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GestureLockActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
