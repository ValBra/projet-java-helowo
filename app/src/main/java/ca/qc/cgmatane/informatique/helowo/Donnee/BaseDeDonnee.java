package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnee extends SQLiteOpenHelper {

    private static BaseDeDonnee instance = null;

    public static BaseDeDonnee getInstance(Context contexte)
    {
        if(null == instance) instance = new BaseDeDonnee(contexte);
        return instance;
    }

    public static BaseDeDonnee getInstance()
    {
        return instance;
    }

    public BaseDeDonnee(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseDeDonnee(Context contexte) {

        super(contexte, "BDD_Helowo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table table_test(id INTEGER PRIMARY KEY, nom_utilisateur TEXT, url_image TEXT, lieu TEXT, nb_like int)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from table_test where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into table_test(nom_utilisateur, url_image, lieu, nb_like) VALUES('nom', 'D:\\Test\\matane1.png', 'Matane', 11)";

        db.execSQL(INSERT_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String CREATE_TABLE = "create table table_test(id INTEGER PRIMARY KEY, nom_utilisateur TEXT, url_image TEXT, lieu TEXT, nb_aime TEXT)";
        db.execSQL(CREATE_TABLE);

    }


}
