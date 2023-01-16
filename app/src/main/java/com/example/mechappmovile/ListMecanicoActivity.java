package com.example.mechappmovile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mechappmovile.Modelo.mecanico;
import com.example.mechappmovile.adapter.cAdapterMecanicos;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ListMecanicoActivity extends AppCompatActivity {

    private ArrayList<mecanico> mListMecanicos = new ArrayList<>();
    private DatabaseReference mDatabase;
    private double LatitudOrigin;
    private double LongitudOrigin;
    private RecyclerView oRecyclerView;
    private MaterialToolbar oMaterialToolbar;

    private cAdapterMecanicos oCAdapterMecanicos;
    private CircularProgressIndicator oCircularProgressIndicator;
    private SwipeRefreshLayout oSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mecanico);
        this.oRecyclerView = findViewById(R.id.RecyclerViewMecanicos);
        this.oMaterialToolbar = findViewById(R.id.ToolbarMecanico);
        this.oCircularProgressIndicator = findViewById(R.id.CircularProgressIndicator);
        this.oSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayoutMecanicos);

        this.LatitudOrigin = getIntent().getDoubleExtra("lat",0);
        this.LongitudOrigin = getIntent().getDoubleExtra("lng",0);

        this.oCAdapterMecanicos = new cAdapterMecanicos(mListMecanicos);

        this.oRecyclerView.setAdapter(this.oCAdapterMecanicos);


        mDatabase = FirebaseDatabase.getInstance().getReference("mecanico");
        initFirebaseDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.oSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                oSwipeRefreshLayout.setRefreshing(true);
                initFirebaseDatabase();
            }
        });
    }

    private double distance(double latO, double lngO, double latD, double lngD)
    {
        Location locationA = new Location("punto A");

        locationA.setLatitude(latO);
        locationA.setLongitude(lngO);

        Location locationB = new Location("punto B");

        locationB.setLatitude(latD);
        locationB.setLongitude(lngD);

        return locationA.distanceTo(locationB)/1000;
    }

    private void initFirebaseDatabase()
    {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if (snapshot.exists()){
                    mListMecanicos.clear();
                    oCAdapterMecanicos.notifyDataSetChanged();
                    for (DataSnapshot mDataSnapshot : snapshot.getChildren())
                    {
                        try {
                            Log.e("CHILD",mDataSnapshot.getKey());
                            mecanico oM = new mecanico();
                            oM.setIdKey(mDataSnapshot.getKey());
                            oM.setFull_name(mDataSnapshot.child("full_name").getValue().toString());
                            oM.setDireccion(mDataSnapshot.child("direccion").getValue().toString());

                            double latD = Double.parseDouble(mDataSnapshot.child("latitud").getValue().toString());
                            double lngD = Double.parseDouble(mDataSnapshot.child("longitud").getValue().toString());

                            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
                            decimalFormatSymbols.setDecimalSeparator('.');
                            DecimalFormat mDecimalFormat = new DecimalFormat("##.00",decimalFormatSymbols);

                            oM.setDistancia(mDecimalFormat.format(distance(LatitudOrigin,
                                    LongitudOrigin,latD,lngD))+" KM");
                            Log.e("DISTANCE",oM.getDistancia());

                            mListMecanicos.add(oM);
                        }catch (Exception e){
                            Toast.makeText(ListMecanicoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    oCircularProgressIndicator.setVisibility(View.GONE);
                    oCAdapterMecanicos.notifyDataSetChanged();
                    oSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                oCircularProgressIndicator.setVisibility(View.GONE);
                oSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ListMecanicoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}