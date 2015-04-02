package davidepatrizi.com.scadenzarioauto.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.Constant;
import davidepatrizi.com.scadenzarioauto.utility.DateManage;

/**
 * Created by Bobaldo on 31/03/2015.
 */
public class ListTagliandoAdapter extends CursorAdapter {
    public ListTagliandoAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_list_tagliando, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            String note = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_NOTE));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry._ID));
            String data = cursor.getString((cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_DATA)));
            ((TextView) view.findViewById(R.id.txtNote)).setText(note);
            ((TextView) view.findViewById(R.id.txtDataTagliando)).setText(DateManage.setDate(data, Constant.formatterYYYYMMDD));
            ((TextView) view.findViewById(R.id.txtIdTagliando)).setText(_id);
        } catch (Exception ex) {
            //Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}