package gautero.tuma.memo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class LogInActivity extends AppCompatActivity {
    Button LogIn;
    Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogIn = findViewById(R.id.buttonLogIn);
        SignIn = findViewById(R.id.buttonSignIn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            Intent i = new Intent(LogInActivity.this, FeedActivity.class);
            startActivity(i);
        }

            // User is signed out


        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this, UserLogInActivity.class);
                startActivity(i);
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogInActivity.this, UserSignInActivity.class);
                startActivity(i);
            }
        });
    }


}