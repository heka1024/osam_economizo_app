package com.example.tooth;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    public int[] moneys;
    public MainAdapter(int[] u) {
        this.moneys = u;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            this.textView = view.findViewById(R.id.recycleItem);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder= new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int index) {
        myViewHolder.textView.setText(Integer.toString(this.moneys[index]));
    }


    @Override
    public int getItemCount() {
        return moneys.length;
    }

}