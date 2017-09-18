package com.pptv.giftext.app;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
/**
 * @author LeiKang
 * @time 2017/3/1
 */
public class MyApplication extends Application
{
    Context context;
    @Override
    public void onCreate()
    {
        super.onCreate();
        context =this.getApplicationContext();
        DiskCacheConfig diskCacheConfig = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            diskCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryName(".image").setBaseDirectoryPath(
                    new File(Environment.getExternalStorageDirectory() + "/ppfinance/")).build();
        }
        ImagePipelineConfig config1 = ImagePipelineConfig.newBuilder(context).setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(context, config1);

    }

}
