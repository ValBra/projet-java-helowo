package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ca.qc.cgmatane.informatique.helowo.modele.Publication;

public class DAOPublication {
    private static DAOPublication instance = null;
    private BaseDeDonnee baseDeDonnees;
    //declarer ici une instance de la BD

    public static DAOPublication getInstance(){
        if(null==instance){
            instance=new DAOPublication();
        }
        return instance;
    }

    public DAOPublication(){
        this.baseDeDonnees = BaseDeDonnee.getInstance();
    }

    public void modifierPublication(Publication publication){
        ContentValues publicationModifiee = new ContentValues();
        publicationModifiee.put("url_photo",publication.getUrl_photo());
        publicationModifiee.put("description",publication.getDescription());
        publicationModifiee.put("lieu",publication.getLieu());
        SQLiteDatabase baseDeDonnees = this.baseDeDonnees.getWritableDatabase();
        baseDeDonnees.update("publication",publicationModifiee,"id_publication="+publication.getId(),null);
    }

    public void ajouterPublication(Publication publication){
        ContentValues nouvellePublication = new ContentValues();
        nouvellePublication.put("url_photo",publication.getUrl_photo());
        nouvellePublication.put("description",publication.getDescription());
        nouvellePublication.put("lieu",publication.getLieu());
        SQLiteDatabase baseDeDonnees = this.baseDeDonnees.getWritableDatabase();
        baseDeDonnees.insert("publication",null,nouvellePublication);
    }
}
