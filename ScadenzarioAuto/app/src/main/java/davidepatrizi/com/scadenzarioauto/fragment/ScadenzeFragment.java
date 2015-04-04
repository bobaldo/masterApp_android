package davidepatrizi.com.scadenzarioauto.fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import davidepatrizi.com.scadenzarioauto.MezzoActivity;
import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.AlarmReceiver;
import davidepatrizi.com.scadenzarioauto.utility.Constant;
import davidepatrizi.com.scadenzarioauto.utility.DataSetterFragment;
import davidepatrizi.com.scadenzarioauto.utility.ScadenzeItem;

/**
 * Created by Bobaldo on 19/03/2015.
 */
public class ScadenzeFragment extends Fragment implements View.OnClickListener {
    private TextView txtScadenzaAssicurazione;
    private TextView txtScadenzaBollo;
    private CheckBox txtAllarmaScadenzaAssicurazione;
    private CheckBox txtAllarmaScadenzaBollo;
    private int _id_auto;
    private String _targa;
    private int mYearAss;
    private int mMonthAss;
    private int mDayAss;
    private int mYearBol;
    private int mMonthBol;
    private int mDayBol;
    private boolean onFireCheckedEvent = false;
    private AlarmReceiver alarm;
    private ScadenzarioAdapterDB saDB = null;
    private ScadenzeItem scadenzeItem = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        mYearBol = mYearAss = calendar.get(Calendar.YEAR);
        mMonthBol = mMonthAss = calendar.get(Calendar.MONTH);
        mDayBol = mDayAss = calendar.get(Calendar.DAY_OF_MONTH);
        calendar = null;
        alarm = new AlarmReceiver();
        saDB = new ScadenzarioAdapterDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_scadenze, container, false);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        _targa = getArguments().getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);

        this.txtScadenzaAssicurazione = (TextView) layout.findViewById(R.id.txtScadenzaAssicurazione);
        this.txtScadenzaBollo = (TextView) layout.findViewById(R.id.txtScadenzaBollo);
        this.txtAllarmaScadenzaAssicurazione = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaAssicurazione);
        this.txtAllarmaScadenzaBollo = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaBollo);

        (layout.findViewById(R.id.btnScadenzaAssicurazione)).setOnClickListener(this);
        (layout.findViewById(R.id.btnScadenzaBollo)).setOnClickListener(this);

        txtAllarmaScadenzaBollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onFireCheckedEvent) {
                    save();
                    if (isChecked) {
                        //settare l'alarm
                        try {
                            alarm.setAlarm(getActivity(), Constant.ALARM_SCADENZA_BOLLO, scadenzeItem.getBollo().toString(), _targa);
                            Toast.makeText(getActivity(), R.string.ita_avviso_bollo_attivato, Toast.LENGTH_LONG).show();
                        } catch (ParseException ex) {
                            Toast.makeText(getActivity(), R.string.ita_avvismo_impossibile_impostare_allarme, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        alarm.cancelAlarm(getActivity(), Constant.ALARM_SCADENZA_BOLLO, scadenzeItem.getBollo().toString(), _targa);
                    }
                }
            }
        });

        txtAllarmaScadenzaAssicurazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onFireCheckedEvent) {
                    save();
                    if (isChecked) {
                        //settare l'alarm
                        try {
                            alarm.setAlarm(getActivity(), Constant.ALARM_SCADENZA_ASSICURAZIONE, scadenzeItem.getAssicurazione().toString(), _targa);
                            Toast.makeText(getActivity(), R.string.ita_avviso_assicurazione_attivato, Toast.LENGTH_LONG).show();
                        } catch (ParseException ex) {
                            Toast.makeText(getActivity(), R.string.ita_avvismo_impossibile_impostare_allarme, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        alarm.cancelAlarm(getActivity(), Constant.ALARM_SCADENZA_ASSICURAZIONE, scadenzeItem.getAssicurazione().toString(), _targa);
                    }
                }
            }
        });
        load();
        return layout;
    }

    private void load() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                return saDB.getScadenze(_id_auto);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                cursor.moveToNext();
                //posso fare la getCount perchè so che ritorno sempre un solo elemento dal DB
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        scadenzeItem = new ScadenzeItem(cursor, _id_auto);
                        updateDisplay(scadenzeItem.getAssicurazione(), txtScadenzaAssicurazione);
                        updateDisplay(scadenzeItem.getBollo(), txtScadenzaBollo);
                        txtAllarmaScadenzaAssicurazione.setChecked(scadenzeItem.getAllarmaAssicurazione());
                        txtAllarmaScadenzaBollo.setChecked(scadenzeItem.getAllarmaBollo());
                        onFireCheckedEvent = true; //per evitare che al primo checked salvi i dati
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        ((MezzoActivity) getActivity()).goMainActivity();
                    }
                } else {
                    txtScadenzaAssicurazione.setText("");
                    txtScadenzaBollo.setText("");
                    txtAllarmaScadenzaAssicurazione.setChecked(false);
                    txtAllarmaScadenzaBollo.setChecked(false);
                }
            }
        }.execute();
    }

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerAssicurazione =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearAss = year;
                    mMonthAss = monthOfYear;
                    mDayAss = dayOfMonth;
                    scadenzeItem.setAssicurazione(dayOfMonth, monthOfYear, year);
                    alarm.cancelAlarm(getActivity(), Constant.ALARM_SCADENZA_ASSICURAZIONE, scadenzeItem.getAssicurazione().toString(), _targa);
                    scadenzeItem.setAllarmaAssicurazione(0);
                    updateDisplay(mYearAss, mMonthAss, mDayAss, txtScadenzaAssicurazione, txtAllarmaScadenzaAssicurazione);
                    save();
                }
            };

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerBollo =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearBol = year;
                    mMonthBol = monthOfYear;
                    mDayBol = dayOfMonth;
                    scadenzeItem.setBollo(dayOfMonth, monthOfYear, year);
                    alarm.cancelAlarm(getActivity(), Constant.ALARM_SCADENZA_BOLLO, scadenzeItem.getBollo().toString(), _targa);
                    scadenzeItem.setAllarmaBollo(0);
                    updateDisplay(mYearBol, mMonthBol, mDayBol, txtScadenzaBollo, txtAllarmaScadenzaBollo);
                    save();
                }
            };

    private void save() {
        saDB.insertScadenze(scadenzeItem.getIdAuto(),
                scadenzeItem.getBollo(),
                scadenzeItem.getAssicurazione(),
                scadenzeItem.getAllarmaBollo(),
                scadenzeItem.getAllarmaAssicurazione());
    }

    public void updateDisplay(int year, int month, int day, TextView textView, CheckBox checkBox) {
        textView.setText(new StringBuilder()
                .append(day).append("-")
                .append(month + 1).append("-")
                .append(year).append(" "));
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
    }

    public void updateDisplay(Timestamp date, TextView textView) {
        if (date != null) {
            try {
                Date _date = Constant.formatterYYYYMMDD.parse(date.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(_date);
                updateDisplay(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), textView, null);
            } catch (ParseException ex) {
                Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                ((MezzoActivity) getActivity()).goMainActivity();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnScadenzaAssicurazione) {
            DataSetterFragment dsf = new DataSetterFragment(getActivity(), mDateSetListenerAssicurazione, mYearAss, mMonthAss, mDayAss);
            dsf.show();
        } else if (view.getId() == R.id.btnScadenzaBollo) {
            DataSetterFragment dsf = new DataSetterFragment(getActivity(), mDateSetListenerBollo, mYearBol, mMonthBol, mDayBol);
            dsf.show();
        }
    }
}