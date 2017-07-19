package com.domin.demo01.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/**
 * Created by erhii on 2017/7/10.
 */

public class BitmapUtils {
    private final String TAG =this.getClass().getSimpleName();

    /**
     *  Google  压缩图片大小方法
     * @param res  资源
     * @param resId  资源id
     * @param reqWidth  要求图片压缩后的宽度
     * @param reqHeight  要求图片压缩后的高度
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res,int resId, int reqWidth,int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);
        //调用方法计算 SampleSize
        options.inSampleSize =calculateInSampleSize(options,reqWidth,reqHeight);
        //inJustDecodeBounds设为false，开始真正加载图片
        options.inJustDecodeBounds=false;
        //RGB_565是默认情况Bitmap.Config.RGB_565内存的一半
        options.inPreferredConfig =Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(res,resId,options);

    }
    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight)
    {
        if(reqWidth==0||reqHeight==0)
        {
            return 1;
        }
        //获取图片原生的宽高
        final  int height =options.outHeight;
        final  int width = options.outWidth;
        Log.i(TAG,"height :"+height+";"+"weight :"+width);
        int inSampleSize =1;
        //如果原生的宽高大于请求的宽高，那么将原生的宽高都设成原来的一半
        if(height>reqHeight||width>reqWidth)
        {
            final int halfHeight =height/2;
            final int halfWeight =width/2;
            while ((halfHeight/inSampleSize)>=reqHeight&&(halfWeight/inSampleSize)>=reqWidth)
            {
                inSampleSize*=2;
            }
        }
        Log.i(TAG,"sampleSize:"+inSampleSize);
        return inSampleSize;

    }
}
