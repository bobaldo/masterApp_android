package davidepatrizi.com.scadenzarioauto.dba;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import davidepatrizi.com.scadenzarioauto.R;

public class ItemTagliandoActivity extends ActionBarActivity {
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tagliando);

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


    /*protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerDataSpesa =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    ((TextView) alertDialogItemTagliando.findViewById(R.id.txtDataTagliando)).setText(
                            new StringBuilder()
                                    .append(day).append("-")
                                    .append(month + 1).append("-")
                                    .append(year).append(" "));
                }
            };
            */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
