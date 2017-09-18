package com.pptv.giftext.span;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pptv.giftext.EmotionConstants;
import com.pptv.giftext.ImageCache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 文字替换图片(包括gif)
 *
 * @author LeiKang on 2017/9/15 15:27
 */
public class TextReplaceImageManager
{

    private Context mContext;

    private IngetDrawableCallBack callBack;

    private ImageCache imageCache;

    // 需要替换的图片
    private List<EmotionConstants.Emotion> textEmotionList;

    public TextReplaceImageManager(Context mContext)
    {
        this.mContext = mContext;
        imageCache = new ImageCache(20);
        textEmotionList = new ArrayList<>();
    }

    public void setCallBack(IngetDrawableCallBack callBack)
    {
        this.callBack = callBack;
    }

    // 获取drawable
    public void getAnimatedDrawable(final EmotionConstants.Emotion e)
    {
        if (imageCache.get(e.resId) != null)
        {
            getDrawable(imageCache.get(e.resId), e);
            return;
        }
        // ImageRequest.fromUri("res://"+resId);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(e.resId).build();

        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(
                imageRequest, null);

        DataSubscriber<CloseableReference<CloseableImage>> dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableImage>>()
        {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource)
            {
                Log.e("TAG", Thread.currentThread() == Looper.getMainLooper().getThread() ? "true" : "false");
                if (!dataSource.isFinished())
                {
                    return;
                }
                getDrawable(dataSource.getResult(), e);
                imageCache.put(e.resId, dataSource.getResult());
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource)
            {
                Throwable t = dataSource.getFailureCause();
                // handle failure
            }
        };

        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());

    }

    public void clear()
    {
        // 释放所有应用
        Map<Integer, CloseableReference<CloseableImage>> snapshot = imageCache.snapshot();
        Iterator<Map.Entry<Integer, CloseableReference<CloseableImage>>> i = snapshot.entrySet().iterator();
        while (i.hasNext())
        {
            Map.Entry<Integer, CloseableReference<CloseableImage>> entry = i.next();
            entry.getValue().close();
        }

        imageCache.evictAll();
    }

    public interface IngetDrawableCallBack
    {

        void getDrawable(Drawable drawable, EmotionConstants.Emotion e);

    }

    public void replaceTextByImage(String content)
    {
        textEmotionList.clear();
        int start = 0;
        int end;
        while ((start = content.indexOf("[", start)) != -1 && (end = content.indexOf("]", start)) != -1)
        {
            String key = content.substring(start, end + 1);
            if (EmotionConstants.EMOTION_CONSTANT.containsKey(key))
            {
                int resId = EmotionConstants.EMOTION_CONSTANT.get(key);
                EmotionConstants.Emotion emotion = new EmotionConstants.Emotion(key, resId);
                emotion.start = start;
                emotion.end = end + 1;
                textEmotionList.add(emotion);
            }
            start++;
        }

        for (EmotionConstants.Emotion emotion : textEmotionList)
        {
            getAnimatedDrawable(emotion);
        }
    }

    public void getDrawable(CloseableReference<CloseableImage> closeableImageCloseableReference,
            EmotionConstants.Emotion e)
    {
        CloseableImage image = closeableImageCloseableReference.get();
        Drawable drawable;
        if(image instanceof CloseableAnimatedImage)
        {
            DrawableFactory drawableFactory = ImagePipelineFactory.getInstance().getAnimatedDrawableFactory(mContext);
            drawable = drawableFactory.createDrawable(image);
        }
        else
        {
            CloseableStaticBitmap image1 = (CloseableStaticBitmap) image;
            drawable = new BitmapDrawable(image1.getUnderlyingBitmap());
        }
        callBack.getDrawable(drawable, e);
    }

}
