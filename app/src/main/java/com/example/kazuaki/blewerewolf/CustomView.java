package com.example.kazuaki.blewerewolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

        //SettingScene用Rect初期化
        backgroundRect = new Rect(0,0,dp_width,dp_height);
        clientButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 50 / 100,dp_width * 90 / 100 ,dp_height * 60 / 100);
        userSettingButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 65 / 100,dp_width * 90 / 100 ,dp_height * 75 / 100);

        //GameScene用Rect初期化
        confirmButtonRect = new Rect(dp_width * 10 / 100 ,dp_height * 80 / 100,dp_width * 90 / 100 ,dp_height * 90 / 100);
        actionButtonRect = new Rect (dp_width * 80 / 100 ,dp_height * 10 / 100,dp_width * 95 / 100 ,dp_height * 20 / 100);

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
                    break;
                default:
                    break;
            }
        }else if(scene.equals("game_scene")){

            switch (gamePhase){
                case "rule_confirm":
                    break;

                case "night_roleRotate":
                    break;


            }

        }

    }
}
