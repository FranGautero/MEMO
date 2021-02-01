package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import gautero.tuma.memo.R;

public class UserLogInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView email, password;

    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);

        mAuth = FirebaseAuth.getInstance();

        logIn = findViewById(R.id.buttonUserLogIn);

        email = findViewById(R.id.editTextUserMail);
        password = findViewById(R.id.editTextUserPassword);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(UserLogInActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(UserLogInActivity.this, FeedActivity.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(UserLogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });

    }
}