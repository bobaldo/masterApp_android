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
    private WiFiDBHelper db;

    public WifiReceiver() {
    }

    public WifiReceiver(Context ctx) {
        this.db = new WiFiDBHelper(ctx);
        wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            List<ScanResult> res = wifi.getScanResults();
            List<WifiResult> toAdd = new ArrayList<WifiResult>();
            for (int i = 0; i < res.size(); i++) {
                String signal = String.valueOf(WifiManager.calculateSignalLevel(res.get(i).level, 101));
                String timestamp = String.valueOf(res.get(i).timestamp);
                WifiResult item = new WifiResult(res.get(i).SSID, res.get(i).BSSID, timestamp, 0, signal);
                toAdd.add(item);
            }
            db.insertWiFiResults(toAdd);
        }
    }
}