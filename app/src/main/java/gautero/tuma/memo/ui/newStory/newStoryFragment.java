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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.acl.Permission;
import java.util.Objects;

import gautero.tuma.memo.R;
import gautero.tuma.memo.ui.activities.UserLogInActivity;

public class newStoryFragment extends Fragment {

    private newStoryViewModel slideshowViewModel;


    FloatingActionButton addFoto00, addFoto01, addFoto02, addFoto10, addFoto11, addFoto12, faGlobalImSoSorry;
    CardView card00, card01, card02, card10, card11, card12, cvGlobalImSoSorry;
    static final int GALERIA_REQUEST_PERMISSION = 1;
    static final int GALERIA_REQUEST = 2;

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
                checkPermissionThenStartgallery(card00, addFoto00);
            }
        });

        addFoto00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card00, addFoto00);
            }
        });

        //Segunda Card ------------------

        addFoto01 = root.findViewById(R.id.floatingActionButton01);
        card01 = root.findViewById(R.id.foto01);
        card01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card01, addFoto01);
            }
        });

        addFoto01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card01, addFoto01);
            }
        });

        //Tercera Card -------------------------

        addFoto02 = root.findViewById(R.id.floatingActionButton02);
        card02 = root.findViewById(R.id.foto02);
        card02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card02, addFoto02);
            }
        });

        addFoto02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card02, addFoto02);
            }
        });

        //Cuarta Card --------------------------

        addFoto10 = root.findViewById(R.id.floatingActionButton10);
        card10 = root.findViewById(R.id.foto10);
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10);
            }
        });

        addFoto10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card10, addFoto10);
            }
        });

        //Quinta Card -----------------------------

        addFoto11 = root.findViewById(R.id.floatingActionButton11);
        card11 = root.findViewById(R.id.foto11);
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11);
            }
        });

        addFoto11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card11, addFoto11);
            }
        });

        //Sexta Card ---------------------------

        addFoto12 = root.findViewById(R.id.floatingActionButton12);
        card12 = root.findViewById(R.id.foto12);
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12,addFoto12);
            }
        });

        addFoto12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartgallery(card12,addFoto12);
            }
        });

        return root;


    }

    public void checkPermissionThenStartgallery(CardView cardView, FloatingActionButton floatingActionButton){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALERIA_REQUEST_PERMISSION);
            } else {
                cvGlobalImSoSorry = cardView;
                faGlobalImSoSorry = floatingActionButton;
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
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        Drawable d = Drawable.createFromStream(inputStream, selectedImage.toString());
                        cvGlobalImSoSorry.setBackground(d);
                        faGlobalImSoSorry.setVisibility(View.INVISIBLE);
                    } catch (Exception exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}

