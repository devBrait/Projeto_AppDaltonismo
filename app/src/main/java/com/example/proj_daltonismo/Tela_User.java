package com.example.proj_daltonismo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Tela_User extends AppCompatActivity {

    private View btnVolta;
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;
    boolean senhaAberta;
    EditText nomeUsuario, emailUser, senhaUser, telefoneUser;
    private ImageView btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_user);

        IniciarComponentes();
        auth = FirebaseAuth.getInstance();

        // Faz o efeito do olho para ver a senha.
        senhaUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=senhaUser.getRight()-senhaUser.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=senhaUser.getSelectionEnd();
                        if(senhaAberta){
                            senhaUser.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);

                            senhaUser.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaAberta=false;
                        }
                        else {
                            senhaUser.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);

                            senhaUser.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaAberta=true;
                        }
                        senhaUser.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Tela_User.this, Tela_Inicial.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String senha = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String telefone = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nomeUsuario.setText(documentSnapshot.getString( "nome"));
                    emailUser.setText(documentSnapshot.getString( "email"));
                    senhaUser.setText(documentSnapshot.getString( "senha"));
                    telefoneUser.setText(documentSnapshot.getString("telefone"));
                }
            }
        });
    }

    private void IniciarComponentes(){
        nomeUsuario = findViewById(R.id.txtNome);
        emailUser = findViewById(R.id.txtEmail);
        senhaUser = findViewById(R.id.txtSenha);
        telefoneUser = findViewById(R.id.txtTelefone);
        btnVolta = findViewById(R.id.btnVolta);
        btnSair = findViewById(R.id.imgSair);
    }
}