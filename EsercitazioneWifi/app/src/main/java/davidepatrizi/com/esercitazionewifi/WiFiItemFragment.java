package davidepatrizi.com.esercitazionewifi;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleCursorAdapter;

public class WiFiItemFragment extends ListFragment {
    private SimpleCursorAdapter simpleArrayAdapter;
    private WiFiDBHelper db;

    public WiFiItemFragment() {
        db = new WiFiDBHelper(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor c = db.selectsWifi();
        simpleArrayAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, c, new String[]{"ssid"}, new int[]{android.R.id.text1});
        this.setListAdapter(simpleArrayAdapter);
    }
}
