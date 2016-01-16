package com.example.kazuaki.blewerewolf;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.widget.FrameLayout.*;

public class MainActivity extends Activity {
    public void main(String[] args){

    }

    // 各種List宣言
    public static List<Map<String,Object>> playerArray;//参加者Array
    public static List<Map<String,String>> ListInfoDicArray;//リストに表示する情報のArray
    public static ArrayList<Integer> listPlayerIdArray;//listに入っているplayerId Array
    public static ArrayList<Integer> victimArray;//夜間犠牲者Array

    public static int selectedPlayerId;//リストで選択されたプレイヤーのID

    // TODO Adapter宣言

    // フラグ管理用 変数宣言
    public static int day;
    public static String scene;
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
        scene = "setting_scene";
        settingPhase = "setting_menu";
        super.onCreate(savedInstanceState);
        initBackground();
//        initControls();
//        turnOn();

        // FrameLayout作成
        FrameLayout mFrameLayout = new FrameLayout(this);
        setContentView(mFrameLayout);

        //TODO FrameLayoutに追加
        final CustomView customView = new CustomView(this);
        mFrameLayout.addView(customView);

        View view = getLayoutInflater().inflate(R.layout.activity_chat,null);
        LayoutParams lp = new LayoutParams(customView.width * 90 /100,customView.height * 80 / 100);
        lp.gravity = Gravity.TOP;
        lp.topMargin = 200;
        addContentView(view,lp);

//        mFrameLayout.addView(R.layout.activity_chat,100,100);



        //TODO List追加
        // 夜アクション用リストのタッチ動作
//        //ListView add
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
//        adapter = new SimpleAdapter(this,listInfoDicArray,android.R.layout.simple_list_item_2,new String[]{"name","listSecondInfo"},new int[]{android.R.id.text1,android.R.id.text2});
//
//        listView.setAdapter(adapter);
//        listView.setLayoutParams(lp);
//        listView.setBackgroundColor(Color.WHITE);
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
//        layout.addView(listView);
//        * */
        //TODO Chat追加

    }
    private void initControls() {
//        messagesContainer = new ListView(this);
//        messageET = new EditText(this);
//        sendBtn = new Button(this);
//
//        TextView meLabel = new TextView(this);
//        TextView companionLabel = new TextView(this);
//        RelativeLayout container = new RelativeLayout(this);

        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        companionLabel.setText("My Buddy");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(MainActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
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
