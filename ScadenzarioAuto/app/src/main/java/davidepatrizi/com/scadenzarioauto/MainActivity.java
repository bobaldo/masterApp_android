package davidepatrizi.com.scadenzarioauto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private AsyncTask<Void, Void, Cursor> asyncTaskTarghe;
    private static final int DIALOG_NEW = 1;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        listView = (ListView) findViewById(R.id.txtListaTarghe);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(context);
                String targa = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                int _idTarga = saDB.getId(targa);
            }
        });
        */

        Button btnAggiungi = (Button) findViewById(R.id.btnAdd);
        btnAggiungi.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               showDialog(DIALOG_NEW);
                                           }
                                       }
        );
        loadTarghe();
    }

    private void loadTarghe() {
        final Context context = this;
        asyncTaskTarghe = new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(context);
                return saDB.getMezzo();
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                try {
                    ListAdapter listAdapter = new SimpleCursorAdapter(
                            context,
                            android.R.layout.simple_list_item_2,
                            cursor,
                            new String[]{ScadenzarioDBEntry.COLUMN_NAME_TARGA, ScadenzarioDBEntry.COLUMN_NAME_TIPO},
                            new int[]{android.R.id.text1, android.R.id.text2},
                            0
                    );
                    listView.setAdapter(listAdapter);
                } catch (NullPointerException e) {
                    // se l'activity viene distrutta
                    ;
                }
            }
        }.execute();
    }

    /*
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(this);
        String targa = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
        int _idTarga = saDB.getId(targa);
    }
    */

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((TextView) ((AlertDialog) dialog).findViewById(R.id.txtTarga)).setText("");
        ((Spinner) ((AlertDialog) dialog).findViewById(R.id.txtTipo)).setSelection(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_NEW:
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
                showDialog(DIALOG_NEW);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}