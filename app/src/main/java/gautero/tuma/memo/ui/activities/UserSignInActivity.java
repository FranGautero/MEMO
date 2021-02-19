package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import gautero.tuma.memo.R;
import gautero.tuma.memo.notifications.MyFirebaseMassagingService;

public class UserSignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView email, password;

    Button register;

    ImageView profilePic;

    private boolean t;

    static final int GALERIA_REQUEST_PERMISSION = 1;

    static final int GALERIA_REQUEST = 2;

    private StorageReference mStorageRef;

    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        t = false;

        email = findViewById(R.id.editTextNewUserEmail);
        password = findViewById(R.id.editTextNewUserPassword);
        register = findViewById(R.id.buttonRegisterNewUser);
        profilePic = findViewById(R.id.imageViewAddProfilePic);

        mAuth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionThenStartGallery();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!email.getText().toString().isEmpty()) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        Toast.makeText(UserSignInActivity.this, "Email Inválido", Toast.LENGTH_SHORT).show();

                    }else {

                        if(password.getText().toString().isEmpty()){
                            Toast.makeText(UserSignInActivity.this, "Debe ingresar Contraseña", Toast.LENGTH_SHORT).show();
                        }else{
                            if(t){
                                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                        .addOnCompleteListener(UserSignInActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                            }
                                        });

                                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                        .addOnCompleteListener(UserSignInActivity.this, new OnCompleteListener<AuthResult>() {

                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    String s = email.getText().toString();
                                                    //creo path de la foto
                                                    final StorageReference filePath = mStorageRef.child("ProfilePics").child(s);
                                                    //subo la foto
                                                    final ProgressDialog progressDialog = new ProgressDialog(UserSignInActivity.this);
                                                    progressDialog.setTitle("Creando Cuenta");

                                                    filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            progressDialog.dismiss();
                                                            MyFirebaseMassagingService service = new MyFirebaseMassagingService();
                                                            service.onNewToken(FirebaseInstanceId.getInstance().getToken());
                                                            Intent i = new Intent(UserSignInActivity.this, FeedActivity.class);
                                                            startActivity(i);

                                                        }

                                                    })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception exception) {
                                                                    Log.d("imagenuipload", exception.getMessage());
                                                                    Toast.makeText(UserSignInActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                            progressDialog.show();
                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(UserSignInActivity.this, "Email o Contraseña inválidos", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                Toast.makeText(UserSignInActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
//                                finish();
                            }else Toast.makeText(UserSignInActivity.this, "Debe elegir foto de perfil", Toast.LENGTH_SHORT).show();


                        }

                    }
                }else {
                    Toast.makeText(UserSignInActivity.this, "Falta ingresar Email", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void checkPermissionThenStartGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(UserSignInActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UserSignInActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALERIA_REQUEST_PERMISSION);
            } else {
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

    public void abrirGaleria(){
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == GALERIA_REQUEST) && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImage = data.getData();
                if (selectedImage != null) {
                    try {
                        InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                        Drawable d = Drawable.createFromStream(inputStream, selectedImage.toString());
                        profilePic.setBackground(d);
                        profilePic.setImageResource(0);
                        t=true;

                    } catch (Exception exception) {
                        Toast.makeText(UserSignInActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}