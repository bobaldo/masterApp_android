package davidepatrizi.com.scadenzarioauto.utility;

import android.database.Cursor;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;

/**
 * Created by Bobaldo on 02/04/2015.
 */
public class ScadenzeItem {
    private int idAuto;
    private Timestamp assicurazione;
    private Timestamp bollo;
    private boolean allarmaAssicurazione;
    private boolean allarmaBollo;

    public ScadenzeItem(Cursor cursor, int _id_auto) throws ParseException {
        setIdAuto(_id_auto);
        setAssicurazione(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE)));
        setBollo(cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_BOLLO)));
        setAllarmaAssicurazione(cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_ASSICURAZIONE)));
        setAllarmaBollo(cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_BOLLO)));
    }

    public int getIdAuto() {
        return idAuto;
    }

    private void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public Timestamp getAssicurazione() {
        return assicurazione;
    }

    public void setAssicurazione(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        this.assicurazione = new Timestamp(c.getTimeInMillis());
    }

    private void setAssicurazione(String value) throws ParseException {
        Log.w("PD", "assicurazione " + value);
        this.assicurazione = new Timestamp(System.currentTimeMillis());
        if (value != null) {
            Date date = Constant.formatterYYYYMMDD.parse(value);
            this.assicurazione.setTime(date.getTime());
        }
    }

    public Timestamp getBollo() {
        return bollo;
    }

    private void setBollo(String value) throws ParseException {
        Log.w("PD", "bollo " + value);
        this.bollo = new Timestamp(System.currentTimeMillis());
        if (value != null) {
            Date date = Constant.formatterYYYYMMDD.parse(value);
            this.bollo.setTime(date.getTime());
        }
    }

    public void setBollo(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        this.bollo = new Timestamp(c.getTimeInMillis());
    }

    public boolean getAllarmaAssicurazione() {
        return allarmaAssicurazione;
    }

    public void setAllarmaAssicurazione(int value) {
        this.allarmaAssicurazione = value == 1 ? true : false;
    }

    public boolean getAllarmaBollo() {
        return allarmaBollo;
    }

    public void setAllarmaBollo(int value) {
        this.allarmaBollo = value == 1 ? true : false;
    }

    private Timestamp getTimestamp(String timeFromView) throws ParseException {
        Timestamp ret = new Timestamp(System.currentTimeMillis());
        if (timeFromView != null) {
            Date date = Constant.formatterDDMMYYYY.parse(timeFromView);
            ret.setTime(date.getTime());
        }
        return ret;
    }
}