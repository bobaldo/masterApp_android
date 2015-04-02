package davidepatrizi.com.scadenzarioauto.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bobaldo on 02/04/2015.
 */
public class DateManage {
    public static StringBuilder setDate(String date, DateFormat dateFormat) {
        StringBuilder ret = new StringBuilder();
        try {
            Date _date = dateFormat.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(_date);
            ret.append(c.get(Calendar.DAY_OF_MONTH)).append("-")
                    .append(c.get(Calendar.MONTH) + 1).append("-")
                    .append(c.get(Calendar.YEAR)).append(" ");


        } catch (ParseException ex) {
        }
        return ret;
    }
}