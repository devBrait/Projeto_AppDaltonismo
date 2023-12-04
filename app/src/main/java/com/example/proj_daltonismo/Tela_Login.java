package com.example.proj_daltonismo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Tela_Login extends AppCompatActivity {

    private View btnVolta;
    private TextView btnCadastro;
    private FirebaseAuth auth;
    private Button btnEntrar;
    private EditText textSenha, textEmail;
    boolean senhaAberta;
    String[] mensagens = {"Sucesso", "Erro ao acessar a conta", "Senha não pode ser vazia", "Email não pode ser vazio"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        IniciarComponentes();

        textSenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=textSenha.getRight()-textSenha.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=textSenha.getSelectionEnd();
                        if(senhaAberta){
                            textSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);

                            textSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            senhaAberta=false;
                        }
                        else {
                            textSenha.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);

                            textSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            senhaAberta=true;
                        }
                        textSenha.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        auth = FirebaseAuth.getInstance();
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = textEmail.getText().toString();
                String Senha = textSenha.getText().toString();
                if(Email.isEmpty() || Senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view, mensagens[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    AutenticarUsuario(view);
                }

            }
        });

        btnVolta.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Inicial.class));
        });

        btnCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, Tela_Cadastro.class));
        });

      /*  btnEntrar.setOnClickListener(view -> {
            startActivity((new Intent(this, MainActivity.class)));

        });*/

    }

    private void IniciarComponentes(){
        btnVolta = findViewById(R.id.btnVolta);
        btnCadastro = findViewById(R.id.txtCadastro);
        btnEntrar = findViewById(R.id.btnLogin);
        textEmail = findViewById(R.id.txtEmail);
        textSenha = findViewById(R.id.txtSenha);
    }

    private void AutenticarUsuario(View view){
        String Email = textEmail.getText().toString();
        String Senha = textSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, Senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Snackbar snackbar = Snackbar.make(view, "Login bem-sucedido", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    TelaPrincipal();
                                }
                            }, 1000);
                        }else{
                            String erro = "Erro ao logar usuário";

                            Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                        }
                    }
                });
    }


    private void TelaPrincipal(){
        Intent intent = new Intent(Tela_Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}