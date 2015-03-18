package davidepatrizi.com.esercitazionewifi;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class WiFiItemFragment extends ListFragment {
    private SimpleCursorAdapter simpleArrayAdapter;

    public WiFiItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WiFiDBHelper db = new WiFiDBHelper(getActivity());
        Cursor c = db.selectsWifi();
        if (c != null)
            Toast.makeText(getActivity(), String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
        simpleArrayAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, c, new String[]{DataDB.SSID}, new int[]{android.R.id.text1});
        this.setListAdapter(simpleArrayAdapter);
    }
}