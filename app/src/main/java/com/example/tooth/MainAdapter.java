package com.example.tooth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private ArrayList<Integer> moneys = new ArrayList<>();

    public MainAdapter(ArrayList<Integer> u) {
        this.moneys = u;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView moneyView;
        public TextView dateView;

        public MyViewHolder(View view) {
            super(view);
            this.moneyView = view.findViewById(R.id.usedMoney);
            this.dateView = view.findViewById(R.id.textDate);
        }

    }

    public void addItem(int new_val) {
        moneys.add(new_val);
        notifyItemInserted(moneys.size() - 1);
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
        DecimalFormat formatter = new DecimalFormat("###,###");
        String money_string = formatter.format(this.moneys.get(index))  + "원";
        myViewHolder.moneyView.setText(money_string);

        Calendar c = Calendar.getInstance();
        String now_string = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
        myViewHolder.dateView.setText(now_string);
    }


    @Override
    public int getItemCount() {
        return moneys.size();
    }

}