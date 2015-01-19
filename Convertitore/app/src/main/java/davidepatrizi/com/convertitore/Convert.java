package davidepatrizi.com.convertitore;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by Bobaldo on 16/01/2015.
 */
public class Convert {
    private DecimalFormat df;

    public Convert(){
        df = new DecimalFormat("####0.00");
    }

    public static Lunghezza getEnumLunghezza(String label) {
        if (label.equals("metro"))
            return Lunghezza.mt;
        else if (label.equals("centimetro"))
            return Lunghezza.cm;
        else if (label.equals("pollice"))
            return Lunghezza.pll;
        else if (label.equals("piede"))
            return Lunghezza.pd;
        else if (label.equals("iard"))
            return Lunghezza.y;
        else
            return Lunghezza.mt;
    }

    public static Dimensione getEnumDimensione(String label){
        if(label.equals("lunghezza"))
            return Dimensione.lung;
        else if(label.equals("peso"))
            return  Dimensione.pes;
        else
            return Dimensione.lung;
    }

    public static Peso getEnumPeso(String label) {
        if (label.equals("kilogrammi")) {
            return Peso.kg;
        } else if (label.equals("grammi")) {
            return Peso.g;
        } else if (label.equals("libbre")) {
            return Peso.l;
        }else
            return Peso.kg;
    }

    public String convert(Peso from, Peso to, Double value) {
        //Log.w("convert", "from " + from.toString() + " to " + to.toString() + " value " + Double.toString(value));
        double convertor = 0.0;
        convertor = getConvertor(from, to);
        if (convertor == 0.0)
            convertor = 1 / getConvertor(to, from);
        return df.format(value * convertor);
    }

    public String convert(Lunghezza from, Lunghezza to, Double value) {
        //Log.w("convert", "from " + from.toString() + " to " + to.toString() + " value " + Double.toString(value));
        double convertor = 0.0;
        convertor = getConvertor(from, to);
        if (convertor == 0.0)
            convertor = 1 / getConvertor(to, from);
        return df.format(value * convertor);
    }

    public double getConvertor(Peso from, Peso to){
        double convertor =0.0;
        switch (from) {
            case kg:
                switch (to) {
                    case kg:
                        convertor = 1.0;
                        break;
                    case g:
                        convertor = 0.001;
                        break;
                    case l:
                        convertor = 2.20462262;
                        break;
                }
                break;
            case g:
                switch (to) {
                    case g:
                        convertor = 1;
                        break;
                    case l:
                        convertor = 0.00220462262;
                        break;
                }
                break;
            case l:
                switch (to) {
                    case l:
                        convertor = 1.0;
                        break;
                }
                break;
        }
        return convertor;
    }

    public double getConvertor(Lunghezza from, Lunghezza to){
        double convertor =0.0;
        switch (from) {
            case mt:
                switch (to) {
                    case mt:
                        convertor = 1.0;
                        break;
                    case cm:
                        convertor = 100.0;
                        break;
                    case pll:
                        convertor = 39.37;
                        break;
                    case pd:
                        convertor = 3.281;
                        break;
                    case y:
                        convertor = 1.094;
                        break;
                }
                break;
            case cm:
                switch (to) {
                    case cm:
                        convertor = 1.0;
                        break;
                    case pll:
                        convertor = 0.3937;
                        break;
                    case pd:
                        convertor = 0.03281;
                        break;
                    case y:
                        convertor = 0.01094;
                        break;
                }
                break;
            case pll:
                switch (to) {
                    case pll:
                        convertor = 1.0;
                        break;
                    case pd:
                        convertor = 0.08333;
                        break;
                    case y:
                        convertor = 0.02778;
                        break;
                }
                break;
            case pd:
                switch (to) {
                    case pd:
                        convertor = 1.0;
                        break;
                    case y:
                        convertor = 0.33330;
                        break;
                }
                break;
            case y:
                switch (to){
                    case y:
                        convertor = 1.0;
                        break;
                }
                break;
        }
        return convertor;
    }
}