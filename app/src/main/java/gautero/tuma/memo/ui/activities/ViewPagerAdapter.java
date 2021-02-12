package gautero.tuma.memo.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
import gautero.tuma.memo.ui.storys.StoryFragment;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    List<String> Urls;
    private StorageReference mStorageRef;
    Activity context;

    public ViewPagerAdapter(List<String> images, Activity context1){
        Urls = images;
        context = context1;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewPagerAdapter.ViewHolder holder, int position) {

        if(!Urls.get(position).isEmpty()) {
            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(Urls.get(position));


            try {
                final File localFile = File.createTempFile("imagenPost" + position, "jpg");
                mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Drawable d = Drawable.createFromPath(localFile.getPath());
                        DisplayMetrics dm = new DisplayMetrics();
                        holder.image.setMaxHeight(dm.heightPixels);
                        holder.image.setMaxWidth(dm.widthPixels);
                        holder.image.setBackground(d);

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
        }


    }

    @Override
    public int getItemCount() {
        return Urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagesItem);

        }
    }
}
