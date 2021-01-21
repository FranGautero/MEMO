package gautero.tuma.memo.ui.storys;

import android.content.Context;
import android.content.Intent;
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
import gautero.tuma.memo.ui.activities.PostActivity;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder>{

    List<Post> posts;

    Context context;

    public StoryAdapter (Context ct, List<Post> p){
        posts = p;
        context = ct;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_row, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostActivity.class);
                v.getContext().startActivity(i);
            }
        });

        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, int position) {

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
