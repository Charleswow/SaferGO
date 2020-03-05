package xin.skingorz.Bean;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class BaseApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    public void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

}

