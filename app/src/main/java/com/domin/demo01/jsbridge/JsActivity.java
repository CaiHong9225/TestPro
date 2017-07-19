package com.domin.demo01.jsbridge;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.domin.demo01.BaseActivity;
import com.domin.demo01.R;
import com.domin.demo01.utils.ToastUtils;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;


public class JsActivity extends BaseActivity {

    private TextInputLayout mInputLayout;
    private TextView tv_search;
    private EditText mEditText;
    private String content="";
    private LinearLayout mLinearLayout_webview;
    private  AgentWeb mAgentWeb;
    private WebView mWebView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_js;
    }

    @Override
    protected void initViews() {
/*        mInputLayout =findView(R.id.textinputlayout);
        mEditText =findView(R.id.edittext);
        tv_search =findView(R.id.text_seach);*/
        mLinearLayout_webview=findView(R.id.content_webview);
        //setOnClick(tv_search);
      //  setOnClick(mEditText);
/*        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().startsWith("www"))
                {
                    mInputLayout.setErrorEnabled(false);

                }else
                {
                    mInputLayout.setError("格式输入错误");
                    mInputLayout.setErrorEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
  /*      mWebView =findView(R.id.bridge_webview);
        mWebView.setWebViewClient(new WebViewClient());*/
       // mWebView.setWebChromeClient(new WebChromeClient());
       mAgentWeb=AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout_webview,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()//使用默认进度条
                .defaultProgressBarColor()//默认进度条颜色
                .setReceivedTitleCallback(mReceivedTitleCallback)//设置页面的title回调
                .createAgentWeb()
                .ready()
                .go("http://www.jd.com");
        mWebView = mAgentWeb.getWebCreator().get();

//        AgentWeb.AgentBuilder agentBuilder = new AgentWeb.AgentBuilder(JsActivity.this);
    }

    ChromeClientCallbackManager.ReceivedTitleCallback mReceivedTitleCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            toolbar.setTitle(title);
        }
    };

    /**
     * android端调用js方法
     * @param methodName
     */
    private void queryJs(String methodName)
    {
        mAgentWeb.getJsEntraceAccess().quickCallJs(methodName);

    }

    /**
     * js调用android native代码
     * @param jsCallMethod
     */
    private void callAndroid(String jsCallMethod)
    {
        mAgentWeb.getJsInterfaceHolder().addJavaObject(jsCallMethod,new AndroidInterface(mAgentWeb,this));
//        mAgentWeb.getJsInterfaceHolder().addJavaObjects()
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected void processClick(View v) {
 /*           switch (v.getId())
            {
                case R.id.text_seach:
                    Log.i(TAG,"content :"+"a");
                    content= mInputLayout.getEditText().getText().toString();
                    Log.i(TAG,"content :"+content);
                    mWebView.loadUrl("http://"+content);
                    break;
            }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mAgentWeb.handleKeyEvent(keyCode,event)){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.web_reload)
        {
            mWebView.reload();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
       mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();

    }
}
