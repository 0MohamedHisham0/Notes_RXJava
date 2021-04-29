package com.example.test_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class Observer_CreationOperations extends AppCompatActivity {
    private static final String TAG = "Observer";
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer__creation_operatiors);

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Observer_CreationOperations.this, Threading_Schedulers_SubscribeOn_ObserveOn.class));
            }
        });

        //upStream Observable give observer .doOnNext
        //downStream observer listen to observable .OnNext

        //Simplest way to create observer في كل حاجه
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        //SingleObserver مهتم بمعلومه واحده وبيرجعها في (o)
        SingleObserver singleObserver = new SingleObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        };

        //MaybeObserver معلومه واحده برجو بس في
        // onComplete علشان الاستريم
        MaybeObserver maybeObserver = new MaybeObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Object o) {
                //byrg3 al m3loma hna fe (o)
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //CompletableObserver اكتملت او لا بس
        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        };

        //Operator to create observable
        //like intervalRange()

        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                for (int i = 0; i < 5; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        });
        observable.subscribe(observer);


        //ObservableJust take only 10 item
        Observable observableJust = Observable.just(1, 2, 3, 4);
        observableJust.subscribe(observer);

        List<String> list = new ArrayList<>();
        list.add("Mo7a");
        list.add("Moksha");
        list.add("Mard");

        //FromArray repeat for repeat xD
        Observable observableFromArray = Observable.fromArray(list).repeat(2);
        observableFromArray.subscribe(observer);

        //range start and count
        Observable observableRange = Observable.range(0, 5);
        observableRange.subscribe(observer);



    }
}