package davidepatrizi.com.usedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLDataException;


public class DbActMainActivity extends ActionBarActivity implements View.OnClickListener {

    private ContentDB contDB = null;
    private Context ctx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_act_main);
        contDB = new ContentDB(this);
        ctx = this;
        ((Button) this.findViewById(R.id.buttonOpen)).setOnClickListener(this);
        ((Button) this.findViewById(R.id.buttonInserisci)).setOnClickListener(this);
        ((Button) this.findViewById(R.id.buttonSeleziona)).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_db_act_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOpen:
                //TODO: da migliorare perchè se non invocato non posso lavorare con il DB
                contDB.open();
                break;
            case R.id.buttonInserisci:
                contDB.insertProduct("pippo", 10);
                break;
            case R.id.buttonSeleziona:
                Cursor c = contDB.fetchProducts();
                ListView lvItems = (ListView) findViewById(R.id.listViewProdocts);
                ContentAdapter ca = new ContentAdapter(ctx, c);
                lvItems.setAdapter(ca);
                lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        contDB.deleteProduct(id);
                        Toast.makeText(ctx, "rimosso", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}