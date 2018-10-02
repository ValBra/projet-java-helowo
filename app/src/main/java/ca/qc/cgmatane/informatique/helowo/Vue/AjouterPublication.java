package ca.qc.cgmatane.informatique.helowo.Vue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ca.qc.cgmatane.informatique.helowo.Donnee.DAOPublication;
import ca.qc.cgmatane.informatique.helowo.R;
import ca.qc.cgmatane.informatique.helowo.modele.Publication;

public class AjouterPublication extends AppCompatActivity {
    protected DAOPublication accesseurPublication;
    private Button appareil;
    private Button galerie;
    private Button publication;
    private EditText description;
    private EditText lieu;
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
        lieu=(EditText)findViewById(R.id.champ_lieu);
        publication=(Button)findViewById(R.id.action_publier);
        imageView=(ImageView)findViewById(R.id.preview_image);

        appareil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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
        accesseurPublication.ajouterPublication(publication); //Probleme ici. Pourrait être du à l'url
        naviguerRetourListe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);
        }

        if(requestCode == CAMERA_PIC_REQUEST){
            imageUrl=data.getData();
            imageView.setImageURI(imageUrl);
        }
    }

    public void naviguerRetourListe() {
        this.finish();
    }
}
