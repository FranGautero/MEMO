package gautero.tuma.memo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gautero.tuma.memo.R;
import gautero.tuma.memo.model.Post;

public class PostActivity extends AppCompatActivity {

    TextView titulo, historia;
    List<String> imagesUrls;
    ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        titulo = findViewById(R.id.textViewPostTitle);
        historia = findViewById(R.id.textViewPostStory);
        Gson gson = new Gson();
        Post post = gson.fromJson(getIntent().getStringExtra("post"), Post.class);
        titulo.setText(post.getTitulo());
        historia.setText(post.getHistoria());

        //Creo lista para el adapter del viewPager

        imagesUrls = new ArrayList<>();

        imagesUrls.add(post.getImg0());
        imagesUrls.add(post.getImg1());
        imagesUrls.add(post.getImg2());
        imagesUrls.add(post.getImg3());
        imagesUrls.add(post.getImg4());
        imagesUrls.add(post.getImg5());

        viewPager2 = findViewById(R.id.imagesPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(imagesUrls, this);

        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));
        transformer.addTransformer(new ViewPager2.PageTransformer(){

            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });

        viewPager2.setPageTransformer(transformer);
    }
}