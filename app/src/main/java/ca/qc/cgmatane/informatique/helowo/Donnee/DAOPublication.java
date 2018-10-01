package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.helowo.modele.Publication;

public class DAOPublication {
    private static DAOPublication instance = null;
    private BaseDeDonnee baseDeDonnees;
    protected List<Publication> listePublications;

    public static DAOPublication getInstance(){
        if(null==instance){
            instance=new DAOPublication();
        }
        return instance;
    }

    public DAOPublication(){
        this.baseDeDonnees = BaseDeDonnee.getInstance();
        listePublications = new ArrayList<Publication>();
    }

    public List<Publication> listerPublications(){
        String LISTER_PUBLICATIONS = "SELECT * FROM publications";
        Log.d("Helowo", "listerPublication - avant curseur");
        Cursor curseur = baseDeDonnees.getReadableDatabase().rawQuery(LISTER_PUBLICATIONS,null); //Le probleme viendrait de là
        Log.d("Helowo", "listerPublication - apres curseur");
        this.listePublications.clear();
        Publication publication;
        int indexId_Publication=curseur.getColumnIndex("id_publication");
        int indexAuteur=curseur.getColumnIndex("auteur");
        int indexUrlPhoto=curseur.getColumnIndex("url_image");
        int indexDescription=curseur.getColumnIndex("description");
        int indexLieu=curseur.getColumnIndex("lieu");

        for (curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            int id_publication=curseur.getInt(indexId_Publication);
            String auteur=curseur.getString(indexAuteur);
            String urlPhoto=curseur.getString(indexUrlPhoto);
            String description=curseur.getString(indexDescription);
            String lieu=curseur.getString(indexLieu);
            publication=new Publication(id_publication,auteur,urlPhoto,description,lieu);
            this.listePublications.add(publication);
        }
        return listePublications;
    }

    public Publication trouverPublication(int id_publi){
        for(Publication publicationRecherche : this.listePublications){
            if(publicationRecherche.getId() == id_publi) return publicationRecherche;
        }
        return null;
    }

    public void modifierPublication(Publication publication){
        ContentValues publicationModifiee = new ContentValues();
        publicationModifiee.put("auteur",publication.getAuteur());
        publicationModifiee.put("url_image",publication.getUrl_photo());
        publicationModifiee.put("description",publication.getDescription());
        publicationModifiee.put("lieu",publication.getLieu());
        SQLiteDatabase bd = baseDeDonnees.getWritableDatabase();
        bd.update("publications",publicationModifiee,"id_publication="+publication.getId(),null);
    }

    public void ajouterPublication(Publication publication){
        ContentValues nouvellePublication = new ContentValues();
        nouvellePublication.put("auteur",publication.getAuteur());
        nouvellePublication.put("url_image",publication.getUrl_photo());
        nouvellePublication.put("description",publication.getDescription());
        nouvellePublication.put("lieu",publication.getLieu());
        SQLiteDatabase bd = baseDeDonnees.getWritableDatabase();
        bd.insert("publications",null,nouvellePublication);
    }

    public List<HashMap<String, String>> recupererListePourAdapteur(){
        List<HashMap<String, String>> listePourAdapteur = new ArrayList<HashMap<String, String>>();
        listerPublications(); //C'est cette methode qui a un probleme
        for(Publication publication:listePublications){
            listePourAdapteur.add(publication.obtenirPublicationPourAdapteur());
        }
        return listePourAdapteur;
    }
}
