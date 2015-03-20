package davidepatrizi.com.scadenzarioauto;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MezzoActivity extends ActionBarActivity {

    private int _id_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mezzo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id_auto = extras.getInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO);
            if (savedInstanceState == null) {
                showFragment(new ChooseFragment(), true);
            }
        } else {
            //debug line
            Toast.makeText(this, "ID_AUTO NON PASSATO", Toast.LENGTH_LONG).show();
        }
    }

    public void showInfo() {
        this.setTitle(R.string.ita_title_show_info);
        showFragment(new InfoFragment(),true);
    }

    public void showTagliandi() {
        this.setTitle(R.string.ita_title_show_tagliando);
        showFragment(new TagliandiFragment(),true);
    }

    public void showScadenze() {
        this.setTitle(R.string.ita_title_show_scadenza);
        showFragment(new ScadenzeFragment(),true);
    }

    private void showFragment(Fragment f, boolean withIdAuto){
        if(withIdAuto) {
            Bundle arguments = new Bundle();
            arguments.putInt(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, _id_auto);
            f.setArguments(arguments);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, f);
        transaction.addToBackStack(null); // permettendo di tornare alla lista con back
        transaction.commit();
    }
}