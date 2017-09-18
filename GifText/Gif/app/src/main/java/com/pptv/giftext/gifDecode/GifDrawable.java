package com.pptv.giftext.gifDecode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @anthor LeiKang
 */
public class GifDrawable extends AnimationDrawable implements GifAction
{

    public UpdateListener mListener;

    public GifDecoder gifDecoder;

    public int mCurrentIndex;

    private Context mContext;

    private Bitmap firstBitmap;

    public GifDrawable(Context context, int resId, UpdateListener listener)
    {
        mListener = listener;
        gifDecoder = new GifDecoder(this);
        gifDecoder.setGifImage(context.getResources().openRawResource(resId));
        gifDecoder.start();
        firstBitmap = BitmapFactory.decodeStream(context.getResources().openRawResource(resId));
        mContext = context;
    }

    /**
     * 继续下一帧，同时通知监听器
     */
    public void nextFrame()
    {
        // 当循环到最后一帧时，索引就会为0，注意索引比帧的数量小1
        if (getNumberOfFrames() == 0)
        {
            return;
        }
        mCurrentIndex = (mCurrentIndex + 1) % getNumberOfFrames();
        if (mListener != null)
        {
            mListener.update();
        }
    }

    /**
     * 返回当前帧的显示时间
     *
     * @return
     */
    public int getFrameDuration()
    {
        return getDuration(mCurrentIndex);
    }

    /**
     * 返回当前帧的图片
     *
     * @return Drawable
     */
    public Drawable getDrawable()
    {
        Drawable drawable = getFrame(mCurrentIndex);
        // 因为是异步解析所以这个时候 drawable可能是空的 就默认拿第一帧
        if (drawable == null)
        {
            drawable = new BitmapDrawable(mContext.getResources(), firstBitmap);
            addFrame(drawable, 0);
            setBounds(0, 0, firstBitmap.getWidth(), firstBitmap.getHeight());
            Log.e("TAG", "width :" + firstBitmap.getWidth() + ",height :" + firstBitmap.getHeight());
        }
        return drawable;
    }

    @Override
    public void parseOk(boolean parseStatus, int frameIndex)
    {
        try
        {
            if (parseStatus)
            {
                // 遍历gif图像里面的每一帧，放进animation frame中
                for (int i = 0; i < gifDecoder.getFrameCount(); i++)
                {
                    Bitmap bitmap = gifDecoder.getFrameImage(i);
                    BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    // 为每一帧设置边界
                    drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    addFrame(drawable, gifDecoder.getDelay(i));

                    // 为容器设置一个边界
                    setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());

                }

            }
        }
        catch (Exception e)
        {
        }

    }

    /**
     * 该接口通知监听器更新/重绘界面
     *
     * @author moon
     */
    public interface UpdateListener
    {
        void update();
    }

}
