package it.unipi.di.masterapp.wi_filogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WiFiDbHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;

    // nome del (file associato al) database
    private final static String DATABASE_NAME = "WiFiLogger.db";

    // codice SQL per create la tabella
    private final static String CREATE_TABLE =
            "CREATE TABLE " + WiFiNetEntry.TABLE_NAME + " (" +
                    WiFiNetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WiFiNetEntry.COLUMN_NAME_TIMESTAMP + " INTEGER, " +
                    WiFiNetEntry.COLUMN_NAME_BSSID + " TEXT, " +
                    WiFiNetEntry.COLUMN_NAME_SSID + " TEXT, " +
                    WiFiNetEntry.COLUMN_NAME_SIGNAL + " INTEGER " +
                    ")";

    // codice SQL per eliminare la tabella
    private final static String DROP_TABLE =
            "DROP TABLE IF EXISTS " + WiFiNetEntry.TABLE_NAME;

    public WiFiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // quando viene trovata una versione più vecchia del database semplicemente si riparte da capo
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // idem come per onUpgrade
        onUpgrade(db, oldVersion, newVersion);
    }
}
