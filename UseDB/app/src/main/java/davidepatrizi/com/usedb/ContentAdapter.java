package davidepatrizi.com.usedb;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Bobaldo on 25/02/2015.
 */
public class ContentAdapter extends CursorAdapter {
    public ContentAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.items_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        TextView tvId = (TextView)view.findViewById(R.id.tvId);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        // Populate fields with extracted properties
        tvBody.setText(body);
        tvPriority.setText(String.valueOf(priority));
        tvId.setText(String.valueOf(id));
    }
}