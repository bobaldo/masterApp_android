package davidepatrizi.com.scadenzarioauto.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bobaldo on 20/03/2015.
 */
public class Constant {
    public static final DateFormat formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
    public static final DateFormat formatterYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    public static final int DIALOG_NEW = 1;
    public static final int DIALOG_DELETE_CONFIRM = 2;
    public static final int REQUEST_CAMERA = 3;
}