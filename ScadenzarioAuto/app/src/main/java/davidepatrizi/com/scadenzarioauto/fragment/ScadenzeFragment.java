package davidepatrizi.com.scadenzarioauto.fragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
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
    private int mYearAss;
    private int mMonthAss;
    private int mDayAss;
    private int mYearBol;
    private int mMonthBol;
    private int mDayBol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        mYearBol = mYearAss = calendar.get(Calendar.YEAR);
        mMonthBol = mMonthAss = calendar.get(Calendar.MONTH);
        mDayBol = mDayAss = calendar.get(Calendar.DAY_OF_MONTH);
        calendar = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_scadenze, container, false);
        this.txtScadenzaAssicurazione = (TextView) layout.findViewById(R.id.txtScadenzaAssicurazione);
        this.txtScadenzaBollo = (TextView) layout.findViewById(R.id.txtScadenzaBollo);
        this.txtAllarmaScadenzaAssicurazione = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaAssicurazione);
        this.txtAllarmaScadenzaBollo = (CheckBox) layout.findViewById(R.id.txtAllarmaScadenzaBollo);
        _id_auto = getArguments().getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
        ((Button) layout.findViewById(R.id.btnSalva)).setOnClickListener(this);
        ((Button) layout.findViewById(R.id.btnScadenzaAssicurazione)).setOnClickListener(this);
        ((Button) layout.findViewById(R.id.btnScadenzaBollo)).setOnClickListener(this);
        //Toast.makeText(getActivity(), "id auto: " + _id_auto, Toast.LENGTH_LONG).show(); //debug line
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
                    updateDisplay(mYearAss, mMonthAss, mDayAss, txtScadenzaAssicurazione);
                }
            };

    protected android.app.DatePickerDialog.OnDateSetListener mDateSetListenerBollo =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearBol = year;
                    mMonthBol = monthOfYear;
                    mDayBol = dayOfMonth;
                    updateDisplay(mYearBol, mMonthBol, mDayBol, txtScadenzaBollo);
                }
            };

    public void updateDisplay(String date, TextView textView) {
        try {
            Date _date = (Date) Constant.formatterYYYYMMDD.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(_date);
            updateDisplay(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), textView);
        } catch (ParseException ex) {
            Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ((MezzoActivity) getActivity()).goMainActivity();
        }
    }

    public void updateDisplay(int year, int month, int day, TextView textView) {
        textView.setText(
                new StringBuilder()
                        .append(day).append("-")
                        .append(month + 1).append("-")
                        .append(year).append(" "));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnScadenzaAssicurazione) {
            DataSetterFragment dsf = new DataSetterFragment(getActivity(), mDateSetListenerAssicurazione, mYearAss, mMonthAss, mDayAss);
            dsf.show();
        } else if (view.getId() == R.id.btnScadenzaBollo) {
            DataSetterFragment dsf = new DataSetterFragment(getActivity(), mDateSetListenerBollo, mYearBol, mMonthBol, mDayBol);
            dsf.show();
        } else if (view.getId() == R.id.btnSalva) {
            //salva i dati nel DB
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
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
                        }
                    }

                    if (timeBol != null) {
                        try {
                            Date date = (Date) Constant.formatterDDMMYYYY.parse(timeBol);
                            bollo.setTime(date.getTime());
                        } catch (ParseException ex) {
                            Toast.makeText(getActivity(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    if (txtAllarmaScadenzaAssicurazione.isChecked()) {
                        //TODO: gestire la notifica delle notification nel tempo
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getActivity())
                                        .setSmallIcon(R.drawable.abc_btn_radio_material)
                                        .setContentTitle("Daniel")
                                        .setContentText("I went to the zoo and saw a monkey!");
                        Notification n = builder.build();

                        ((MezzoActivity) getActivity()).submitNotifica(n);
                    }

                    ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getActivity());
                    saDB.insertScadenze(_id_auto, bollo, assicurazione, txtAllarmaScadenzaBollo.isChecked(), txtAllarmaScadenzaAssicurazione.isChecked());
                    return null;
                }
            }.execute();
            //TODO: setta un timer
            //esci dal fragment
            ((MezzoActivity) getActivity()).showChoose();
        }
    }
}