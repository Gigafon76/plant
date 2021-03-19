package space.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import space.example.myapplication.Home.HomeActivity;

public class SplashActivity extends Activity {

    // Время в милесекундах, в течение которого будет отображаться Splash Screen
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // По истечении времени, запускаем главный активити, а Splash Screen закрываем
                Intent mainIntent = new Intent(space.example.myapplication.SplashActivity.this, HomeActivity.class);
                space.example.myapplication.SplashActivity.this.startActivity(mainIntent);
                space.example.myapplication.SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}