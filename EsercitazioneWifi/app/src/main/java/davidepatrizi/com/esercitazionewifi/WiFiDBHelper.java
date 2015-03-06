package davidepatrizi.com.esercitazionewifi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Bobaldo on 06/03/2015.
 */
public class WiFiDBHelper  extends SQLiteOpenHelper {
    private static final String WIFI_RESULT = "tbl_wifi_result";
    private static final String DB_NAME = "data.db";
    private static final int DB_VERSION = 1;
    private Context ctx;
    private SQLiteDatabase mDb;

    public WifiResult selectsWifi(int _id) {
        //TODO:
        return null;
    }

    public Cursor selectsWifi() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(WIFI_RESULT, null, null, null, DataDB.SSID, null, null);
    }

    public void insertWiFiResults(List<WifiResult> result) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < result.size(); i++) {
            insertWiFiResult(result.get(i).ssid, result.get(i).bssid, result.get(i).signal);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertWiFiResult(String ssid, String bssis, String signal) {
        ContentValues cv = new ContentValues();
        cv.put(DataDB.SSID, ssid);
        cv.put(DataDB.BSSID, bssis);
        cv.put(DataDB.SIGNAL, signal);
        cv.put(DataDB.TIMESTAMP, (new Timestamp((int) (System.currentTimeMillis())).toString()));
        mDb.insert(WIFI_RESULT, null, cv);
    }

    private static final String WIFI_RESULT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + WIFI_RESULT + " ("
            + DataDB._ID + " integer primary key autoincrement, "
            + DataDB.SSID + " text not null, "
            + DataDB.BSSID + " text not null, "
            + DataDB.SIGNAL + " text not null, "
            + DataDB.TIMESTAMP + " text not null );";

    private static final String WIFI_RESULT_TABLE_DELETE = "DELETE FROM " + WIFI_RESULT;


    public WiFiDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella
        _db.execSQL(WIFI_RESULT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
        _db.execSQL(WIFI_RESULT_TABLE_DELETE);
        _db.execSQL(WIFI_RESULT_TABLE_CREATE);
    }
}