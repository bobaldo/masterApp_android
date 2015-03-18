package it.unipi.di.masterapp.wi_filogger;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

public class WiFiReceiver extends BroadcastReceiver {
    private final static String TAG = "Receiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        // una nuova scansione degli AP è stata effettuata dal sistema
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            Log.d(TAG, "Wi-Fi scan results available...");

            // accede al database in un thread diverso da quello UI
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    // identifica questa scansione con un timestamp
                    long timestamp = System.currentTimeMillis();

                    // acede al database
                    WiFiDbHelper wiFiDbHelper = new WiFiDbHelper(context);
                    SQLiteDatabase db = wiFiDbHelper.getWritableDatabase();

                    // inizia una transazione per poter inserire tutti i risultato con "un'unica operazione"
                    db.beginTransaction();

                    // scorre i risultati
                    ContentValues contentValues = new ContentValues();
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    for (ScanResult scanResult : wifiManager.getScanResults()) {
                        Log.d(TAG, "> " + scanResult.SSID);

                        // calcola la potenza del segnale come intero tra 0 e 100
                        int signal = WifiManager.calculateSignalLevel(scanResult.level, 101);

                        // inserisce questo record
                        contentValues.put(WiFiNetEntry.COLUMN_NAME_TIMESTAMP, timestamp);
                        contentValues.put(WiFiNetEntry.COLUMN_NAME_BSSID, scanResult.BSSID);
                        contentValues.put(WiFiNetEntry.COLUMN_NAME_SSID, scanResult.SSID);
                        contentValues.put(WiFiNetEntry.COLUMN_NAME_SIGNAL, signal);
                        db.insert(WiFiNetEntry.TABLE_NAME, null, contentValues);
                    }

                    // effettua la scrittura nel database
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    return null;
                }
            }.execute();
        }
    }
}
