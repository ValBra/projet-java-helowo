package ca.qc.cgmatane.informatique.helowo.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.helowo.Donnee.BaseDeDonnee;
import ca.qc.cgmatane.informatique.helowo.Donnee.DAOPublication;
import ca.qc.cgmatane.informatique.helowo.R;

public class ListePublication extends AppCompatActivity {
    static final public int ACTIVITE_AJOUTER_PUBLI = 1;
    static final public int ACTIVITE_MODIFIER_PUBLI = 2;
    protected ListView vueListePubli;
    protected List<HashMap<String,String>> listePubli;
    protected Intent intentionNaviguerAjouterPubli;
    protected DAOPublication accesseurPubli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_publication);

        BaseDeDonnee.getInstance(getApplicationContext());
        accesseurPubli = DAOPublication.getInstance();
        vueListePubli = (ListView)findViewById(R.id.ListView_test);
        afficherPublication();
    }

    protected void afficherPublication(){
        listePubli = accesseurPubli.recupererListePourAdapteur();
        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listePubli,
                R.layout.vue_liste_publication,
                new String[]{"auteur","url_photo","lieu"},
                new int[] {R.id.pseudoAuteur,R.id.image,R.id.lieu});
        vueListePubli.setAdapter(adapteur);
    }

    protected void onActivityResult(int activite,int resultat,Intent donnees){
        switch (activite){
            case ACTIVITE_MODIFIER_PUBLI:
                afficherPublication();
                break;
            case ACTIVITE_AJOUTER_PUBLI:
                afficherPublication();
                break;
        }
    }
}
