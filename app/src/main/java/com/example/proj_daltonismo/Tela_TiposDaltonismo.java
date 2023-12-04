package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tela_TiposDaltonismo extends AppCompatActivity {

    private View btnVolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_tipos_daltonismo);

        btnVolta = findViewById(R.id.btnVolta);
        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}