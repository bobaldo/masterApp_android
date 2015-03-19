package davidepatrizi.com.scadenzarioauto;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bobaldo on 26/02/2015.
 */
public class AdapterListTarghe extends CursorAdapter {
    public AdapterListTarghe(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_list_targhe, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            String targa = cursor.getString(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TARGA));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry._ID));
            String tipo = cursor.getString((cursor.getColumnIndexOrThrow(ScadenzarioDBEntry.COLUMN_NAME_TIPO)));
            ((TextView) view.findViewById(R.id.txtTarga)).setText(targa);
            ((TextView)view.findViewById(R.id.txtTipo)).setText(tipo);
            ((TextView)view.findViewById(R.id.txtIdTarga)).setText(_id);

            //TODO: switch for any case in array string ita_tipi
            /*static image for try
            ImageView iv = (ImageView) view.findViewById(R.id.txtImageTipoMezzo);
            iv.setImageResource(R.drawable.abc_btn_radio_material);*/

        } catch (Exception ex) {
            //Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}