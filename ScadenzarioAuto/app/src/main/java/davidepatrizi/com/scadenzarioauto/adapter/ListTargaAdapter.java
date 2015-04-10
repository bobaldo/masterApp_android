package davidepatrizi.com.scadenzarioauto.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import davidepatrizi.com.scadenzarioauto.R;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;

/**
 * Created by Bobaldo on 26/02/2015.
 */
public class ListTargaAdapter extends CursorAdapter {
    public ListTargaAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_list_targa, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            Resources resources = context.getResources();

            String targa = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TARGA));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry._ID));
            String tipo = cursor.getString((cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TIPO)));
            ((TextView) view.findViewById(R.id.txtTarga)).setText(targa.toUpperCase());
            ((TextView) view.findViewById(R.id.txtTipo)).setText(tipo);
            ((TextView) view.findViewById(R.id.txtIdTarga)).setText(String.valueOf(_id));
            ImageView iv = (ImageView) view.findViewById(R.id.txtImageTipoMezzo);

            if (tipo.equals("auto")) {
                iv.setImageResource(R.drawable.auto);
            } else if (tipo.equals("moto")) {
                iv.setImageResource(R.drawable.moto);
            } else if (tipo.equals("scooter")) {
                iv.setImageResource(R.drawable.scooter);
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}