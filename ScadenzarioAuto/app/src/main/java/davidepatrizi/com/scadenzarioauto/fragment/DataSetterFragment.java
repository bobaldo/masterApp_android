package davidepatrizi.com.scadenzarioauto.fragment;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by Bobaldo on 27/03/2015.
 */
public class DataSetterFragment extends DatePickerDialog {
    public DataSetterFragment(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }
}