package davidepatrizi.com.galleryphoto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Bobaldo on 25/02/2015.
 */
public class DownloadImage extends AsyncTask<Void, Integer, Void> {
    private String url = null;
    private Drawable d = null;
    private InputStream is = null;
    private static Context ctx = null;

    public DownloadImage(String url, Context ctx) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        d = loadImageFromURL(url);
        return null;
    }

    public static Drawable loadImageFromURL(String url) {
    /*--- this method downloads an Image from the given URL,
     *  then decodes and returns a Bitmap object
     ---*/
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "From " + url);
        } catch (Exception e) {
            Toast.makeText(ctx, "Errore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}