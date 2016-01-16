package com.example.kazuaki.blewerewolf;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.le.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends Activity {
    public void main(String[] args){

    }

    public static ListView listView;
    public static SimpleAdapter simpleAdapter;
    public static Adapter adapter;
    public static CustomView customView = null;
    public static String dialogPattern = "default";

    // 各種List宣言
    public static List<Map<String,Object>> playerArray;//参加者Array
    public static List<Map<String,String>> listInfoDicArray;//リストに表示する情報のArray
    public static ArrayList<Integer> listPlayerIdArray;//listに入っているplayerId Array
    public static ArrayList<Integer> victimArray;//夜間犠牲者Array

    public static int selectedPlayerId;//リストで選択されたプレイヤーのID

    // TODO Adapter宣言

    // dialog関連
    public static boolean onDialog = false;

    // フラグ管理用 変数宣言
    public static int day;
    public static String scene;
    public static String settingPhase;
    public static String gamePhase;
    public static boolean isFirstNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        scene = "setting_scene";
        settingPhase = "setting_menu";
        super.onCreate(savedInstanceState);

        //dialog
//        if(onDialog){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("今からアプリを起動してもいいですか？")
//                    .setPositiveButton("起動", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//// ボタンをクリックしたときの動作
//                        }
//                    });
//            builder.show();
//        }


        initBackground();
//        turnOn();

        // FrameLayout作成
        FrameLayout mFrameLayout = new FrameLayout(this);
        setContentView(mFrameLayout);

        //TODO FrameLayoutに追加
        customView = new CustomView(this);
        mFrameLayout.addView(customView);

        //TODO List追加
        // 夜アクション用リストのタッチ動作
        listView = new ListView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(customView.width,customView.height*4/10);
        lp.gravity = Gravity.BOTTOM;
        lp.bottomMargin = 0;
        selectedPlayerId = -2;

        listPlayerIdArray = new ArrayList<>();
        Log.d("array","array=");

        listInfoDicArray = new ArrayList<Map<String,String>>();


        simpleAdapter = new SimpleAdapter(this,listInfoDicArray,android.R.layout.simple_list_item_2,new String[]{"name","listSecondInfo"},new int[]{android.R.id.text1,android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        listView.setLayoutParams(lp);
        listView.setBackgroundColor(Color.WHITE);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(phase.equals("player_setting")){
//                    selectedPlayerId = -2;
//                }else{
//                    selectedPlayerId = listPlayerIdArray.get(position);
//                }
//
//                if(phase.equals("player_setting")){
//
//                }else{
//                    if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf) {
//                        if (isFirstNight) {//人狼：初日の夜はタッチできない
//                            if(selectedPlayerId == -1){
//                                goNextPhase();
//                                customView.invalidate();
//                            }
//
//                        } else {// 人狼：2日目以降タッチされたplayerIdを渡して再描画
//                            wolfkill(selectedPlayerId, 0);
//                            goNextPhase();
//                            customView.invalidate();
//                        }
//                    } else if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard) {
//                        bodyguardId = selectedPlayerId;
//                        goNextPhase();
//                        customView.invalidate();
//                    } else {
//                        goNextPhase();
//                        customView.invalidate();
//                    }
//                }
//            }
//
//        });
        mFrameLayout.addView(listView);

        //TODO Chat追加
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        String dialogText = "dialogText";

        if(event.getAction() == MotionEvent.ACTION_DOWN && onDialog == true ){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            switch (dialogPattern){
                case "Seer":
                    dialogText = String.format("%sさんを占いますか？","xxxx");//TODO String.formatを記入。リストで選択したプレイヤーのID
                    break;
                case "Werewolf":
                    dialogText = String.format("%sさんを襲撃しますか？","wwww");
                    break;
                case "Bodyguard":
                    dialogText = String.format("%さんを護衛しますか？","bbbb");
                    break;
                default:
                    break;
            }
            builder.setMessage(dialogText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
// ボタンをクリックしたときの動作
                            onDialog = false;
                            settingPhase = "client_menu";
                            customView.invalidate();

                        }
                    });
            builder.setMessage(dialogText)
                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
// ボタンをクリックしたときの動作
                                                    }
                    });
            builder.show();
        }

        return true;
    }


    public static void drawListView(boolean visible){
        if(visible == true) {
            listView.setVisibility(View.VISIBLE);
        }else if(visible == false){
            listView.setVisibility(View.INVISIBLE);
        }
    }

    public static void initBackground(){
        scene = "setting_scene";
        settingPhase = "setting_menu";
        gamePhase = "rule_confirm";
        day = 1;
        isFirstNight = true;
        victimArray = new ArrayList<Integer>();

    }
    // settingPhaseについては直書きする
    public static void goNextPhase(){
        if(!(scene.equals("setting_scene"))){  // setting_scene以外に適用
            switch (gamePhase){
                case "rule_confirm":
                    gamePhase = "night_roleRotate";
                    break;
                case "night_roleRotate":
                    gamePhase = "night_roleCheck";
                    break;
                case "night_roleCheck":
                    gamePhase = "night_chat";
                    // TODO Chat表示をOnにする
                    // TODO Timer起動する
                    break;
                case "night_chat":
                    //TODO 役職者はリスト表示Phaseに
                    //TODO 生きていたら朝に、死んでいたら専用

                    gamePhase = "morning";
                    break;
                case "morning":
                    //TODO 終了判定する
                    gamePhase = "afternoon_meeting";
                    break;
                case "afternoon_meeting":
                    gamePhase = "evening_voting";
                    // TODO 投票用リスト表示
                    break;
                case "evening_voting":
                    gamePhase = "excution";
                    break;
                case "excution":
                    //TODO 生存判定をfalseに
                    //TODO mediumIdに追加
                    //TODO 昼の情報をrefresh
                    //TODO 終了判定

                    //if 終了しなかった場合
                    gamePhase = "night_chat";
                    break;

                default:
                    break;
            }
        }
    }

    public static void refresh(){

    }


// bluetooth用
//    private final static int REQUEST_ENABLE_BT = 123456;
//
//    public void turnOn(){
//        // Bluetoothをオンにする許可
//        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        if(btAdapter == null){
//            finish();
//        }
//
//        if(!btAdapter.isEnabled()){
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent,REQUEST_ENABLE_BT);
//        }
//    }


//    // 対象のサーブUUID
//    private static final String TRANSFER_SERVICE_UUID = "9C67274B-A925-4B56-B3E7-A7E02D8CCB71";
//    // キャラスタリッスティックUUID
//    private static final String TRANSFER_CHARACTERISTIC_UUID = "D4C3A985-1A0D-448D-900E-7A6AA521AC07";

//    private final LeScanCallback mScanCallback = new BluetoothAdapter.LeScanCallback(){
//        @Override
//        public void onLeScan(final BluetoothDevice device,final int rssi,final byte[] scanRecord){
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //スキャン中に見つかったデバイスに接続を試みる
//                }
//            });
//        }
//    };



}
