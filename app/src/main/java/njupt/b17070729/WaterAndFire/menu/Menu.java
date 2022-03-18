package njupt.b17070729.WaterAndFire.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import njupt.b17070729.WaterAndFire.GameSurface;
import njupt.b17070729.WaterAndFire.R;
import njupt.b17070729.WaterAndFire.constant;

public class Menu {
    private Bitmap meau_background;//菜单背景图

    private Bitmap meau_start;//按钮图片资源(按下和未按下图)
    private Bitmap meau_start1;//按钮图片资源(按下和未按下图)
    private Bitmap meau_continue[]=new Bitmap[2];
    private int btnX, btnY;//按钮的坐标
    private Boolean isPress;//按钮是否按下标识位
    private int change;//图片切换


    public Menu() {
        meau_background = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.mipmap.meau_background);
        meau_background = Bitmap.createScaledBitmap(meau_background, constant.w, constant.h, true);
        meau_start = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.meau_start);
        meau_start1 = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.meau_start1);
        meau_continue[0]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.meau_continue);
        meau_continue[1] = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.meau_continue1);
        //X居中，Y
        btnX = constant.w / 2 - meau_start.getWidth() / 2;
        btnY = constant.h/2 - meau_start.getHeight();
        change=0;
        isPress = false;
    }//菜单初始化

    //菜单绘图函数
    public void draw(Canvas canvas, Paint paint) {
        //绘制菜单背景图

        canvas.drawBitmap(meau_background, 0, 0, paint);
        //绘制未按下按钮图
        if(GameSurface.gameState== GameSurface.GameStatus.Meau){
            if(!isPress){
                canvas.drawBitmap(meau_start, btnX, btnY, paint);
            }else {
                canvas.drawBitmap(meau_start1, btnX, btnY, paint);
            }
        }
        else {
            if(!isPress){
                canvas.drawBitmap(meau_continue[0], btnX, btnY, paint);
            }else {
                canvas.drawBitmap(meau_continue[1], btnX, btnY, paint);
            }
        }
    }


    //菜单触屏事件函数，主要用于处理按钮事件
    public void onTouchEvent(MotionEvent event) {

        //获取用户当前触屏位置
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();
        //当用户是按下动作或移动动作
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            //判定用户是否点击了按钮
            if (pointX > btnX && pointX < btnX + meau_start.getWidth()) {
                if (pointY > btnY && pointY < btnY + meau_start1.getHeight()) {
                    isPress=true;
                }
            }
            //当用户是抬起动作
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            //抬起判断是否点击按钮，防止用户移动到别处
            if (pointX > btnX && pointX < btnX + meau_start.getWidth()) {
                if (pointY > btnY && pointY < btnY + meau_start1.getHeight()) {
                    //还原Button状态为未按下状态
                    isPress = false;
                    GameSurface.gameState=GameSurface.GameStatus.Runing;
                    //改变当前游戏状态为开始游戏
                }
            }
        }
    }



}
