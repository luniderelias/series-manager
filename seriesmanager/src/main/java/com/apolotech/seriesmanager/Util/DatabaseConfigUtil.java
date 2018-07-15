package com.apolotech.seriesmanager.Util;

import static com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigFile;

public class DatabaseConfigUtil {
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt");
    }
}