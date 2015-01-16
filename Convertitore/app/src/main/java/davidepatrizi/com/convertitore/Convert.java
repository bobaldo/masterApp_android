package davidepatrizi.com.convertitore;

import android.util.Log;

/**
 * Created by Bobaldo on 16/01/2015.
 */
public class Convert {
    public double convert(Lunghezza from, Lunghezza to, Double value) {
        //TODO: applicare la conversione
        Log.w("convert", "from " + from.toString() + " to " + to.toString() + " value " + Double.toString(value));
        return value / 2;
    }
}