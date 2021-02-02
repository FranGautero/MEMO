package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import gautero.tuma.memo.R;

public class UserSignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView email, password;

    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        email = findViewById(R.id.editTextNewUserEmail);
        password = findViewById(R.id.editTextNewUserPassword);
        register = findViewById(R.id.buttonRegisterNewUser);

        mAuth = FirebaseAuth.getInstance();


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

                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(UserSignInActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                        }
                                    });
                            Toast.makeText(UserSignInActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                }else {
                    Toast.makeText(UserSignInActivity.this, "Falta ingresar Email", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}