package com.domin.demo01.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.domin.demo01.R;
import com.domin.demo01.recylerview.Person;
import com.domin.demo01.utils.ToastUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.FutureObserver;
import io.reactivex.internal.operators.maybe.MaybeFromAction;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

public class RxActivity extends AppCompatActivity {

    private Observable<String> mStringObservable;
    private Observable<String> mJust;
    private Observable<List<String>> mListObservable;
    private Observable<String> mDefer;
    private Observable<Long> mInterval;
    private Observable<Integer> mRange;
    private Observable<String> mRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

    }

    private void t1() {
        mStringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

            }
        });
    }

    //一次发送 just1，just2
    private void just() {
        mJust = Observable.just("just1", "just2");
        mJust.doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }

    private void from() {
        //from 会遍历整个list
        List<String> list = new ArrayList<>();
        list.add("f1");
        list.add("f2");
        mListObservable = Observable.fromArray(list);
        mListObservable.doOnNext(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> list) throws Exception {

            }
        });
    }

    //y有观察者订阅时创建Observable,并且为每个观察者创建新的Observable
    private void defer() {
        mDefer = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just("defer");
            }
        });
        mDefer.doOnNext(new Consumer<String>() {

            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }

    //按照时间间隔发射整数序列
    private void interval() {
        mInterval = Observable.interval(1, TimeUnit.SECONDS).fromArray();
        mInterval.doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                ToastUtils.showShortToast(" " + aLong);
            }
        });
    }

    //发射特定整数序列的Obervable
    private void range() {
        mRange = Observable.range(10, 5);
    }

    private void timer() {
        Observable.timer(5, TimeUnit.SECONDS);//3s延迟之后发射第一个数值
    }

    //重复发送特定数据多少次
    private void repeat() {
        mRepeat = Observable.just("和呃呃").repeat(3);
        Observer<String> mObserver = new Observer<String>() {
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
        };
        mRepeat.subscribe(mObserver);
        //Consumer 代替Action1
        mRepeat.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }
    //Function 代替Fun1...    有返回值
    //Cusumer代替Action1.... 无返回值

    /**
     * 从数据库取出姓名为小明父亲的用户
     */
    private void doTest() {
        Observable.create(new ObservableOnSubscribe<List<Person>>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Person>> e) throws Exception {
                List<Person> personList = null;
                //从数据库获取data 赋值给list
                e.onNext(personList);
            }
        }).flatMap(new Function<List<Person>, ObservableSource<Person>>() {
            @Override
            public ObservableSource<Person> apply(@NonNull List<Person> persons) throws Exception {
                for (Person person : persons) {
                    if (person.getName().equals("小明")) {
                        person.setName("全是小明");
                    }
                }
                return Observable.fromIterable(persons);
            }
            //过滤
        }).filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getName().equals("小明");
            }
        }).map(new Function<Person, Person>() {
            @Override
            public Person apply(@NonNull Person person) throws Exception {
                //根据person查出 父亲
                Person pe_f = new Person();
                return pe_f;
            }
        }).subscribe(new Consumer<Person>() {

                    @Override
                    public void accept(@NonNull Person person) throws Exception {
                        //得到小明数据
                        ToastUtils.showShortToast("嘿，我就是小明");
                    }
                });
    }

}
