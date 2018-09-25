package ca.qc.cgmatane.informatique.helowo.Donnee;

public class DAOPublication {
    private static DAOPublication instance = null;
    private BaseDeDonnee baseDeDonnee;
    //declarer ici une instance de la BD

    public static DAOPublication getInstance(){
        if(null==instance){
            instance=new DAOPublication();
        }
        return instance;
    }

    public DAOPublication(){
        //instancier une instance de la BD
    }

    public void modifierPublication(){

    }

    public void ajouterPublication(){

    }
}
