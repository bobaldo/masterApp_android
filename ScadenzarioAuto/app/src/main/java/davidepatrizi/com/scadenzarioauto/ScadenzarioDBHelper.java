package davidepatrizi.com.scadenzarioauto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bobaldo on 18/03/2015.
 */
public class ScadenzarioDBHelper extends SQLiteOpenHelper {
    // versione del database
    private final static int DATABASE_VERSION = 1;

    // nome del database
    private final static String DATABASE_NAME = "Scadenzario.db";

    private final static String CREATE_TABLE_MEZZO = "CREATE TABLE " + ScadenzarioDBEntry.TABLE_NAME_MEZZO + " (" +
            ScadenzarioDBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ScadenzarioDBEntry.COLUMN_NAME_TIPO + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_TARGA + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_VISIBILE + " BOOL " +
            ")";

    private final static String CREATE_TABLE_SCADENZA = "CREATE TABLE " + ScadenzarioDBEntry.TABLE_NAME_SCADENZA + " (" +
            ScadenzarioDBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + " INTEGER, " +
            ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_BOLLO + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_ASSICURAZIONE + " BOOL, " +
            ScadenzarioDBEntry.COLUMN_NAME_ALLARMATA_BOLLO + " BOOL " +
            ")";

    private final static String CREATE_TABLE_TAGLIANDO = "CREATE TABLE " + ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO + " (" +
            ScadenzarioDBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + " INTEGER, " +
            ScadenzarioDBEntry.COLUMN_NAME_DATA + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_SPESA + " TEXT, " +
            ScadenzarioDBEntry.COLUMN_NAME_NOTE + " TEXT " +
            ")";

    public ScadenzarioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEZZO);
        db.execSQL(CREATE_TABLE_SCADENZA);
        db.execSQL(CREATE_TABLE_TAGLIANDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
