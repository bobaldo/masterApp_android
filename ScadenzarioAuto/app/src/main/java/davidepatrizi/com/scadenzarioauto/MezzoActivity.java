package davidepatrizi.com.scadenzarioauto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioAdapterDB;
import davidepatrizi.com.scadenzarioauto.dba.ScadenzarioDBEntry;
import davidepatrizi.com.scadenzarioauto.fragment.ChooseFragment;
import davidepatrizi.com.scadenzarioauto.fragment.InfoFragment;
import davidepatrizi.com.scadenzarioauto.fragment.ScadenzeFragment;
import davidepatrizi.com.scadenzarioauto.fragment.TagliandiFragment;
import davidepatrizi.com.scadenzarioauto.utility.Constant;
import davidepatrizi.com.scadenzarioauto.utility.DataSetterFragment;

public class MezzoActivity extends ActionBarActivity {
    private int _id_auto;
    private String _targa;
    private boolean isShowChoose = false;

    @Override
    public void onResume() {
        super.onResume();
        //TODO: add gestione onResume
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mezzo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id_auto = extras.getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
            _targa = extras.getString(ScadenzarioDBEntry.COLUMN_NAME_TARGA);
            if (savedInstanceState == null) {
                showMenuFragment();
            }
        } else {
            //debug line
            Toast.makeText(this, "ID_AUTO NON PASSATO", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constant.DIALOG_DELETE_CONFIRM:
                return new AlertDialog.Builder(this)
                        .setTitle(R.string.eliminazione_targa)
                        .setMessage(R.string.message_eliminazione_targa)
                        .setPositiveButton(R.string.confermo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    ScadenzarioAdapterDB saDB = ScadenzarioAdapterDB.getInstance(getApplicationContext());
                                    saDB.deleteTarga(_id_auto);
                                    goMainActivity();
                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), "Errore: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.annulla, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }
        return null;
    }

    public void showInfo() {
        isShowChoose = false;
        this.setTitle(getString(R.string.title_show_info) + " " + _targa.toUpperCase());
        showFragment(new InfoFragment(), true);
    }

    public void showTagliando() {
        isShowChoose = false;
        this.setTitle(getString(R.string.title_show_tagliando) + " " + _targa.toUpperCase());
        showFragment(new TagliandiFragment(), true);
    }

    public void showScadenza() {
        isShowChoose = false;
        this.setTitle(getString(R.string.title_show_scadenza) + " " + _targa.toUpperCase());
        showFragment(new ScadenzeFragment(), true);
    }

    public void showMenuFragment() {
        isShowChoose = true;
        this.setTitle(getString(R.string.title_activity_mezzo) + " " + _targa.toUpperCase());
        showFragment(new ChooseFragment(), true);
    }

    private void showFragment(Fragment f, boolean withIdAuto) {
        if (withIdAuto) {
            Bundle arguments = new Bundle();
            arguments.putInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id_auto);
            arguments.putString(ScadenzarioDBEntry.COLUMN_NAME_TARGA, _targa);
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