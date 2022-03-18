package njupt.b17070729.WaterAndFire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import njupt.b17070729.WaterAndFire.R;
import njupt.b17070729.WaterAndFire.constant;

import static njupt.b17070729.WaterAndFire.constant.loadconfig;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public Button start;
    public Button over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_land);
        start=findViewById(R.id.button);
        over=findViewById(R.id.button2);
        start.setOnClickListener(this);
        over.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.button)
        {
            //加载配置
            if (isFristRun()){
                //获取屏幕的宽高
                DisplayMetrics display = getResources().getDisplayMetrics();
                constant.loadfrist(display);
                SharedPreferences preferences= getSharedPreferences("gamedata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();

                int W=constant.w;
                int H=constant.h;
                float proprotion=constant.proportion;
                int CBX=constant.CBX;
                int CBY=constant.CBY;
                int CAX=constant.CAX;
                int CAY=constant.CAY;
                int ABR=constant.ABR;
                float RBig=constant.RBig;
                float  RSmall=constant.RSmall;
                int life=constant.life;

                editor.putInt("life",life);
                editor.putInt("W",W);
                editor.putInt("H",H);
                editor.putInt("CBX",CBX);
                editor.putInt("CBY",CBY);
                editor.putInt("CAX",CAX);
                editor.putInt("CAY",CAY);
                editor.putInt("ABR",ABR);
                editor.putFloat("proprotion",proprotion);
                editor.putFloat("RBig",RBig);
                editor.putFloat("RSmall",RSmall);
                editor.apply();
            }else {
                SharedPreferences preferences= getSharedPreferences("gamedata", Context.MODE_PRIVATE);
                constant.w=preferences.getInt("W",constant.w);
                constant.h=preferences.getInt("H",constant.h);
                constant.proportion=preferences.getFloat("proprotion", constant.proportion);
                constant.CBX=preferences.getInt("CBX",constant.CBX);
                constant.CBY=preferences.getInt("CBY",constant.CBY);
                constant.CAX=preferences.getInt("CAX",constant.CAX);
                constant.CAY=preferences.getInt("CAY",constant.CAY);
                constant.ABR=preferences.getInt("ABR",constant.ABR);
                constant.RBig=preferences.getFloat("RBig", constant.RBig);
                constant.RSmall=preferences.getFloat("RSmall", constant.RSmall);
                constant.life=preferences.getInt("life",constant.life);
                loadconfig();
            }

            Intent it = new Intent(getApplicationContext(), GameStartActivity.class);
            startActivity(it);
        }
        else if(v.getId()== R.id.button2)
        {
            Intent it = new Intent(getApplicationContext(), config.class);
            startActivity(it);
            finish();
        }
    }

//检验是否为第一次开启应用，第一次会存一个新建SharedPreferences。
    private boolean isFristRun() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "gamedata", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            editor.putBoolean("isFirstRun", false);
            editor.apply();
            return true;
        }
    }
}
