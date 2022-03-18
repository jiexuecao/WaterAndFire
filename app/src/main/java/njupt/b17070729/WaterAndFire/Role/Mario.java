package njupt.b17070729.WaterAndFire.Role;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import njupt.b17070729.WaterAndFire.R;
import njupt.b17070729.WaterAndFire.constant;

import static njupt.b17070729.WaterAndFire.Control.isjumping;
import static njupt.b17070729.WaterAndFire.GameMap.coulddown;
import static njupt.b17070729.WaterAndFire.GameMap.couldleft;
import static njupt.b17070729.WaterAndFire.GameMap.couldright;
import static njupt.b17070729.WaterAndFire.GameMap.couldup;
import static njupt.b17070729.WaterAndFire.GameSurface.PlayerStatus;
import static njupt.b17070729.WaterAndFire.constant.speed;

public class Mario  implements Runnable{

//绘制图像
    public static Bitmap[] Right=new Bitmap[6];
    private static Bitmap[] Left=new Bitmap[6];
    private  Bitmap stand;
    private  Bitmap flag;
    //游戏初始位置
    public static int X=540;
    public static int Y=800;
    private final Thread mThread;

    private int chenge=1;
    private  int add=0;


    public Mario(){
        Right[0]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_stand);
        Right[1]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_run01);
        Right[2]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_run02);
        Right[3]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_jump);
        Right[4]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_attack);
        Right[5]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_right_down);
        Left[0]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_stand);
        Left[1]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_run01);
        Left[2]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_run02);
        Left[3]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_jump);
        Left[4]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_attack);
        Right[5]= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_left_down);
        stand=BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_out_of_sewer);
        flag=BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.mario_flag);
        mThread = new Thread(this);
        mThread.start();
    }


    public void myDraw(Canvas canvas, Paint paint) {
        switch (PlayerStatus){
            case 1:
                canvas.drawBitmap(Left[5],X,Y,new Paint());
                break;
            case 7:
                canvas.drawBitmap(Left[3],X,Y,new Paint());
                break;
            case 4:
                if(isjumping)
                    canvas.drawBitmap(Left[3],X,Y,new Paint());
                else
                canvas.drawBitmap(Left[chenge],X,Y,new Paint());
                add++;
                if(add>=5){
                    chenge=chenge%2+1;
                    add=0;
                }
                break;
            case 9:
            case 8://暂定跳高是向右
                canvas.drawBitmap(Right[3],X,Y,new Paint());
                break;
            case 6:
                if(isjumping)
                    canvas.drawBitmap(Right[3],X,Y,new Paint());
                else{
                    canvas.drawBitmap(Right[chenge],X,Y,new Paint());
                    add++;
                    if(add>=5){
                        chenge=chenge%2+1;
                        add=0;
                }
                }
                break;
            case 2://暂定下蹲是向右
            case 3:
                canvas.drawBitmap(Right[5],X,Y,new Paint());
                break;
            case 5:
                canvas.drawBitmap(stand,X,Y,new Paint());
                break;
            default:
                canvas.drawBitmap(stand,X,Y,new Paint());
                break;
        }

    }

    public void logic() {

        switch (PlayerStatus){
            case 1:
                if(couldleft(X,Y))
                    X-= speed;
                if(coulddown(X, Y))
                    Y+=speed;
                break;
            case 2:
                if (coulddown(X, Y))
                    Y+=speed;
                break;
            case 3:
                if(couldright(X,Y))
                    X+= speed;
                if (coulddown(X, Y))
                    Y+=speed;
                break;
            case 4:

                if(couldleft(X,Y)){
                    if(X<speed){
                        X=speed;
                    }else {
                        X-= speed;
                    }

                }

                break;
            case 5:
                break;
            case 6:
                if(couldright(X,Y))
                    if(X<= constant.w/2){
                        X+= speed;
                    }
                break;
            case 7:
                if(couldleft(X,Y))
                    X-= speed;
                if (couldup(X, Y))
                  //  Y+=speed;
                break;
            case 8:
                if(couldup(X,Y))
                    Y-=speed;
                break;
            case 9:
                if(couldright(X,Y))
                    X+= speed;
                if(couldup(X,Y))
                  //  Y-=speed;
                break;
                default:
                    break;

        }

    }

    @Override
    public void run() {
                //维持重力
            while (true) {
                try {
                    if (coulddown(X, Y)) {
                        Y+=speed/2;
                        Thread.sleep(25);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}
