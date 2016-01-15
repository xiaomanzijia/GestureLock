package com.licheng.github.gesturelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by licheng on 24/9/15.
 */
public class SettingGestureLockActivity extends Activity {
    private Button btn_setting,btn_valid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinggesture);
        btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_valid = (Button) findViewById(R.id.btn_valid);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingGestureLockActivity.this,GestureLockActivity.class);
                startActivity(intent);
            }
        });
        btn_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingGestureLockActivity.this,ValidGestureActivity.class);
                startActivity(intent);
            }
        });
    }
}
