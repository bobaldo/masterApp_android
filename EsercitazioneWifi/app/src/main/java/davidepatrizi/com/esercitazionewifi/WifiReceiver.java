package davidepatrizi.com.esercitazionewifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class WifiReceiver extends BroadcastReceiver {
    private WifiManager wifi;


    public WifiReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            WiFiDBHelper db = new WiFiDBHelper(context);
            wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
         /*   List<ScanResult> res = ;
            List<WifiResult> toAdd = new ArrayList<WifiResult>();
            for (int i = 0; i < res.size(); i++) {
                String signal = String.valueOf(WifiManager.calculateSignalLevel(res.get(i).level, 101));
                String timestamp = String.valueOf(res.get(i).timestamp);
                WifiResult item = new WifiResult(res.get(i).SSID, res.get(i).BSSID, timestamp, 0, signal);
                toAdd.add(item);
            }*/
            db.insertWiFiResults(wifi.getScanResults());
        }
    }
}