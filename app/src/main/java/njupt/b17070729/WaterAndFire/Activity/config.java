package njupt.b17070729.WaterAndFire.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import njupt.b17070729.WaterAndFire.R;

;

//该Activity可以通过修改sharepre
public class config extends BaseActivity implements View.OnClickListener {
    Button button;
    EditText editText1;
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
         preferences= getSharedPreferences("gamedata", Context.MODE_PRIVATE);

        button=findViewById(R.id.lifenumBT);
        button.setOnClickListener(this);

        EditText editText1 =(EditText) findViewById (R.id.lifenum);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.lifenumBT)
        {
            try{

                String str=  editText1.getText().toString();
                Log.e("life","str="+str);
                int i =Integer.parseInt(editText1.getText().toString());
                Log.e("life","i="+i);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putInt("life",i);
                editor.apply();

            }catch (Exception e)
            {

            }


        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //表示此按键已处理，不再交给系统处理，
            //从而避免游戏被切入后台
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //处理back返回按键//表示此按键已处理，不再交给系统处理，从而避免游戏被切入后台
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //游戏胜利、失败、进行时都默认返回菜单
                 finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
