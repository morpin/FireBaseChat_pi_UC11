package com.harissonappchat.firebasechat.Activity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.harissonappchat.firebasechat.Entidades.Usuario;
import com.harissonappchat.firebasechat.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNome,txtEmail,txtSenha,txtSenhaRepetir;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNome = (EditText) findViewById(R.id.idRegistroNome);
        txtEmail = (EditText) findViewById(R.id.idRegistroEmail);
        txtSenha = (EditText) findViewById(R.id.idRegistroSenha);
        txtSenhaRepetir = (EditText) findViewById(R.id.idRegistroSenhaRepetir);
        btnRegistrar = (Button) findViewById(R.id.idRegistroRegistrar);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtEmail.getText().toString();
                final String nome = txtNome.getText().toString();
                if (isValidEmail(email) && validarSenha() && validarNome(nome)){
                    String senha = txtSenha.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegistroActivity.this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Usuario usuario = new Usuario();
                                        usuario.setEmail(email);
                                        usuario.setNome(nome);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                        reference.setValue(usuario);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistroActivity.this, "Erro ao se registrar.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }else{
                    Toast.makeText(RegistroActivity.this, "Validações funcionando", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarSenha(){
        String senha,senhaRepetir;
        senha = txtSenha.getText().toString();
        senhaRepetir = txtSenhaRepetir.getText().toString();
        if (senha.equals(senhaRepetir)){
            if(senha.length()>=6 && senha.length()<=16){
                return true;
            }else return false;
        }else return false;
    }

    public boolean validarNome(String nome){
        return !nome.isEmpty();
    }

}
