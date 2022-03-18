package njupt.b17070729.WaterAndFire;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import njupt.b17070729.WaterAndFire.Role.Mario;
import njupt.b17070729.WaterAndFire.menu.Menu;
import njupt.b17070729.WaterAndFire.menu.Winmenu;
import njupt.b17070729.WaterAndFire.menu.losemenu;

import static njupt.b17070729.WaterAndFire.GameMap.positionX;
import static njupt.b17070729.WaterAndFire.GameSurface.GameStatus.Meau;
import static njupt.b17070729.WaterAndFire.GameSurface.GameStatus.Over;
import static njupt.b17070729.WaterAndFire.GameSurface.GameStatus.Pauesd;
import static njupt.b17070729.WaterAndFire.GameSurface.GameStatus.Runing;
import static njupt.b17070729.WaterAndFire.GameSurface.GameStatus.Win;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    public static int proudscroe =0;
    public static int lengthscroe =0;
    public  enum GameStatus{
        Meau,
        Runing,
        Win,
        Pauesd,
        Over
    };

    /**    PlayerStatus不同的数值代表不同的运动方位
     *          7 左上   8跳     9右上
     *          4 左     5不动   6右
     *          1 左下   2蹲下   3右下
     */
    public static int  PlayerStatus=0;

    public static int life =constant.life;
    public static boolean  Apress;
    public static boolean  Bpress;


    public static boolean flag;
    public Paint paint;
    public Thread mThread;
    public Canvas canvas;
    public Context mContext;
    public static GameStatus gameState= Meau;
    public SurfaceHolder mSurfaceHolder;

    public Menu meau;
    public losemenu losemen;
    public Winmenu winmenu;
    public Control control;
    public GameMap gameMap;
    public Mario mario;


    public GameSurface(Context context) {
        this(context, null);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext  = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        gameState= Meau;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initGame();
        flag = true;
        mThread = new Thread(this);
        mThread.start();
        //初始化游戏资源
    }

    private void initGame() {
       if (gameState == GameStatus.Meau) {
            paint=new Paint();
            meau = new Menu( );
            control=new Control();
           gameMap=new GameMap();
           mario=new Mario();
           losemen=new losemenu();
           winmenu=new Winmenu();
        }
    }




    public void myDraw() {
        try {
            canvas = mSurfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                switch (gameState){
                    case Meau:
                        meau.draw(canvas, paint);
                        break;
                    case Runing:
                        canvas.drawARGB(255,67,198,255);
                        gameMap.myDraw(canvas ,paint);
                        mario.myDraw(canvas ,paint);
                        control.myDraw(canvas,paint);
                        drawScore(canvas );
                        break;
                    case Pauesd:
                        canvas.drawARGB(255,67,198,255);
                        gameMap.myDraw(canvas ,paint);
                        break;
                   case Over:
                       canvas.drawARGB(255,67,198,255);
                       drawScore(canvas );
                       gameMap.myDraw(canvas ,paint);
                       mario.myDraw(canvas ,paint);
                       losemen.draw(canvas ,paint);
                        break;
                    case Win:
                        canvas.drawARGB(255,67,198,255);
                        drawScore(canvas );
                        gameMap.myDraw(canvas ,paint);
                        mario.myDraw(canvas ,paint);
                        winmenu.draw(canvas ,paint);
                        break;
                }
            }
        } catch (Exception e){
            Log.e("ｓｕｒｆａｃｅ的ｄｒａｗ","ｄｒａｗ");
        } finally {
            if (canvas != null)
                mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }




//只有当游戏处于运行状态时运行逻辑模块
    private void logic() {
        if (gameState == Runing) {
            gameMap.logic();
            mario.logic(); }
    }





    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    @Override  //刷新率１０００／30＝33ｈｚ
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 30) {
                    Thread.sleep(30 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }




    private void drawScore(Canvas canvas){
        Paint paint1=new Paint();
        paint1.setARGB(255,255,0,0);
        paint1.setTextSize(64);
        paint1.setFakeBoldText(true);
        int score=proudscroe+lengthscroe;
        int jindu=positionX*1000/9/93/540;
        canvas.drawText("剩余生命："+life+
                "      奖励得分:"+ proudscroe+"　距离得分"+lengthscroe+"         进度"+jindu+"%",200,100,paint1);
        Log.e("分数",""+proudscroe+" "+lengthscroe);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameState) {
            case Meau:
                meau.onTouchEvent(event);
                break;
            case Runing:
                control.onTouchEvent(event);
                break;
            case Pauesd:
                break;
            case Win:
                winmenu.onTouchEvent(event);
                break;
            case Over:
                losemen.onTouchEvent(event);
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (gameState == Runing || gameState == Win || gameState == Over|| gameState == Pauesd) {
                gameState = Meau;
                initGame();
            } else if (gameState == Meau) {
                constant.gameActivity.finish();
                System.exit(0);
            }
            //表示此按键已处理，不再交给系统处理，
            //从而避免游戏被切入后台
            return true;
        }

        switch (gameState) {
            case Meau:
                break;
            case Runing:
                break;
            case Pauesd:
                break;
            case Win:
                break;
            case Over:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //处理back返回按键//表示此按键已处理，不再交给系统处理，从而避免游戏被切入后台
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //游戏胜利、失败、进行时都默认返回菜单
            if (gameState == Runing || gameState == Win || gameState == Over|| gameState == Pauesd) {
                gameState = Meau;
            }
            return true;
        }
        switch (gameState) {
            case Meau:
                break;
            case Runing:
               // player.onKeyUp(keyCode, event);
                break;
            case Pauesd:
                break;
            case Win:
                break;
            case Over:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }





}
