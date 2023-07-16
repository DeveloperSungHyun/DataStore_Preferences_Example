package com.example.datastore.datastore;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class DataStoreManager {

    public static final DataStoreManager instance = new DataStoreManager();

    private static final Handler dataStoreManagerHandler = new Handler(Looper.getMainLooper());

    private DataStoreManager() {}

    private RxDataStore<Preferences> dataStore;

    public void init(Context context){
        dataStore = new RxPreferenceDataStoreBuilder(context, "test").build();
    }

    public void saveValue(String keyName, int Value){
        Preferences.Key<Integer> key = new Preferences.Key<>(keyName);

        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();

            saveValue(keyName, Value);

            mutablePreferences.set(key, Value);
            return Single.just(mutablePreferences);
        }).subscribe();

    }

    public void saveValue(String keyName, float Value){
        Preferences.Key<Float> key = new Preferences.Key<>(keyName);

        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();

            saveValue(keyName, Value);

            mutablePreferences.set(key, Value);
            return Single.just(mutablePreferences);
        }).subscribe();
    }

    public void saveValue(String keyName, boolean Value){
        Preferences.Key<Boolean> key = new Preferences.Key<>(keyName);

        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();

            saveValue(keyName, Value);

            mutablePreferences.set(key, Value);
            return Single.just(mutablePreferences);
        }).subscribe();
    }

    public void saveValue(String keyName, String Value){
        Preferences.Key<String> key = new Preferences.Key<>(keyName);

        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            String currentKey = preferences.get(key);

            if(currentKey == null){
                saveValue(keyName, Value);
            }

            mutablePreferences.set(key, currentKey != null ? Value : "");
            return Single.just(mutablePreferences);
        }).subscribe();
    }

    public void getIntegerValue(String keyName, IntegerValueDelegate integerValueDelegate) {
        Preferences.Key<Integer> key = new Preferences.Key<>(keyName);


        dataStore.data().map(preferences -> preferences.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        dataStoreManagerHandler.post(() -> integerValueDelegate.onGetValue(integer));
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getFloatValue(String keyName, FloatValueDelegate floatValueDelegate){
        Preferences.Key<Float> key = new Preferences.Key<>(keyName);

        dataStore.data().map(preferences -> preferences.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Float>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Float aFloat) {
                        dataStoreManagerHandler.post(() -> floatValueDelegate.onGetValue(aFloat));

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getBooleanValue(String keyName, BooleanValueDelegate booleanValueDelegate){
        Preferences.Key<Boolean> key = new Preferences.Key<>(keyName);

        dataStore.data().map(preferences -> preferences.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Boolean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        dataStoreManagerHandler.post(() -> booleanValueDelegate.onGetValue(aBoolean));

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getStringValue(String keyName, StringValueDelegate stringValueDelegate){
        Preferences.Key<String> key = new Preferences.Key<>(keyName);

        dataStore.data().map(preferences -> preferences.get(key))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        dataStoreManagerHandler.post(() -> stringValueDelegate.onGetValue(s));

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface IntegerValueDelegate {
        void onGetValue(int i);
    }

    public interface FloatValueDelegate {
        void onGetValue(float f);
    }

    public interface BooleanValueDelegate {
        void onGetValue(boolean b);
    }


    interface StringValueDelegate {
        void onGetValue(String s);
    }
}
