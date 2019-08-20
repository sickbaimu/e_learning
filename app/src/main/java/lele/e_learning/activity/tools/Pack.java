package lele.e_learning.activity.tools;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class Pack {
    public static String pack(String order,String name,String unit){
        return "第"+order+unit+" "+name;
    }
    public static void ShowToast(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }
}
