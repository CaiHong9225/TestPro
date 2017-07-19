package com.domin.demo01.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.domin.demo01.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        Observable<String> observable = new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {


            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
