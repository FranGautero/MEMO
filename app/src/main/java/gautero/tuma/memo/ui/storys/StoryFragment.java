package gautero.tuma.memo.ui.storys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class StoryFragment extends Fragment {

    RecyclerView storyRecycler ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_storys, container, false);

        //TODO se recuperan los post de todos los usuarios por orden cronológico

        ArrayList<Post> posts = new ArrayList<>();

//        Post p1 = new Post("UNA GUITARRA RE PIOLA DE LOS AÑOS 80 RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//        Post p2 = new Post("UNA GUITARRA RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//        Post p3 = new Post("UNA GUITARRA RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//        Post p4 = new Post("UNA GUITARRA RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//        Post p5 = new Post("UNA GUITARRA RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//        Post p6 = new Post("UNA GUITARRA RE PIOLA", "asd", "Ricardo 420", R.drawable.product_example);
//
//        posts.add(p1);
//        posts.add(p2);
//        posts.add(p3);
//        posts.add(p4);
//        posts.add(p5);
//        posts.add(p6);


        storyRecycler = root.findViewById(R.id.recyclerView_Storys);
        StoryAdapter myAdapter = new StoryAdapter(this.getActivity(), posts);
        storyRecycler.setAdapter(myAdapter);
        storyRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return root;
    }




}