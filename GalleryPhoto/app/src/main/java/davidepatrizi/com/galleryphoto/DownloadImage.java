package davidepatrizi.com.galleryphoto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by Bobaldo on 25/02/2015.
 */
public class DownloadImage extends AsyncTask<String, Void, Drawable> {
    private Context context;
    private String url;
    private final WeakReference<ImageView> imageViewReference;

    public DownloadImage(ImageView imageView, Context context) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.context = context;
    }

    @Override
    protected Drawable doInBackground(String... s) {
        try {
            InputStream is = (InputStream) new URL(s[0]).getContent();
            return Drawable.createFromStream(is, "From " + s[0]);
        } catch (Exception e) {
            Toast.makeText(context, "Errore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
// Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Drawable draw) {
        if (isCancelled()) {
            draw = null;
        }
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageDrawable(draw);
            }
        }
    }
}