package exam.nsj.com.exam;

import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getAppContext() {
        return instance;
    }
}
