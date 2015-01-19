package davidepatrizi.com.convertitore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements TextWatcher, AdapterView.OnItemSelectedListener {
    private DecimalFormat df;
    private Convert conv;
    private Spinner spDa;
    private Spinner spA;
    private EditText value;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDa = (Spinner) findViewById(R.id.da);
        spA = (Spinner) findViewById(R.id.a);
        result = (TextView) findViewById(R.id.result);
        value = (EditText) findViewById(R.id.toConvert);
        spDa.setOnItemSelectedListener(this);
        spA.setOnItemSelectedListener(this);
        value.addTextChangedListener(this);
        conv = new Convert();
        df = new DecimalFormat("####0.00");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        convert();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void convert() {
        String from = spDa.getSelectedItem().toString();
        String to = spA.getSelectedItem().toString();
        if (value.length() > 0) {
            Double aux = Double.parseDouble(value.getText().toString());
            Lunghezza f = Convert.getEnumLabel(from);
            Lunghezza t = Convert.getEnumLabel(to);
            aux = conv.convert(f, t, aux);
            result.setText(df.format(aux));
            result.setVisibility(View.VISIBLE);
        } else
            result.setVisibility(View.INVISIBLE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.convertAll) {
            Intent _int = new Intent(this, AllConvertActivity.class);
            _int.putExtra("TITOLO", "Lunghezza");
            _int.putExtra("VALORE", Double.parseDouble(value.getText().toString()));
            startActivity(_int);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        convert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}