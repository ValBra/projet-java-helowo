package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.helowo.modele.Lieu;

public class DAOLieu {
    private static DAOLieu instance = null;
    private BaseDeDonnee baseDeDonnees;
    //declarer ici une instance de la BD
    private List<Lieu> listeLieux;

    public static DAOLieu getInstance(){
        if(null==instance){
            instance=new DAOLieu();
        }
        return instance;
    }

    public DAOLieu(){
        this.baseDeDonnees = BaseDeDonnee.getInstance();
        listeLieux = new ArrayList<Lieu>();
    }

    public List<Lieu> listerLieux(){
        String LISTER_LIEUX="SELECT * FROM lieu";
        Cursor curseur=baseDeDonnees.getReadableDatabase().rawQuery(LISTER_LIEUX,null);
        this.listeLieux.clear();
        Lieu lieu;
        int indexId_lieu=curseur.getColumnIndex("id_lieu");
        int indexNom=curseur.getColumnIndex("nom");
        int indexLongitude=curseur.getColumnIndex("longitude");
        int indexLatitude=curseur.getColumnIndex("latitude");
        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            int idLieu=curseur.getInt(indexId_lieu);
            String nom=curseur.getString(indexNom);
            float longitude=curseur.getFloat(indexLongitude);
            float latitude=curseur.getFloat(indexLatitude);
            lieu=new Lieu(idLieu,nom,longitude,latitude);
            this.listeLieux.add(lieu);
        }
        return listeLieux;
    }

    public List<HashMap<String, String>> recupererListePourAdapteur(){
        List<HashMap<String, String>> listePourAdapteur;
        listePourAdapteur = new ArrayList<HashMap<String, String>>();
        listerLieux();
        for(Lieu lieu:listeLieux){
            listePourAdapteur.add(lieu.obtenirLieuPourAdapteur());
        }
        return listePourAdapteur;
    }
}
