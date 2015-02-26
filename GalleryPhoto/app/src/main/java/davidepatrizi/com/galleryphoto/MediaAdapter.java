package davidepatrizi.com.galleryphoto;

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
public class MediaAdapter extends CursorAdapter {
    private Uri baseUri = null;

    public MediaAdapter(Context context, Cursor cursor, Uri baseUri) {
        super(context, cursor, 0);
        this.baseUri = baseUri;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.media_adapter, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            String displayName = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
            int _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            TextView tv = (TextView) view.findViewById(R.id.txtDisplayName);
            tv.setText(displayName);

            ImageView iv = (ImageView) view.findViewById(R.id.imageItem);
            iv.setImageURI(Uri.parse(baseUri + "/" + _id));

        } catch (Exception ex) {
            Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}