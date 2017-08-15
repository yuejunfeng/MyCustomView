package yuejunfeng.bawei.com.mycustomview.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/8/14.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context,String msg){
        if(toast == null){
           toast= Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
