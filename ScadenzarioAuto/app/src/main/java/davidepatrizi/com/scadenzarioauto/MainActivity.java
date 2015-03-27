package davidepatrizi.com.scadenzarioauto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.utility.Constant;

//import com.googlecode.tesseract.android.TessBaseAPI;
//import com.venky.ocr.TextRecognizer;

public class MainActivity extends ActionBarActivity {
    private ListView listView;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.txtListaTarghe);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Cursor cursor = (Cursor) listView.getItemAtPosition(i);
                    int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ScadenzarioDBEntry._ID));

                    Intent intent = new Intent(context, MezzoActivity.class);
                    intent.putExtra(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnAggiungi = (Button) findViewById(R.id.btnAdd);
        btnAggiungi.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               showDialog(
                                                       Constant.DIALOG_NEW);
                                           }
                                       }
        );
        loadTarghe();
    }

    private void loadTarghe() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(context);
                return saDB.getMezzo();
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                try {
                    /*
                    ListAdapter listAdapter = new SimpleCursorAdapter(
                            context,
                            android.R.layout.simple_list_item_2,
                            cursor,
                            new String[]{ScadenzarioDBEntry.COLUMN_NAME_TARGA, ScadenzarioDBEntry.COLUMN_NAME_TIPO},
                            new int[]{android.R.id.text1, android.R.id.text2},
                            0
                    );*/
                    listView.setAdapter(new ListTargheAdapter(context, cursor));
                } catch (NullPointerException e) {
                    // se l'activity viene distrutta
                    ;
                }
            }
        }.execute();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((TextView) ((AlertDialog) dialog).findViewById(R.id.txtTarga)).setText("");
        ((Spinner) ((AlertDialog) dialog).findViewById(R.id.txtTipo)).setSelection(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constant.DIALOG_NEW:
                LayoutInflater factory = LayoutInflater.from(this);
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.ita_btnNewMezzo)
                        .setView(factory.inflate(R.layout.dialog_new, null))
                        .setPositiveButton(R.string.ita_aggiungi, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String targa = ((TextView)((AlertDialog)dialog).findViewById(R.id.txtTarga)).getText().toString();
                                String tipo = ((Spinner)((AlertDialog)dialog).findViewById(R.id.txtTipo)).getSelectedItem().toString();
                                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(((AlertDialog)dialog).getContext());
                                saDB.insertTarga(targa,tipo);
                                loadTarghe();
                            }
                        })
                        .setNegativeButton(R.string.ita_cancella, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new:
                //showDialog(Constant.DIALOG_NEW); //vecchia gestione

                Intent vi = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (vi.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(vi, Constant.REQUEST_CAMERA);
                }else{
                    //TODO:capire se mettere dialog
                    Toast.makeText(context, R.string.ita_message_camera_non_presente, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        if (req == Constant.REQUEST_CAMERA && res == RESULT_OK) {
          //  Uri video = data.getData();
            // myVideoView.setVideoURI(video);
            String targaTakenFromCamera = onPhotoTaken(data.getData());
        }
    }

    protected String onPhotoTaken(Uri data) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile( data.toString(), options);

        try {
            ExifInterface exif = new ExifInterface(data.toString());
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            //Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            //Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {
                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }

            // Convert to ARGB_8888, required by tess
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        } catch (IOException ex) {
            Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // _image.setImageBitmap( bitmap );
        //Log.v(TAG, "Before baseApi");
        //com.venky.ocr

        /*try {


            TextRecognizer t = new TextRecognizer();
            File f = new File(data.toString());
            StringBuffer targa = t.recognize(f);

            ;
        }catch (IOException ex){
            Toast.makeText(context, "Errore: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        */
        //1t.getLines(bitmap);

        /*
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(DATA_PATH, lang);
        baseApi.setImage(bitmap);

        String recognizedText = baseApi.getUTF8Text();

        baseApi.end();
        baseApi = null;

        */

        // You now have the text in recognizedText var, you can do anything with it.
        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
        // so that garbage doesn't make it to the display.

        //Log.v(TAG, "OCRED TEXT: " + recognizedText);

        //if ( lang.equalsIgnoreCase("eng") ) {
            //recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
        //}

        return "";// recognizedText.trim();

        /*
        if ( recognizedText.length() != 0 ) {
            _field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
            _field.setSelection(_field.getText().toString().length());
*/        //}

        // Cycle done.
    }
}