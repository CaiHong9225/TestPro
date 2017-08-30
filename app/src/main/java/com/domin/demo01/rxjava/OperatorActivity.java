package com.domin.demo01.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.domin.demo01.recylerview.Person;
import com.domin.demo01.utils.ToastUtils;
import com.domin.demo01.widget.GangedRecyclerview.SortBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //subscribeOn( )决定了发射数据在哪个调度器上执行，
    // observeOn(AndroidSchedulers.mainThread())则指定数据接收发生在UI线程
    private void test01() {
        Observable.create(new ObservableOnSubscribe<Person>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Person> e) throws Exception {
                Person p = null;
                //从数据库取出海量数据
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Person>() {
            @Override
            public void accept(@NonNull Person person) throws Exception {

            }
        });
    }

    private void Map01() {
        Observable.just("path").map(new Function<String, Bitmap>() {

            @Override
            public Bitmap apply(@NonNull String s) throws Exception {
                //路径指定图片转换成bitmap

                return BitmapFactory.decodeFile(s);
            }
        }).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(@NonNull Bitmap bitmap) throws Exception {
                //拿到bitmap到 控件上显示
            }
        });
    }
    //map只能完成一对一的关系
    private void map02(){
        Observable.just("123456").map(new Function<String, String>() {

            @Override
            public String apply(@NonNull String s) throws Exception {
                //取出前几个字节
                return s.substring(0,4);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }
    private void flatMap01(){
        List<SortBean> list = new ArrayList<>();

        Observable.fromIterable(list).flatMap(new Function<SortBean, ObservableSource<SortBean.CategoryOneArrayBean>>() {
            @Override
            public ObservableSource<SortBean.CategoryOneArrayBean> apply(@NonNull SortBean sortBean) throws Exception {
                return Observable.fromIterable(sortBean.getCategoryOneArray());
            }
        }).subscribe(new Consumer<SortBean.CategoryOneArrayBean>() {
            @Override
            public void accept(@NonNull SortBean.CategoryOneArrayBean categoryOneArrayBean) throws Exception {
                ToastUtils.showShortToast(categoryOneArrayBean.getName()+"");
            }
        });
    }
    private void buffer01(){
        Observable.just(1,2,3).buffer(2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(@NonNull List<Integer> integers) throws Exception {
                Log.i("Op","size :"+integers.size());
            }
        });
        //输出结果： 2,1
    }
    private void buffer02(){
        List<Person> list = new ArrayList<>();
        Observable.fromIterable(list).map(new Function<Person, Person>() {

            @Override
            public Person apply(@NonNull Person person) throws Exception {
                person.setName("可以");
                return person;
            }
        }).buffer(list.size()).subscribe(new Consumer<List<Person>>() {
            @Override
            public void accept(@NonNull List<Person> persons) throws Exception {

            }
        });
    }

}
