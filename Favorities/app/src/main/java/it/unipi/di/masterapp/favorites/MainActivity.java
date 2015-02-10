package it.unipi.di.masterapp.favorites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements PageList.OnPageSelectedListener {

    private boolean singleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recupera la modalità (uno o due fragment) a seconda dei frame (container) presenti
        singleFragment = (findViewById(R.id.page_display) == null);
    }

    @Override
    public void onPageSelected(ListView l, int position) {
        PageList pageList = (PageList) getSupportFragmentManager().findFragmentById(R.id.page_list);

        // versione con un solo fragment
        if (singleFragment) {
            // usa elementi NON selezionabili
            l.setChoiceMode(ListView.CHOICE_MODE_NONE);
            l.clearChoices();

            // fa partire la Activity per mostrare la pagina
            pageList.startActivityFor(position);
        }
        // versione con due fragment
        else {
            PageDisplay pageDisplay = (PageDisplay) getSupportFragmentManager().findFragmentById(R.id.page_display);

            // usa elementi selezionabili
            l.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            l.setItemChecked(position, true);

            // carica la pagina specificata
            pageDisplay.loadUrl(pageList.getUrl(position));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // action bar: nuova pagina
        if (id == R.id.action_new) {
            // chiede etichetta e URL della nuova pagina tramite dialog
            final View content = getLayoutInflater().inflate(R.layout.prompt_dialog, null);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_new)
                    .setView(content)
                    .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // prende etichetta e URL dalla view passata
                            String label = ((EditText) content.findViewById(R.id.label)).getText().toString();
                            String url = ((EditText) content.findViewById(R.id.url)).getText().toString();
                            PageList pageList = (PageList) getSupportFragmentManager().findFragmentById(R.id.page_list);
                            pageList.addPage(label, url);
                        }

                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
