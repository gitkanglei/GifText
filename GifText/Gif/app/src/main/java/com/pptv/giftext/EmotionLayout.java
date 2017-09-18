package com.pptv.giftext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pptv.giftext.util.DisplayUtil;


/**
 * @anthor LeiKang
 */
public class EmotionLayout extends LinearLayout
{
    private Context mContext;

    private OnEmotionClcikListener onEmotionClcikListener;

    public EmotionLayout(Context context)
    {
        this(context, null);
    }

    public EmotionLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        setOrientation(VERTICAL);
        if (context instanceof OnEmotionClcikListener)
        {
            onEmotionClcikListener = (OnEmotionClcikListener) context;
        }

    }
    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        initView();
    }

    public void initView()
    {
        int index = 0;
        for (int i = 0; i < 3; i++)
        {
            LinearLayout rowContainer = new EmotionLayout(mContext);
            rowContainer.setOrientation(HORIZONTAL);
            rowContainer.setGravity(Gravity.CENTER);
            addView(rowContainer, LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, 50));
            for (int j = 0; j < 4; j++)
            {
                ImageView imageView = new ImageView(mContext);

                final EmotionConstants.Emotion emotion = EmotionConstants.EMOTION_LIST.get(index);

                imageView.setImageResource(emotion.resId);
                imageView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onEmotionClcikListener.onEmotionClick(emotion);
                    }
                });
                rowContainer.addView(imageView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                index++;
            }

        }
    }

    public interface OnEmotionClcikListener
    {

        void onEmotionClick(EmotionConstants.Emotion e);
    }
}
