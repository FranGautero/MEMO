package gautero.tuma.memo.ui.collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import gautero.tuma.memo.R;

import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.storys.StoryAdapter;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.StoryHolder>{
    List<Post> posts;

    Context context;

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
    public void onBindViewHolder(@NonNull CollectionAdapter.StoryHolder holder, int position) {

        holder.Titulo.setText(posts.get(position).getTitulo());
        holder.Usuario.setText(posts.get(position).getUsiario());
        holder.PostImage.setImageResource(R.drawable.product_example);


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
