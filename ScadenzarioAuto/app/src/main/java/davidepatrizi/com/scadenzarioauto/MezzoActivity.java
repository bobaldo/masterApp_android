package davidepatrizi.com.scadenzarioauto;

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
        }else{
            //debug line
            Toast.makeText(this, "ID AUTO NON PASSATO", Toast.LENGTH_LONG).show();
        }
    }
}