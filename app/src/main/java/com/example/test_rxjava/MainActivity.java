package com.example.test_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Observer_CreationOperations.class));
            }
        });

        //Cold Observer Al meza 2no msh bysht8al mn8er student
        //Observable (doctor) should have Observer (student) to start
        // Ex bt3 al Doctor(Observable) w hana da bard > cold ... and student(subscribe)
        // he3ed lw gded 7ad d5l
        Observable<Long> cold = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS);
        cold.subscribe(i -> Log.d(TAG, "cold S1: " + i));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cold.subscribe(i -> Log.d(TAG, "cold S2: " + i));

        //Hot Observer
        //Observable (doctor) shouldn't have Observer to start (student)
        //First way to Use Hot is to convert to hot form cold With this ( ConnectableObservable .publish() - .connect(); )
        // Ex bt3 al Doctor(Observable) w hana da Hot ... and student(subscribe) msh
        // he3ed lw 7ad d5l.
        // .publish() - .connect();
        ConnectableObservable<Long> Hot = ConnectableObservable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS).publish();
        Hot.connect();

        Hot.subscribe(i -> Log.d(TAG, "Hot S1: " + i));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Hot.subscribe(i -> Log.d(TAG, "Hot S2: " + i));

        //PublishSubject
        //Second Way to use hot is to make hot form start
        //called Hot Observable or subject
        //Hana al student byotlb ano al doctor y3ed aw la
        //Law al doc(observer) 3ad yb2a al student(subscribe) called PublishSubject
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribeOn(Schedulers.io());

        subject.subscribe(i -> Log.d(TAG, "subject S1: " + i));

        subject.onNext("A");
        sleep(1000);
        subject.onNext("B");
        sleep(1000);
        subject.onNext("C");
        sleep(1000);
        subject.onNext("D");
        sleep(1000);

        subject.subscribe(i -> Log.d(TAG, "subject S2: " + i));

        subject.onNext("E");
        sleep(1000);
        subject.onNext("f");
        sleep(1000);
        subject.onNext("g");

        //BehaviorSubject
        //Han al student 3ayz ysm3 25er m3loma 2bl d5olo bs
        BehaviorSubject<String> subject_behavior = BehaviorSubject.create();
        subject_behavior.subscribeOn(Schedulers.io());

        subject_behavior.subscribe(i -> Log.d(TAG, "subject_behavior S1: " + i));

        subject_behavior.onNext("A");
        sleep(1000);
        subject_behavior.onNext("B");
        sleep(1000);
        subject_behavior.onNext("C");
        sleep(1000);
        subject_behavior.onNext("D");
        sleep(1000);

        subject_behavior.subscribe(i -> Log.d(TAG, "subject_behavior S2: " + i));

        subject_behavior.onNext("E");
        sleep(1000);
        subject_behavior.onNext("f");
        sleep(1000);
        subject_behavior.onNext("g");

        //ReplaySubject
        //hna al doc be3d kol haga tany for new student w b3den by kml shr7

        ReplaySubject<String> subject_replay = ReplaySubject.create();

        subject_replay.subscribeOn(Schedulers.io());

        subject_replay.subscribe(i -> Log.d(TAG, "subject_replay S1: " + i));

        subject_replay.onNext("A");
        sleep(1000);
        subject_replay.onNext("B");
        sleep(1000);
        subject_replay.onNext("C");
        sleep(1000);
        subject_replay.onNext("D");
        sleep(1000);

        subject_replay.subscribe(i -> Log.d(TAG, "subject_replay S2: " + i));

        subject_replay.onNext("E");
        sleep(1000);
        subject_replay.onNext("f");
        sleep(1000);
        subject_replay.onNext("g");

        //AsyncSubject AlRowsh (async_subject.onComplete();) 3alshan y3rf 2no 5als
        //hana la student msh hysm3 2y haga 8er 25ar gmal
        AsyncSubject<String> async_subject = AsyncSubject.create();
        async_subject.subscribeOn(Schedulers.io());

        async_subject.subscribe(i -> Log.d(TAG, "async_subject S1: " + i));

        async_subject.onNext("A");
        sleep(1000);
        async_subject.onNext("B");
        sleep(1000);
        async_subject.onNext("C");
        sleep(1000);
        async_subject.onNext("D");
        sleep(1000);

        async_subject.subscribe(i -> Log.d(TAG, "async_subject S2: " + i));

        async_subject.onNext("E");
        sleep(1000);
        async_subject.onNext("f");
        sleep(1000);
        async_subject.onNext("g");
        async_subject.onComplete();
    }

    public void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}