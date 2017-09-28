package dmcjj.rmitpp.toiletlocator.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.helper.LruImageCache;

/**
 * Created by A on 16/09/2017.
 */

public class NetworkImageAdapter extends RecyclerView.Adapter<NetworkImageAdapter.ImageViewHolder>
{
    private List<String> urls = new ArrayList<>();
    private ImageLoader imageLoader;

    public NetworkImageAdapter(Context c) {

        LruImageCache imageCache = new LruImageCache();
        imageLoader = new ImageLoader(Volley.newRequestQueue(c), imageCache);

    }

    public void add(String url){
        urls.add(url);
        //imageUrls.add("http://www.petguide.com/wp-content/uploads/2013/05/jack-russell-terrier.jpg");
        //imageUrls.add("http://jrtrescue.net/wp-content/uploads/2017/01/Buddy-Happy-adoption-story-1024x768.jpg");
        //imageUrls.add("https://i.pinimg.com/736x/a4/6b/0f/a46b0f391b994618065b944f3e8995f8--miniature-fox-terrier-a-small.jpg");
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_network,parent, false);
        
        ImageViewHolder imageViewHolder = new ImageViewHolder(imageView);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

        holder.imageView.setImageUrl(urls.get(position), imageLoader);

    }

    @Override
    public int getItemCount() {
        if(urls == null)
            return 0;
        return urls.size();
    }

    public List<String> getUrls() {
        return urls;
    }

    public void clear() {
        urls.clear();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView =(NetworkImageView) itemView;
        }
    }
}
