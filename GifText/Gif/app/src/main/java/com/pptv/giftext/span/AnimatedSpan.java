package com.pptv.giftext.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

/**
 * @author LeiKang on 2017/9/15 15:38
 */
public class AnimatedSpan extends DynamicDrawableSpan
{
    private Drawable mDrawable;

    public AnimatedSpan(Drawable d)
    {
        mDrawable = d;
    }

    @Override
    public Drawable getDrawable()
    {
        Log.e("TAG", "getDrawable");
        Drawable drawable = mDrawable.getCurrent();
        Rect rect = new Rect(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setBounds(rect);
        return drawable;
    }

    /**
     * 代码跟父类代码相似，就是getCachedDrawable()替换成getDrawable（）,
     * 因为前者里面的图片是WeakReference， 容易被gc回收，所以这里要避免这个问题
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        Log.e("TAG", "getSize");
        Drawable d = getDrawable();
        Rect rect = d.getBounds();

        if (fm != null)
        {
            int fontHeight = fm.bottom - fm.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;

        }
        Log.e("TAG", "x :" + rect.left + ",top :" + rect.top + ",y :" + rect.right + " ,bottom :" + rect.bottom);
        return rect.right;
    }

    /**
     * 代码跟父类代码相似，就是getCachedDrawable()替换成getDrawable（）,
     * 因为前者里面的图片是WeakReference， 容易被gc回收，所以这里要避免这个问题
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
            Paint paint)
    {
        // TODO Auto-generated method stub
        Drawable b = getDrawable();
        canvas.save();
        int transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
        if (mVerticalAlignment == ALIGN_BASELINE)
        {
            transY -= paint.getFontMetricsInt().descent;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
        Log.e("TAG", "x :" + x + ",top :" + top + ",y :" + y + " ,bottom :" + bottom);
    }

}
