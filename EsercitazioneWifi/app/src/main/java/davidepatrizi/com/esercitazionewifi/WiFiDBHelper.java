package davidepatrizi.com.esercitazionewifi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Bobaldo on 06/03/2015.
 */
public class WiFiDBHelper  extends SQLiteOpenHelper {
    private static final String WIFI_RESULT = "tbl_wifi_result";
    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;
    private Context ctx;
private final SQLiteDatabase db;

    public WiFiDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    public WifiResult selectsWifi(String ssis) {
        //TODO:
        return null;
    }

    public Cursor selectsWifi() {
        try {

            return db.query(WIFI_RESULT, new String[]{DataDB.SSID}, null, null, DataDB.SSID, null, null);
        } catch (Exception ex) {
            return null;
        }
    }

    public void insertWiFiResults(List<ScanResult> result) {

            new AsyncTask<List<ScanResult>, Void, Void>() {
                @Override
                protected Void doInBackground(List<ScanResult>... lists) {
                    try {
                        String timeStamp = (new Timestamp((int) (System.currentTimeMillis())).toString());
                        db.beginTransaction();
                        for (int i = 0; i < lists[0].size(); i++) {
                            String signal = String.valueOf(WifiManager.calculateSignalLevel(lists[0].get(i).level, 101));
                            ContentValues cv = new ContentValues();
                            cv.put(DataDB.SSID, lists[0].get(i).SSID);
                            cv.put(DataDB.BSSID, lists[0].get(i).BSSID);
                            cv.put(DataDB.SIGNAL, signal);
                            cv.put(DataDB.TIMESTAMP, timeStamp);
                            db.insert(WIFI_RESULT, null, cv);
                        }

                        db.setTransactionSuccessful();
                        db.endTransaction();

                    } catch (Exception ex) {
                        ;
                    }
                    return null;
                }
            }.execute();

    }

    private static final String WIFI_RESULT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + WIFI_RESULT + " ("
            + DataDB._ID + " integer primary key autoincrement, "
            + DataDB.SSID + " text not null, "
            + DataDB.BSSID + " text not null, "
            + DataDB.SIGNAL + " text not null, "
            + DataDB.TIMESTAMP + " text not null );";

    private static final String WIFI_RESULT_TABLE_DELETE = "DELETE FROM " + WIFI_RESULT;

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(WIFI_RESULT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
        _db.execSQL(WIFI_RESULT_TABLE_DELETE);
        onCreate(_db);
    }
}