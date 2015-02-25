package davidepatrizi.com.galleryphoto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Bobaldo on 25/02/2015.
 */
public class GalleryAdapter extends BaseAdapter {
    int galleryItem;
    private Context context;

    public GalleryAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 18;
    }

    @Override
    public Object getItem(int position) {
        return urlFor(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(loadImageFromURL(urlFor(position)));
        imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    private String urlFor(int position) {
        return "http://masterapp.di.unipi.it/img/slideshow/a" + (position + 1);
    }

    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "From " + url);
            return d;
        } catch (Exception e) {
            Toast.makeText(context, "Errore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}