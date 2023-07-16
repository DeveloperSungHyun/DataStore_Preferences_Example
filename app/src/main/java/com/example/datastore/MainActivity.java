package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.datastore.datastore.DataStoreManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataStoreManager.instance.init(getApplicationContext());

        DataStoreManager.instance.saveValue("Name", 555);

        DataStoreManager.instance.getIntegerValue("Name", i1 -> {
            Log.d("=================", "" + i1);
        });
    }
}
