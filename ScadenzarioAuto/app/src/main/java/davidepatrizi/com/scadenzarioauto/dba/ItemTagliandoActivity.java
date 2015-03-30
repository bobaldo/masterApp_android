package davidepatrizi.com.scadenzarioauto.dba;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.utility.Constant;
import davidepatrizi.com.scadenzarioauto.utility.DataSetterFragment;

public class ItemTagliandoActivity extends ActionBarActivity implements View.OnClickListener {
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button btnManageItem;
    private int _id_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tagliando);
        //TODO: recuperare l'id_auto
        btnManageItem = ((Button) findViewById(R.id.btnManageItem));
        btnManageItem.setOnClickListener(this);
        ((Button) findViewById(R.id.btnDataTagliando)).setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar = null;

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
                String _dataTagliando = ((TextView) findViewById(R.id.txtDataTagliando)).getText().toString();
                float spesaTagliando = Float.parseFloat(((EditText) findViewById(R.id.txtSpesaTagliando)).getText().toString());
                String note = ((EditText) findViewById(R.id.txtNote)).getText().toString();
                Timestamp dataTagliando = new Timestamp(System.currentTimeMillis());
                Date date = (Date) Constant.formatterDDMMYYYY.parse(_dataTagliando);
                dataTagliando.setTime(date.getTime());

                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(this);
                saDB.insertTagliando(_id_auto, dataTagliando, spesaTagliando, note);
                finish();
            }catch (ParseException ex){

            }
        }
    }
}