package it.unipi.di.masterapp.wi_filogger;

import android.provider.BaseColumns;

public class WiFiNetEntry implements BaseColumns {
    public final static String TABLE_NAME = "WiFiNet";
    public final static String COLUMN_NAME_TIMESTAMP = "Timestamp";
    public final static String COLUMN_NAME_BSSID = "BSSID";
    public final static String COLUMN_NAME_SSID = "SSID";
    public final static String COLUMN_NAME_SIGNAL = "Signal";
}
