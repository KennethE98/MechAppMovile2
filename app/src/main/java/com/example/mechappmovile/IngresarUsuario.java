package com.example.mechappmovile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IngresarUsuario extends AppCompatActivity {
    private Button ingresar;
    private EditText email;
    private EditText pass;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_usuario);
        ingresar = (Button) findViewById(R.id.btnIniciarSesion);
        email = (EditText) findViewById(R.id.txtEmailIniciar);
        pass = (EditText) findViewById(R.id.txtPassIniciar);
        mAuth = FirebaseAuth.getInstance();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUsuario = email.getText().toString().trim();
                String passUsuario = pass.getText().toString().trim();
                if(email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()){
                    Toast.makeText(IngresarUsuario.this, "Campos vacios ", Toast.LENGTH_SHORT).show();
                }else{
                    loginUsuario(emailUsuario,passUsuario);


                }
            }
        });
    }
    private void loginUsuario(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(IngresarUsuario.this,Mapa_ubicacion.class));
                    Toast.makeText(IngresarUsuario.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(IngresarUsuario.this, "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}