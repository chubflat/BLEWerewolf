package com.example.kazuaki.blewerewolf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Kazuaki on 2016/01/17.
 */
public class SettingScene extends Activity {
    public static ListView listView;
    public static SimpleAdapter simpleAdapter;
    //    public static Adapter adapter;
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
    public static Boolean isSettingScene;
    public static Boolean isGameScene;
    public static String settingPhase;
    public static String gamePhase;
    public static boolean isFirstNight;

    //Chat用
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    // list_item
    public static LinearLayout content;
    public static TextView txtInfo;
    public static LinearLayout contentWithBackground;
    public static TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSettingScene = true;
        isGameScene = false;
        settingPhase = "setting_menu";
        super.onCreate(savedInstanceState);
//        initBackground();
        // FrameLayout作成
        FrameLayout mFrameLayout = new FrameLayout(this);
        setContentView(mFrameLayout);

        //TODO FrameLayoutに追加
        customView = new CustomView(this);
        mFrameLayout.addView(customView);

        //TODO List追加
        // 夜アクション用リストのタッチ動作
//        listView = new ListView(this);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(customView.width,customView.height*4/10);
//        lp.gravity = Gravity.BOTTOM;
//        lp.bottomMargin = 0;
//        selectedPlayerId = -2;
//
//        listPlayerIdArray = new ArrayList<>();
//        Log.d("array", "array=");
//
//        listInfoDicArray = new ArrayList<Map<String,String>>();
//
//
//        simpleAdapter = new SimpleAdapter(this,listInfoDicArray,android.R.layout.simple_list_item_2,new String[]{"name","listSecondInfo"},new int[]{android.R.id.text1,android.R.id.text2});
//
//        listView.setAdapter(simpleAdapter);
//        listView.setLayoutParams(lp);
//        listView.setBackgroundColor(Color.WHITE);
////        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                if(phase.equals("player_setting")){
////                    selectedPlayerId = -2;
////                }else{
////                    selectedPlayerId = listPlayerIdArray.get(position);
////                }
////
////                if(phase.equals("player_setting")){
////
////                }else{
////                    if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf) {
////                        if (isFirstNight) {//人狼：初日の夜はタッチできない
////                            if(selectedPlayerId == -1){
////                                goNextPhase();
////                                customView.invalidate();
////                            }
////
////                        } else {// 人狼：2日目以降タッチされたplayerIdを渡して再描画
////                            wolfkill(selectedPlayerId, 0);
////                            goNextPhase();
////                            customView.invalidate();
////                        }
////                    } else if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard) {
////                        bodyguardId = selectedPlayerId;
////                        goNextPhase();
////                        customView.invalidate();
////                    } else {
////                        goNextPhase();
////                        customView.invalidate();
////                    }
////                }
////            }
////
////        });
//        mFrameLayout.addView(listView);

        //TODO Chat追加

    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        if(!isSettingScene && isGameScene){
//            Intent intent = new Intent(SettingScene.this,GameScene.class);
//            startActivity(intent);
//        }
//
//        return true;
////        String dialogText = "dialogText";
////
////        if(event.getAction() == MotionEvent.ACTION_DOWN && onDialog == true ){
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////
////            switch (dialogPattern){
////                case "Seer":
////                    dialogText = String.format("%sさんを占いますか？","xxxx");//TODO String.formatを記入。リストで選択したプレイヤーのID
////                    break;
////                case "Werewolf":
////                    dialogText = String.format("%sさんを襲撃しますか？","wwww");
////                    break;
////                case "Bodyguard":
////                    dialogText = String.format("%さんを護衛しますか？","bbbb");
////                    break;
////                default:
////                    break;
////            }
////            builder.setMessage(dialogText)
////                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////// ボタンをクリックしたときの動作
////                            onDialog = false;
////                            settingPhase = "client_menu";
////                            customView.invalidate();
////
////                        }
////                    });
////            builder.setMessage(dialogText)
////                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////// ボタンをクリックしたときの動作
////                        }
////                    });
////            builder.show();
////        }
//
//    }

    public static void drawListView(boolean visible){
        if(visible == true) {
            listView.setVisibility(View.VISIBLE);
        }else if(visible == false){
            listView.setVisibility(View.INVISIBLE);
        }
    }

//    public static void initBackground(){
//        scene = "setting_scene";
//        settingPhase = "setting_menu";
//
//    }

}
