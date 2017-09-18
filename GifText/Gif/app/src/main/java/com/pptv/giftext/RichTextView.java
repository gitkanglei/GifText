package com.pptv.giftext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.fresco.animation.drawable.AnimationListener;
import com.pptv.giftext.span.AnimatedSpan;
import com.pptv.giftext.span.TextReplaceImageManager;

/**
 * @author LeiKang on 2017/9/15 15:38
 */
public class RichTextView extends TextView implements TextReplaceImageManager.IngetDrawableCallBack, AnimationListener
{
    private TextReplaceImageManager manager;

    private Context mContext;

    private SpannableStringBuilder stringBuilder;

    public RichTextView(Context context)
    {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        manager = new TextReplaceImageManager(context);
        mContext = context;
        manager.setCallBack(this);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        stringBuilder = new SpannableStringBuilder(getText().toString());
        manager.replaceTextByImage(getText().toString());
    }

    public void replaceText(EmotionConstants.Emotion e)
    {
        //stringBuilder.clear();
        String key = "["+e.key+"]";
        stringBuilder .append(key);
        e.start = stringBuilder.length()-key.length();
        e.end = stringBuilder.length();
        manager.getAnimatedDrawable(e);
    }
    @Override
    public void getDrawable(Drawable drawable, EmotionConstants.Emotion e)
    {
        AnimatedSpan span = new AnimatedSpan(drawable);
        if (drawable instanceof AnimatedDrawable2)
        {
            ((AnimatedDrawable2) drawable).start();
            ((AnimatedDrawable2) drawable).setAnimationListener(this);
        }
        stringBuilder.setSpan(span, e.start, e.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(stringBuilder);
    }

    @Override
    public void onAnimationStart(AnimatedDrawable2 drawable)
    {

    }

    @Override
    public void onAnimationStop(AnimatedDrawable2 drawable)
    {

    }

    @Override
    public void onAnimationReset(AnimatedDrawable2 drawable)
    {

    }

    @Override
    public void onAnimationRepeat(AnimatedDrawable2 drawable)
    {

    }

    @Override
    public void onAnimationFrame(AnimatedDrawable2 drawable, int frameNumber)
    {
        invalidate();
    }

}
