package dmcjj.rmitpp.toiletlocator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by A on 10/08/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "Toilet.db";
    private static final int VERSION = 2;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DbSchema.CREATE_PHOTOS);
        db.execSQL(DbSchema.CREATE_TOILET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        switch(newVersion)
        {
            case 2:{
                //db.execSQL();
            }
        }
    }
}
