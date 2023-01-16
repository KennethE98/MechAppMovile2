package com.example.mechappmovile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mechappmovile.Modelo.mecanico;
import com.example.mechappmovile.R;

import java.util.ArrayList;

public class cAdapterMecanicos extends RecyclerView.Adapter<cAdapterMecanicos.cViewHolderMecanico>
{
    private ArrayList<mecanico> mecanicoArrayList;

    public cAdapterMecanicos(ArrayList<mecanico> mecanicoArrayList) {
        this.mecanicoArrayList = mecanicoArrayList;
    }

    @NonNull
    @Override
    public cViewHolderMecanico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mecanico_item,parent,false);
        return new cViewHolderMecanico(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderMecanico holder, int position)
    {
        holder.oTextViewName.setText(mecanicoArrayList.get(position).getFull_name().toString());
        holder.oTextViewDir.setText(mecanicoArrayList.get(position).getDireccion().toString());
        holder.oTextViewDist.setText(mecanicoArrayList.get(position).getDistancia().toString());
    }

    @Override
    public int getItemCount() {
        return mecanicoArrayList.size();
    }

    public class cViewHolderMecanico extends RecyclerView.ViewHolder
    {
        private TextView oTextViewName;
        private TextView oTextViewDir;
        private TextView oTextViewDist;
        public cViewHolderMecanico(@NonNull View itemView)
        {
            super(itemView);
            oTextViewName = itemView.findViewById(R.id.txtNameMecanico);
            oTextViewDir = itemView.findViewById(R.id.txtDirMecanico);
            oTextViewDist = itemView.findViewById(R.id.txtDistanceMecanico);
        }
    }
}

