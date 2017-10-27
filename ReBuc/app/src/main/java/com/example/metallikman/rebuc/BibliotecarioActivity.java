package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BibliotecarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecario);
    }

    public void logout(View v){
        new User(BibliotecarioActivity.this).remove();
        Intent intent = new Intent(BibliotecarioActivity.this, Login.class);
        startActivity(intent);
        finish();

    }

    public void iniciarLevantarTicket(View v){
        Intent intent = new Intent(BibliotecarioActivity.this, LevantarTicketActivity.class);
        startActivity(intent);
    }
}
