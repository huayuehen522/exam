package exam.nsj.com.exam;

import android.content.Context;
import android.util.Log;

/**
 * Created by Ni Shaojian on 2018/3/29.
 */

public class Application extends android.app.Application {
    private static Context context;
    public static Context getAppContext() {
        return Application.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(Application.class.getSimpleName(), "[force stop app], onTerminate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(Application.class.getSimpleName(), "[force stop app], onTrimMemory");
    }
}
