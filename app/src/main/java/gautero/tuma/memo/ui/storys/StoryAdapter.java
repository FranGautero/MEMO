package gautero.tuma.memo.ui.storys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import gautero.tuma.memo.R;

import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.activities.PostActivity;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder>{

    List<Post> posts;

    Context context;

    private StorageReference mStorageRef;



    public StoryAdapter (Context ct, List<Post> p){
        posts = p;
        context = ct;

    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_row, parent, false);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, PostActivity.class);
//                v.getContext().startActivity(i);
//            }
//        });

        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, final int position) {

        holder.Titulo.setText(posts.get(position).getTitulo());
        holder.Usuario.setText(posts.get(position).getUsuario());

        //recuperar primer imagen del post y setearle al view

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(posts.get(position).getImg0());

        try {
            File localFile = File.createTempFile("imagenPost"+ position, "jpg");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Successfully downloaded data to local file
                    // ...
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

            holder.PostImage.setImageURI(Uri.fromFile(localFile));
        } catch (IOException e) {
           Log.d("error de imagen", Objects.requireNonNull(e.getMessage()));
        }




        //La actividad se lanza con el post

        holder.Titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(posts.get(position));
                i.putExtra("post", myJson);
                v.getContext().startActivity(i);
            }
        });

        holder.PostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(posts.get(position));
                i.putExtra("post", myJson);
                v.getContext().startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class StoryHolder extends RecyclerView.ViewHolder{

        TextView Titulo, Usuario;
        ImageView PostImage;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.textTituloPost);
            Usuario = itemView.findViewById(R.id.textUsuario);
            PostImage = itemView.findViewById(R.id.imagePost);

        }
    }
}
