package davidepatrizi.com.scadenzarioauto.dba;

import android.provider.BaseColumns;

/**
 * Created by Bobaldo on 18/03/2015.
 */
public class ScadenzarioDBEntry implements BaseColumns {
    public final static String TABLE_NAME_TAGLIANDO = "tagliando";
    public final static String TABLE_NAME_SCADENZA = "scadenza";
    public final static String TABLE_NAME_MEZZO = "mezzo";
    public final static String COLUMN_NAME_TIPO = "tipo";
    public final static String COLUMN_NAME_TARGA = "targa";
    public final static String COLUMN_NAME_VISIBILE = "visibile";
    public final static String COLUMN_NAME_ID_AUTO = "id_auto";
    public final static String COLUMN_NAME_ASSICURAZIONE = "assicurazione";
    public final static String COLUMN_NAME_BOLLO = "bollo";
    public final static String COLUMN_NAME_ALLARMATA_ASSICURAZIONE = "allarmata_assicurazione";
    public final static String COLUMN_NAME_ALLARMATA_BOLLO = "allarmata_bollo";
    public final static String COLUMN_NAME_DATA = "data";
    public final static String COLUMN_NAME_SPESA = "spesa";
    public final static String COLUMN_NAME_NOTE = "note";
}