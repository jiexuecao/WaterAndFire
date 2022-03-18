package njupt.b17070729.WaterAndFire.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import njupt.b17070729.WaterAndFire.GameMap;
import njupt.b17070729.WaterAndFire.GameSurface;
import njupt.b17070729.WaterAndFire.R;
import njupt.b17070729.WaterAndFire.Role.Mario;
import njupt.b17070729.WaterAndFire.constant;

import static njupt.b17070729.WaterAndFire.GameMap.initmap;
import static njupt.b17070729.WaterAndFire.GameSurface.PlayerStatus;
import static njupt.b17070729.WaterAndFire.GameSurface.gameState;
import static njupt.b17070729.WaterAndFire.GameSurface.life;

public class losemenu {
    private Bitmap meau_background;//菜单背景图
    private Bitmap meau_continue;
    private Bitmap meau_continue1;
    private Bitmap meau_restart;//按钮图片资源(按下和未按下图)
    private Bitmap meau_restart1;//按钮图片资源(按下和未按下图)

    private int CbtnX, CbtnY;//按钮的坐标
    private int RbtnX, RbtnY;//按钮的坐标
    private int BbtnX, BbtnY;//按钮的坐标

    private Boolean CisPress;//按钮是否按下标识位，按下时绘制深色图像，未按下绘制浅色图线
    private Boolean RisPress;//按钮是否按下标识位

    public losemenu() {
        meau_background = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.losemenu);
        meau_background = Bitmap.createScaledBitmap(meau_background, constant.w/2, constant.h/2, true);
        meau_restart = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.restart);
        meau_restart1 = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.restart);
        meau_continue= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.cintinues);
        meau_continue1= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.cintinues1);

        BbtnX= constant.w / 2 - meau_background.getWidth() / 2;
        BbtnY=constant.h/2 - meau_background.getHeight()/2;

        CbtnX =constant.w / 2- meau_continue.getHeight()*2;
        CbtnY=constant.h/2 - meau_continue.getHeight()/2;

        RbtnX=constant.w / 2+meau_restart.getHeight();
        RbtnY=constant.h/2 - meau_restart.getHeight()/2;

        CisPress = false;
        RisPress=false;
    }


    public void draw(Canvas canvas, Paint paint) {

            canvas.drawBitmap(meau_background, BbtnX, BbtnY, paint);
            if(!RisPress){
                canvas.drawBitmap(meau_restart, RbtnX, RbtnY, paint);
            }else {
                canvas.drawBitmap(meau_restart1, RbtnX, RbtnY, paint);
            }
            if(GameSurface.life >0){//生命小于０时不容许在继续
                if(!CisPress){
                    canvas.drawBitmap(meau_continue, CbtnX, CbtnY, paint);
                }else {
                    canvas.drawBitmap(meau_continue1, CbtnX, CbtnY, paint);
                }
                Log.e("GameSurface.life&&&&&&&&&&&&& ",""+GameSurface.life );
            }
            Paint paint1=new Paint();
            paint1.setARGB(255,255,0,0);
            paint1.setTextSize(64);
            paint1.setFakeBoldText(true);
            canvas.drawText("you dead! 剩余生命："+life,800,400,paint1);
    }


    //菜单触屏事件函数，主要用于处理按钮事件
    public void onTouchEvent(MotionEvent event) {

        //获取用户当前触屏位置
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();
        //当用户是按下动作或移动动作
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            //判定用户是否点击了按钮
            if (pointX > CbtnX && pointX < CbtnX + meau_continue.getWidth()&&pointY > CbtnY && pointY < CbtnY + meau_continue.getHeight()) {
                    CisPress=true;
            }
            if (pointX > RbtnX && pointX < RbtnX + meau_restart.getWidth()&&pointY > RbtnY && pointY < RbtnY + meau_restart.getHeight()) {
                RisPress=true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (GameSurface.life >0&&pointX > CbtnX && pointX < CbtnX + meau_continue.getWidth()&&pointY > CbtnY && pointY < CbtnY + meau_continue.getHeight()) {
                //生命减一后继续游戏
                CisPress=false;
                Mario.X=100;
                Mario.Y=100;
                GameSurface.life=GameSurface.life-1;
                gameState= GameSurface.GameStatus.Runing;
            }
            if (pointX > RbtnX && pointX < RbtnX + meau_restart.getWidth()&&pointY > RbtnY && pointY < RbtnY + meau_restart.getHeight()) {
               //游戏重置
                RisPress=false;
                CisPress=false;
                Mario.X=540;
                Mario.Y=800;
                GameMap.positionX=0;
                GameSurface.life =constant.life;
                initmap();//游戏地图重置
                gameState= GameSurface.GameStatus.Runing;
                PlayerStatus=5;
            }
        }
    }

}
