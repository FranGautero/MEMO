package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Comment;
import gautero.tuma.memo.model.Post;

public class PostActivity extends AppCompatActivity {

    TextView titulo, historia;
    List<String> imagesUrls;
    ViewPager2 viewPager2;
    private FirebaseUser mUser;
    private StorageReference mStorageRef;
    CardView cv;
    ImageButton subirComment;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootNode = db.getReference().child("Comments");
    private long commentID;
    private Long idPost;
    RecyclerView comentarios;
    List<Comment> coments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        // recuperar datos del post que fue abierto

        titulo = findViewById(R.id.textViewPostTitle);
        historia = findViewById(R.id.textViewPostStory);
        Gson gson = new Gson();
        final Post post = gson.fromJson(getIntent().getStringExtra("post"), Post.class);
        idPost = post.getIdPost();
        titulo.setText(post.getTitulo());
        historia.setText(post.getHistoria());


        //Creo lista para el adapter del viewPager

        imagesUrls = new ArrayList<>();
        imagesUrls.add(post.getImg0());
        imagesUrls.add(post.getImg1());
        imagesUrls.add(post.getImg2());
        imagesUrls.add(post.getImg3());
        imagesUrls.add(post.getImg4());
        imagesUrls.add(post.getImg5());

        viewPager2 = findViewById(R.id.imagesPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(imagesUrls, this);

        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));
        transformer.addTransformer(new ViewPager2.PageTransformer() {

            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });

        viewPager2.setPageTransformer(transformer);


        //crear lista de comentarios

        coments = new ArrayList<>();

//        Comment c = new Comment();
//        c.setProfilePic("gs://memo-7be26.appspot.com/ProfilePics/asd@asd.com");
//        c.setComment("asdasd");
//        c.setUsuario("asd@asd.com");
//
//        coments.add(c);


        CommentAdapter commentAdapter = new CommentAdapter(this, coments);

        comentarios = findViewById(R.id.listComments);
        comentarios.setLayoutManager(new LinearLayoutManager(this));
        comentarios.setAdapter(commentAdapter);


        getDatabaseData();


        // setear el comentario con la foto de perfil del usuario

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        final String url = "gs://memo-7be26.appspot.com/ProfilePics/" + mUser.getEmail();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        cv = findViewById(R.id.profileComment);

        try {
            final File localFile = File.createTempFile("temp", "jpg");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Drawable d = Drawable.createFromPath(localFile.getPath());
                    cv.setBackground(d);

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

        // subir el comment cuando se hace click en el boton

        rootNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentID = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        subirComment = findViewById(R.id.imageButtonPostComment);

        subirComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setIdPost(post.getIdPost());
                comment.setIdComment(commentID);
                comment.setProfilePic(url);
                EditText comentario = findViewById(R.id.editTextComment);
                String texto = comentario.getText().toString();
                comment.setComment(texto);
                comment.setUsuario(mUser.getEmail());
                rootNode.child(String.valueOf(commentID)).setValue(comment);
                getDatabaseData();
                comentario.getText().clear();
                Toast.makeText(PostActivity.this, "Comentario Hecho", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getDatabaseData() {
        coments.clear();

        rootNode.orderByChild("idPost").equalTo(idPost).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String idComment = snapshot.child("idComment").getValue().toString();
                String comment = snapshot.child("comment").getValue().toString();
                String usuario = snapshot.child("usuario").getValue().toString();
                String profilePic = snapshot.child("profilePic").getValue().toString();
                String postID = snapshot.child("idPost").getValue().toString();
                Long idPost = Long.parseLong(postID);
                Long commentId = Long.parseLong(idComment);

                Comment comentario = new Comment();
                comentario.setIdPost(idPost);
                comentario.setUsuario(usuario);
                comentario.setComment(comment);
                comentario.setIdComment(commentId);
                comentario.setProfilePic(profilePic);
                coments.add(comentario);
                comentarios.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("errordedatabase", error.getMessage());

            }
        });
    }
}