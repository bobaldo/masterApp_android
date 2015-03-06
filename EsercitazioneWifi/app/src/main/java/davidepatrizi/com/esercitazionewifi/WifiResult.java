package davidepatrizi.com.esercitazionewifi;

/**
 * Created by Bobaldo on 06/03/2015.
 */
public class WifiResult {
    public String ssid;
    public String bssid;
    public String timeStamp;
    public int id;
    public String signal;

    public WifiResult(String ssid, String bssid, String timeStamp, int id, String signal) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.timeStamp = timeStamp;
        this.signal = signal;
        this.id = id;
    }
}