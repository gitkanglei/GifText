package com.pptv.giftext;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements EmotionLayout.OnEmotionClcikListener
{
    RichTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmotionFragment fragment = new EmotionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.emotion_container, fragment).commit();
        textView = (RichTextView) findViewById(R.id.rich_text);

    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        super.onAttachFragment(fragment);
        // fragment.setCallback();
        Log.e("TAG", fragment.toString());
    }

    @Override
    public void onEmotionClick(EmotionConstants.Emotion key)
    {
        textView.replaceText(key);
    }
}
