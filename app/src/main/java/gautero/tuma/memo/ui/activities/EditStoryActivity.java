package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class EditStoryActivity extends AppCompatActivity {

    Button submit;
    int numerodeFoto;
    FloatingActionButton addFoto00, addFoto01, addFoto02, addFoto10, addFoto11, addFoto12, faGlobalImSoSorry;
    CardView card00, card01, card02, card10, card11, card12, cvGlobalImSoSorry;
    static final int GALERIA_REQUEST_PERMISSION = 1;
    static final int GALERIA_REQUEST = 2;
    private long postID;
    EditText titulo, historia;
    String usuario, i1, i2, i3, i4, i5, i0;
    List<String> Urls;
    List<CardView> Cards;
    List<FloatingActionButton> Buttons;

    FirebaseUser mUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootNode = db.getReference().child("Posts");
    private StorageReference mStorageRef, mStorageRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        titulo = findViewById(R.id.editTextTitulo);
        historia = findViewById(R.id.editTextTextMultiLineDEscHistoria);
        Gson gson = new Gson();
        Post post = gson.fromJson(getIntent().getStringExtra("post"), Post.class);
        postID = post.getIdPost();

        mStorageRef2 = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        usuario = mUser.getEmail();

        //Inicializar URLS, y datos en la vista

        titulo.setText(post.getTitulo());
        historia.setText(post.getHistoria());

        i0 = post.getImg0();
        i2 = post.getImg1();
        i3 = post.getImg2();
        i4 = post.getImg3();
        i5 = post.getImg4();
        i1 = post.getImg5();

        Urls = new ArrayList<>();

        Urls.add(i0);
        Urls.add(i1);
        Urls.add(i2);
        Urls.add(i3);
        Urls.add(i4);
        Urls.add(i5);



        //Primera Card---------------------



            addFoto00 = findViewById(R.id.floatingActionButton00);
            card00 = findViewById(R.id.foto00);
            card00.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermissionThenStartgallery(card00, addFoto00, 0);

                }
            });

            addFoto00.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermissionThenStartgallery(card00, addFoto00, 0);
                }
            });

        //else cargar la foto

        //Segunda Card ------------------

        addFoto01 = findViewById(R.id.floatingActionButton01);
        card01 = findViewById(R.id.foto01);
        card01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card01, addFoto01, 1);
            }
        });

        addFoto01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card01, addFoto01, 1);
            }
        });

        //Tercera Card -------------------------

        addFoto02 = findViewById(R.id.floatingActionButton02);
        card02 = findViewById(R.id.foto02);
        card02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card02, addFoto02, 2);
            }
        });

        addFoto02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card02, addFoto02, 2);
            }
        });

        //Cuarta Card --------------------------

        addFoto10 = findViewById(R.id.floatingActionButton10);
        card10 = findViewById(R.id.foto10);
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10, 3);
            }
        });

        addFoto10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10, 3);
            }
        });

        //Quinta Card -----------------------------

        addFoto11 = findViewById(R.id.floatingActionButton11);
        card11 = findViewById(R.id.foto11);
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11, 4);
            }
        });

        addFoto11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11, 4);
            }
        });

        //Sexta Card ---------------------------

        addFoto12 = findViewById(R.id.floatingActionButton12);
        card12 = findViewById(R.id.foto12);
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12, addFoto12, 5);
            }
        });

        addFoto12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12, addFoto12, 5);
            }
        });

        //Inicializar las Cards con las imagenes existentes
        Cards = new ArrayList<>();

        Cards.add(card00);
        Cards.add(card01);
        Cards.add(card02);
        Cards.add(card10);
        Cards.add(card11);
        Cards.add(card12);

        Buttons = new ArrayList<>();

        Buttons.add(addFoto00);
        Buttons.add(addFoto01);
        Buttons.add(addFoto02);
        Buttons.add(addFoto10);
        Buttons.add(addFoto11);
        Buttons.add(addFoto12);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(i0);


        for(int i=0; i<Cards.size(); i++){
            inicializarCard(Cards.get(i),Buttons.get(i), Urls.get(i));
        }

        submit = findViewById(R.id.buttonUpload);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tituloString = titulo.getText().toString();
                String historiaString = historia.getText().toString();
                Post p1 = new Post(tituloString, historiaString, usuario, i0, i1, i2, i3, i4, i5, postID);
                rootNode.child(String.valueOf(postID)).setValue(p1);
                Toast.makeText(EditStoryActivity.this, "Historia Guardada", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });


    }

    public void inicializarCard(final CardView cardView, FloatingActionButton floatingActionButton, String url){


        if(!url.isEmpty()) {
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                final File localFile = File.createTempFile("temp", "jpg");
                actualizarImagen(localFile, cardView, floatingActionButton, mStorageRef, url);


            } catch (IOException e) {
                Log.d("error de imagen", Objects.requireNonNull(e.getMessage()));
            }
        }
    }

    public void actualizarImagen(final File lf, final CardView cv, final FloatingActionButton fa, StorageReference storageReference, String url){



        mStorageRef.getFile(lf).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d("error de imagen", "llega hasta acá");
                Drawable d = Drawable.createFromPath(lf.getPath());
                cv.setBackground(d);
                fa.setVisibility(View.INVISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("error de imagen", Objects.requireNonNull(exception.getMessage()));
            }
        });

    }

    public void checkPermissionThenStartgallery(CardView cardView, FloatingActionButton floatingActionButton, int fotoNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(EditStoryActivity.this.getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditStoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALERIA_REQUEST_PERMISSION);
            } else {
                cvGlobalImSoSorry = cardView;
                faGlobalImSoSorry = floatingActionButton;
                numerodeFoto = fotoNumber;
                abrirGaleria();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GALERIA_REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show();
        }
    }


    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == GALERIA_REQUEST) && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    try {
                        InputStream inputStream = EditStoryActivity.this.getContentResolver().openInputStream(selectedImage);
                        Drawable d = Drawable.createFromStream(inputStream, selectedImage.toString());
                        cvGlobalImSoSorry.setBackground(d);
                        faGlobalImSoSorry.setVisibility(View.INVISIBLE);


                        //creo path de la foto
                        final StorageReference filePath = mStorageRef2.child("Fotos").child(String.valueOf(postID) + numerodeFoto);
                        //subo la foto
                        final ProgressDialog progressDialog = new ProgressDialog(EditStoryActivity.this);
                        progressDialog.setTitle("Cargando Imagen");

                        filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                String downloadUrl = filePath.toString();
//                                String downloadUrl = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString();
                                String s = "i" + numerodeFoto;
                                switch (s) {
                                    case "i0": {
                                        i0 = downloadUrl;
                                        break;
                                    }
                                    case "i1": {
                                        i1 = downloadUrl;
                                        break;
                                    }
                                    case "i2": {
                                        i2 = downloadUrl;
                                        break;
                                    }
                                    case "i3": {
                                        i3 = downloadUrl;
                                        break;
                                    }
                                    case "i4": {
                                        i4 = downloadUrl;
                                        break;
                                    }
                                    case "i5": {
                                        i5 = downloadUrl;
                                        break;
                                    }
                                }
                                progressDialog.dismiss();
                            }

                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Log.d("imagenuipload", exception.getMessage());
                                        Toast.makeText(EditStoryActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                progressDialog.show();
                            }
                        });


                    } catch (Exception exception) {
                        Toast.makeText(EditStoryActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}