package com.harissonappchat.firebasechat.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.harissonappchat.firebasechat.AdapterMensagens;
import com.harissonappchat.firebasechat.Entidades.MensagemEnviar;
import com.harissonappchat.firebasechat.Entidades.MensagemReceber;
import com.harissonappchat.firebasechat.Entidades.Usuario;
import com.harissonappchat.firebasechat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nome;
    private RecyclerView rvMensagens;
    private EditText txtMensagem;
    private Button btnEnviar, encerrarSessao;

    private AdapterMensagens adapter;
    private ImageButton btnEnviarFoto;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCorda;

    private FirebaseAuth mAuth;
    private String NOME_USUARIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nome = (TextView) findViewById(R.id.nome);
        rvMensagens = (RecyclerView) findViewById(R.id.rvMensagens);
        txtMensagem = (EditText) findViewById(R.id.txtMensagem);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);
        encerrarSessao = (Button) findViewById(R.id.encerrarSessao);
        fotoPerfilCorda = "";


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chatV2");//Sala de chat (nome) version 2
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        adapter = new AdapterMensagens(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rvMensagens.setLayoutManager(linearLayoutManager1);
        rvMensagens.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new MensagemEnviar(txtMensagem.getText().toString(),NOME_USUARIO, fotoPerfilCorda, "1", ServerValue.TIMESTAMP));
                txtMensagem.setText("");
            }
        });

        encerrarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                returnLogin();

            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getIntent().ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecione uma foto"), PHOTO_SEND);
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getIntent().ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecione uma foto"), PHOTO_PERFIL);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensagemReceber m = dataSnapshot.getValue(MensagemReceber.class);
                adapter.addMensagem(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        verifyStoragePermissions(this);

    }

    private void setScrollBar() {
        rvMensagens.scrollToPosition(adapter.getItemCount() - 1);
    }

    public static boolean verifyStoragePermissions(Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_EXTERNAL_STORAGE = 1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("imagens_chat");//imagens_chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = data.getData();
                    MensagemEnviar m = new MensagemEnviar(NOME_USUARIO+"enviou uma foto", u.toString(),NOME_USUARIO,fotoPerfilCorda,"2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });
        } else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");//foto_perfil
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = data.getData();
                    fotoPerfilCorda = u.toString();
                    MensagemEnviar m = new MensagemEnviar(NOME_USUARIO+"atualizou a foto de perfil", u.toString(),NOME_USUARIO,fotoPerfilCorda,"2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                    Glide.with(MainActivity.this).load(u.toString()).into(fotoPerfil);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
             reference.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     Usuario usuario = dataSnapshot.getValue(Usuario.class);
                     NOME_USUARIO = usuario.getNome();
                     nome.setText(NOME_USUARIO);
                     btnEnviar.setEnabled(true);
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
        } else {
            returnLogin();
        }


    }

    private void returnLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }
}
