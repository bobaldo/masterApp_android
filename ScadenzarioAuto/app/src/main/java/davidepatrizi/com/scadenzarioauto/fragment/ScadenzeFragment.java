package davidepatrizi.com.scadenzarioauto.fragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        mYearBol = mYearAss = calendar.get(Calendar.YEAR);
        mMonthBol = mMonthAss = calendar.get(Calendar.MONTH);
        mDayBol = mDayAss = calendar.get(Calendar.DAY_OF_MONTH);
        calendar = null;
        alarm = new AlarmReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_scadenze, container, false);
        this.txtScadenzaAssicurazione = (TextView) layout.findViewById(R.id.txtScadenzaAssicurazione);
        this.txtScadenzaBollo = (TextView) layout.findViewById(R.id.txtScadenzaBollo);
        this.txtAllarmaScadenzaAssicurazione = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaAssicurazione);
        this.txtAllarmaScadenzaBollo = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaBollo);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        _targa = getArguments().getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);
        ((Button) layout.findViewById(R.id.btnScadenzaAssicurazione)).setOnClickListener(this);
        ((Button) layout.findViewById(R.id.btnScadenzaBollo)).setOnClickListener(this);
        txtAllarmaScadenzaBollo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onFireCheckedEvent) {
                    if (save()) {
                        if (isChecked) {
                            Toast.makeText(getActivity(), R.string.ita_avviso_bollo_attivato, Toast.LENGTH_LONG).show();
                            //TODO: setta un timer + gestire la notifica delle notification nel tempo
                        }
                    }
                }

            }
        });
        txtAllarmaScadenzaAssicurazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onFireCheckedEvent) {
                    if (save()) {
                        if (isChecked) {
                            //setta un alarm
                            alarm.setAlarm(getActivity(), Constant.ALARM_SCADENZA_ASSICURAZIONE, txtScadenzaAssicurazione.getText().toString(), _targa);
                            Toast.makeText(getActivity(), R.string.ita_avviso_assicurazione_attivato, Toast.LENGTH_LONG).show();
                        }else{
                            alarm.cancelAlarm(getActivity(), Constant.ALARM_SCADENZA_ASSICURAZIONE, txtScadenzaAssicurazione.getText().toString(), _targa);
                        }
                    }
                }
            }
        });

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                return saDB.getScadenze(_id_auto);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                cursor.moveToNext();
                //posso fare la getCount perchè so che ritorno sempre un solo elemento dal DB
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        updateDisplay(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE)), txtScadenzaAssicurazione);
                        updateDisplay(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_BOLLO)), txtScadenzaBollo);

                        Boolean allarmaScadenzaAssicurazione = false;
                        Boolean allarmaScadenzaBollo = false;
                        if (cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_ASSICURAZIONE)) == 1) {
                            allarmaScadenzaAssicurazione = true;
                        }
                        if (cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_BOLLO)) == 1) {
                            allarmaScadenzaBollo = true;
                        }

                        txtAllarmaScadenzaAssicurazione.setChecked(allarmaScadenzaAssicurazione);
                        txtAllarmaScadenzaBollo.setChecked(allarmaScadenzaBollo);
                        onFireCheckedEvent = true;
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
        return layout;
    }

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerAssicurazione =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearAss = year;
                    mMonthAss = monthOfYear;
                    mDayAss = dayOfMonth;
                    updateDisplay(mYearAss, mMonthAss, mDayAss, txtScadenzaAssicurazione, true);
                }
            };

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerBollo =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearBol = year;
                    mMonthBol = monthOfYear;
                    mDayBol = dayOfMonth;
                    updateDisplay(mYearBol, mMonthBol, mDayBol, txtScadenzaBollo, true);
                }
            };

    public void updateDisplay(String date, TextView textView) {
        if (!date.isEmpty() && date != null) {
            try {
                Date _date = (Date) Constant.formatterYYYYMMDD.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(_date);
                updateDisplay(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), textView, false);
            } catch (ParseException ex) {
                Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                ((MezzoActivity) getActivity()).goMainActivity();
            }
        }
    }

    public void updateDisplay(int year, int month, int day, TextView textView, boolean doSave) {
        textView.setText(
                new StringBuilder()
                        .append(day).append("-")
                        .append(month + 1).append("-")
                        .append(year).append(" "));
        if (doSave) {
            if (save()) {
                //TODO: Toast di avviso salvataggio ok che discrimina sul textView
                //Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
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

    public boolean save() {
        boolean ret = true;
        try {
            Timestamp assicurazione = new Timestamp(System.currentTimeMillis());
            Timestamp bollo = new Timestamp(System.currentTimeMillis());
            String timeAss = txtScadenzaAssicurazione.getText().toString();
            String timeBol = txtScadenzaBollo.getText().toString();
            if (timeAss != null) {
                try {
                    Date date = (Date) Constant.formatterDDMMYYYY.parse(timeAss);
                    assicurazione.setTime(date.getTime());
                } catch (ParseException ex) {
                    Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    ret = false;
                }
            }

            if (timeBol != null) {
                try {
                    Date date = (Date) Constant.formatterDDMMYYYY.parse(timeBol);
                    bollo.setTime(date.getTime());
                } catch (ParseException ex) {
                    Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    ret = false;
                }
            }

            ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
            saDB.insertScadenze(_id_auto, bollo, assicurazione, txtAllarmaScadenzaBollo.isChecked(), txtAllarmaScadenzaAssicurazione.isChecked());
        } catch (Exception ex) {
            ret = false;
        }
        return ret;
    }

    private Timestamp getTimeStamp(String date) {
        Timestamp ret = new Timestamp(System.currentTimeMillis());
        try {
            Date d = (Date) Constant.formatterDDMMYYYY.parse(date);
            ret.setTime(d.getTime());
        } catch (ParseException ex) {
            //Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return ret;
    }
}