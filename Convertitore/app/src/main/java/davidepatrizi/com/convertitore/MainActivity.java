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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements TextWatcher, AdapterView.OnItemSelectedListener {
    private Convert conv;
    private Spinner dimension;
    private Spinner spDa;
    private Spinner spA;
    private EditText value;
    private TextView result;
    private Dimensione dim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dim = Dimensione.lung;
        dimension=(Spinner)findViewById(R.id.dimension);
        spDa = (Spinner)findViewById(R.id.da);
        spA = (Spinner)findViewById(R.id.a);
        result = (TextView)findViewById(R.id.result);
        value = (EditText)findViewById(R.id.toConvert);
        dimension.setOnItemSelectedListener(this);
        spDa.setOnItemSelectedListener(this);
        spA.setOnItemSelectedListener(this);
        value.addTextChangedListener(this);
        conv = new Convert();
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
            switch (dim) {
                case lung:
                    Lunghezza f = Convert.getEnumLunghezza(from);
                    Lunghezza t = Convert.getEnumLunghezza(to);
                    result.setText(conv.convert(f, t, aux));
                    break;
                case pes:
                    Peso fp = Convert.getEnumPeso(from);
                    Peso tp = Convert.getEnumPeso(to);
                    result.setText(conv.convert(fp,tp,aux));
                    break;
            }
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
            _int.putExtra("VALORE", Double.parseDouble(value.getText().toString()));
            _int.putExtra("DIMENSIONE", dim.toString());
            startActivity(_int);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
        if (adapterView.getItemAtPosition(i).toString().equals("peso") ||
                adapterView.getItemAtPosition(i).toString().equals("lunghezza")
                ) {
            String _dim = ((TextView) view).getText().toString();
            dim = Convert.getEnumDimensione(_dim);
            String[] sa = new String[1];
            switch (dim) {
                case lung:
                    sa = getResources().getStringArray(R.array.lista_lunghezza);
                    break;
                case pes:
                    sa = getResources().getStringArray(R.array.lista_peso);
                    break;
            }
            ArrayAdapter<String> data = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sa);
            spDa.setAdapter(data);
            spA.setAdapter(data);
        }
        convert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}