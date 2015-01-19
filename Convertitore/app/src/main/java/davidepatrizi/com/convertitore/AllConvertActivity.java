package davidepatrizi.com.convertitore;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllConvertActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_convert);
        Convert convertor = new Convert();

        LinearLayout ll = (LinearLayout) findViewById(R.id.results);
        TextView title = (TextView) findViewById(R.id.title);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double value = extras.getDouble("VALORE");
            title.setText(extras.getString("TITOLO") + title.getText() + " "  + Double.toString(value) + Lunghezza.mt );

            String[] sa =  getResources().getStringArray(R.array.lista_lunghezza);

            for(int i=1; i< sa.length; i++){
                TextView tx = new TextView(this);
                tx.setText(convertor.convertS(Lunghezza.mt, Convert.getEnumLabel(sa[i]), value) + " " + Convert.getEnumLabel(sa[i]));
                tx.setTextSize(20);
                ll.addView(tx);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.convertAll) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
