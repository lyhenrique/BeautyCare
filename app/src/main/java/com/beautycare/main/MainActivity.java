package com.beautycare.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.beautycare.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.start, null);
        setContentView(view);

        //TypeFace  font setting
        Typeface fontFace = Typeface.createFromAsset(getAssets(),"fonts/segoe print.ttf");
        TextView logo_en = (TextView) findViewById(R.id.logo_en);
        TextView logo_zh = (TextView) findViewById(R.id.logo_zh);
        logo_en.setTypeface(fontFace);
        logo_zh.setTypeface(fontFace);

        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(3500);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }
    /**
     * 跳转到...
     */
    private void redirectTo(){
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}
