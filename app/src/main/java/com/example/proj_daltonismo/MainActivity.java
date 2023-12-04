package com.example.proj_daltonismo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    // Declara as variáveis dos componentes e do Banco de Dados.
    private TextView txtNomeUsuario, txtCorLida;
    private ImageView btnSobre, btnTiposDaltonismo, imgCorExibida, btnTesteDaltonismo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenceEntrada1 = database.getReference("Valores");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;
    private View btnTelaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        IniciarComponentes();

        // Esse trecho do código é qual verifica a cor enviada para o Firebase e exibe no aplicativo
        // A imagem da tela principal também é alterada conforme a mensagem da cor exibida
        referenceEntrada1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cor = snapshot.child("Cores").getValue().toString();
                txtCorLida.setText(cor);

                if("Vermelho".equals(cor)){
                    txtCorLida.setTextColor(getResources().getColor(R.color.vermelho));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.circulo_vermelho));
                }else if("Verde".equals(cor)){
                    txtCorLida.setTextColor(getResources().getColor(R.color.verde));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.circulo_verde));
                }else if("Azul".equals(cor)){
                    txtCorLida.setTextColor(getResources().getColor(R.color.azul));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.circulo_azul));
                }else if("Preto".equals(cor)){
                    txtCorLida.setTextColor(getResources().getColor(R.color.black));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.circulo_preto));
                }else if("Branco".equals(cor)){
                    txtCorLida.setTextColor(getResources().getColor(R.color.black));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.circulo_branco));
                } else{
                    txtCorLida.setTextColor(getResources().getColor(R.color.black));
                    imgCorExibida.setImageDrawable(getResources().getDrawable(R.drawable.ic_color));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Ação que de botões que vão para outras telas do APP
        btnSobre.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Sobre.class));
        });

        btnTiposDaltonismo.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_TiposDaltonismo.class));
        });

        btnTelaUser.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_User.class));
        });

        btnTesteDaltonismo.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_TesteDaltonismo.class));
        });
    }

    // Carrega o nome do usuário que está logado na conta
    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    txtNomeUsuario.setText(documentSnapshot.getString( "nome"));
                }
            }
        });
    }
    // Função Para Inicializar as variáveis
    private void IniciarComponentes(){
        txtNomeUsuario = findViewById(R.id.txtNome);
        btnSobre = findViewById(R.id.imgTelaSobre);
        btnTiposDaltonismo = findViewById(R.id.imgTelaTiposDaltonismo);
        btnTelaUser = findViewById(R.id.btnUser);
        txtCorLida = findViewById(R.id.txtCorExibida);
        imgCorExibida = findViewById(R.id.imgCor);
        btnTesteDaltonismo = findViewById(R.id.imgTeste);
    }
}