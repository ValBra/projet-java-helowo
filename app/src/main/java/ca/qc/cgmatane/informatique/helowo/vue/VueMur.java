package ca.qc.cgmatane.informatique.helowo.vue;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
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
    public static final String CHANNEL_ID = "1";
    SwipeRefreshLayout swipeLayout;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    protected TextView nbLike;

    View vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_mur);

        createNotificationChannel();

        swipeLayout = findViewById(R.id.swipeContainer);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Rafraichissement avec gesture Scroll", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 4000);
            }
        });

        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        Log.d("Helowo", "onCreate");

        BaseDeDonnee.getInstance(getApplicationContext());
        accesseurPubli=DAOPublication.getInstance();

        vueListePublication = (ListView)findViewById(R.id.ListView_test);


        /*File dossierImage= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Uri file= Uri.fromFile(new File(dossierImage,"matane1.png"));*/

        /*if(file.toString() != null && file.toString().length()>0)
        {
            Picasso.get().load("https://i.imgur.com/11izGY2.jpg").into(imageView);
        }
        else
        {
            Toast.makeText(VueMur.this, "Empty URI", Toast.LENGTH_SHORT).show();
        }*/


        nbLike = (TextView) findViewById(R.id.nbLikes);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

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
            case R.id.like1:
                Toast.makeText(getApplicationContext(), "Envoi d'une notification lorsqu'une publication est aimée", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.coeur)
                        .setContentTitle("Publication likée")
                        .setContentText("La publication a été likée")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(0, mBuilder.build());
                break;
            case R.id.lieu1:
                //Lancer la vue Google Maps
                Log.d("Helowo", "clic Maps");
                Toast.makeText(getApplicationContext(), "Changement de page avec gesture Tap/Affichage vue Google Maps", Toast.LENGTH_LONG).show();
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


        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vue = inflater.inflate(R.layout.vue_liste_publication, null);
        imageView = (ImageView) vue.findViewById(R.id.image);

        //setContentView(R.layout.vue_liste_publication);
        Picasso.get().load("https://i.imgur.com/11izGY2.jpg").into(imageView);

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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        Toast.makeText(getApplicationContext(), "Tentative de zoom avec gesture Pinch", Toast.LENGTH_LONG).show();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
