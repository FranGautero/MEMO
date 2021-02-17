package gautero.tuma.memo.ui.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.service.controls.templates.TemperatureControlTemplate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Comment;
import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.storys.StoryAdapter;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    List<Comment> comments;

    Context context;

    public CommentAdapter(Context ct, List<Comment> c){
        comments = c;
        context = ct;
    }


    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_row, parent, false);

        return new CommentAdapter.CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(comments.get(position).getProfilePic());

        try {
            final File localFile = File.createTempFile("imagenPost"+ position, "jpg");
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Drawable d = Drawable.createFromPath(localFile.getPath());
                    holder.cardView.setBackground(d);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });

        } catch (IOException e) {
            Log.d("error de imagen", Objects.requireNonNull(e.getMessage()));
        }

        holder.texto.setText(comments.get(position).getComment());
        holder.usuario.setText(comments.get(position).getUsuario());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        TextView texto, usuario;
        CardView cardView;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.comment);
            usuario = itemView.findViewById(R.id.userComment);
            cardView = itemView.findViewById(R.id.profileCommentrow);
        }
    }
}
