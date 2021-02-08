package gautero.tuma.memo.ui.newStory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.PermissionInfoCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.activities.UserLogInActivity;

public class newStoryFragment extends Fragment {

    private newStoryViewModel slideshowViewModel;

    Button submit;
    int numerodeFoto;
    FloatingActionButton addFoto00, addFoto01, addFoto02, addFoto10, addFoto11, addFoto12, faGlobalImSoSorry;
    CardView card00, card01, card02, card10, card11, card12, cvGlobalImSoSorry;
    static final int GALERIA_REQUEST_PERMISSION = 1;
    static final int GALERIA_REQUEST = 2;

    private long postID;
    EditText titulo, historia;
    String usuario, i1,i2,i3,i4,i5,i0;

    List<InputStream> inputStreams = new ArrayList<>();

    FirebaseUser mUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference  rootNode = db.getReference().child("Posts");
    private StorageReference mStorageRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(newStoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newstory, container, false);


        //Primera Card---------------------

        addFoto00 = root.findViewById(R.id.floatingActionButton00);
        card00 = root.findViewById(R.id.foto00);
        card00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card00, addFoto00,0 );

            }
        });

        addFoto00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card00, addFoto00,0);
            }
        });

        //Segunda Card ------------------

        addFoto01 = root.findViewById(R.id.floatingActionButton01);
        card01 = root.findViewById(R.id.foto01);
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

        addFoto02 = root.findViewById(R.id.floatingActionButton02);
        card02 = root.findViewById(R.id.foto02);
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

        addFoto10 = root.findViewById(R.id.floatingActionButton10);
        card10 = root.findViewById(R.id.foto10);
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10, 3);
            }
        });

        addFoto10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10,3);
            }
        });

        //Quinta Card -----------------------------

        addFoto11 = root.findViewById(R.id.floatingActionButton11);
        card11 = root.findViewById(R.id.foto11);
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11,4);
            }
        });

        addFoto11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11,4);
            }
        });

        //Sexta Card ---------------------------

        addFoto12 = root.findViewById(R.id.floatingActionButton12);
        card12 = root.findViewById(R.id.foto12);
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12,addFoto12,5);
            }
        });

        addFoto12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12,addFoto12,5);
            }
        });

        //alta del post en firebase

        mStorageRef = FirebaseStorage.getInstance().getReference();



        titulo = root.findViewById(R.id.editTextTitulo);
        historia = root.findViewById(R.id.editTextTextMultiLineDEscHistoria);


        mUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mUser != null;

        String uid = mUser.getUid();
        Log.d("useruid", uid);
        usuario = mUser.getEmail();


        rootNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postID = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit = root.findViewById(R.id.buttonUpload);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tituloString = titulo.getText().toString();
                String historiaString = historia.getText().toString();
                Post p1 = new Post(tituloString, historiaString, usuario, i0, i1,i2,i3,i4,i5);
                rootNode.child(String.valueOf(postID)).setValue(p1);
                Toast.makeText(getContext(), "Historia Subida", Toast.LENGTH_SHORT).show();
                requireActivity().finish();

            }
        });

        return root;


    }

    public void checkPermissionThenStartgallery(CardView cardView, FloatingActionButton floatingActionButton, int fotoNumber){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
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
            } else Toast.makeText(getContext(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
        }
    }


    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if ((requestCode == GALERIA_REQUEST) && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    try {
                        InputStream inputStream = requireActivity().getContentResolver().openInputStream(selectedImage);
                        Drawable d = Drawable.createFromStream(inputStream, selectedImage.toString());
                        cvGlobalImSoSorry.setBackground(d);
                        faGlobalImSoSorry.setVisibility(View.INVISIBLE);


                        //creo path de la foto
                        StorageReference filePath = mStorageRef.child("Fotos").child(String.valueOf(postID)+ numerodeFoto);
                        //subo la foto
                            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    String downloadUrl = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString();
                                    String s = "i"+numerodeFoto;
                                    switch (s){
                                        case"i0": {
                                            i0 = downloadUrl;
                                            break;
                                        }
                                        case"i1": {
                                            i1 = downloadUrl;
                                            break;
                                        }
                                        case"i2": {
                                            i2 = downloadUrl;
                                            break;
                                        }
                                        case"i3": {
                                            i3 = downloadUrl;
                                            break;
                                        }
                                        case"i4": {
                                            i4 = downloadUrl;
                                            break;
                                        }
                                        case"i5": {
                                            i5 = downloadUrl;
                                            break;
                                        }
                                    }
                                }

                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                    } catch (Exception exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}

