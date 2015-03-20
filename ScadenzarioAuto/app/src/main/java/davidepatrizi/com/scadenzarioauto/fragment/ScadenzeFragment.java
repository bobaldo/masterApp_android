package davidepatrizi.com.scadenzarioauto.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import davidepatrizi.com.scadenzarioauto.MezzoActivity;
import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class ScadenzeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int _id_auto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_scadenze, container, false);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        //((Button) layout.findViewById(R.id.btnElimina)).setOnClickListener(this);
        //Toast.makeText(getActivity(), "id auto: " + _id_auto, Toast.LENGTH_LONG).show(); //debug line
        new AsyncTask<Void, Void, Cursor>() {

            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                return saDB.getScadenze(_id_auto);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                try {
                    //TODO: gestire anche i valori allarme_assicurata e allarme bollo
                    cursor.moveToNext();
                    String ass = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE));
                    String bol = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_BOLLO));
                    boolean allAss = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_ASSICURAZIONE)));
                    boolean allBol = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_BOLLO)));
                    ((EditText) layout.findViewById(R.id.txtScadenzaAssicurazione)).setText(ass);
                    ((EditText) layout.findViewById(R.id.txtTipo)).setText(bol);
                    ((CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaAssicurazione)).setChecked(allAss);
                    ((CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaBollo)).setChecked(allBol);
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    ((MezzoActivity) getActivity()).goMainActivity();
                }
            }
        }.execute();
        return layout;
    }
}