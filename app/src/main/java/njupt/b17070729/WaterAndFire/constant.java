package njupt.b17070729.WaterAndFire;


import android.app.Activity;
import android.graphics.RectF;
import android.util.DisplayMetrics;

//该类存放常用静态常量，其他类调用，方便修改
// 屏幕长宽、默认生命值、绘制角色的长宽、游戏手柄的位置、大小
public  class constant {
    public  static int speed=15;    //移动的速度
    public  static  int Mario_w=54;  //人物的宽高
    public  static  int Mario_h=108;
    public  static  int life=3;      //生命值
    public static  int numweight=54;

    //我自己手机红米note7屏幕的宽高
    public static int w=2340,h=1080;
    //屏幕的比例++++
    public static float proportion=1;





    //A B按钮的xy坐标
    public static int CBX=w*5/8,CBY=h*5/8;
    public static int CAX=w/2,CAY=h/2;
    //AB 键的半径
    public static int ABR=w/16;
    //A键的区域
    public static RectF buttonA=new RectF(CAX-ABR,CAY-ABR,CAX+ABR,CAY+ABR);
    //B键的区域
    public static RectF buttonB=new RectF(CBX-ABR,CBY-ABR,CBX+ABR,CBY+ABR);




    //手柄的透明度
    public static int ontouchAlpha=50;
    //摇杆的监听区域  左半边屏幕
    public static RectF RF=new RectF(0,0,w/2,h);
    //摇杆的默认XY坐标
    public   static int defaultX=280 ;
    public   static int defaultY=h-250;
    //摇杆大小圆半径
    public static float RBig=480 * 0.5f * constant.proportion;
    public static float RSmall=300 * 0.5f * constant.proportion;




    //主activirt实例，方便其他类调用context。
    public static Activity gameActivity;







//    public static RectF defaultBig=new RectF(defaultX-RBig,defaultY-RBig,defaultX+RBig,defaultY+RBig);
//    public static RectF defaultSmall=new RectF(defaultX-RSmall,defaultY-RSmall,defaultX+RSmall,defaultY+RSmall);



//第一次加载配置 根据获得的屏幕长宽修改一些配置，并且会被存在gamedata的
    public static void loadfrist(DisplayMetrics display){
        w = display.widthPixels;
        h = display.heightPixels;
        //测试手机2340*1080的比例 以便适应不同大小的屏幕
        proportion = (float) (Math.sqrt(constant.w * constant.h) / Math.sqrt(2340 * 1080));

        CBX=w*13/16;CBY=h*13/16;

        CAX=w*7/8;CAY=h*5/8;
        ABR=w/20;

        //摇杆大小圆半径
        RBig=430 * 0.5f * constant.proportion;
        RSmall=270 * 0.5f * constant.proportion;//r1大圆的半径 r2小圆的半径

        defaultX=280 ;
        defaultY=h-250;
        life=3;
        //摇杆的监听区域
        RF=new RectF(0,0,w/2,h);
        //A键的区域
        //B键的区域
        buttonA.set(CAX-ABR,CAY-ABR,CAX+ABR,CAY+ABR);
        buttonB.set(CBX-ABR,CBY-ABR,CBX+ABR,CBY+ABR);

    }


    public static void loadconfig(){
        RF=new RectF(0,0,w/2,h);
        buttonA.set(CAX-ABR,CAY-ABR,CAX+ABR,CAY+ABR);
        buttonB.set(CBX-ABR,CBY-ABR,CBX+ABR,CBY+ABR);
    }
}
