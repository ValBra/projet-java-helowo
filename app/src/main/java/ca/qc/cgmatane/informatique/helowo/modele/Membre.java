package ca.qc.cgmatane.informatique.helowo.modele;

import java.util.HashMap;

public class Membre {
    private int id;
    private String pseudo;
    private String motDePasse;

    public Membre(int id, String pseudo, String motDePasse) {
        this.id = id;
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public Membre(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public HashMap<String, String> obtenirMembrePourAdapteur(){
        HashMap<String, String> membrePourAdapteur=new HashMap<String,String>();
        membrePourAdapteur.put("id_membre",""+this.getId());
        membrePourAdapteur.put("pseudo",this.getPseudo());
        membrePourAdapteur.put("mdp",""+this.getMotDePasse());
        return membrePourAdapteur;
    }
}
