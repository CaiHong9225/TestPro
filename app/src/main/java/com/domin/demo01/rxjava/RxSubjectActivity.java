package com.domin.demo01.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
/**
 * Subject继承了Observable，又实现了Observer接口，
 * 所以说它既是Observable又是Observer，完全合理。
 * 从实际应用上讲，Subject也能实现Observable和Observer相同的功能
 */
public class RxSubjectActivity extends AppCompatActivity {

    //subject 是Observable和Observer之间的桥梁
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //Observer会接收AsyncSubject的`onComplete()之前的最后一个数据，如果因异常而终止，AsyncSubject将不会释放任何数据，但是会向Observer传递一个异常通知
    private void asyncSubject(){
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("asy1");
        asyncSubject.onNext("asy2");
        asyncSubject.onNext("asy3");
        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    //收到被订阅前最后一个数据 behaviorSubject2，如果被订阅前没有收到任何数据，就收到默认数据 "default"
    private void behaviorSubject(){
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("default");
        behaviorSubject.onNext("behaviorSubject1");
        behaviorSubject.onNext("behaviorSubject2");
        behaviorSubject.subscribe(new Observer<String>() {

            @Override
            public void onError(Throwable e) {

//                LogUtil.log("behaviorSubject:error");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(String s) {

//                Log.i("behaviorSubject:"+s);
            }
        });

        behaviorSubject.onNext("behaviorSubject3");
        behaviorSubject.onNext("behaviorSubject4");
        //behaviorSubject2  behaviorSubject3 behaviorSubject4
    }
    //PublishSubject的Observer只接受被订阅之后发送的数据
    private void publishSubject(){
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.onNext("publishSubject1");
        publishSubject.onNext("publishSubject2");
        publishSubject.onNext("publishSubject3");
        publishSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        publishSubject.onNext("11111111111");
        publishSubject.onNext("22222222222");
        //只接收到 1111111111111 22222222222
    }
    //默认会发送所有数据给订阅者
    private void replaySubject(){
        ReplaySubject<String>  replaySubject = ReplaySubject.create();
        //缓存订阅5s前和2条数据
        ReplaySubject.createWithTimeAndSize(5, TimeUnit.SECONDS, Schedulers.computation(),2);
        //replaySubject=ReplaySubject.createWithTime(1,TimeUnit.SECONDS,Schedulers.computation());  //replaySubject被订阅前的前1秒内发送的数据才能被接收
        replaySubject.onNext("replaySubject:pre1");
        replaySubject.onNext("replaySubject:pre2");
        replaySubject.onNext("replaySubject:pre3");
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {

            }

        });
        replaySubject.onNext("replaySubject:after1");
        replaySubject.onNext("replaySubject:after2");
        //收到 replaySubject:pre2 replaySubject:pre3 replaySubject:after1 replaySubject:after2
    }
    private void doTest1(){
        PublishSubject<String> publishSubject=PublishSubject.create();
        publishSubject.onNext("as Observale");
        publishSubject.onComplete();

        //or
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("这个");
                e.onComplete();
            }
        });
    }
    private void doTest2(){
        PublishSubject<String> publishSubject = PublishSubject.create();
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("adasdadas");
                e.onComplete();
            }
        }).subscribe(publishSubject);
        publishSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i("ada","qiaoliang");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
