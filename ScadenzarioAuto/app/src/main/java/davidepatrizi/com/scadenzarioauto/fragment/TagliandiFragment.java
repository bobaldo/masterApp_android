package davidepatrizi.com.scadenzarioauto.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.Constant;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class TagliandiFragment extends Fragment implements View.OnClickListener {
    private int _id_auto;
    private String _targa;
    private final Context context = getActivity();
    private ListView txtListaTagliandi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tagliando, container, false);
        ((Button) layout.findViewById(R.id.btnAdd)).setOnClickListener(this);
        this.txtListaTagliandi = (ListView) layout.findViewById(R.id.txtListaTagliandi);
        this.txtListaTagliandi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: opem detail when click on item
            }
        });
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        _targa = getArguments().getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                try {
                    ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(context);
                    return saDB.getTagliandi(_id_auto);
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
                            new String[]{ScadenzarioDBEntry._ID + " " + ScadenzarioDBEntry.COLUMN_NAME_DATA},
                            new int[]{android.R.id.text1},
                            0
                    );
                    txtListaTagliandi.setAdapter(listAdapter);
                } catch (NullPointerException e) {
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            //TODO: aprire nuova activity
            //getActivity().showDialog(Constant.DIALOG_NEW_TAGLIANDO);
        }
    }
}