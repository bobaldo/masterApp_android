package davidepatrizi.com.galleryphoto;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private Gallery gallery;
    private Context ctx = null;
    private ListView galleryMedia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new GalleryAdapter(ctx));

        /*
        galleryMedia = (ListView) findViewById(R.id.galleryMedia);
        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        Cursor c = cr.query(uri,
                null,
                null,
                null,
                null
        );
        galleryMedia.setAdapter(new MediaAdapter(this, c, uri));*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), "Immagine " + pos, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}