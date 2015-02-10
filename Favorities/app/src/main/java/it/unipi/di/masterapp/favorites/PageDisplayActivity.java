package it.unipi.di.masterapp.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PageDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_display);

        // carica la pagina il cui URL è stato passato tramite Intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String label = intent.getStringExtra("label");
        setTitle(label);
        PageDisplay pageDisplay = (PageDisplay) getSupportFragmentManager().findFragmentById(R.id.page_display);
        pageDisplay.loadUrl(url);
    }

}
