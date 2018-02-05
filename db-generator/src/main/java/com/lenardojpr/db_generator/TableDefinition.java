package com.lenardojpr.db_generator;

import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by Leonardojpr on 11/27/17.
 */

public class TableDefinition {

    public static Entity addNotificationSetting(Schema schema) {
        Entity beaconControl = schema.addEntity("NotificationSetting");
        beaconControl.setCodeBeforeClass("@SuppressWarnings(\"ALL\")");
        beaconControl.addIdProperty().notNull().autoincrement().unique().primaryKey();
        beaconControl.addStringProperty("topic");
        beaconControl.addBooleanProperty("status");
        return beaconControl;

    }
}
