package com.domin.demo01.wechatpublish.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.domin.demo01.MainActivity;
import com.domin.demo01.R;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class WeChatPublishActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_publish);
    }
    public void intentPublishActivity(View v){
        MultiImageSelector.create()
                .showCamera(true) // show camera or not. true by default
                .count(9) // max select image size, 9 by default. used width #.multi()
                .multi() // multi mode, default mode;
                .start(WeChatPublishActivity.this, REQUEST_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {//文章图片
            PostImagesActivity.startPostActivity(WeChatPublishActivity.this,
                    data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
        }
    }
}
