package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import modelos.User;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();
        final User user=new User(SplashActivity.this);
        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(user.getEmail()!=""){
                    String rol=user.getRol();
                    if(rol.equals("1")) {

                    }else if (rol.equals("2")){
                        Intent intent = new Intent(SplashActivity.this, BibliotecarioActivity.class);
                        startActivity(intent);
                        finish();

                    }else if(rol.equals("3")){
                        Intent intent = new Intent(SplashActivity.this, UniversitarioActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(rol.equals("4")){
                        Intent intent = new Intent(SplashActivity.this, ResponsableActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else{
                    Intent intent= new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
