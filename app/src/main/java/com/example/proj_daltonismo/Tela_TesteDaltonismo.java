package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tela_TesteDaltonismo extends AppCompatActivity {

    private View btnVolta;
    private Button btnIniciaTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_teste_daltonismo);

        IniciarComponentes();

        btnIniciaTeste.setOnClickListener(view -> {
            startActivity(new Intent( this, Tela_QuestaoUm.class));
        });

        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void IniciarComponentes(){
        btnVolta = findViewById(R.id.btnVolta);
        btnIniciaTeste = findViewById(R.id.btnIniciarTeste);

    }
}