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

        // Table test pour une première publication
        String CREATE_TABLE = "create table publications(id_publication INTEGER PRIMARY KEY, nom_utilisateur TEXT, description TEXT, url_image TEXT, lieu TEXT, nb_like int)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        String DELETE = "delete from publications where 1 = 1";
        db.execSQL(DELETE);

        // Données test pour une première publication
        String INSERT_1 = "insert into publications(nom_utilisateur, description, url_image, lieu, nb_like) VALUES('nom', 'une photo', 'D:\\Test\\matane1.png', 'Matane', 11)";

        db.execSQL(INSERT_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Table test pour une première publication
        String CREATE_TABLE = "create table publications(id INTEGER PRIMARY KEY, nom_utilisateur TEXT, description TEXT, url_image TEXT, lieu TEXT, nb_aime TEXT)";
        db.execSQL(CREATE_TABLE);

    }


}
