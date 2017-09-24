package com.laoyao.normal.imusic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //开始动画
        ImageView back = (ImageView) findViewById(R.id.splash_background) ;
        Animation backAnimation = AnimationUtils.loadAnimation(this , R.anim.activity_scale_appear) ;
        back.startAnimation(backAnimation);

        //开始动画
        ImageView icon = (ImageView) findViewById(R.id.splash_image) ;
        Animation iconAnimation = AnimationUtils.loadAnimation(this , R.anim.splash) ;
        iconAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                Intent toMain = new Intent(Splash.this , MainActivity.class) ;
                startActivity(toMain);
                overridePendingTransition(R.anim.activity_scale_appear , R.anim.activity_scale_disappear);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        icon.startAnimation(iconAnimation);
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.activity_scale_appear , R.anim.activity_scale_disappear);
    }
}
