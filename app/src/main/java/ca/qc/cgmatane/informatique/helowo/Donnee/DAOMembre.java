package ca.qc.cgmatane.informatique.helowo.Donnee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.helowo.modele.Membre;

public class DAOMembre {
    private static DAOMembre instance=null;
    private BaseDeDonnee baseDeDonnees;
    private List<Membre> listeMembres;

    public static DAOMembre getInstance(){
        if(null==instance){
            instance=new DAOMembre();
        }
        return instance;
    }

    public DAOMembre(){
        this.baseDeDonnees=BaseDeDonnee.getInstance();
    }

    public List<Membre> listerMembres(){
        String LISTER_MEMBRES="SELECT * FROM membre";
        Cursor curseur = baseDeDonnees.getReadableDatabase().rawQuery(LISTER_MEMBRES,null);
        this.listeMembres.clear();
        Membre membre;
        int indexId_Membre=curseur.getColumnIndex("id_membre");
        int indexPseudo=curseur.getColumnIndex("pseudo");
        int indexMdp=curseur.getColumnIndex("mdp");

        for (curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            int id_membre=curseur.getInt(indexId_Membre);
            String pseudo=curseur.getString(indexPseudo);
            String mdp=curseur.getString(indexMdp);
            membre=new Membre(id_membre,pseudo,mdp);
            this.listeMembres.add(membre);
        }

        return listeMembres;
    }

    public Membre trouverMembre(int id_membre){
        for(Membre membreRecherche : this.listeMembres){
            if(membreRecherche.getId() == id_membre) return membreRecherche;
        }
        return null;
    }

    public void modifierMembre(Membre membre){
        ContentValues membreModifie=new ContentValues();
        membreModifie.put("pseudo",membre.getPseudo());
        membreModifie.put("mdp",membre.getMotDePasse());
        SQLiteDatabase baseDeDonnees = this.baseDeDonnees.getWritableDatabase();
        baseDeDonnees.update("membre",membreModifie,"id_membre="+membre.getId(),null);
    }

    public void ajouterMembre(Membre membre){
        ContentValues nouveauMembre=new ContentValues();
        nouveauMembre.put("pseudo",membre.getPseudo());
        nouveauMembre.put("mdp",membre.getMotDePasse());
        SQLiteDatabase baseDeDonnees = this.baseDeDonnees.getWritableDatabase();
        baseDeDonnees.insert("membre",null,nouveauMembre);
    }

    public List<HashMap<String, String>> recupererListePourAdapteur(){
        List<HashMap<String, String>> listePourAdapteur;
        listePourAdapteur = new ArrayList<HashMap<String, String>>();
        listerMembres();
        for(Membre membre:listeMembres){
            listePourAdapteur.add(membre.obtenirMembrePourAdapteur());
        }
        return listePourAdapteur;
    }
}
