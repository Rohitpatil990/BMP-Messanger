package com.example.bmpmessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    ImageView imgView ;
    TextView logoName, FromM,NameM;
    Animation topAnim,BottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgView = findViewById(R.id.LOgo);
        logoName = findViewById(R.id.logoMessage);
        FromM = findViewById(R.id.FromMesssage);
        NameM = findViewById(R.id.NameMessage);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        BottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        imgView.setAnimation(topAnim);
        logoName.setAnimation(BottomAnim);
        FromM.setAnimation(BottomAnim);
        NameM.setAnimation(BottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}