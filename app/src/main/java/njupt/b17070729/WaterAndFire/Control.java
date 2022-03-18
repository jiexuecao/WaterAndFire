package njupt.b17070729.WaterAndFire;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;
import static njupt.b17070729.WaterAndFire.GameMap.coulddown;
import static njupt.b17070729.WaterAndFire.GameMap.couldup;
import static njupt.b17070729.WaterAndFire.GameSurface.PlayerStatus;
import static njupt.b17070729.WaterAndFire.Role.Mario.X;
import static njupt.b17070729.WaterAndFire.Role.Mario.Y;
import static njupt.b17070729.WaterAndFire.constant.speed;

public class Control implements Runnable {


    Paint paint;//画笔
    public   static boolean Cisdown=false;
    public   static boolean Aisdown=false;
    public   static boolean Bisdown=false;

    //定义两个圆形的中心点坐标与半径
    private float smallCenterX,smallCenterY,smallCenterR;
    private float BigCenterX,BigCenterY,BigCenterR;
    private  float AX,AY,BX,BY,ABR;


    private int movePointerId =0;


    //构造函数，输入中心位置和小圆、大圆半径
    private Bitmap BigPIC;
    private Bitmap SmallPIC;
    private Bitmap bitmapA;
    private Bitmap bitmapA_Over;
    private Bitmap bitmapB;
    private Bitmap bitmapB_Over;
    private Thread jumpThread;
    private Thread bultThread;
    private boolean isflying;
    public static boolean isjumping;

    Control(){
        BigPIC = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.seek_button_press);
        SmallPIC = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.seek_button_no_focus);
        bitmapA = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.button_a);
        bitmapA_Over = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.button_a_over);
        bitmapB = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.button_b);
        bitmapB_Over = BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.button_b_over);

        smallCenterR=constant.RSmall;
        BigCenterR=constant.RBig;
        ABR=constant.ABR;
        AX=constant.CAX;
        AY=constant.CAY;
        BX=constant.CBX;
        BY=constant.CBY;
        smallCenterX=BigCenterX=constant.defaultX;
        BigCenterY=smallCenterY=constant.defaultY;

        jumpThread = new Thread(this);

        bultThread=new Thread(new Runnable() {
            //留着以后发子弹用
            @Override
            public void run() {
                Log.e("dsds","fds***8");
            }
        });

    }
    //重新恢复原状态
    public void reSet(){
        smallCenterX=BigCenterX;
        smallCenterY=BigCenterY;
    }
    //绘图函数
    public void myDraw(Canvas canvas,Paint paint) {
            canvas.drawBitmap(BigPIC, null, new RectF(BigCenterX-BigCenterR,BigCenterY - BigCenterR,BigCenterX + BigCenterR,BigCenterY +BigCenterR), paint); //画大圆
            canvas.drawBitmap(SmallPIC, null,  new RectF(smallCenterX - smallCenterR,smallCenterY - smallCenterR,smallCenterX + smallCenterR,smallCenterY + smallCenterR), paint); //画小圆
        if(Aisdown){
            canvas.drawBitmap(bitmapA_Over, null,  constant.buttonA, paint);
        }else {
            canvas.drawBitmap(bitmapA, null,  constant.buttonA, paint);
        }
        if(Bisdown){
            canvas.drawBitmap(bitmapB_Over, null,  constant.buttonB, paint);
        }else {
            canvas.drawBitmap(bitmapB, null,  constant.buttonB, paint);
        }
    }



    //触屏监听
    public boolean onTouchEvent(MotionEvent event) {

        final float pointx = event.getX(event.getActionIndex()), pointY = event.getY(event.getActionIndex());

        if(constant.buttonA.contains(pointx,pointY)){
            switch (event.getActionMasked()) {
               case ACTION_DOWN:
                case ACTION_POINTER_DOWN:
                    Aisdown = true;
                    if (!isflying) {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 25; i++) {
                                        if (couldup(X, Y)) {
                                            isjumping = true;
                                            isflying = true;
                                            Y -= speed;
                                            try {
                                                Thread.sleep(15);
                                            } catch (Exception e) {
                                                Log.e("匿名线程", "fsfeswf");
                                            }
                                        }
                                    }

//                                    for(int i=0;i<25;i++){
//                                            try {
//                                                Thread.sleep(15);
//                                            }catch (Exception e){
//                                                Log.e("匿名线程","fsfeswf");
//                                            }
//
//                                    }
//
//                                    isjumping=false;
//                                    isflying=false;

                                    //这样可以禁止连跳，但是增加了难度
                                    while (coulddown(X, Y)) {
                                        try {
                                            Thread.sleep(30);
                                        } catch (Exception e) {
                                            Log.e("匿名线程", "fsfeswf");
                                        }
                                    }
                                    isflying = false;
                                    isjumping = false;

                                }
                            }).start();
                        } catch (Exception e) {

                        }
                    }
                     break;
              case ACTION_UP:
                case ACTION_POINTER_UP:
                    Aisdown=false;
                    break;
                case ACTION_MOVE:
                    //使不能激活ACTION_MOVE摇杆的手指成为非主要手指的时候也能move;
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if(event.getPointerId(i)==movePointerId){
                            int pointerIndex = event.findPointerIndex(movePointerId);
                            float  x=event.getX(pointerIndex);
                            float  y=event.getY(pointerIndex);
                            move( x,y);
                            Log.e("x:y:id",""+x+"   "+y+"  "+movePointerId);
                        }
                    }
                    break;

            }
        }
         if(constant.buttonB.contains(pointx,pointY))
        {
            switch (event.getActionMasked()) {
               case ACTION_DOWN:
                case ACTION_POINTER_DOWN:
                    Bisdown=true;
                    break;
                case ACTION_UP:
                case ACTION_POINTER_UP:
                    Bisdown=false;
                    break;
            }

        }
         if(constant.RF.contains(pointx,pointY)){
            switch (event.getActionMasked()) {
               case ACTION_DOWN:
                case ACTION_POINTER_DOWN:
                    movePointerId=event.getPointerId(event.getActionIndex());
                    Cisdown=true;
                    break;

                case ACTION_UP:
                case ACTION_POINTER_UP:
                    movePointerId=-1;
                    Cisdown=false;
                    PlayerStatus=0;
                    reSet();
                    break;

                case ACTION_MOVE:
                    move( pointx,pointY);//移动时的操
                    break;

            }

        }




//
//        switch (event.getActionMasked()){
//            case ACTION_DOWN:
//            case ACTION_POINTER_DOWN:
//                if(constant.buttonA.contains(pointx,pointY)){
//                    Aisdown=true;
//                    if(!isflying){
//                        try {
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    for(int i=0;i<25;i++){
//                                        if(couldup(X,Y)){
//                                            isjumping=true;
//                                            isflying=true;
//                                            Y-=speed;
//                                            try {
//                                                Thread.sleep(15);
//                                            }catch (Exception e){
//                                                Log.e("匿名线程","fsfeswf");
//                                            }
//                                        }
//                                    }
//
////                                    for(int i=0;i<25;i++){
////                                            try {
////                                                Thread.sleep(15);
////                                            }catch (Exception e){
////                                                Log.e("匿名线程","fsfeswf");
////                                            }
////
////                                    }
////
////                                    isjumping=false;
////                                    isflying=false;
//
//                                    //这样可以禁止连跳，但是增加了难度
//                                    while (coulddown(X,Y)){
//                                        try {
//                                            Thread.sleep(30);
//                                        }catch (Exception e){
//                                            Log.e("匿名线程","fsfeswf");
//                                        }
//                                    }
//                                    isflying=false;
//                                    isjumping=false;
//
//                                }
//                            }).start();
//                        }catch (Exception e){
//
//                        }
//                    }
//
//
//
//                }
//
//                else if(constant.buttonB.contains(pointx,pointY))
//                {
//                    Bisdown=true;
//                }
//                else if(constant.RF.contains(pointx,pointY)){
//                    Cisdown=true;
//                    movePointerId=event.getPointerId(event.getActionIndex());
//
//                }
//                break;
//            case ACTION_UP:
//            case ACTION_POINTER_UP:
//                if(constant.buttonA.contains(pointx,pointY)){
//                    Aisdown=false;
//                }
//
//                else if(constant.buttonB.contains(pointx,pointY))
//                {
//                    Bisdown=false;
//                }
//
//                else if(constant.RF.contains(pointx,pointY)){
//                    PlayerStatus=0;
//                    reSet();
//                }
//                break;
//            case ACTION_MOVE:
//                if(constant.RF.contains(pointx,pointY)&&event.getPointerId(event.getActionIndex())== movePointerId)
//                    move( pointx,pointY);//移动时的操
//                    //当长按时视为连发
////                else if(constant.buttonA.contains(pointx,pointY)){
////                    Aisdown=true;
////                    //跳不可连续
////                }
////                else if(constant.buttonB.contains(pointx,pointY)) {
////                    Bisdown=true;
////                    bultThread.start();
////
////                }
//                break;
//
//        }




        return true;
    }

    private void move(float pointx, float pointy) {
        double angle=getRad(BigCenterX, BigCenterY, pointx, pointy);//获取偏转角度弧度
        //判断用户点击的位置是否在大圆内
        if (Math.sqrt(Math.pow((BigCenterX - (int) pointx), 2) + Math.pow((BigCenterY - (int) pointy), 2)) <= BigCenterR) {
            //让小圆跟随用户触点位置移动
            smallCenterX = pointx;
            smallCenterY = pointy;
        } else {
            setSmallCircleXY(BigCenterX, BigCenterY, BigCenterR,angle);
        }
        angle=angle/Math.PI*180;//将弧度转换为角度[控制]

        if(angle>=-45 && angle<=45)PlayerStatus=6;
        else if(angle>=45 && angle<=135)PlayerStatus=2;
        else if(angle>=135 || angle<=-135)PlayerStatus=4;
        else if(angle>=-135 && angle<=-45)PlayerStatus=8;


    }



    public void setSmallCircleXY(float centerX, float centerY, float R, double rad) {
        //获取圆周运动的X坐标
        smallCenterX = (float) (R * Math.cos(rad)) + centerX;
        //获取圆周运动的Y坐标
        smallCenterY = (float) (R * Math.sin(rad)) + centerY;
    }

    public double getRad(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float x = px2 - px1;
        //得到两点Y的距离
        float y = py1 - py2;
        //算出斜边长
        float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        float cosAngle = x / Hypotenuse;
        //通过反余弦定理获取到其角度的弧度
        float rad = (float) Math.acos(cosAngle);
        //当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }



    //调启动子线程
    //之后做发射子弹
    @Override
    public void run() {
        try{

        }catch (Exception e){
            Log.e("d","d");
        }

    }




}
