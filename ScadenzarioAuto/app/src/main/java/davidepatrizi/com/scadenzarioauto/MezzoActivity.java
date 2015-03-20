package davidepatrizi.com.scadenzarioauto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.Toast;

import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.fragment.ChooseFragment;
import davidepatrizi.com.scadenzarioauto.fragment.InfoFragment;
import davidepatrizi.com.scadenzarioauto.fragment.ScadenzeFragment;
import davidepatrizi.com.scadenzarioauto.fragment.TagliandiFragment;
import davidepatrizi.com.scadenzarioauto.utility.Constant;

public class MezzoActivity extends ActionBarActivity {

    private int _id_auto;
    private boolean isShowChoose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mezzo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id_auto = extras.getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
            if (savedInstanceState == null) {
                showChoose();
            }
        } else {
            //debug line
            Toast.makeText(this, "ID_AUTO NON PASSATO", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constant.DIALOG_NEW:
                LayoutInflater factory = LayoutInflater.from(this);
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.ita_eliminazione_targa)
                        .setMessage(R.string.ita_message_elinazione_targa)
                        .setPositiveButton(R.string.ita_confermo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    ScadenzarioAdapterDB saDB = new ScadenzarioAdapterDB(getApplicationContext());
                                    saDB.deleteTarga(_id_auto);
                                    goMainActivity();
                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.ita_annulla, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }
        return null;
    }

    public void showInfo() {
        isShowChoose = false;
        this.setTitle(R.string.ita_title_show_info);
        showFragment(new InfoFragment(), true);
    }

    public void showTagliandi() {
        isShowChoose = false;
        this.setTitle(R.string.ita_title_show_tagliando);
        showFragment(new TagliandiFragment(), true);
    }

    public void showScadenze() {
        isShowChoose = false;
        this.setTitle(R.string.ita_title_show_scadenza);
        showFragment(new ScadenzeFragment(), true);
    }

    public void showChoose() {
        isShowChoose = true;
        this.setTitle(R.string.title_activity_mezzo);
        showFragment(new ChooseFragment(), true);
    }

    private void showFragment(Fragment f, boolean withIdAuto) {
        if (withIdAuto) {
            Bundle arguments = new Bundle();
            arguments.putInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id_auto);
            f.setArguments(arguments);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, f);
        transaction.addToBackStack(null); // permette di tornare alla lista con back
        transaction.commit();
    }

    public void goMainActivity() {
        Intent _int = new Intent(this, MainActivity.class);
        startActivity(_int);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowChoose) {
            //Toast.makeText(this, "BACK", Toast.LENGTH_LONG).show(); //debug line
            Intent _int = new Intent(this, MainActivity.class);
            startActivity(_int);
            return false;
        } else {
            isShowChoose = true;
            return super.onKeyDown(keyCode, event);
        }
    }
}