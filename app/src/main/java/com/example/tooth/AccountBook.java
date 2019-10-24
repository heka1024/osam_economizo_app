package com.example.tooth;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountBook extends AppCompatActivity {
    private ArrayList<Integer> ints = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstance) {
        ints.add(12000);
        ints.add(36000);
        ints.add(25000);

        super.onCreate(savedInstance);
        setContentView(R.layout.layout_account_book);

        recyclerView = findViewById(R.id.main_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(ints);
        recyclerView.setAdapter(adapter);

        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.homeMenu:
                intent = new Intent(AccountBook.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.accountBook:
                intent = new Intent(AccountBook.this, AccountBook.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    public void setup() {
        Button addSpendLog = findViewById(R.id.addFinish);
        addSpendLog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText spendMoney = findViewById(R.id.inputUsedMoney);
                int tmp_val = Integer.parseInt(spendMoney.getText().toString().trim());
                ints.add(0, tmp_val);
                adapter.notifyDataSetChanged();
            }
        });


    }
}
