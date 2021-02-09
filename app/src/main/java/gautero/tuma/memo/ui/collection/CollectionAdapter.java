package gautero.tuma.memo.ui.collection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;
import gautero.tuma.memo.R;

import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.activities.EditStoryActivity;
import gautero.tuma.memo.ui.activities.PostActivity;
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
    public void onBindViewHolder(@NonNull CollectionAdapter.StoryHolder holder, final int position) {

        holder.Titulo.setText(posts.get(position).getTitulo());
        holder.Usuario.setText(posts.get(position).getUsuario());

        //aca hay que traer la primera foto de las foto y setearle como preview
        holder.PostImage.setImageResource(R.drawable.product_example);

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
        ImageView PostImage,Edit;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.textTituloPost);
            Usuario = itemView.findViewById(R.id.textUsuario);
            PostImage = itemView.findViewById(R.id.imagePost);
            Edit = itemView.findViewById(R.id.imageViewEditStory);

        }
    }
}
