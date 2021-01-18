package gautero.tuma.memo.ui.storys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class StoryFragment extends Fragment {

    RecyclerView storyRecycler ;

    List<Post> posts = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_storys, container, false);


        storyRecycler = root.findViewById(R.id.recyclerView_Storys);
        StoryAdapter myAdapter = new StoryAdapter(this.getActivity(), posts);
        storyRecycler.setAdapter(myAdapter);
        storyRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return root;
    }

    Post p1 = new Post("p1", "asd", "p1", R.drawable.product_example);
    Post p2 = new Post("p2", "asd", "p1", R.drawable.product_example);
    Post p3 = new Post("p3", "asd", "p1", R.drawable.product_example);
    Post p4 = new Post("p4", "asd", "p1", R.drawable.product_example);
    Post p5 = new Post("p5", "asd", "p1", R.drawable.product_example);
    Post p6 = new Post("p6", "asd", "p1", R.drawable.product_example);

    


}