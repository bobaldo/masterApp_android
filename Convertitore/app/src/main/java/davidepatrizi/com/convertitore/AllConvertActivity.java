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
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_convert);
        Dimensione d = Dimensione.lung;
        ll = (LinearLayout) findViewById(R.id.results);
        LinearLayout ll = (LinearLayout) findViewById(R.id.results);
        TextView title = (TextView) findViewById(R.id.title);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double value = extras.getDouble("VALORE");
            String dim = extras.getString("DIMENSIONE");
            String[] sa = null;
            if (dim.equals("lung")) {
                title.setText(getResources().getString(R.string.lunghezza) + title.getText() + " " + Double.toString(value) + Lunghezza.mt);
                d = Dimensione.lung;
            }else if(dim.equals("pes")) {
                title.setText(getResources().getString(R.string.peso) + title.getText() + " " + Double.toString(value) + Peso.kg);
                d = Dimensione.pes;
            }


            switch (d) {
                case lung:
                    sa = getResources().getStringArray(R.array.lista_lunghezza);
                    break;
                case pes:
                    sa = getResources().getStringArray(R.array.lista_peso);
                    break;
            }
            insertView(sa, d, value);
        }
    }

    private void insertView(String[] array, Dimensione dim, double value) {
        Convert convertor = new Convert();
        int heghtTextViewResult = 20;
        for (int i = 1; i < array.length; i++) {

            TextView tx = new TextView(this);
            switch (dim) {
                case lung:
                    tx.setText(convertor.convert(Lunghezza.mt, Convert.getEnumLunghezza(array[i]), value) + "  " + Convert.getEnumLunghezza(array[i]));
                    tx.setTextSize(heghtTextViewResult);
                    ll.addView(tx);
                    break;
                case pes:
                    tx = new TextView(this);
                    tx.setText(convertor.convert(Peso.kg, Convert.getEnumPeso(array[i]), value) + "  " + Convert.getEnumPeso(array[i]));
                    tx.setTextSize(heghtTextViewResult);
                    ll.addView(tx);
                    break;
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
