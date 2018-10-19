package ca.qc.cgmatane.informatique.helowo.vue;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.qc.cgmatane.informatique.helowo.Donnee.DAOPublication;
import ca.qc.cgmatane.informatique.helowo.R;
import ca.qc.cgmatane.informatique.helowo.modele.Publication;

public class AjouterPublication extends AppCompatActivity {
    protected DAOPublication accesseurPublication;
    private Button appareil;
    private Button galerie;
    private Button publication;
    private EditText description;
    private TextView lieu;
    private ImageView imageView;
    private Uri imageUrl;
    public static final int PICK_IMAGE=0;
    public static final int CAMERA_PIC_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vue_ajouter_publication);
        this.accesseurPublication=DAOPublication.getInstance();

        appareil=(Button)findViewById(R.id.action_photo);
        galerie=(Button)findViewById(R.id.action_galerie);
        description=(EditText)findViewById(R.id.champ_description);
        lieu=(TextView)findViewById(R.id.champ_lieu);
        publication=(Button)findViewById(R.id.action_publier);
        imageView=(ImageView)findViewById(R.id.preview_image);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            appareil.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        try{
            lieu.setText(obtenirNomVille());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(AjouterPublication.this,"Localisation réussie!",Toast.LENGTH_SHORT).show();
            lieu.setText("Matane, QC");
        }

        appareil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
            }
        });

        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        publication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterPublication();
            }
        });
    }

    private void ajouterPublication(){
        String url=imageUrl.toString();
        Publication publication = new Publication("Auteur",url,description.getText().toString(),lieu.getText().toString());
        accesseurPublication.ajouterPublication(publication);
        naviguerRetourListe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageUrl = Uri.fromFile(getOutputMediaFile());
                //imageUrl=data.getData();
                imageView.setImageURI(imageUrl);
            } else{
                imageUrl=data.getData();
            }
        }
        if (requestCode == PICK_IMAGE) {
            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);
        }

        /*if(requestCode == CAMERA_PIC_REQUEST){
            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);
        }*/
    }

    public void naviguerRetourListe() {
        this.finish();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                appareil.setEnabled(true);
            }
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public String hereLocation(double latitude, double longitude){
        String nomVille = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> adresses;
        try{
            adresses = geocoder.getFromLocation(latitude,longitude,10);
            if (adresses.size()>0){
                for (Address adr : adresses){
                    if (adr.getLocality() != null && adr.getLocality().length()>0){
                        nomVille=adr.getLocality();
                        break;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return nomVille;
    }

    public String obtenirNomVille(){
        String ville = "";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            ville = hereLocation(location.getLatitude(),location.getLongitude());
        }
        return ville;
    }
}
