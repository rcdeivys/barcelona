package com.millonarios.MillonariosFC.commons;

import android.content.Context;

import com.millonarios.MillonariosFC.db.DaoMaster;
import com.millonarios.MillonariosFC.db.DaoSession;
import com.millonarios.MillonariosFC.db.NotificationSetting;
import com.millonarios.MillonariosFC.db.NotificationSettingDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by Leonardojpr on 11/27/17.
 */

public class DBController {

    private static final String TAG = DBController.class.getSimpleName();
    public static final String DBNAME = "com.millonarios.MillonariosFC.sqlite";


    private static DBController dbController = null;
    private DaoMaster daoMaster = null;

    private DBController() {
    }

    public static DBController getControler() {
        if (dbController == null)
            dbController = new DBController();
        return dbController;
    }

    public void createDB(Context context) {
        DaoMaster.createAllTables(connection(context), true);
    }

    @SuppressWarnings("unused")
    public void cleanBD(Context context) {
        DaoMaster daoMaster = new DaoMaster(connection(context));
        daoMaster.newSession().clear();
    }

    private Database connection(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DBNAME, null);
        Database db = openHelper.getWritableDb();
        return db;
    }

    private DaoSession getSession(Context context) {
        if (daoMaster == null)
            daoMaster = new DaoMaster(connection(context));
        return daoMaster.newSession();
    }


    public void createNotificationSetting(Context context, NotificationSetting notificationSetting) {
        NotificationSettingDao notificationSettingDao = getSession(context).getNotificationSettingDao();
        notificationSettingDao.insertOrReplace(notificationSetting);
    }

    public void removeNotificationSetting(Context context, NotificationSetting notificationSetting) {
        NotificationSettingDao notificationSettingDao = getSession(context).getNotificationSettingDao();
        notificationSettingDao.delete(notificationSetting);
    }

    public NotificationSetting getNotificationSetting(Context context, Long notification_id) {
        DaoSession session = getSession(context);
        NotificationSettingDao notificationSettingDao = session.getNotificationSettingDao();
        return notificationSettingDao.load(notification_id);
    }

    public List<NotificationSetting> geNotificationSettingList(Context context) {
        DaoSession session = getSession(context);
        NotificationSettingDao notificationSettingDao = session.getNotificationSettingDao();
        return notificationSettingDao.loadAll();
    }
}

