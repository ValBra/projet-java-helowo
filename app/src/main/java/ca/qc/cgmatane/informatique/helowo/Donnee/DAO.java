package ca.qc.cgmatane.informatique.helowo.Donnee;

public class DAO {
    private static DAO instance = null;
    private BaseDeDonnee baseDeDonnee;
    //declarer ici une instance de la BD

    public static DAO getInstance(){
        if(null==instance){
            instance=new DAO();
        }
        return instance;
    }

    public DAO(){
        //instancier une instance de la BD
    }
}
