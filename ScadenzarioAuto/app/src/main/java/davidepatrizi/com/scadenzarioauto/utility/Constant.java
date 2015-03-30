package davidepatrizi.com.scadenzarioauto.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bobaldo on 20/03/2015.
 */
public class Constant {
    public static final DateFormat formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
    public static final DateFormat formatterYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    public static final int DIALOG_NEW_MEZZO = 1;
    public static final int DIALOG_DELETE_CONFIRM = 2;
    public static final int DIALOG_NEW_TAGLIANDO = 3;
    public static final int REQUEST_CAMERA = 3;
    public static final int ALARM_SCADENZA_ASSICURAZIONE = 1;
    public static final int ALARM_SCADENZA_BOLLO = 2;
    public static final int NOTIFICA_SCADENZA_ASSICURAZIONE = 99;
    public static final int NOTIFICA_SCADENZA_BOLLO = 100;
    public static final String TIPO_ALARM = "tipo_alarm";
    public static final String SCADENZA = "scadenza";
    public static final String TARGA = "targa";
    public static final String IS_NEW = "is_new";
}