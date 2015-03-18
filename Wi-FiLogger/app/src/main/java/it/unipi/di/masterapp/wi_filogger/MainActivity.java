package it.unipi.di.masterapp.wi_filogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // crea il fragment che si occupa di mostrare la lista solo la prima volta
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, new WiFiNetList());
            transaction.commit();
        }
    }

    public void showWiFiNet(String ssid) {
        // crea il fragment e passa gli argomenti
        Fragment wiFiNetShow = new WiFiNetShow();
        Bundle arguments = new Bundle();
        arguments.putString(WiFiNetEntry.COLUMN_NAME_SSID, ssid);
        wiFiNetShow.setArguments(arguments);

        // sostituisce il fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, wiFiNetShow);
        transaction.addToBackStack(null); // permettendo di tornare alla lista con back
        transaction.commit();
    }
}
