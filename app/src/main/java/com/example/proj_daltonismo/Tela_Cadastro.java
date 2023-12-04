package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Tela_Cadastro extends AppCompatActivity {
    // Declaração das Variaveis
    private FirebaseAuth auth;
    private TextView btnLogin;
    private EditText txtNome, txtEmail, txtTelefone, txtSenha;
    private View btnVolta;
    private Button btnInicial;
    boolean senhaAberta;
    String usuarioID;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso", "Erro ao cadastrar usuário"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        IniciarComponentes();

        // Faz o efeito do olho para ver a senha.
        txtSenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=txtSenha.getRight()-txtSenha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=txtSenha.getSelectionEnd();
                        if(senhaAberta){
                            txtSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);

                            txtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaAberta=false;
                        }
                        else {
                            txtSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);

                            txtSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaAberta=true;
                        }
                        txtSenha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        auth = FirebaseAuth.getInstance();

        btnInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();

                if(user.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    auth.createUserWithEmailAndPassword(user, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                                SalvarDadosUsuario();
                                startActivity(new Intent(Tela_Cadastro.this, Tela_Login.class));
                            }else{
                                Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                            }
                        }
                    });
                }
            }
        });
        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Inicial.class));
        });

        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Login.class));
        });


    }
    // Área das Funções:

    // Função que inicializa todas as variaveis
    private void IniciarComponentes(){
        txtEmail = findViewById(R.id.txtEmail);
        txtNome = findViewById(R.id.txtNome);
        txtSenha = findViewById(R.id.txtSenha);
        txtTelefone = findViewById(R.id.txtTelefone);
        btnVolta = findViewById(R.id.btnVolta);
        btnLogin = findViewById(R.id.txtLogin);
        btnInicial = findViewById(R.id.btnInicial);
    }
    // Cria tabela no firebaseStore
    private void SalvarDadosUsuario(){

        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String senha = txtSenha.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("email", email);
        usuarios.put("telefone", telefone);
        usuarios.put("senha", senha);
        usuarios.put("resp1", 0);
        usuarios.put("resp2", 0);
        usuarios.put("resp3", 0);
        usuarios.put("resp4", 0);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db", "Sucesso ao salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar os dados" + e.toString());
                    }
                });

    }

}