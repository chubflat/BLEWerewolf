package com.example.kazuaki.blewerewolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Kazuaki on 2016/01/10.
 */
public class CustomView extends View {

    //TODO Bitmap宣言

    private Bitmap backgroundImg = null;
    private Bitmap roleImg = null;
    private Bitmap frameImg = null;
    private Bitmap timerFrameImg = null;
    private Bitmap buttonImg = null;

    //TODO 画面サイズ取得用
    public static int dp_width;
    public static int dp_height;

    //SettingScene用
    public static Rect backgroundRect;
    public static Rect clientButtonRect;
    public static Rect userSettingButtonRect;
    // TODO ユーザー設定中身

    //gameScene用
    public static Rect confirmButtonRect;
    public static Rect actionButtonRect;
    public static Rect topTextRect;
    public static Rect roleCardRect;
    public static Rect timerRect;

    // TODO GameSceneと共通の変数
    public static int day = 0;
    public static int selectedPlayerId;
    public static int mediumId;
    public static boolean isFirstNight;
    public static String scene;
    public static String settingPhase;
    public static String gamePhase;

    //TODO Canvasに新要素追加時

    public CustomView(Context context) {
        super(context);
        setFocusable(true);

        //WindowsManager取得
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //Displayインスタンス生成
        Display dp = wm.getDefaultDisplay();
        //Displayサイズ取得
        dp_width = dp.getWidth();
        dp_height = dp.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setTextSize(50);

        // Bitmap初期化
        backgroundImg = BitmapFactory.decodeResource(getResources(),R.drawable.afternoon);
        frameImg = BitmapFactory.decodeResource(getResources(),R.drawable.frame);
        timerFrameImg = BitmapFactory.decodeResource(getResources(),R.drawable.time_frame);
        buttonImg = BitmapFactory.decodeResource(getResources(),R.drawable.button);
        roleImg = BitmapFactory.decodeResource(getResources(),R.drawable.card0);

        //SettingScene用Rect初期化
        backgroundRect = new Rect(0,0,dp_width,dp_height);
        clientButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 50 / 100,dp_width * 90 / 100 ,dp_height * 60 / 100);
        userSettingButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 65 / 100,dp_width * 90 / 100 ,dp_height * 75 / 100);

        //GameScene用Rect初期化
        confirmButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 80 / 100,dp_width * 90 / 100 ,dp_height * 90 / 100);
        actionButtonRect = new Rect (dp_width * 80 / 100 ,dp_height * 5 / 100,dp_width * 95 / 100 ,dp_height * 15 / 100);
        topTextRect = new Rect(dp_width * 20 / 100 ,dp_height * 5 / 100,dp_width * 80 / 100 ,dp_height * 15 / 100);
        roleCardRect = new Rect(dp_width * 5 / 100, dp_height * 5/100 ,dp_width * 15 / 100 ,dp_height * 20 / 100);
        timerRect = new Rect(dp_width * 20 / 100, dp_height * 5/100 ,dp_width * 60 / 100 ,dp_height * 20 / 100);

        //TODO GameSceneとの共有変数の初期化
        scene = MainActivity.scene;
        day = MainActivity.day;
        selectedPlayerId = MainActivity.selectedPlayerId;
        isFirstNight = MainActivity.isFirstNight;
        settingPhase = MainActivity.settingPhase;
        gamePhase = MainActivity.gamePhase;

        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
        canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);


        if(scene.equals("setting_scene")){
            switch (settingPhase){
                case "setting_menu":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    // Client Button
                    canvas.drawBitmap(buttonImg,null,clientButtonRect,paint);
                    canvas.drawText("クライアント", dp_width * 25 / 100, dp_height * 55 / 100, paint);
                    // UserSetting Button
                    canvas.drawBitmap(buttonImg,null,userSettingButtonRect,paint);
                    canvas.drawText("ユーザー設定",dp_width * 25/100,dp_height * 70/100,paint);

                    break;
                case "user_setting":
                    // TODO User設定
                    break;
                case "client_menu":
                    //TODO Client設定 部屋探索
                    backgroundImg = BitmapFactory.decodeResource(getResources(),R.drawable.night);
                    paint.setColor(Color.WHITE);
                    canvas.drawText("ルール設定待ち",dp_width * 30/100,dp_height * 50 / 100,paint);

                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ",dp_width * 25/100,dp_height * 90/100,paint);

                    break;
                default:
                    break;
            }
        }else if(scene.equals("game_scene")){

            switch (gamePhase){
                case "rule_confirm":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    //topText
                    canvas.drawBitmap(timerFrameImg,null,topTextRect,paint);
                    canvas.drawText("ルール",dp_width * 30/100,dp_height * 10/100,paint);

                    // TODO List表示
                    // 2行リスト MainActivityに記述

                    //confirmButton
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("確認",dp_width * 25/100,dp_height * 90/100,paint);
                    break;

                case "night_roleRotate":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    // rotateImg 表示
                    Rect rotateCardRect = new Rect(dp_width * 15 /100,dp_height * 30 / 100 ,dp_width * 85 / 100 ,dp_height * 70 /100);

                    //TODO cardRotate
                    //TODO roleImgを取ってくる:デフォルトで村人

                    roleImg = BitmapFactory.decodeResource(getResources(),R.drawable.card0);
                    canvas.drawBitmap(roleImg,null,rotateCardRect,paint);
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("詳細確認", dp_width * 25 / 100, dp_height * 90 / 100, paint);

                    break;
                case "night_roleCheck":
                    //Rect宣言
                    Rect topFrameRect = new Rect(dp_width * 5 /100,dp_height * 5 / 100 ,dp_width * 95 / 100 ,dp_height * 50 /100);
                    Rect belowFrameRect = new Rect(dp_width * 20 /100,dp_height * 60 / 100 ,dp_width * 80 / 100 ,dp_height * 75 /100);
                    Rect roleCheckCardRect = new Rect(dp_width * 45 /100,dp_height * 10 / 100 ,dp_width * 55 / 100 ,dp_height * 20 /100);

                    // canvasDraw
                    canvas.drawBitmap(frameImg,null,topFrameRect,paint);
                    canvas.drawBitmap(frameImg,null,belowFrameRect,paint);
                    canvas.drawBitmap(roleImg,null,roleCheckCardRect,paint);
                    canvas.drawBitmap(buttonImg,null,confirmButtonRect,paint);
                    canvas.drawText("初日夜へ", dp_width * 25 / 100, dp_height * 90 / 100, paint);

                    break;

                case "night_chat":
                    canvas.drawBitmap(roleImg,null,roleCardRect,paint);
                    canvas.drawBitmap(timerFrameImg,null,timerRect,paint);
                    canvas.drawBitmap(buttonImg,null,actionButtonRect,paint);

                    String action = "占う";
                    // TODO 役職ごとに文字を変えるif文
                    canvas.drawText(action,dp_width * 75 / 100,dp_height * 10 / 100,paint);

                    // TODO Chat実装
                    break;

                case "morning":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.morning);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    break;
                case "afternoon_opening":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    break;
                case "afternoon_meeting":
                    break;
                case "afternoon_voting":
                    // background
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.evening);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    break;
                case "excution":
                    break;
                case "gameover":
                    break;


                default:
                    break;


            }

        }

    }
}
