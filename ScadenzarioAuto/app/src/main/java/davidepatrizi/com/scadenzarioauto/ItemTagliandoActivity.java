package davidepatrizi.com.scadenzarioauto;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.Constant;
import davidepatrizi.com.scadenzarioauto.utility.DataSetterFragment;

public class ItemTagliandoActivity extends ActionBarActivity implements View.OnClickListener {
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button btnManageItem;
    private int _id;
    private int _id_auto;
    private String _targa;
    private boolean _isNew;
    private EditText txtNote;
    private EditText txtSpesaTagliando;
    private TextView txtDataTagliando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tagliando);
        txtDataTagliando = (TextView) findViewById(R.id.txtDataTagliando);
        txtSpesaTagliando = (EditText) findViewById(R.id.txtSpesaTagliando);
        txtNote = (EditText) findViewById(R.id.txtNote);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _isNew = extras.getBoolean(Constant.IS_NEW);
            _id_auto = extras.getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
            _targa = extras.getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);

            if(!_isNew) {
                _id = extras.getInt(ScadenzarioDBEntry._ID);
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(this);
                Cursor cursor = saDB.getTagliando(_id);
                cursor.moveToNext();
                try {

                    //TODO: castare le date al formato dd-mm-yyyy

                    String auxData = cursor.getString((cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_DATA)));
                    auxData = auxData.substring(0, auxData.lastIndexOf(' '));
                    txtDataTagliando.setText(auxData);
                    txtNote.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_NOTE)));
                    txtSpesaTagliando.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_SPESA)));
                }catch (Exception ex){
                    finish(); //close the activity
                }
            }

            ((EditText) findViewById(R.id.txtTarga)).setText(_targa);
            btnManageItem = ((Button) findViewById(R.id.btnManageItem));
            btnManageItem.setText(R.string.ita_salva);
            btnManageItem.setOnClickListener(this);
            ((Button) findViewById(R.id.btnDataTagliando)).setOnClickListener(this);
            if (_isNew) {
                setTitle(getString(R.string.ita_aggungi_tagliando_per) + " " + _targa);
            } else {
                setTitle(getString(R.string.ita_modifica_tagliando_per) + " " + _targa);
            }
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            calendar = null;
        }
        /*
        * switch (id) {
            case Constant.DIALOG_NEW_TAGLIANDO:
                //TODO: inserisci nuovo tagliando
                LayoutInflater factory = LayoutInflater.from(this);
                alertDialogItemTagliando = new AlertDialog.Builder(this)
                        .setTitle(R.string.ita_btnNewTagliando)
                        .setView(factory.inflate(R.layout.dialog_new_tagliando, null))
                        .setPositiveButton(R.string.ita_aggiungi, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //TODO: save data
                                /*String targa = ((TextView)((AlertDialog)dialog).findViewById(R.id.txtTarga)).getText().toString();
                                String tipo = ((Spinner)((AlertDialog)dialog).findViewById(R.id.txtTipo)).getSelectedItem().toString();
                                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(((AlertDialog)dialog).getContext());
                                saDB.insertTarga(targa,tipo);
                                loadTarghe();

    }
})
        .setNegativeButton(R.string.ita_cancella, new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int whichButton) {
        }
        })
        .create();

        View promptView = factory.inflate(R.layout.dialog_new_tagliando, null);
        promptView.findViewById(R.id.btnDataTagliando).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        //TODO: click sull'impostazione della data
        DataSetterFragment dsf = new DataSetterFragment(context, mDateSetListenerDataSpesa, mYear, mMonth, mDay);
        dsf.show();
        }
        });
        ((EditText) promptView.findViewById(R.id.txtTarga)).setText(_targa);
        return alertDialogItemTagliando;
        *
        * */

    }

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerDataSpesa =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    ((TextView) findViewById(R.id.txtDataTagliando)).setText(
                            new StringBuilder()
                                    .append(day).append("-")
                                    .append(month + 1).append("-")
                                    .append(year).append(" "));
                }
            };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDataTagliando) {
            DataSetterFragment dsf = new DataSetterFragment(this, mDateSetListenerDataSpesa, mYear, mMonth, mDay);
            dsf.show();
        } else if (view.getId() == R.id.btnManageItem) {
            //TODO: salva e chiudi
            try {
                String _dataTagliando = txtDataTagliando.getText().toString();
                float spesaTagliando = Float.parseFloat(txtSpesaTagliando.getText().toString());
                String note = txtNote.getText().toString();
                Timestamp dataTagliando = new Timestamp(System.currentTimeMillis());
                Date date = Constant.formatterDDMMYYYY.parse(_dataTagliando);
                dataTagliando.setTime(date.getTime());
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(this);
                if(_isNew){
                    saDB.insertTagliando(_id_auto, dataTagliando, spesaTagliando, note);
                }else {
                    saDB.updateTagliando(_id, _id_auto, dataTagliando, spesaTagliando, note);
                }
                finish();
            } catch (ParseException ex) {
                Toast.makeText(this, "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }catch (Exception ex){
                Toast.makeText(this, R.string.ita_message_data_errata, Toast.LENGTH_LONG).show();
            }
        }
    }
}