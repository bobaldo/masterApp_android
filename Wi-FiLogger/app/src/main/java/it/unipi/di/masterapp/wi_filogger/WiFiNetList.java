package it.unipi.di.masterapp.wi_filogger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WiFiNetList extends ListFragment {
    private AsyncTask<Void, Void, Cursor> asyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // accede al database in un thread diverso da quello UI
        asyncTask = new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                try {
                    // ottiene il database
                    WiFiDbHelper wiFiDbHelper = new WiFiDbHelper(getActivity());
                    SQLiteDatabase db = wiFiDbHelper.getReadableDatabase();

                    // effettua la query
                    return db.query(
                            WiFiNetEntry.TABLE_NAME,
                            new String[]{WiFiNetEntry._ID, WiFiNetEntry.COLUMN_NAME_SSID},
                            null,
                            null,
                            WiFiNetEntry.COLUMN_NAME_SSID,
                            null,
                            null,
                            null
                    );
                } catch (NullPointerException e) { // se l'activity viene distrutta
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                try {
                    // costruisce l'adapter per la lista
                    ListAdapter listAdapter = new SimpleCursorAdapter(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            cursor,
                            new String[]{WiFiNetEntry.COLUMN_NAME_SSID},
                            new int[]{android.R.id.text1},
                            0
                    );
                    setListAdapter(listAdapter);
                } catch (NullPointerException e) { // se l'activity viene distrutta
                    ;
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        // chiede all'activity di mostrare le informazioni relative al SSID selezionato
        String ssid = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
        ((MainActivity) getActivity()).showWiFiNet(ssid);
    }
}
