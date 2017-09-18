package com.pptv.giftext;

import android.util.LruCache;

import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.CloseableImage;

import java.util.HashMap;

/**
 * 简单的使用LRU复用
 * @author LeiKang on 2017/9/15 15:55
 */
public class ImageCache extends LruCache<Integer, CloseableReference<CloseableImage>>
{

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *            the maximum number of entries in the cache. For all other
     *            caches, this is the maximum sum of the sizes of the entries
     *            in this cache.
     */
    public ImageCache(int maxSize)
    {
        super(maxSize);
    }
}
