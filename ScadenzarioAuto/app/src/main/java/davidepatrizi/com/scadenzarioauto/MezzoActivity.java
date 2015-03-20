package davidepatrizi.com.scadenzarioauto;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

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