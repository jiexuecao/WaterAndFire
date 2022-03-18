package njupt.b17070729.WaterAndFire.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import njupt.b17070729.WaterAndFire.GameSurface;
import njupt.b17070729.WaterAndFire.constant;

public class GameStartActivity extends BaseActivity {
    public GameSurface gameSurface;
  //  public static GameStartActivity gameActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gameActivity = this;
        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
        constant.gameActivity=this;//方便其他类调用上下文
    }



}
