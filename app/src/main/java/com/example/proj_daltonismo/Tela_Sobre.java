package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tela_Sobre extends AppCompatActivity {

    private View btnVolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sobre);

        btnVolta = findViewById(R.id.btnVolta);
        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}