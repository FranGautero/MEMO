package gautero.tuma.memo.ui.storys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class StoryFragment extends Fragment {

    RecyclerView storyRecycler ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootNode = database.getReference().child("Posts");

    List<Post> posts, posts2;

    public static Activity fa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_storys, container, false);

        fa = requireActivity();

        //TODO se recuperan los post de todos los usuarios por orden cronológico

        posts = new ArrayList<>();

        //Recuperar la data de cada post y sus imagenes

        getDatabaseData();

        //posts2 = Collections.reverse(posts);

        storyRecycler = root.findViewById(R.id.recyclerView_Storys);
        StoryAdapter myAdapter = new StoryAdapter(this.getActivity(), posts);
        storyRecycler.setAdapter(myAdapter);
        storyRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return root;
    }

   public void getDatabaseData(){
       rootNode.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               posts.clear();

               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   String titulo = dataSnapshot.child("titulo").getValue().toString();
                   String historia = dataSnapshot.child("historia").getValue().toString();
                   String usuario = dataSnapshot.child("usuario").getValue().toString();
                   String img1 = dataSnapshot.child("img0").getValue().toString();
                   String img2 = dataSnapshot.child("img1").getValue().toString();
                   String img3 = dataSnapshot.child("img2").getValue().toString();
                   String img4 = dataSnapshot.child("img3").getValue().toString();
                   String img5 = dataSnapshot.child("img4").getValue().toString();
                   String img6 = dataSnapshot.child("img5").getValue().toString();

                   Post p = new Post(titulo, historia, usuario, img1, img2, img3, img4, img5, img6);
//                 Post p = dataSnapshot.getValue(Post.class);

                   posts.add(p);
               }
               Collections.reverse(posts);
               storyRecycler.getAdapter().notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

               Log.d("errordedatabase", error.getMessage());

           }
       });
    }


}