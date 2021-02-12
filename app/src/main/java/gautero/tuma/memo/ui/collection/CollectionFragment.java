package gautero.tuma.memo.ui.collection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;
import gautero.tuma.memo.ui.storys.StoryAdapter;


public class CollectionFragment extends Fragment {

    RecyclerView storyRecycler;

    CollectionViewModel homeViewModel;
    FirebaseUser mUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootNode = database.getReference().child("Posts");

    List<Post> posts;

    public static Activity fa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);

        View root = inflater.inflate(R.layout.fragment_collection, container, false);

        //TODO se recuperan los post del usuario por orden cronológico

        fa = requireActivity();

        posts = new ArrayList<>();


        getDatabaseData();


        storyRecycler = root.findViewById(R.id.recyclerView_Collection);
        CollectionAdapter myAdapter = new CollectionAdapter(this.getActivity(), posts);
        storyRecycler.setAdapter(myAdapter);
        storyRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return root;
    }

    public void getDatabaseData() {
        posts.clear();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootNode.orderByChild("usuario").equalTo(mUser.getEmail()).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String titulo = snapshot.child("titulo").getValue().toString();
                String historia = snapshot.child("historia").getValue().toString();
                String usuario = snapshot.child("usuario").getValue().toString();
                String img1 = snapshot.child("img0").getValue().toString();
                String img2 = snapshot.child("img1").getValue().toString();
                String img3 = snapshot.child("img2").getValue().toString();
                String img4 = snapshot.child("img3").getValue().toString();
                String img5 = snapshot.child("img4").getValue().toString();
                String img6 = snapshot.child("img5").getValue().toString();
                String id = snapshot.child("idPost").getValue().toString();
                Long idPost = Long.parseLong(id);

                Post p = new Post(titulo, historia, usuario, img1, img2, img3, img4, img5, img6, idPost);
//                 Post p = dataSnapshot.getValue(Post.class);
                posts.add(p);
//                Collections.reverse(posts);
                storyRecycler.getAdapter().notifyDataSetChanged();
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