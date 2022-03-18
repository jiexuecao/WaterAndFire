package njupt.b17070729.WaterAndFire;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static njupt.b17070729.WaterAndFire.GameSurface.PlayerStatus;
import static njupt.b17070729.WaterAndFire.GameSurface.gameState;
import static njupt.b17070729.WaterAndFire.GameSurface.proudscroe;
import static njupt.b17070729.WaterAndFire.Role.Mario.X;
import static njupt.b17070729.WaterAndFire.Role.Mario.Y;
import static njupt.b17070729.WaterAndFire.constant.Mario_h;
import static njupt.b17070729.WaterAndFire.constant.Mario_w;
import static njupt.b17070729.WaterAndFire.constant.speed;

public class GameMap {

    public static AssetManager mAssetManger;
    public static InputStream is;
    public static BufferedReader reader;

    public  Canvas cv;
    private PaintFlagsDrawFilter pfd;
    public  BitmapFactory.Options options;
    private Paint mypaint;

    public Bitmap allgamestaff;
    public Bitmap Temp;
    public Bitmap[] staff = new Bitmap[100];
    public Bitmap screenbitmap   ;

    public static int[][] csvmap=new int[20][1000];
    public static int positionX=0;
    public static int kuainum;
    public static int offset;
    public  int change=0;
    public boolean A=false;

    public GameMap(){
        options=new BitmapFactory.Options();
        options.inScaled=false;
        allgamestaff= BitmapFactory.decodeResource(constant.gameActivity.getResources(), R.drawable.gamestaff,options);
        Matrix matrix = new Matrix();
        matrix.preScale((float) (54/16.0), (float) (54/16.0));
        //先裁剪再放大每一个元素
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++){
                Temp=Bitmap.createBitmap(allgamestaff,16*j,16*i,16,16, null, false);
                staff[10*i+j]=Bitmap.createBitmap(Temp, 0, 0, Temp.getWidth(), Temp.getHeight(), matrix, false);
            }
        initmap();
        //初始化屏幕的bitmap
        screenbitmap=Bitmap.createBitmap(2340,1080,Bitmap.Config.ARGB_8888);
        cv = new Canvas(screenbitmap);
        pfd= new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        mypaint=new Paint();
        mypaint.setFilterBitmap(true);


    }
    public  static void initmap(){
        //读取csv地图
        mAssetManger =constant.gameActivity.getAssets();
        is = null;
        try {
            is = mAssetManger.open("mapdate.csv");
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            int j=0;
            while(reader.ready()) {
                line = reader.readLine();
                String[] buffer = line.split(",");// 以逗号分隔
                for(int i=0;i<buffer.length;i++)
                    csvmap[j][i]=Integer.parseInt(buffer[i]);
                j++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        //界面绘制函数
    public void myDraw(Canvas canvas,Paint paint) {
        getscreen(positionX);
        canvas.drawBitmap(screenbitmap,0,0,paint);
    }

    private void getscreen(int position) {
        //清除上一帧画面
        Paint paint1 = new Paint();
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        cv.drawPaint(paint1);

        cv.setDrawFilter(pfd);
        kuainum = position / 54;
        offset=position%54;
        change+=1;
        //分为20*51块
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 51; j++)
                if(csvmap[i][kuainum+j]!=0){
                    if(csvmap[i][kuainum+j]-1!=54){
                        Bitmap bitmap=staff[csvmap[i][kuainum+j]-1];
                        bitmap.setDensity(constant.gameActivity.getResources().getDisplayMetrics().densityDpi);
                        cv.drawBitmap(bitmap, 54 * j-offset, 54 * i,mypaint); //放置画面被放大
                    }
                    else{
                        Bitmap bitmap;
                        if(change%10==0){
                            change=0;
                            A=!A;
                        }
                        if(A){
                             bitmap=staff[55];
                        }else {
                            bitmap=staff[54];
                        }
                        bitmap.setDensity(constant.gameActivity.getResources().getDisplayMetrics().densityDpi);
                        //放置画面被放大
                        cv.drawBitmap(bitmap, 54 * j-offset, 54 * i,mypaint);
                    }

                }
        GameSurface.lengthscroe =positionX/100;
    }

    public void logic() {
        switch(PlayerStatus){
            case 6:
            case 9:
                if(X>=constant.w/2-speed&&couldright(X,Y)){
                    positionX+=speed;
                }
                break;
            default:
                break;
        }
    }





    public  static boolean coulddown(int x, int y) {
        //int l=(x+positionX)/54;
        int r=(x+positionX+Mario_w/2)/54;
        int j=y/54+1;
       /* switch (csvmap[j+1][l]){
            case 0 : case 21: case 22: case 25: case 26:
            case 27 :case 28 :case 29 :case 30 :case 31 :
            case 32 :case 35 :case 39 :case 40 : case 50 :
            case 81 :case 82 :case 83 :case 49: case 84 :
            case 85 :case 91 :case 92 :case 93 :case 94 :
            case 95:
                break;
            case 41:
            case 44:
                gameState= GameSurface.GameStatus.Over;
                break;
            case 55:
                proudscroe +=10;
                Log.e("map","+"+proudscroe);
                csvmap[j+1][l]=0;
                return true;
            case 24:
                gameState= GameSurface.GameStatus.Win;
                return true;
            default:
                return false;
        }*/
        switch (csvmap[j+1][r]){
            case 0:
            case 21:
            case 22:

            case 25:
            case 26:case 27 :case 28 :case 29 :case 30 :case 31 :
            case 32 :case 35 :case 39 :case 40 : case 50 :case 81 :case 82 :case 83 :case 49:
            case 84 :case 85 :case 91 :case 92 :case 93 :case 94 : case 95:
                break;
            case 41:
            case 44:
                gameState= GameSurface.GameStatus.Over;
                break;
            case 55:
                proudscroe +=10;
                Log.e("map","+"+proudscroe);
                csvmap[j+1][r]=0;
                return true;
            case 24:
                gameState= GameSurface.GameStatus.Win;
                return true;
                default:
                return false;
        }
        return true;


    }
    public  static boolean couldup(int x, int y) {
        if(y<54+54/2)
            return false;

        int i=(x+positionX+Mario_w/2)/54;
        int j=y/54;
        switch (csvmap[j][i]){
            case 41:
            case 44:
                return false;
            case 55:
                proudscroe +=10;
                Log.e("map","+"+proudscroe);
                csvmap[j][i]=0;
                return true;

            case 0:
            case 21:
            case 22:

            case 25:
            case 26:case 27 :case 28 :case 29 :case 30 :case 31 :
            case 32 :case 35 :case 39 :case 40 : case 50 :case 81 :case 82 :case 83 : case 49:
            case 84 :case 85 :case 91 :case 92 :case 93 :case 94 : case 95:
                return true;
            case 3:
                //咋箱子
                break;
            case 24:
                gameState= GameSurface.GameStatus.Win;
                return true;
            default:
                return false;
        }
        return false;

    }
    public  static boolean couldright(int x, int y) {
        int mapp=positionX;
        int i=(x+mapp+Mario_w)/54;
        int j=(y+Mario_h/2)/54;

        switch (csvmap[j][i]){
            case 41:
            case 44:
                gameState= GameSurface.GameStatus.Over;

                break;
            case 55:
                proudscroe +=10;
                Log.e("map","+"+proudscroe);
                csvmap[j][i]=0;
                return true;
            case 0:
            case 21:
            case 22:

            case 25:
                    case 26:case 27 :case 28 :case 29 :case 30 :case 31 :
                        case 32 :case 35 :case 39 :case 40 : case 50 :case 81 :case 82 :case 83 :case 49:
                            case 84 :case 85 :case 91 :case 92 :case 93 :case 94 : case 95:
                return true;
            case 24:
                gameState= GameSurface.GameStatus.Win;
                return true;
            default:
                return false;

        } return false;
    }
    public  static boolean couldleft(int x, int y) {
        int i=(x+positionX)/54;
        int j=(y+Mario_h/2)/54;
        if(i-1<0||j<0||x<speed)
            return false;
        switch (csvmap[j][i]){

            case 41:
            case 44:
                gameState= GameSurface.GameStatus.Over;
                break;
            case 55:
                proudscroe +=10;
                Log.e("map","+"+proudscroe);
                csvmap[j][i]=0;
                return true;
            case 0:
            case 21:
            case 22:

            case 25:
            case 26:case 27 :case 28 :case 29 :case 30 :case 31 :
            case 32 :case 35 :case 39 :case 40 : case 50 :case 81 :case 82 :case 83 :case 49:
            case 84 :case 85 :case 91 :case 92 :case 93 :case 94 : case 95:
                return true;
            case 24:
                gameState= GameSurface.GameStatus.Win;
                return true;
            default:
                return false;

        } return false;
    }
}