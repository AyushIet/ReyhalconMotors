package com.reyhalconmotors;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ramotion.circlemenu.CircleMenuView;

public class MainActivity extends AppCompatActivity {
    boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircleMenuView menu = findViewById(R.id.circle_menu);
        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart| index: " + index);
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
                if (index == 1) {
                    Intent intent = new Intent(MainActivity.this, EmployeeLoginActivity.class);
                    startActivity(intent);
                }
                if (index == 0) {
                    Intent intent = new Intent(MainActivity.this, AdminRegisterEmployee.class);
                    startActivity(intent);
                }
                if (index == 2) {
                    Intent intent = new Intent(MainActivity.this, ViewEmployeeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    //Double Tap to exit again logic
    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Press Back to exit again", Toast.LENGTH_SHORT).show();
        backPressed = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressed=false;
            }
        },2000);

    }
}
