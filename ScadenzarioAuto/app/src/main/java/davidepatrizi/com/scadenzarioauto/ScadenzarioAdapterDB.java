package davidepatrizi.com.scadenzarioauto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;

/**
 * Created by Bobaldo on 18/03/2015.
 */
public class ScadenzarioAdapterDB {
    private Context ctx;
    private SQLiteDatabase db;

    public ScadenzarioAdapterDB(Context context) {
        this.ctx = context;
        ScadenzarioDBHelper wiFiDbHelper = new ScadenzarioDBHelper(ctx);
        db = wiFiDbHelper.getReadableDatabase();
    }

    public void insertTarga(String targa, String tipo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_TARGA, targa);
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_TIPO, tipo);
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_VISIBILE, true);
        db.insert(ScadenzarioDBEntry.TABLE_NAME_MEZZO, null, contentValues);
    }

    public void deleteTarga(int id) {
        db.beginTransaction();
        db.delete(ScadenzarioDBEntry.TABLE_NAME_MEZZO,
                ScadenzarioDBEntry._ID + "=?", new String[]{String.valueOf(id)});
        db.delete(ScadenzarioDBEntry.TABLE_NAME_SCADENZA,
                ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + "=?", new String[]{String.valueOf(id)});
        db.delete(ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO,
                ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + "=?", new String[]{String.valueOf(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertTagliando(int id_auto, Timestamp data, float spesa, String note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, id_auto);
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_DATA, String.valueOf(data));
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_SPESA, String.valueOf(spesa));
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_NOTE, note);
        db.insert(ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO, null, contentValues);
    }

    public void updateTagliando(int id, int id_auto, Timestamp data, float spesa, String note) {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, id_auto); //campo non aggiornabile
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_DATA, String.valueOf(data));
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_SPESA, String.valueOf(spesa));
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_NOTE, note);
        db.update(ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO,
                contentValues,
                ScadenzarioDBEntry._ID + "=?",
                new String[]{String.valueOf(id)});
    }

    public void insertScadenzaBollo(int id_auto, Timestamp bollo) {
        String auxBollo = String.valueOf(bollo);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_BOLLO, auxBollo);
        insertScadenza(contentValues, auxBollo, id_auto);
    }

    public void insertScadenzaAssicurazione(int id_auto, Timestamp assicurazione) {
        String auxAssicurazione = String.valueOf(assicurazione);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE, auxAssicurazione);
        insertScadenza(contentValues, auxAssicurazione, id_auto);
    }

    private void insertScadenza(ContentValues cv, String scadenza, int id_auto) {
        db.update(ScadenzarioDBEntry.TABLE_NAME_SCADENZA,
                cv,
                ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + "=?",
                new String[]{String.valueOf(id_auto)});
    }

    public Cursor getTagliando(int id) {
        return db.query(false,
                ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO,
                new String[]{ScadenzarioDBEntry._ID, ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, ScadenzarioDBEntry.COLUMN_NAME_DATA, ScadenzarioDBEntry.COLUMN_NAME_SPESA, ScadenzarioDBEntry.COLUMN_NAME_NOTE},
                ScadenzarioDBEntry._ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
    }

    public Cursor getTagliandi(int id_auto) {
        return db.query(false,
                ScadenzarioDBEntry.TABLE_NAME_TAGLIANDO,
                new String[]{ScadenzarioDBEntry._ID, ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO, ScadenzarioDBEntry.COLUMN_NAME_DATA, ScadenzarioDBEntry.COLUMN_NAME_SPESA, ScadenzarioDBEntry.COLUMN_NAME_NOTE},
                ScadenzarioDBEntry.COLUMN_NAME_ID_AUTO + "=?",
                new String[]{String.valueOf(id_auto)},
                null,
                null,
                ScadenzarioDBEntry.COLUMN_NAME_DATA,
                null
        );
    }

    public Cursor getScadenze(int id_auto) {
        return db.query(false,
                ScadenzarioDBEntry.TABLE_NAME_SCADENZA,
                new String[]{ScadenzarioDBEntry.COLUMN_NAME_ASSICURAZIONE, ScadenzarioDBEntry.COLUMN_NAME_BOLLO},
                ScadenzarioDBEntry._ID + "=?",
                new String[]{String.valueOf(id_auto)},
                null,
                null,
                null,
                null
        );
    }

    public Cursor getMezzo() {
        return db.query(false,
                ScadenzarioDBEntry.TABLE_NAME_MEZZO,
                new String[]{ScadenzarioDBEntry._ID, ScadenzarioDBEntry.COLUMN_NAME_TARGA, ScadenzarioDBEntry.COLUMN_NAME_TIPO},
                ScadenzarioDBEntry.COLUMN_NAME_VISIBILE + " =? ",
                new String[]{"1"}, //1 il valore true nel DB
                null,
                null,
                ScadenzarioDBEntry.COLUMN_NAME_TARGA,
                null
        );
    }
}