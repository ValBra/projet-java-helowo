package ca.qc.cgmatane.informatique.helowo.modele;

import java.util.HashMap;

public class Lieu {
    private int id;
    private String nom;
    private float longitude;
    private float latitude;

    public Lieu(int id, String nom, float longitude, float latitude) {
        this.id = id;
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Lieu(String nom, float longitude, float latitude) {
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public HashMap<String, String> obtenirLieuPourAdapteur(){
        HashMap<String, String> lieuPourAdapteur=new HashMap<String,String>();
        lieuPourAdapteur.put("id_lieu",""+this.getId());
        lieuPourAdapteur.put("nom",this.getNom());
        lieuPourAdapteur.put("longitude",""+this.getLongitude());
        lieuPourAdapteur.put("latitude",""+this.getLatitude());
        return lieuPourAdapteur;
    }
}
