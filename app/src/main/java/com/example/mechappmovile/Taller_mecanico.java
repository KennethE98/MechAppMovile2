package com.example.mechappmovile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Taller_mecanico extends AppCompatActivity {
    TextView txtNombreMec;
    TextView txtEmailMec;
    TextView txtProvincia;
    TextView txtCiudad;
    TextView txtDistancia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller_mecanico);
        txtNombreMec = (TextView) findViewById(R.id.txtmecanicoName);
        txtEmailMec = (TextView) findViewById(R.id.txtMecanicoEmail);
        txtProvincia = (TextView) findViewById(R.id.txtProvincia);
        txtCiudad = (TextView) findViewById(R.id.txtCiudad);
        txtDistancia = (TextView) findViewById(R.id.txtDistancia);

    }
}