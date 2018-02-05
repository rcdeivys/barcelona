package com.lenardojpr.db_generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DBGenerator {
    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java/";


    public static void main(String args[]) throws Exception {
        System.out.println("Esta mierda no sirve" + OUT_DIR);
        Schema schema = new Schema(1, "com.millonarios.MillonariosFC.db");
        schema.enableKeepSectionsByDefault();
        addTables(schema);

        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, "../app/src/main/java");
    }


    private static void addTables(Schema schema) {
        /* entities */
        Entity user = TableDefinition.addNotificationSetting(schema);
    }

}
