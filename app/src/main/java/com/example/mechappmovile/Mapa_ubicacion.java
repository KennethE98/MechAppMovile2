package com.example.mechappmovile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mechappmovile.Modelo.mecanico;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.security.Permissions;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class Mapa_ubicacion extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private Button sos;

    private MapView oMapView;
    private GoogleMap oGoogleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker oMarkerGps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ubicacion);

        sos = (Button) findViewById(R.id.btnSOS);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        this.oMapView = findViewById(R.id.MapView);

        if (this.oMapView != null) {
            this.oMapView.onCreate(null);
            this.oMapView.onResume();
            this.oMapView.getMapAsync(this::onMapReady);
        }



        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Mapa_ubicacion.this, Listado_talleres.class);
                startActivity(i);*/
                getCurrentLocation();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Log.e("LOCATION_FINE","SIN PERMISOS");
        }
    }

    private void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(oGoogleMap != null){
                    oMarkerGps =  oGoogleMap.addMarker(new MarkerOptions().title("MI UBICACION").position(new LatLng(location.getLatitude(),location.getLongitude())));
                    oGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),18));
                    /****/

                    //Location.distanceBetween(location.getLatitude(),location.getLongitude(),,);
                    Intent oI = new Intent(Mapa_ubicacion.this,ListMecanicoActivity.class);
                    oI.putExtra("lat",oMarkerGps.getPosition().latitude);
                    oI.putExtra("lng",oMarkerGps.getPosition().longitude);
                    oI.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(oI);
                }
            }
        });
    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.oGoogleMap = googleMap;
        this.oGoogleMap.setOnMarkerClickListener(this::onMarkerClick);
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}