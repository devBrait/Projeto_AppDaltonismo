package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Tela_QuestaoQuatro extends AppCompatActivity {

    private static final String TAG = "Erro";
    private Button btnResultado;
    EditText txtResposta;
    String[] mensagens = {"Preencha todos os campos"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_questao_quatro);

        IniciarComponentes();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnResultado.setOnClickListener(view -> {
            try {
                if (txtResposta.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    int repost = Integer.parseInt(txtResposta.getText().toString());
                    db.collection("Usuarios").document(usuarioID).update("resp4", repost);
                    startActivity(new Intent(this, Tela_Resultado.class));
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing resposta to integer: " + e.getMessage());
            }
        });

    }

    private void IniciarComponentes(){
        txtResposta = findViewById(R.id.txtRespostaQuatro);
        btnResultado = findViewById(R.id.btnProximaQuestao);
    }
}