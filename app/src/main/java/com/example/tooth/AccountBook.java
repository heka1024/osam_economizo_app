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
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    SingletonInts ints;

    @Override
    protected void onCreate(Bundle savedInstance) {
        ints = ints.getInts();

        super.onCreate(savedInstance);
        setContentView(R.layout.layout_account_book);

        recyclerView = findViewById(R.id.main_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter();
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
                try {
                    EditText spendMoney = findViewById(R.id.inputUsedMoney);
                    String send_string = spendMoney.getText().toString().trim();
                    ((MainActivity)MainActivity.mainContext).bt.send(send_string, true);

                    int tmp_val = Integer.parseInt(send_string);
                    ints.add(tmp_val);
                    ((MainActivity)MainActivity.mainContext).useCurrentMoney(tmp_val);
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                    Log.e("AccountBook", "failed to add in list");
                    finish();
                }

            }
        });


    }
}
