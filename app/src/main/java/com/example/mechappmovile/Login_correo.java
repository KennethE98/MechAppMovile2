package com.example.mechappmovile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mechappmovile.Modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login_correo extends AppCompatActivity {
    private EditText nombre;
    private  EditText email;
    private  EditText contrasenia;
    private EditText contrasenia2;
    private Button aceptar;
    private  FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_correo);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();



        nombre = (EditText) findViewById(R.id.edtUserNombre);
        email = (EditText) findViewById(R.id.edtUserEmail);
        contrasenia = (EditText) findViewById(R.id.edtUserPassword);
        contrasenia2 = (EditText) findViewById(R.id.edtUserPassword2);
        aceptar = (Button) findViewById(R.id.btnRegistrarUsuario);



        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreU = nombre.getText().toString().trim();
                String emailU = email.getText().toString().trim();
                String passU = contrasenia.getText().toString().trim();
                String passU2 = contrasenia2.getText().toString().trim();

                if(nombreU.isEmpty() && emailU.isEmpty() && passU.isEmpty()){
                    Toast.makeText(Login_correo.this,"Complete Datos",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!passU.equals(passU2)){
                        Toast.makeText(Login_correo.this,"Contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                    }else{
                        registrarUsuario(nombreU,emailU,passU);
                    }
                }
            }
        });
    }
    private void updateProfileUser(FirebaseUser oU, String name, Uri foto){
        UserProfileChangeRequest oP = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).setPhotoUri(foto)
                .build();
        oU.updateProfile(oP).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()){
                    Toast.makeText(Login_correo.this, "PERFIL ACTUALIZDO CON EXITO", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_correo.this,Mapa_ubicacion.class));
                }else{
                    Toast.makeText(Login_correo.this, "NO SE ACTUALIZO EL PERFIL", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_correo.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarUsuario(String Usernombre, String Useremail, String Userpassword) {


        mAuth.createUserWithEmailAndPassword(Useremail,Userpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser oUser = mAuth.getCurrentUser();
                    if(oUser == null){
                        return;
                    }
                    /**UPLOAD FOTO**/
                    //StorageReference ref =
                    updateProfileUser(oUser,Usernombre,Uri.parse("https://firebasestorage.googleapis.com/v0/b/mechapp2-8f75e.appspot.com/o/batman.webp?alt=media"));
                }else{
                    Log.e("CREATE_USER","NO SUCCESS CREATE USER");
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.e("FAIL",e.toString());
            }
        });

    }
}