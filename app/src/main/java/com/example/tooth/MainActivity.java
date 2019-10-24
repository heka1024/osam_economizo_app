package com.example.tooth;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;
    public BluetoothSPP bt;
    public Ledger ld;
    public SingletonInts singletonInts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pnew_layout);
        mainContext = this;
        ld = ld.getLedger();
        bt = new BluetoothSPP(this); //Initializing
        singletonInts = singletonInts.getInts();

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.bluetoothConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    // wrapper method to revise current money, and automatically send bluetooth signal
    public void setCurrentMoney(int val) {
        ld.setMoney(val);
        TextView curMoney = findViewById(R.id.currentMoneyNotice);

        DecimalFormat formatter = new DecimalFormat("###,###");
        String money_string = formatter.format(ld.getMoney())  + "원";

        bt.send(Integer.toString(ld.getMoney()), true);

        curMoney.setText(money_string);
    }

    public void useCurrentMoney(int use_val) {
        ld.useMoney(use_val);
        TextView curMoney = findViewById(R.id.currentMoneyNotice);
        TextView spentMoney = findViewById(R.id.spentMoneyNotice);

        DecimalFormat formatter = new DecimalFormat("###,###");
        String current_money_string = formatter.format(ld.getMoney())  + "원";
        String spent_money_string = formatter.format(ld.getUsedMoney())  + "원";

        bt.send(Integer.toString(ld.getMoney()), true);

        curMoney.setText(current_money_string);
        spentMoney.setText(spent_money_string);

    }

    public void setTime() {
        final Calendar time = Calendar.getInstance();
        final TextView curTime = findViewById(R.id.header) ;
        String iText = (time.get(Calendar.MONTH) + 1) + "월 " + time.get(Calendar.WEEK_OF_MONTH) + "번째 주";
        curTime.setText(iText);
    }

    public void setup() {
        setTime();

        // set go to account bank
        Button goToAccountBook = findViewById(R.id.goToAccountBook);
        goToAccountBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent accountBook = new Intent(MainActivity.this, AccountBook.class);
                startActivity(accountBook);
            }
        });

        Button setMoney= findViewById(R.id.setMoney);
        setMoney.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("이번 주에 사용할 돈");
                ad.setMessage("이번 주에 사용할 금액을 입력하세요!");

                final EditText et = new EditText(MainActivity.this);
                et.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                ad.setView(et);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strValue = et.getText().toString().trim();
                        int iValue = Integer.parseInt(strValue);
                        setCurrentMoney(iValue);

                        TextView spentMoney = findViewById(R.id.spentMoneyNotice);
                        spentMoney.setText("0원");

                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });
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
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.accountBook:
                intent = new Intent(MainActivity.this, AccountBook.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}



