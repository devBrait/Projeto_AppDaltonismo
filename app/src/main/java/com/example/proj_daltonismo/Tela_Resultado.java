package com.example.proj_daltonismo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Tela_Resultado extends AppCompatActivity {

    private Button btnInicio;
    private TextView txtResultadoFinal;
    String usuarioID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        IniciarComponentes();

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tela_Resultado.this, MainActivity.class));
            }
        });


    }

    private void IniciarComponentes(){
        btnInicio = findViewById(R.id.btnVoltarHome);
        txtResultadoFinal = findViewById(R.id.txtResultado);
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    long resposta1 = documentSnapshot.getLong("resp1");
                    long resposta2 = documentSnapshot.getLong("resp2");
                    long resposta3 = documentSnapshot.getLong("resp3");
                    long resposta4 = documentSnapshot.getLong("resp4");
                    if(resposta1 == 12 && resposta2 == 8 && resposta3 == 5 && resposta4 == 29) {
                        txtResultadoFinal.setText("Você não possui nenhum tipo de daltonismo, mas é crucial valorizar e respeitar aqueles que vivenciam essa condição visual. Reconhecendo a diversidade de experiências, contribuímos para um ambiente inclusivo e respeitoso para todos.");
                    }else{
                        txtResultadoFinal.setText("Muito provavelmente você tem daltonismo procure um médico oftalmologista para confirmar a sua situação. A avaliação de um profissional de saúde é essencial para obter um diagnóstico preciso.");
                    }

                }
            }
        });
    }


}