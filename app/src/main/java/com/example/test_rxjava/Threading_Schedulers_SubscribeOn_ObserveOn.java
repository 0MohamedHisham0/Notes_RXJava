package com.example.test_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Threading_Schedulers_SubscribeOn_ObserveOn extends AppCompatActivity {
    private Button button;
    private static final String TAG = "Mohamed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threading__schedulers__subscribe_on__observe_on);
        button = findViewById(R.id.button3);

        button.setOnClickListener(v -> {
            startActivity(new Intent(Threading_Schedulers_SubscribeOn_ObserveOn.this, map_flatMap_debounce_filter.class));
        });
        //ThreadBool دا مكان اللي بجيب منو ال ثريد بتعتي (Schedulers > متوصله ب threadBool)
        //1 - Schedulers.io() can open any number of thread >> Because it's used in simple status so if this status take a lot of time it will reduce performance so you can use another one
        //2 - Schedulers.computation() it used in big status like render video or resize image, it's open limit number of Thread depending on your cores.
        //3 - Schedulers.newThread() every new task open new thread it's not worth to use
        //4 - AndroidSchedulers.mainThread() work on main thread
        //5 - Schedulers.trampoline() fifo trampoline >> single tasks
        //Tread.currentTread().getName()


        //Make UpStream and down work on the same Thread
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .doOnNext(c -> Log.d(TAG, "SS UpStream: " + Thread.currentThread().getName()))
                .subscribe(o -> Log.d(TAG, "SS DownStream: " + Thread.currentThread().getName()));

        //Make UpStream and down work on different Thread
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .doOnNext(c -> Log.d(TAG, "SS UpStream: " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation()) // Make down stream in different Thread
                .subscribe(o -> Log.d(TAG, "SS DownStream: " + Thread.currentThread().getName()));

        //subscribeOn vs observeOn
        //sub use to change both up and down Stream /ob in only down
        //sub can change it's place and if you use more than one the first only use and others are dismiss 
        //ob can't change it's place so the operators under ob1 as example it use ob1(schedulers) and if we but another one like ob2 it's under operators use it
    }
}