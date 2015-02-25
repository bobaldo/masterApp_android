package davidepatrizi.com.usedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bobaldo on 20/02/2015.
 */
public class ContentDB {
    SQLiteDatabase mDb;
    DbHelper mDbHelper;
    Context mContext;
    private static final String DB_NAME = "data";//nome del db
    private static final int DB_VERSION = 1; //numero di versione del nostro db

    public ContentDB(Context ctx){
        mContext=ctx;
        mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);   //quando istanziamo questa classe, istanziamo anche l'helper (vedi sotto)
    }

    public void open(){  //il database su cui agiamo è leggibile/scrivibile
        mDb=mDbHelper.getWritableDatabase();
    }

    public void close(){ //chiudiamo il database su cui agiamo
        mDb.close();
    }


    //i seguenti 2 metodi servono per la lettura/scrittura del db. aggiungete e modificate a discrezione
    // consiglio:si potrebbe creare una classe Prodotto, i quali oggetti verrebbero passati come parametri dei seguenti metodi, rispettivamente ritornati. Lacio a voi il divertimento

    public void insertProduct(String name,int price){ //metodo per inserire i dati
        ContentValues cv=new ContentValues();
        cv.put(ProductsMetaData.PRODUCT_NAME_KEY, name);
        cv.put(ProductsMetaData.PRODUCT_PRICE_KEY, price);
        mDb.insert(ProductsMetaData.PRODUCTS_TABLE, null, cv);
    }

    public Cursor fetchProducts(){ //metodo per fare la query di tutti i dati
        return mDb.query(ProductsMetaData.PRODUCTS_TABLE, null,null,null,null,null,null);
    }

    public void deleteProduct(long id) {
        mDb.delete(ProductsMetaData.PRODUCTS_TABLE, "_id=?",new String[]{String.valueOf(id)});
    }

    static class ProductsMetaData {  // i metadati della tabella, accessibili ovunque
        static final String PRODUCTS_TABLE = "products";
        static final String ID = "_id";
        static final String PRODUCT_NAME_KEY = "name";
        static final String PRODUCT_PRICE_KEY = "price";
    }

    private static final String PRODUCTS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "  //codice sql di creazione della tabella
            + ProductsMetaData.PRODUCTS_TABLE + " ("
            + ProductsMetaData.ID+ " integer primary key autoincrement, "
            + ProductsMetaData.PRODUCT_NAME_KEY + " text not null, "
            + ProductsMetaData.PRODUCT_PRICE_KEY + " integer not null);";

    private class DbHelper extends SQLiteOpenHelper { //classe che ci aiuta nella creazione del db

        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella
            _db.execSQL(PRODUCTS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            //qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione

        }

    }
}