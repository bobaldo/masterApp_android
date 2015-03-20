package davidepatrizi.com.scadenzarioauto;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {
    private int _id_auto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_info, container, false);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        ((Button) layout.findViewById(R.id.btnElimina)).setOnClickListener(this);
        //Toast.makeText(getActivity(), "id auto: " + _id_auto, Toast.LENGTH_LONG).show(); //debug line
        new AsyncTask<Void, Void, Cursor>() {

            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                return saDB.getMezzo(_id_auto);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                try {
                    cursor.moveToNext();
                    String targa = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TARGA));
                    String tipo = cursor.getString((cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TIPO)));
                    ((EditText) layout.findViewById(R.id.txtTarga)).setText(targa);
                    ((EditText) layout.findViewById(R.id.txtTipo)).setText(tipo);
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    ((MezzoActivity) getActivity()).goMainActivity();
                }
            }
        }.execute();
        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnElimina) {
            getActivity().showDialog(Constant.DIALOG_DELETE_CONFIRM);
        }
    }
}