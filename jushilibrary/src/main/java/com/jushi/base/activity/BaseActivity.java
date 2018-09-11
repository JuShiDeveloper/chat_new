package com.jushi.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author WYF
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initResource();
        initWidget();
        saveData(savedInstanceState);
    }

    protected abstract void setContentView();

    protected abstract void initWidget();

    protected abstract void initResource();

    protected void saveData(Bundle savedInstanceState) {

    }
}
