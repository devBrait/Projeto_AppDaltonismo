package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Tela_Inicial extends AppCompatActivity {

    private Button btnLogin, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Proj_Daltonismo);

        setContentView(R.layout.activity_tela_inicial);
        
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Login.class));
        });

         btnCadastro = findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Cadastro.class));
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            TelaPrincipal();
        }
    }

    private void TelaPrincipal(){
        Intent intent = new Intent(Tela_Inicial.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}