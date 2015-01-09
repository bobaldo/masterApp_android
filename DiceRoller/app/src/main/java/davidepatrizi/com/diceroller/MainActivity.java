package davidepatrizi.com.diceroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageView imageViewContext;
    private int numberFaces = 2;
    private int numberDice = 1;
    private Button btnFaces;
    private Button btnDice;
    private LinearLayout ll;
    private Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFaces = (Button) findViewById(R.id.buttonFaces);
        findViewById(R.id.buttonFaces).setOnClickListener(this);
        btnDice = (Button) findViewById(R.id.buttonDice);
        findViewById(R.id.buttonDice).setOnClickListener(this);
        ll = (LinearLayout) findViewById(R.id.list_item);
        findViewById(R.id.buttonRoll).setOnClickListener(this);
        r = new Random();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetBar:
                ll.removeAllViews();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRoll:
                rolls();
                break;

            case R.id.buttonFaces:
                PopupMenu pm = new PopupMenu(this, v);
                MenuInflater mi = pm.getMenuInflater();
                mi.inflate(R.menu.popup_faces, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        numberFaces = Integer.parseInt(item.getTitle().toString());
                        btnFaces.setText(item.getTitle());
                        return true;
                    }
                });
                pm.show();
                break;

            case R.id.buttonDice:
                PopupMenu pm1 = new PopupMenu(this, v);
                MenuInflater mi1 = pm1.getMenuInflater();
                mi1.inflate(R.menu.popup_dice, pm1.getMenu());
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        numberDice = Integer.parseInt(item.getTitle().toString());
                        btnDice.setText(item.getTitle());
                        return true;
                    }
                });
                pm1.show();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu m, View v, ContextMenu.ContextMenuInfo cmi) {
        super.onCreateContextMenu(m, v, cmi);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.context, m);
        imageViewContext = (ImageView) v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem m) {
        switch (m.getItemId()) {
            case R.id.delete:
                ll.removeView(imageViewContext);
                break;
            case R.id.rollAgain:
                ll.removeView(imageViewContext);
                roll();
                break;
        }
        return super.onContextItemSelected(m);
    }

    private void roll(){
        ImageView iv = new ImageView(this);
        switch (r.nextInt(numberFaces + 1)) {
            case 0:
            case 1:
                iv.setImageResource(R.drawable.face_1);
                break;
            case 2:
                iv.setImageResource(R.drawable.face_2);
                break;
            case 3:
                iv.setImageResource(R.drawable.face_3);
                break;
            case 4:
                iv.setImageResource(R.drawable.face_4);
                break;
            case 5:
                iv.setImageResource(R.drawable.face_5);
                break;
            case 6:
                iv.setImageResource(R.drawable.face_6);
                break;
        }
        registerForContextMenu(iv);
        ll.addView(iv);
    }

    private void rolls(){
        ll.removeAllViews();
        for (int i = 1; i <= numberDice; i++) {
            roll();
        }
    }
}