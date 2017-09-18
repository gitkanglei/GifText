package com.pptv.giftext;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @anthor LeiKang
 */
public class EmotionFragment extends Fragment
{

    private View rootView;

    private Context mContext;

    private EmotionLayout emotionLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        if(rootView == null)
        {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.emotion_fragment,container,false);
            emotionLayout = (EmotionLayout) rootView.findViewById(R.id.emotion_container);

        }
        return rootView;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mContext = context;
    }


}
