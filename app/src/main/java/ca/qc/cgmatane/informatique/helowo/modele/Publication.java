package ca.qc.cgmatane.informatique.helowo.modele;

import java.util.HashMap;

public class Publication {
    private int id;
    private String url_photo;
    private String description;
    private String lieu;
    //instance de likes
    //instance de comments


    public Publication(int id, String url_photo, String description, String lieu) {
        this.id = id;
        this.url_photo = url_photo;
        this.description = description;
        this.lieu = lieu;
    }

    public Publication(String url_photo, String description, String lieu) {
        this.url_photo = url_photo;
        this.description = description;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public HashMap<String, String> obtenirPublicationPourAdapteur(){
        HashMap<String, String> publicationPourAdapteur=new HashMap<String,String>();
        publicationPourAdapteur.put("id_publication",""+this.getId());
        publicationPourAdapteur.put("url_photo",this.getUrl_photo());
        publicationPourAdapteur.put("description",this.getDescription());
        publicationPourAdapteur.put("lieu",this.getLieu());
        return publicationPourAdapteur;
    }
}
