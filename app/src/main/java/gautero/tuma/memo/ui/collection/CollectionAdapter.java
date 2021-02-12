package gautero.tuma.memo.ui.collection;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import gautero.tuma.memo.ui.activities.EditStoryActivity;
import gautero.tuma.memo.ui.activities.PostActivity;
import gautero.tuma.memo.ui.storys.StoryAdapter;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.StoryHolder>{
    List<Post> posts;

    Context context;

    private StorageReference mStorageRef;

    public CollectionAdapter (Context ct, List<Post> p){
        posts = p;
        context = ct;
    }

    @NonNull
    @Override
    public CollectionAdapter.StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_row, parent, false);

        return new CollectionAdapter.StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollectionAdapter.StoryHolder holder, final int position) {

        holder.Titulo.setText(posts.get(position).getTitulo());
        holder.Usuario.setText(posts.get(position).getUsuario());

        //aca hay que traer la primera foto de las foto y setearle como preview
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(posts.get(position).getImg0());

        try {
            final File localFile = File.createTempFile("imagenPost"+ position, "jpg");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Drawable d = Drawable.createFromPath(localFile.getPath());
                    holder.imageLayout.setBackground(d);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        } catch (IOException e) {
            Log.d("error de imagen", Objects.requireNonNull(e.getMessage()));
        }

        //aca si se clickea la imagen o el titulo se va al Post

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

        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(posts.get(position));
                i.putExtra("post", myJson);
                v.getContext().startActivity(i);
            }
        });

        //aca si se clickea el boton de editar se va al edit Post

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditStoryActivity.class);
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
        ImageView Edit;
        LinearLayout imageLayout;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.textTituloPost);
            Usuario = itemView.findViewById(R.id.textUsuario);
            imageLayout = itemView.findViewById(R.id.imageLayoutEdit);
            Edit = itemView.findViewById(R.id.imageViewEditStory);

        }
    }
}
