package com.harissonappchat.firebasechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harissonappchat.firebasechat.R;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail,txtSenha;
    private Button btnLogin,btnRegistro;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = (EditText) findViewById(R.id.idEmailLogin);
        txtSenha = (EditText) findViewById(R.id.idSenhaLogin);
        btnLogin = (Button) findViewById(R.id.idLoginLogin);
        btnRegistro = (Button) findViewById(R.id.idRegistroLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                if(isValidEmail(email) && validarSenha()){
                    String senha = txtSenha.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                                        nextActivity();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Erro, credenciais incorretas", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }else{
                    Toast.makeText(LoginActivity.this, "Validações funcionando", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
            }
        });

    }
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarSenha(){
        String senha;
        senha = txtSenha.getText().toString();
        if(senha.length()>=6 && senha.length()<=16){
                return true;
            }else return false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Toast.makeText(this, "Usuário logado", Toast.LENGTH_SHORT).show();
            nextActivity();
        }

    }

    private void nextActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

}
