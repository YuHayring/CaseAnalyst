package cn.hayring.caseanalyst;

import android.app.Application;

/**
 * @author hayring
 * @date 6/21/21 7:39 PM
 */
public class CaseAnalystApplication extends Application {

    private static CaseAnalystApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CaseAnalystApplication getInstance() {
        return instance;
    }
}
