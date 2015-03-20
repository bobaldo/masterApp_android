package davidepatrizi.com.scadenzarioauto.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;

import davidepatrizi.com.scadenzarioauto.MezzoActivity;
import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class ScadenzeFragment extends Fragment implements View.OnClickListener {
    private EditText txtScadenzaAssicurazione;
    private EditText txtScadenzaBollo;
    private CheckBox txtAllarmaScadenzaAssicurazione;
    private CheckBox txtAllarmaScadenzaBollo;
    private int _id_auto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_scadenze, container, false);
        this.txtScadenzaAssicurazione = (EditText)layout.findViewById(R.id.txtScadenzaAssicurazione);
        this.txtScadenzaBollo=(EditText) layout.findViewById(R.id.txtScadenzaBollo);
        this.txtAllarmaScadenzaAssicurazione = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaAssicurazione);
        this.txtAllarmaScadenzaBollo = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaBollo);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        ((Button) layout.findViewById(R.id.btnSalva)).setOnClickListener(this);
        Toast.makeText(getActivity(), "id auto: " + _id_auto, Toast.LENGTH_LONG).show(); //debug line
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                return saDB.getScadenze(_id_auto);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                cursor.moveToNext();
                //posso fare la getCount perchè so che ritorno sempre un elemento dal DB
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        //TODO: gestire anche i valori allarme_assicurata e allarme bollo
                        txtScadenzaAssicurazione.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE)));
                        txtScadenzaBollo.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_BOLLO)));
                        txtAllarmaScadenzaAssicurazione.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_ASSICURAZIONE))));
                        txtAllarmaScadenzaBollo.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_BOLLO))));
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        ((MezzoActivity) getActivity()).goMainActivity();
                    }
                }
            }
        }.execute();
        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSalva) {
            //TODO: salva i dati nel DB
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    /* *
                    String str_date="13-09-2011";
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = (Date)formatter.parse(str_date);
                    System.out.println("Today is " +date.getTime());
                    * */

                    int time = (int) (System.currentTimeMillis());
                    Timestamp assicurazione = new Timestamp(time);
                    time = (int) (System.currentTimeMillis());
                    Timestamp bollo = new Timestamp(time);

                    ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                    saDB.insertScadenze (_id_auto, bollo, assicurazione,true,false );
                    return null;
                }
            }.execute();
            //TODO: setta un timer
            //TODO:esci
            ((MezzoActivity) getActivity()).showChoose();
        }
    }
}