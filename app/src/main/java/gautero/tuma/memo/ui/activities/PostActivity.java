package gautero.tuma.memo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class PostActivity extends AppCompatActivity {

    TextView titulo, historia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        titulo = findViewById(R.id.textViewPostTitle);
        historia = findViewById(R.id.textViewPostStory);
        Gson gson = new Gson();
        Post post = gson.fromJson(getIntent().getStringExtra("post"), Post.class);

        titulo.setText(post.getTitulo());
        historia.setText(post.getHistoria());


    }
}