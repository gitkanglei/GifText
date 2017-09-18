package com.pptv.giftext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @anthor LeiKang
 */
public class EmotionConstants
{
    public static final HashMap<String, Integer> EMOTION_CONSTANT = new HashMap<>();;

    public final static List<Emotion> EMOTION_LIST = new ArrayList<>();
    static
    {
        EMOTION_LIST.add(new Emotion("云仔猪头", R.raw.xm_01));
        EMOTION_LIST.add(new Emotion("云仔震惊", R.raw.xm2));
        EMOTION_LIST.add(new Emotion("云仔咋唬", R.raw.xm3));
        EMOTION_LIST.add(new Emotion("云仔阴险", R.raw.xm4));

        EMOTION_LIST.add(new Emotion("云仔疑问", R.raw.xm5));
        EMOTION_LIST.add(new Emotion("云仔羞愧", R.raw.xm6));
        EMOTION_LIST.add(new Emotion("云仔心碎", R.raw.xm7));
        EMOTION_LIST.add(new Emotion("云仔乌鸦", R.raw.xm8));

        EMOTION_LIST.add(new Emotion("云仔握手", R.raw.xm9));
        EMOTION_LIST.add(new Emotion("云仔吻", R.raw.xm10));
        EMOTION_LIST.add(new Emotion("云仔委屈", R.raw.xm11));
        EMOTION_LIST.add(new Emotion("云仔微笑", R.raw.xm12));

        EMOTION_CONSTANT.put("[云仔猪头]", R.raw.xm_01);
        EMOTION_CONSTANT.put("[云仔震惊]", R.raw.xm2);
        EMOTION_CONSTANT.put("[云仔咋唬]", R.raw.xm3);
        EMOTION_CONSTANT.put("[云仔阴险]", R.raw.xm4);

        EMOTION_CONSTANT.put("[云仔疑问]", R.raw.xm5);
        EMOTION_CONSTANT.put("[云仔羞愧]", R.raw.xm6);
        EMOTION_CONSTANT.put("[云仔心碎]", R.raw.xm7);
        EMOTION_CONSTANT.put("[云仔乌鸦]", R.raw.xm8);

        EMOTION_CONSTANT.put("[云仔握手]", R.raw.xm9);
        EMOTION_CONSTANT.put("[云仔吻]", R.raw.xm10);
        EMOTION_CONSTANT.put("[云仔委屈]", R.raw.xm11);
        EMOTION_CONSTANT.put("[云仔微笑]", R.raw.xm12);
    }

    public static class Emotion
    {
        public String key;

        public int resId;

        public  int start;

        public int end;

        public Emotion(String key, int resId)
        {
            this.key = key;
            this.resId = resId;
        }
    }

}
