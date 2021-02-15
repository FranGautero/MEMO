package gautero.tuma.memo.ui.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import gautero.tuma.memo.R;

public class FeedActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private FirebaseUser mUser;
    private StorageReference mStorageRef;
    ImageView profilePic;
    TextView usermail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Collection, R.id.nav_storys, R.id.nav_newStory/*, R.id.nav_editStory*/)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String url = "gs://memo-7be26.appspot.com/ProfilePics/" + mUser.getEmail();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);

        View headerView = navigationView.getHeaderView(0);
        if(mUser != null){
            String User = mUser.getEmail();
            usermail = (TextView) headerView.findViewById(R.id.textNameEmailUserDisplayed);
            usermail.setText(User);
            profilePic = (ImageView) headerView.findViewById(R.id.imageViewProfilePic);
            try {
                final File localFile = File.createTempFile("temp", "jpg");
                mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("error de imagen", "llega hasta acá");
                        Drawable d = Drawable.createFromPath(localFile.getPath());
                        profilePic.setBackground(d);
                        profilePic.setImageResource(0);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("error de imagen", Objects.requireNonNull(exception.getMessage()));
                    }
                });

            } catch (IOException e) {
                Log.d("error de imagen", Objects.requireNonNull(e.getMessage()));
            }

        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}