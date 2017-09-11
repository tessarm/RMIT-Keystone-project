package dmcjj.rmitpp.toiletlocator.view;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;

/**
 * Created by A on 4/09/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private List<Bitmap> images = new ArrayList<>();
    //private List<String> images = new ArrayList<>();

    public ImageAdapter() {

    }

    public void add(Bitmap bitmap){
        images.add(bitmap);
        //imageUrls.add("http://www.petguide.com/wp-content/uploads/2013/05/jack-russell-terrier.jpg");
        //imageUrls.add("http://jrtrescue.net/wp-content/uploads/2017/01/Buddy-Happy-adoption-story-1024x768.jpg");
        //imageUrls.add("https://i.pinimg.com/736x/a4/6b/0f/a46b0f391b994618065b944f3e8995f8--miniature-fox-terrier-a-small.jpg");
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent, false);



        ImageViewHolder imageViewHolder = new ImageViewHolder(imageView);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        //Picasso.with(holder.imageView.getContext()).load(images.get(position)).into(holder.imageView);
        holder.imageView.setImageBitmap(images.get(position));

    }

    @Override
    public int getItemCount() {
        if(images == null)
            return 0;
        return images.size();
    }

    public List<Bitmap> getImages() {
        return images;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView)itemView;
        }
    }
}
