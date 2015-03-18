package it.unipi.di.masterapp.wi_filogger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WiFiNetShow extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_show, container, false);

        // ottiene l'SSID dagli argomenti
        final String ssid = getArguments().getString(WiFiNetEntry.COLUMN_NAME_SSID);

        // scrive l'SSID nel layout
        ((TextView) layout.findViewById(R.id.ssid)).setText(ssid);

        // accede al database in un thread diverso da quello UI
        new AsyncTask<Void, Void, Cursor[]>() {
            @Override
            protected Cursor[] doInBackground(Void... params) {
                try {
                    // ottiene il database
                    WiFiDbHelper wiFiDbHelper = new WiFiDbHelper(getActivity());
                    SQLiteDatabase db = wiFiDbHelper.getReadableDatabase();

                    // calcola il segnale e la presenza
                    String paramsQuery = String.format("SELECT AVG(%s), 100 * (" +
                                    "(SELECT CAST(COUNT(DISTINCT %s) AS FLOAT) FROM %s WHERE SSID = ?)" + // le scansioni che contengono l'SSID
                                    "/" +
                                    "(SELECT CAST(COUNT(DISTINCT %s) AS FLOAT) FROM %s)" + // tutte le scansioni
                                    ") FROM %s WHERE %s = ? GROUP BY %s",
                            WiFiNetEntry.COLUMN_NAME_SIGNAL,
                            WiFiNetEntry.COLUMN_NAME_TIMESTAMP,
                            WiFiNetEntry.TABLE_NAME,
                            WiFiNetEntry.COLUMN_NAME_TIMESTAMP,
                            WiFiNetEntry.TABLE_NAME,
                            WiFiNetEntry.TABLE_NAME,
                            WiFiNetEntry.COLUMN_NAME_SSID,
                            WiFiNetEntry.COLUMN_NAME_SSID);
                    Cursor paramsCursor = db.rawQuery(paramsQuery, new String[]{ssid, ssid});

                    // ottiene la lista dei BSSID
                    Cursor bssidCursor = db.query(
                            WiFiNetEntry.TABLE_NAME,
                            new String[]{WiFiNetEntry._ID, WiFiNetEntry.COLUMN_NAME_BSSID},
                            String.format("%s = ?", WiFiNetEntry.COLUMN_NAME_SSID),
                            new String[]{ssid},
                            WiFiNetEntry.COLUMN_NAME_BSSID,
                            null,
                            null,
                            null
                    );
                    return new Cursor[]{paramsCursor, bssidCursor};
                } catch (NullPointerException e) { // se l'activity viene distrutta
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Cursor[] cursors) {
                try {
                    Cursor paramsCursor = cursors[0];
                    Cursor bssidCursor = cursors[1];

                    // scrive presenza e segnale nel layout
                    paramsCursor.moveToNext();
                    float signal = paramsCursor.getFloat(0);
                    float presence = paramsCursor.getFloat(1);
                    ((TextView) layout.findViewById(R.id.signal)).setText(String.format("%.2f%%", signal));
                    ((TextView) layout.findViewById(R.id.presence)).setText(String.format("%.2f%%", presence));
                    paramsCursor.close();

                    // costruisce l'adapter per la lista
                    ListAdapter listAdapter = new SimpleCursorAdapter(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            bssidCursor,
                            new String[]{WiFiNetEntry.COLUMN_NAME_BSSID},
                            new int[]{android.R.id.text1},
                            0
                    );

                    // assegna l'adapter
                    ListView bssidList = (ListView) layout.findViewById(R.id.bssids);
                    bssidList.setAdapter(listAdapter);
                } catch (NullPointerException e) { // se l'activity viene distrutta
                    ;
                }
            }
        }.execute();

        return layout;
    }
}
