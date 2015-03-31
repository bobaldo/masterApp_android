package davidepatrizi.com.scadenzarioauto.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import davidepatrizi.com.scadenzarioauto.ListTagliandoAdapter;
import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.ItemTagliandoActivity;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.Constant;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class TagliandiFragment extends Fragment implements View.OnClickListener {
    private int _id_auto;
    private String _targa;
    private ListView txtListaTagliandi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tagliando, container, false);
        ((Button) layout.findViewById(R.id.btnAdd)).setOnClickListener(this);
        this.txtListaTagliandi = (ListView) layout.findViewById(R.id.txtListaTagliandi);
        this.txtListaTagliandi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: open detail when click on item
                Cursor cursor = (Cursor) txtListaTagliandi.getItemAtPosition(i);
                int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry._ID));
                Intent intent = new Intent(getActivity(), ItemTagliandoActivity.class);
                intent.putExtra(ScadenzarioDBEntry.COLUMN_NAME_TARGA, _targa);
                intent.putExtra(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id_auto);
                intent.putExtra(ScadenzarioDBEntry._ID, _id);
                intent.putExtra(Constant.IS_NEW, false);
                startActivityForResult(intent, Constant.RELOAD_DATA);
            }
        });
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        _targa = getArguments().getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);
        loadData();
    }

    private void loadData() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                try {
                    ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                    return saDB.getTagliandi(_id_auto);
                } catch (NullPointerException e) { // se l'activity viene distrutta
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        txtListaTagliandi.setAdapter(new ListTagliandoAdapter(getActivity(), cursor));
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            Intent intent = new Intent(getActivity(), ItemTagliandoActivity.class);
            intent.putExtra(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id_auto);
            intent.putExtra(ScadenzarioDBEntry.COLUMN_NAME_TARGA, _targa);
            intent.putExtra(Constant.IS_NEW, true);
            startActivityForResult(intent, Constant.RELOAD_DATA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RELOAD_DATA) {
            loadData();
        }
    }
}