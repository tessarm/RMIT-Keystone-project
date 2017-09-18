package dmcjj.rmitpp.toiletlocator.helper;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by A on 16/09/2017.
 */

public class LruImageCache implements ImageLoader.ImageCache {

    private static final int SIZE = 4 * 1024 * 1024;
    private LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(SIZE)
    {
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };


    @Override
    public Bitmap getBitmap(String url)
    {
        return bitmapCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        bitmapCache.put(url, bitmap);
    }
}
