package com.example.test_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;

public class map_flatMap_debounce_filter extends AppCompatActivity {
    private static final String TAG = "Map_FlatMap";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_flat_map_debounce_filter);

        editText = findViewById(R.id.EditText);

        //Map بيعمل عامله علي الحاجه قبل متخرج  
        //debounce بتاخر اللود شويه
        //distinctUntilChanged علشان مجبش حاجه انا لسه جيبها
        //filter علشان افلتر بكتب جواه كلمه ولو هو شفها مش بيحيب الحاجه دي
        //flatMap دي بتاخد اوبزرفابل و اوبجيكت دي علشان اطلع الداتا قبل متروح للدون استريم و منغير اوبرزفابل
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() != 0)
                            emitter.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }).doOnNext(c -> Log.d(TAG, "SS UpStream: " + c))
                .map((Function<Object, Object>) o -> Integer.parseInt(o.toString()) * 2)
                .debounce(2, TimeUnit.SECONDS)
                .filter(c -> c.toString().equals("555"))
                .distinctUntilChanged()
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Throwable {
                        return SendDataToAPI(o.toString());
                    }
                })
                .subscribe(s -> Log.d(TAG, "SS downStream: " + s))
        ;
    }

    public Observable SendDataToAPI(String s) {
        Observable observable = Observable.just("SS Calling Api 1 to send" + s);
        observable.subscribe(c -> Log.d(TAG, "SS SendDataToAPI: " + s));

        return observable;
    }
}