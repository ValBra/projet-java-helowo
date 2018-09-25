package ca.qc.cgmatane.informatique.helowo.modele;

public class Publication {
    private int id;
    private String url_photo;
    private String description;
    private String lieu;
    private int classement;
    //instance de likes
    //instance de comments


    public Publication(int id, String url_photo, String description, String lieu, int classement) {
        this.id = id;
        this.url_photo = url_photo;
        this.description = description;
        this.lieu = lieu;
        this.classement = classement;
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

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }
}
