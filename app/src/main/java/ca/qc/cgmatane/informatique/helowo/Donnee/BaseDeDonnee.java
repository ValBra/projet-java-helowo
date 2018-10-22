package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnee extends SQLiteOpenHelper {

    private static BaseDeDonnee instance = null;

    public static BaseDeDonnee getInstance(Context contexte) {
        if(null == instance) instance = new BaseDeDonnee(contexte);
        return instance;
    }

    public static BaseDeDonnee getInstance() {
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
        //String CREATE_TABLE = "create table publications(id_publication INTEGER PRIMARY KEY, nom_utilisateur TEXT, description TEXT, url_image TEXT, lieu TEXT, nb_like INTEGER)";
        String CREATE_TABLE_PUBLI = "create table publications(id_publication INTEGER PRIMARY KEY, auteur TEXT, url_image TEXT, description TEXT, lieu TEXT)";
        String CREATE_TABLE_MEMBRES = "create table membres(id_utilisateur INTEGER PRIMARY KEY, pseudo TEXT, mdp TEXT)";
        db.execSQL(CREATE_TABLE_PUBLI);
        db.execSQL(CREATE_TABLE_MEMBRES);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String DELETE = "delete from publications where 1 = 1";
        db.execSQL(DELETE);

        // Données test pour une première publication
        String INSERT_1 = "insert into publications(auteur,url_image,description,lieu) VALUES('ValBra','https://i.imgur.com/11izGY2.jpg','une photo','Matane, QC')";
        String INSERT_2 = "insert into membres(pseudo, mdp) VALUES('ValBra', 'Test')";
        //String INSERT_1 = "insert into publications(auteur,url_image,description,lieu) VALUES('ValBra','default','une photo','Matane, QC')";
        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Table test pour une première publication
        String CREATE_TABLE_PUBLICATION = "create table publications(id_publication INTEGER PRIMARY KEY, auteur TEXT, url_image TEXT, description TEXT, lieu TEXT)";
        String CREATE_TABLE_MEMBRES = "create table membres(id_utilisateur INTEGER PRIMARY KEY, pseudo TEXT, mdp TEXT)";
        db.execSQL(CREATE_TABLE_PUBLICATION);
        db.execSQL(CREATE_TABLE_MEMBRES);

    }
}
