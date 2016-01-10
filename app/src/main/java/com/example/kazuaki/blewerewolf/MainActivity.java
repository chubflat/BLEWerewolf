package com.example.kazuaki.blewerewolf;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
    static void main(String[] args){}

    public static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    public void getBluetoothAdapter(){
        if(mBluetoothAdapter == null){
            //サポートされていないときの処理
        }else{
            //サポートされている
        }
         //Bluetooth無効時に有効にする処理
         if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
//        }
    }
    }
    //    private BluetoothManager mBluetoothManager;
//    private BluetoothAdapter mBluetoothAdapter;
//    private BluetoothAdapter.LeScanCallback mLeScanCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBluetoothAdapter();
    }
//
//        //BLE
//        mBluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = mBluetoothManager.getAdapter();
//
//        //BLEスキャンした際のコールバック
//        mLeScanCallback = new BluetoothAdapter.LeScanCallback(){
//            @Override
//            public void onLeScan(BluetoothDevice device,int rssi,byte[] scanRecord){
//                String msg = "ADDRESS=" + device.getAddress() + "\nRSSI=" + rssi;
//                Log.d("BLE", msg);
//            }
//        };
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
