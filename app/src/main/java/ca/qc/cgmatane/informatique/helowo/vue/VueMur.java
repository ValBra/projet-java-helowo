package ca.qc.cgmatane.informatique.helowo.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.qc.cgmatane.informatique.helowo.Donnee.BaseDeDonnee;
import ca.qc.cgmatane.informatique.helowo.Donnee.DAOPublication;
import ca.qc.cgmatane.informatique.helowo.R;

public class VueMur extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected ListView vueListePublication;
    protected ImageView imageView;
    protected List<HashMap<String, String>> listePublicationPourAdapteur;
    protected DAOPublication accesseurPubli;
    public static final int ACTION_AJOUTER_PUBLICATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_mur);

        Log.d("Helowo", "onCreate");

        BaseDeDonnee.getInstance(getApplicationContext());
        accesseurPubli=DAOPublication.getInstance();

        vueListePublication = (ListView)findViewById(R.id.ListView_test);
        imageView = (ImageView) findViewById(R.id.image);

        //listePublicationPourAdapteur=preparerListePublis();
        afficherPublications();

        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listePublicationPourAdapteur,
                R.layout.vue_liste_publication,
                new String[]{"auteur","url_image","lieu"},
                new int[] {R.id.pseudoAuteur,R.id.image,R.id.lieu1});

        vueListePublication.setAdapter(adapteur);

        vueListePublication.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int positionItem, long id) {
                        ListView vueListePubli = (ListView)vue.getParent();

                        HashMap<String,String> publi =
                                (HashMap<String,String>)
                                        vueListePubli.getItemAtPosition((int)positionItem);
                    }
                }
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VueMur.this,AjouterPublication.class);
                startActivityForResult(intent, ACTION_AJOUTER_PUBLICATION);
                //startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void handleOnClick(View view)
    {
        switch(view.getId())
        {
            case R.id.lieu1:
                //Lancer la vue Google Maps
                Snackbar.make(view, "Vous avez cliqué sur le lieu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(VueMur.this,VueCarte.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void afficherPublications(){
        listePublicationPourAdapteur=accesseurPubli.recupererListePourAdapteur();
        SimpleAdapter adapteur = new SimpleAdapter(
                this,
                listePublicationPourAdapteur,
                R.layout.vue_liste_publication,
                new String[]{"auteur","url_image","lieu"},
                new int[] {R.id.pseudoAuteur,R.id.image,R.id.lieu1});

        vueListePublication.setAdapter(adapteur);
    }

    protected void onActivityResult(int activite,int resultat,Intent donnees){
        switch (activite){
            case ACTION_AJOUTER_PUBLICATION:
                afficherPublications();
                break;
        }
    }

    public  List<HashMap<String,String>> preparerListePublis(){
        List<HashMap<String,String>> listePublis = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> publi;

        publi= new HashMap<String, String>();
        publi.put("auteur","ValBra");
        publi.put("url_image","C:\\Users\\1801042\\Documents\\matane1.jpg");
        publi.put("description","Matane");
        publi.put("lieu","Matane");
        //publi.put("nb_likes","20");
        listePublis.add(publi);

        publi = new HashMap<String, String>();
        publi.put("auteur","ValBra2");
        publi.put("url_image","C:\\Users\\1801042\\Documents\\matane1.jpg");
        publi.put("description","Matane");
        publi.put("lieu","Matane");
        //publi.put("nb_likes","1560");
        listePublis.add(publi);

        return listePublis;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
